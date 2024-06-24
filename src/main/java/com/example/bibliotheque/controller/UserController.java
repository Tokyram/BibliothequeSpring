package com.example.bibliotheque.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.bibliotheque.bean.Emprunt;
import com.example.bibliotheque.bean.Livre;
import com.example.bibliotheque.bean.Utilisateur;
import com.example.bibliotheque.service.EmpruntService;
import com.example.bibliotheque.service.LivreService;
import com.example.bibliotheque.service.RenduService;
import com.example.bibliotheque.service.SanctionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    LivreService livreService;
    @Autowired
    EmpruntService empruntService;
    @Autowired
    RenduService renduService;
    @Autowired
    SanctionService sanctionService;

    @GetMapping("/livre")
    public String livre(Model model,RedirectAttributes redirectAttributes){
        if (model.containsAttribute("error")) {
            String message = (String) model.getAttribute("error");
            model.addAttribute("error", message);
            System.out.println(message);
        }
        
        try{
            List<Livre> liste = livreService.getListeLivreDispo();
            model.addAttribute("livres", liste);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "user/livre";
    }

    @GetMapping("/lecture")
    public String lecture(Model model,HttpSession session){
        List<String> lecture = (List<String>) session.getAttribute("livres");
        List<Livre> livres = new ArrayList<Livre>();

        try{
            if(lecture !=  null){
                for(var livre : lecture){
                    System.out.println(livre);
                    livres.add(livreService.findLivreById(livre));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }


        model.addAttribute("livres", livres);

        return "user/lecture";
    }

    @GetMapping("/lecture_place")
    public RedirectView lectureSurPlace(@RequestParam("id") String idLivre,HttpSession session,RedirectAttributes redirectAttributes){
        List<String> lecture = (List<String>) session.getAttribute("livres");
        String idUtilisateur = (String) session.getAttribute("idUtilisateur");
        try{
            if(lecture != null){
                if(lecture.contains(idLivre)){
                    System.out.println("Livre non ajouté : " + idLivre);
                    return new RedirectView("/livre");
                }else{
                    if(livreService.peutLireLivre(idUtilisateur, idLivre)){
                        lecture.add(idLivre);                    
                        System.out.println("Livre ajouté : " + idLivre);
                        return new RedirectView("/livre");
                    }
                }
            }else{
                if(livreService.peutLireLivre(idUtilisateur, idLivre)){
                    lecture = new ArrayList<String>();
                    lecture.add(idLivre);
                    System.out.println("Livre ajouté : " + idLivre);
                }
            }
    
            session.setAttribute("livres", lecture);
            return new RedirectView("/livre");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return new RedirectView("/livre");
        }

    }



    @GetMapping("/testLivre")
    public void testLivre(HttpSession session){
        List<String> lecture = (List<String>) session.getAttribute("livres");
        if(lecture != null){
            for(var livre : lecture){
                System.out.println(livre);
            }
        }
    }

    @GetMapping("/emprunt")
    public String emprunt(@RequestParam(name = "livre",required = false)String[] livres,HttpSession session,Model model){
        List<String> emprunt = (List<String>) session.getAttribute("emprunt");
        List<Livre> listeLivre = new ArrayList<Livre>();

        if (model.containsAttribute("error")) {
            String message = (String) model.getAttribute("error");
            model.addAttribute("error", message);
            System.out.println(message);
        }

        try{
            if(livres != null){
                List<String> liste = new ArrayList<String>();
                
                for(int i = 0;i < livres.length ; i++){
                    liste.add(livres[i]);
                    listeLivre.add(livreService.findLivreById(livres[i]));
                }
    
                if(emprunt != null){
                    session.setAttribute("emprunt", liste);
                }else{
                    emprunt = new ArrayList<String>();
                    session.setAttribute("emprunt", liste);
                }
            }
            model.addAttribute("emprunt", listeLivre);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "user/emprunt";
    }

    @GetMapping("/rendu")
    public String rendu(@RequestParam(name = "livre",required = false)String[] livres,@RequestParam(name = "emprunt")String idEmprunt,HttpSession session,Model model){
        List<String> rendu = (List<String>) session.getAttribute("rendu");
        List<Livre> listeLivre = new ArrayList<Livre>();

        if (model.containsAttribute("error")) {
            String message = (String) model.getAttribute("error");
            model.addAttribute("error", message);
            System.out.println(message);
        }

        try{
            if(livres != null){
                List<String> liste = new ArrayList<String>();
    
                for(int i=0;i< livres.length ; i++){
                    liste.add(livres[i]);
                    listeLivre.add(livreService.findLivreById(livres[i]));
                }
    
                if(rendu != null){
                    session.setAttribute("rendu", liste);
                }else{
                    rendu = new ArrayList<String>();
                    session.setAttribute("rendu", liste);
                }
            }
            model.addAttribute("rendu", listeLivre);
            model.addAttribute("emprunt", idEmprunt);
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }


        return "user/rendu";
    }


    @PostMapping("/emprunter")
    public RedirectView emprunter(@RequestParam(name = "date") String date,HttpSession session,RedirectAttributes redirectAttributes){
        try{
            String idUtilisateur = (String)session.getAttribute("idUtilisateur");
            List<String> emprunt = (List<String>) session.getAttribute("emprunt");
            String[] tabEmprunt = new String[emprunt.size()];
    
            int index = 0;
            for(String emp : emprunt){
                tabEmprunt[index] = emp;
                index ++; 
            }
    
            Date dateEmprunt = Date.valueOf(date);

            if(renduService.verifRenduLivre(idUtilisateur) == false){
                if(sanctionService.verifSanction(idUtilisateur, dateEmprunt) == false){
                    System.out.println("Tsy misy sanction");
                    empruntService.insertEmprunt(idUtilisateur, dateEmprunt, tabEmprunt);
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return new RedirectView("/emprunt");

        }
        return new RedirectView("/livre");
    }

    @PostMapping("/rendre")
    public RedirectView rendre(@RequestParam(name = "date")String date,@RequestParam(name = "emprunt")String emprunt,HttpSession session,RedirectAttributes redirectAttributes){
        try{
            String idUtilisateur = (String)session.getAttribute("idUtilisateur");
            List<String> rendu = (List<String>) session.getAttribute("rendu");
            String[] tabRendu = new String[rendu.size()];

            int index = 0;
            for(String emp : rendu){
                tabRendu[index] = emp;
                index ++; 
            }

            Date dateRendu = Date.valueOf(date);
            renduService.insertRendu(tabRendu, emprunt, dateRendu,idUtilisateur);
            sanctionService.addSanctionIfHave(emprunt, dateRendu, idUtilisateur);

        }catch(Exception e){
            // e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return new RedirectView("/livre");
        }
        return new RedirectView("/livre");

    }

    @GetMapping("/listeEmprunt")
    public String listeEmprunt(Model model,HttpSession session){
        try{
            String idUtil = (String) session.getAttribute("idUtilisateur");
            Emprunt emp = empruntService.getDernierEmpruntUtil(idUtil);
            List<Livre> liste = empruntService.getListeLivreEmprunte(idUtil,emp.getIdEmprunt());

            LocalDate dateRetour = emp.getDateRetour().toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy"); 
            String date = dateRetour.format(formatter);

            System.out.println("Emprunt : " + emp);
            model.addAttribute("emprunt", emp.getIdEmprunt());
            model.addAttribute("dateRetour", date);
            model.addAttribute("livres", liste);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "user/listeEmprunt";
    }

    @GetMapping("/user_search")
    public String rechercheLivre(Model model){
        try{
            List<Livre> livre = livreService.getListeLivreDispo();
            model.addAttribute("livres", livre);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "user/recherche";
    }



}
