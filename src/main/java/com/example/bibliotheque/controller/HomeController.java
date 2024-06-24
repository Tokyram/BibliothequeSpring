package com.example.bibliotheque.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.bibliotheque.bean.TypeMembre;
import com.example.bibliotheque.bean.Utilisateur;
import com.example.bibliotheque.service.TypeMembreService;
import com.example.bibliotheque.service.UtilisateurService;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    TypeMembreService typeMembreService;
    @Autowired
    UtilisateurService utilisateurService;


    @GetMapping("/")
    public String login(Model model){
        
        if (model.containsAttribute("error")) {
            String message = (String) model.getAttribute("error");
            model.addAttribute("error", message);
            System.out.println(message);
        }

        return "login";
    }

    @GetMapping("/inscription")
    public String inscription(Model model){
        List<TypeMembre> liste = typeMembreService.getListeType();
        model.addAttribute("type", liste);

        return "inscription";
    }

    @GetMapping("/index")
    public String index(Model model){
        String nom = (String)model.getAttribute("nom");
        String prenom = (String)model.getAttribute("prenom");
        String idUtilisateur = (String)model.getAttribute("idUtilisateur");

        model.addAttribute("nom", nom);
        model.addAttribute("prenom", prenom);
        model.addAttribute("idUtilisateur", idUtilisateur);

        return "user/index";
    }

    @RequestMapping("/logout")
    public RedirectView disconnect(HttpSession session) {
        session.invalidate();
        return new RedirectView("/");
    }
    
    @GetMapping("/user_login")
    public RedirectView userLogin(@RequestParam("identifiant") String id, RedirectAttributes redirectAttributes,HttpSession session){
        if(id.compareTo("")!=0){
            try{
                Utilisateur util = utilisateurService.loginUtilisateur(id); 
            
                if(util.getIdUtilisateur() != null){
                    System.out.println("Hita le util : " + util.getNom());
                    session.setAttribute("idUtilisateur", util.getIdUtilisateur());
    
                    return new RedirectView("/livre");
                }else{
                    System.out.println("Tsy hita");
                    redirectAttributes.addFlashAttribute("error","Identifiant introuvable");
                    return new RedirectView("/");
                }    
            }catch(Exception e){
                // 
                return new RedirectView("/");
            }

        }else{
            redirectAttributes.addFlashAttribute("error","Veuillez remplir tout les champs");
            System.out.println("tsy mety");
            return new RedirectView("/");
        }
    }

    @PostMapping("/add_user")
    public RedirectView addUser(@RequestParam(name = "nom") String nom,@RequestParam(name = "prenom") String prenom,
                                @RequestParam(name = "adresse") String adresse , @RequestParam(name = "date_naissance") String date_naissance,
                                @RequestParam(name = "date_inscription") String date_inscription,@RequestParam(name = "typeMembre",required = false) String typeMembre, RedirectAttributes redirectAttributes,HttpSession session,Model model){

        System.out.println(nom);
        System.out.println(prenom);
        System.out.println(adresse);
        System.out.println(date_naissance);
        System.out.println(date_inscription);
        System.out.println(typeMembre); 

        if(!nom.isEmpty() && !prenom.isEmpty() && !adresse.isEmpty() && !date_naissance.isEmpty() && !date_inscription.isEmpty() && !typeMembre.isEmpty()){
            try{
                Date dateNaissance = Date.valueOf(date_naissance);
                Date dateInscription = Date.valueOf(date_inscription);

                System.out.println("Date Naissance : " + dateNaissance);

                System.out.println("Okay");
                Utilisateur util = utilisateurService.insertUtilisateur(nom, prenom, dateNaissance, adresse, typeMembre, dateInscription);
                


                redirectAttributes.addFlashAttribute("nom",util.getNom());
                redirectAttributes.addFlashAttribute("prenom",util.getPrenom());
                redirectAttributes.addFlashAttribute("idUtilisateur",util.getIdUtilisateur());

                session.setAttribute("idUtilisateur",util.getIdUtilisateur());
                
                return new RedirectView("/index");
            }catch(Exception e){
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error",e.getMessage());
                return new RedirectView("/inscription");
            }
        }else{
            redirectAttributes.addFlashAttribute("error","Veuillez remplir tout les champs");
            return new RedirectView("/inscription");
        }
    
    }


    @GetMapping("/test")
    public void test(HttpSession session){

        try{
            System.out.println( session.getAttribute("idUtilisateur"));
           
            // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // java.util.Date utilDate = dateFormat.parse("2024-05-24");
    
            // Date dateEmprunt = new Date(utilDate.getTime());  
       
            // Calendar calendar = Calendar.getInstance(); 
            // calendar.setTime(dateEmprunt);

            // int jourAjouter = 10;

            // calendar.add(Calendar.DAY_OF_MONTH, jourAjouter );
            // Date dt = new Date(calendar.getTimeInMillis());
    
            // System.out.println("Date rendu : " + dt);

            // System.out.println("Age years : " + ageYears);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
