package com.example.bibliotheque.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.bibliotheque.bean.Categorie;
import com.example.bibliotheque.bean.DBCPDataSource;
import com.example.bibliotheque.bean.Emprunt;
import com.example.bibliotheque.bean.EmpruntNonRendu;
import com.example.bibliotheque.bean.Livre;
import com.example.bibliotheque.bean.Modele;
import com.example.bibliotheque.bean.Sanction;
import com.example.bibliotheque.bean.TypeMembre;
import com.example.bibliotheque.bean.Utilisateur;
import com.example.bibliotheque.service.EmpruntService;
import com.example.bibliotheque.service.LivreService;
import com.example.bibliotheque.service.ModeleService;
import com.example.bibliotheque.service.TypeMembreService;
import com.example.bibliotheque.service.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.pattern.Util;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    LivreService livreService;

    @Autowired
    TypeMembreService typeMembreService;

    @Autowired
    EmpruntService empruntService;

    @Autowired
    ModeleService modeleService;

    @GetMapping("/admin")
    public String admin(Model model){
        
        if (model.containsAttribute("error")) {
            String message = (String) model.getAttribute("error");
            model.addAttribute("error", message);
            System.out.println(message);
        }



        return "admin/login";
    }


    @PostMapping("/admin_login")
    public RedirectView verifAdmin(@RequestParam(name="nom") String nom,@RequestParam(name = "identifiant") String identifiant,HttpSession session,RedirectAttributes redirectAttributes){

        if(nom.compareTo("") != 0 && identifiant.compareTo("") != 0){
            try{
                Utilisateur util = utilisateurService.loginAdmin(identifiant, nom);

                if(util.getIsAdmin() == 1){
                    session.setAttribute("idUtilisateur", util.getIdUtilisateur());
                    return new RedirectView("/dashboard"); 
                }else{
                    redirectAttributes.addFlashAttribute("error","Nom ou identifiant incorrect");
                    return new RedirectView("/admin");
                }
            }catch(Exception e){
                redirectAttributes.addFlashAttribute("error",e.getMessage());
                return new RedirectView("/admin");
            }

        }else{
            redirectAttributes.addFlashAttribute("error","Veuillez remplir tout les champs");
            return new RedirectView("/admin");
        }

    }

    @GetMapping("/livreAjout")
    public String ajout(Model model,@RequestParam(name = "id")String idModele){

        if (model.containsAttribute("error")) {
            String message = (String) model.getAttribute("error");
            model.addAttribute("error", message);
            System.out.println(message);
        }

        if (model.containsAttribute("success")) {
            String message = (String) model.getAttribute("success");
            model.addAttribute("success", message);
            System.out.println(message);
        }

        try{
            List<Categorie> liste = livreService.getListeCategorie();
            List<TypeMembre> membre = typeMembreService.getListeType();

            model.addAttribute("categorie", liste);
            model.addAttribute("membre", membre);
            model.addAttribute("idModele", idModele);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "admin/ajoutLivre";
    }


    @PostMapping("/add_book")
    public RedirectView addBook(@RequestParam(name = "file",required = false)MultipartFile file,@RequestParam(name="titre")String titre,
                                @RequestParam(name = "auteur")String auteur,@RequestParam(name="age") String age,@RequestParam(name = "edition")String edition,
                                @RequestParam(name = "date_edition")String dateEdition, @RequestParam(name = "page")String page , @RequestParam(name="collection")String collection,
                                @RequestParam(name = "langue")String langue,@RequestParam(name = "theme")String theme,@RequestParam(name="lecture",required = false)String[] lecture,
                                @RequestParam(name = "emprunt",required = false)String[] emprunt , RedirectAttributes redirectAttributes,@RequestParam(name="resume")String resume ,
                                @RequestParam(name="categorie",required = false)String[] categorie , @RequestParam(name = "idModele")String idModele)
    {
        if(!file.isEmpty() && titre.compareTo("")!= 0 && auteur.compareTo("") != 0 && age.compareTo("") != 0 && edition.compareTo("") != 0 
            && dateEdition.compareTo("") != 0 && page.compareTo("") != 0 && collection.compareTo("") != 0 && langue.compareTo("") != 0 
            && theme.compareTo("") != 0 && lecture.length != 0 && emprunt.length != 0 && resume.compareTo("") != 0
        ){

            try{
                byte[] bytes = file.getBytes();    
                String folder = "src/main/webapp/assets/upload/";
                Path path = Paths.get(folder + file.getOriginalFilename());
                Files.write(path, bytes);
                
                Date date = Date.valueOf(dateEdition);
                int nbPage = Integer.parseInt(page);
                int ageLimite = Integer.parseInt(age);


                Livre livre = new Livre(edition, date, titre, auteur, collection, resume, nbPage, theme, file.getOriginalFilename(), ageLimite,langue);
                String idLivre = livreService.insertLivre(livre,categorie,idModele);

                System.out.println("ID Livre Controller : " + idLivre);

                livreService.insertCategorie(categorie, idLivre);
                livreService.insertEmpruntAutorise(emprunt, idLivre);
                livreService.insertLectureAutorise(lecture, idLivre);
                
                redirectAttributes.addFlashAttribute("success","Livre inserer avec success");
                return new RedirectView("/livreAjout?id="+ idModele);

            }catch(Exception e){
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error",e.getMessage());
                return new RedirectView("/livreAjout?id="+ idModele);
            }
            
        }else{
            redirectAttributes.addFlashAttribute("error","Veuillez remplir tout les champs");
            return new RedirectView("/livreAjout?id="+ idModele);
        }     

    }


    @GetMapping("/livreListe")
    public String liste(Model model){
        try{
            List<Livre> liste = livreService.getListeLivres();
            model.addAttribute("livres", liste);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "admin/listeLivre";
    }


    @GetMapping("/recherche")
    public String search(Model model){
        try{
            List<Categorie> liste = livreService.getListeCategorie();
            model.addAttribute("categorie", liste);    
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "admin/recherche";
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model)
    {
        try{

            List<String> annee = new Emprunt().getAllYear();
            int nbUtilisateur = new Utilisateur().getListeUtilisateur().size();
            int nbLivre = livreService.getListeLivres().size();

            model.addAttribute("utilisateur", nbUtilisateur);
            model.addAttribute("livre",nbLivre);
            model.addAttribute("annee", annee);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "admin/dashboard";        
    }

    @GetMapping("/getStat")
    public String getStat(HttpServletResponse response) throws Exception {
        List<String[]> stat = new Emprunt().getStatEmprunt();

        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(stat);

        response.setContentType("application/json");
        response.getWriter().write(jsonData);

        return null; 
    }


    @GetMapping("/getEmpruntStat")
    public String empruntStat(@RequestParam(name="year")String year,HttpServletResponse response,Model model){
        try{
            List<String[]> stat = new Emprunt().getStatEmpruntAnnee(year);

            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writeValueAsString(stat);

            response.setContentType("application/json");
            response.getWriter().write(jsonData);

        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/empruntEnCours")
    public String emprunt(Model model){
        List<Emprunt> liste = new ArrayList<Emprunt>();

        try{
            liste = empruntService.getListeEmpruntEnCours();
            model.addAttribute("emprunt", liste);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
        }

        return "admin/emprunt";
    }


    @GetMapping("/empruntNonRendu")
    public String empruntNonRendu(Model model){
        List<EmpruntNonRendu> liste = new ArrayList<EmpruntNonRendu>();
        try{
            liste = new EmpruntNonRendu().getListeEmprunt();
            model.addAttribute("emprunt", liste);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
        }

        return "admin/empruntNonRendu";
    }

    @GetMapping("/listeSanction")
    public String listeSanction(Model model){
        List<Sanction> liste = new ArrayList<Sanction>();

        try{
            liste = new Sanction().getListeSanction();
            model.addAttribute("sanction", liste);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
        }

        return "admin/sanction";
    }
    

    @GetMapping("/listeMembre")
    public String listeMembre(Model model){
        List<Utilisateur> liste = new ArrayList<Utilisateur>();
        List<TypeMembre> listeType = new ArrayList<TypeMembre>();

        try{
            liste = new Utilisateur().getListeUtilisateur();
            listeType = new TypeMembre().getAllTypeMembre();
            model.addAttribute("utilsateur", liste);
            model.addAttribute("typeMembre", listeType);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
        }

        return "admin/membre";
    }

    @GetMapping("/detail")
    public String detailEmprunt(Model model,@RequestParam(name="id")String idEmprunt){

        List<Livre> liste = new ArrayList<Livre>();

        try{
            liste = empruntService.findEmpruntById(idEmprunt).getLivreEmprunter();
            model.addAttribute("livre", liste);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "admin/detail";
    }
    

    @GetMapping("/modele")
    public String modele(Model model){

        if (model.containsAttribute("error")) {
            String message = (String) model.getAttribute("error");
            model.addAttribute("error", message);
            System.out.println(message);
        }

        if (model.containsAttribute("success")) {
            String message = (String) model.getAttribute("success");
            model.addAttribute("success", message);
            System.out.println(message);
        }

        return "admin/modele";
    }


    @PostMapping("/ajoutModele")
    public RedirectView ajoutModele(@RequestParam(name="titre")String titre,@RequestParam(name = "auteur")String auteur,
                                    @RequestParam(name="resume")String resume,RedirectAttributes redirectAttributes){

        if(!titre.isEmpty() && !auteur.isEmpty() && !resume.isEmpty()){
            try{
                modeleService.insertModele(titre, auteur, resume);
                redirectAttributes.addFlashAttribute("success","Modele enregistrée");
                return new RedirectView("/modele");
            }catch(Exception e){
                redirectAttributes.addFlashAttribute("error",e.getMessage());
                return new RedirectView("/modele");
            }
        }else{
            redirectAttributes.addFlashAttribute("error","Veuillez remplir tout les champs");
            return new RedirectView("/modele");
        }
    }

    @GetMapping("/modeleListe")
    public String modeleListe(Model model){
        List<Modele> liste = new ArrayList<Modele>();

        try{
            liste = modeleService.getAllModele(); 
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("modele", liste);
        return "admin/listeModele";
    }

    @GetMapping("/detailModele")
    public String detailModele(Model model,@RequestParam("id")String idModele){
        Modele mod = new Modele();

        try{
            mod = modeleService.findModeleById(idModele);
        }catch(Exception e){
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("modele", mod);
        return "admin/detailModele";
    }



}
