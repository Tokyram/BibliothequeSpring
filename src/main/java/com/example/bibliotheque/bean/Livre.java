package com.example.bibliotheque.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Livre {
    String idLivre;
    String livreEdition;
    Date dateEdition;
    String isbn;
    String numeroCote;
    String titre;
    String auteur;
    String livreCollection;
    
    String livreResume;
    String langue;
    int nombrePages;
    String theme;
    String couveture;
    int ageLimite;
    int disponibilite;
    List<Categorie> categorieLivre;
    List<TypeMembre> lectureAutorise;
    List<TypeMembre> empruntAutorise;
    
    public Livre(String livreEdition,Date dateEdition,String titre,String auteur,String livreCollection,String livreResume,int nbPage,String theme,String couverture,int ageLimite,String langue){
        this.setLivreEdition(livreEdition);
        this.setDateEdition(dateEdition);
        this.setTitre(titre);
        this.setAuteur(auteur);
        this.setLivreCollection(livreCollection);
        this.setLivreResume(livreResume);
        this.setNombrePages(nbPage);
        this.setTheme(theme);
        this.setCouveture(couverture);
        this.setAgeLimite(ageLimite);
        this.setLangue(langue);
    }

    public boolean verifAgePourLivre(Connection con,String idLivre,String idUtilisateur) throws Exception{
        boolean val = false;
        Livre livre = new Livre().findLivreById(con, idLivre);
        Utilisateur utilisateur = new Utilisateur().findUtilisateurById(con, idUtilisateur);

        int ageUtil = new Utilisateur().calculAge(utilisateur.getDateNaissance());

        if(ageUtil >= livre.ageLimite){
            val = true;
        }else{
            Livre nomLivre = new Livre().findLivreById(con,idLivre);

            throw new Exception("Le livre : " + nomLivre.getTitre() + " a une age limite de " + nomLivre.getAgeLimite() + " ans");
        }

        return val;
    }

    public void updateDisponibilite(Connection con,String idLivre) throws Exception{
        String sql = "SELECT DISPONIBILITE FROM LIVRE WHERE ID_LIVRE = '" + idLivre + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        int dispo = 3;
        int newdispo = 3;

        while(rs.next()){
            dispo = rs.getInt("DISPONIBILITE");
        }

        if(dispo == 0){
            newdispo = 1;
        }else if(dispo == 1){
            newdispo = 0;
        }

        statement.close();
        rs.close();

        String sql2 = "UPDATE LIVRE SET DISPONIBILITE = " + newdispo + " WHERE ID_LIVRE = '" + idLivre + "'";
        Statement state = con.createStatement();
        state.executeUpdate(sql2);
    }
    
    // public boolean peutLireSurPlace(Connection con,String idLivre,String idUtilisateur) throws Exception{
    //     boolean val = false;

    //     Utilisateur utilisateur = new Utilisateur().findUtilisateurById(con, idUtilisateur);
    //     Livre livre = new Livre().findLivreById(con, idLivre);

    //     for (TypeMembre type : livre.getLectureAutorise()) {
    //         System.out.println(type.getIdType() + " , " + type.getNomType());
    //     }
    //     System.out.println(utilisateur.getType().getIdType());

    //         if(livre.getLectureAutorise().contains(utilisateur.getType().getIdType())){
    //             System.out.println("Marine teo amin'i : " + utilisateur.getType().getIdType());
    //             val = true;
    //         }else{
    //             Livre nomLivre = new Livre().findLivreById(con,idLivre);
    //             StringBuilder empruntAut = new StringBuilder();

    //             for(var emp : nomLivre.getEmpruntAutorise()){
    //                 empruntAut.append(" " + emp.getNomType());
    //             }
    //             throw new Exception("Le livre : " + nomLivre.getTitre() + " ne peut etre lu que par " + empruntAut);
    //         }
        
    //     return val;
    // }

    public boolean peutLireSurPlace(Connection con, String idLivre, String idUtilisateur) throws Exception {
        boolean val = false;
    
        Utilisateur utilisateur = new Utilisateur().findUtilisateurById(con, idUtilisateur);
        Livre livre = new Livre().findLivreById(con, idLivre);
    
        List<String> typesAutorises = new ArrayList<>();
        for (TypeMembre type : livre.getLectureAutorise()) {
            typesAutorises.add(type.getIdType());
            System.out.println("Type autorisé : " + type.getIdType() + " , " + type.getNomType());
        }
    
        if (typesAutorises.contains(utilisateur.getType().getIdType())) {
            System.out.println("Utilisateur autorisé à lire sur place : " + utilisateur.getType().getIdType());
            val = true;
        } else {
            Livre nomLivre = livre;
            StringBuilder empruntAut = new StringBuilder();
    
            for (var emp : nomLivre.getLectureAutorise()) {
                empruntAut.append(" " + emp.getNomType() + ",");
            }
            
            throw new Exception("Le livre : " + "'" + nomLivre.getTitre() + "'" +" ne peut être lu que par " + empruntAut);
        }
    
        return val;
    }

    public List<TypeMembre> findEmpruntAutorise(Connection con,String idLivre) throws Exception{
        List<TypeMembre> liste = new ArrayList<TypeMembre>();
        TypeMembre type = new TypeMembre();

        String sql = "SELECT * FROM EMPRUNT_LIVRE_AUTORISE WHERE ID_LIVRE = '" + idLivre + "'";

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery(sql);

        while(rs.next()){
            String idType = rs.getString("TYPE_MEMBRE");
            TypeMembre val = type.findTypeById(con, idType);
            liste.add(val);
        }
        rs.close();
        state.close();
        return liste;
    }

    public void insertEmpruntAutorise(Connection con,String[] idType , String idLivre) throws Exception{
        Statement statement = con.createStatement();
        for(int i=0; i < idType.length ; i++){
            System.out.println("LECTURE" + idType[i]);

            String sql = "INSERT INTO EMPRUNT_LIVRE_AUTORISE(ID_LIVRE,TYPE_MEMBRE) VALUES ('"+ idLivre + "','" + idType[i] + "')";
            System.out.println(sql);
            statement.executeUpdate(sql);
        }
        statement.close();
    }

    public List<TypeMembre> findLectureAutorise(Connection con,String idLivre) throws Exception{
        List<TypeMembre> liste = new ArrayList<TypeMembre>();
        TypeMembre type = new TypeMembre();

        String sql = "SELECT * FROM LECTURE_LIVRE_AUTORISE WHERE ID_LIVRE = '" + idLivre + "'";

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery(sql);

        while(rs.next()){
            String idType = rs.getString("TYPE_MEMBRE");
            TypeMembre val = type.findTypeById(con, idType);
            liste.add(val);
        }
        rs.close();
        state.close();
        return liste;
    }

    public void insertLectureAutorise(Connection con,String[] lecture,String idLivre) throws Exception{
        Statement statement = con.createStatement();
        for(int i=0; i < lecture.length ; i++){
            String sql = "INSERT INTO LECTURE_LIVRE_AUTORISE(ID_LIVRE,TYPE_MEMBRE) VALUES ('" + idLivre + "','"+ lecture[i] +"')";
            System.out.println(sql);
            statement.executeUpdate(sql);
        }
        statement.close();   
    }

    public List<Categorie> findCategorieLivre(Connection con,String idLivre) throws Exception{
        List<Categorie> liste = new ArrayList<Categorie>();
        Categorie categorie = new Categorie();

        String sql = "SELECT * FROM CATEGORIE_LIVRE WHERE ID_LIVRE = '" + idLivre + "'";

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery(sql);

        while(rs.next()){
            String idCateg = rs.getString("ID_CATEGORIE");
            Categorie val = categorie.findCategorieById(con, idCateg);
            liste.add(val);
        }

        rs.close();
        state.close();
        return liste;
    }

    public void insertCategorieLivre(Connection con,String[] categorie,String idLivre) throws Exception{
        
        Statement statement = con.createStatement();
        for(int i=0; i < categorie.length ; i++){
            String sql = "INSERT INTO CATEGORIE_LIVRE(ID_LIVRE,ID_CATEGORIE) VALUES ('"+ idLivre +"','" +  categorie[i] + "')";
            System.out.println(sql);
            statement.executeUpdate(sql);
        }
        statement.close();   
    }


    public List<Livre> getListeLivres(Connection con) throws Exception{
        List<Livre> liste = new ArrayList<Livre>();
        
        String sql = "SELECT * FROM LIVRE";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Livre temp = new Livre();

            temp.setIdLivre(rs.getString("ID_LIVRE"));
            temp.setLivreEdition(rs.getString("LIVRE_EDITION"));
            temp.setDateEdition(rs.getDate("DATE_EDITION"));
            temp.setIsbn(rs.getString("ISBN"));
            temp.setNumeroCote(rs.getString("NUMERO_COTE"));
            temp.setTitre(rs.getString("TITRE"));
            temp.setAuteur(rs.getString("AUTEUR"));
            temp.setLivreCollection(rs.getString("LIVRE_COLLECTION"));
            temp.setLivreResume(rs.getString("LIVRE_RESUME"));
            temp.setLangue(rs.getString("LANGUE"));
            temp.setNombrePages(rs.getInt("NOMBRE_PAGES"));
            temp.setTheme(rs.getString("THEME"));
            temp.setCouveture(rs.getString("COUVERTURE"));
            temp.setAgeLimite(rs.getInt("AGE_LIMITE"));
            temp.setDisponibilite(rs.getInt("DISPONIBILITE"));

            temp.setCategorieLivre(findCategorieLivre(con,rs.getString("ID_LIVRE")));
            temp.setLectureAutorise(findLectureAutorise(con,rs.getString("ID_LIVRE")));
            temp.setEmpruntAutorise(findEmpruntAutorise(con,rs.getString("ID_LIVRE")));

            liste.add(temp);
        }

        statement.close();
        rs.close();

        return liste;
    }

    public List<Livre> getListeLivresDispo(Connection con) throws Exception{
        List<Livre> liste = new ArrayList<Livre>();
        
        String sql = "SELECT * FROM LIVRE WHERE DISPONIBILITE = 0";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Livre temp = new Livre();

            temp.setIdLivre(rs.getString("ID_LIVRE"));
            temp.setLivreEdition(rs.getString("LIVRE_EDITION"));
            temp.setDateEdition(rs.getDate("DATE_EDITION"));
            temp.setIsbn(rs.getString("ISBN"));
            temp.setNumeroCote(rs.getString("NUMERO_COTE"));
            temp.setTitre(rs.getString("TITRE"));
            temp.setAuteur(rs.getString("AUTEUR"));
            temp.setLivreCollection(rs.getString("LIVRE_COLLECTION"));
            temp.setLivreResume(rs.getString("LIVRE_RESUME"));
            temp.setLangue(rs.getString("LANGUE"));
            temp.setNombrePages(rs.getInt("NOMBRE_PAGES"));
            temp.setTheme(rs.getString("THEME"));
            temp.setCouveture(rs.getString("COUVERTURE"));
            temp.setAgeLimite(rs.getInt("AGE_LIMITE"));
            temp.setDisponibilite(rs.getInt("DISPONIBILITE"));

            temp.setCategorieLivre(findCategorieLivre(con,rs.getString("ID_LIVRE")));
            temp.setLectureAutorise(findLectureAutorise(con,rs.getString("ID_LIVRE")));
            temp.setEmpruntAutorise(findEmpruntAutorise(con,rs.getString("ID_LIVRE")));

            liste.add(temp);
        }

        statement.close();
        rs.close();

        return liste;
    }

    public boolean isUniqueISBN(Connection con,String isbn) throws Exception{
        boolean val = false;

        String sql = "SELECT * FROM LIVRE WHERE ISBN = '" + isbn + "'";
        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery(sql);

        if(rs.wasNull()){
           val = true; 
        }

        rs.close();
        state.close();

        return val;
    }

    public String generateUniqueISBN(Connection con) throws Exception{
        String isbnGenerate = "";
        int isbnLength = 10;

        do{    
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString().replace("-", "");
            String token = uuidString.substring(0, isbnLength);
            isbnGenerate = token;

        }while(isUniqueISBN(con, isbnGenerate));

        return isbnGenerate;
    }

    public String generateNumeroCote(Connection con,String[] categ) throws Exception{
        String val = "";
        Categorie categorie = new Categorie().findCategorieById(con, categ[0]);
        String preffix = categorie.getNomCategorie().substring(0, 3);

        val = "'" + preffix.toUpperCase() + "'||nextval('seq_livre_numero')";
        System.out.println(val);
        return val;
    }


    public String insertLivre(Connection con,String livreEdition,Date dateEdition,String isbn,String numeroCote,String titre,String auteur,String livreCollection,String livreResume,int nbPage,String theme,String couverture,int ageLimite,String langue) throws Exception{
        String idLivre = "";

        String sql = "INSERT INTO LIVRE (ID_LIVRE,LIVRE_EDITION,DATE_EDITION,ISBN,NUMERO_COTE,TITRE,AUTEUR,LIVRE_COLLECTION,LIVRE_RESUME,LANGUE,NOMBRE_PAGES,THEME,COUVERTURE,AGE_LIMITE,DISPONIBILITE) VALUES ('LIV'||nextval('seq_livre'),?,?,?," + numeroCote +",?,?,?,?,?,?,?,?,?,0) RETURNING ID_LIVRE";

        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, livreEdition);
        statement.setDate(2, dateEdition);
        statement.setString(3, isbn);
        statement.setString(4, titre);
        statement.setString(5, auteur);
        statement.setString(6, livreCollection);
        statement.setString(7, livreResume);
        statement.setString(8,langue);
        statement.setInt(9,nbPage);
        statement.setString(10, theme);
        statement.setString(11, couverture);
        statement.setInt(12, ageLimite);

        System.out.println(statement.toString());

        ResultSet rs = statement.executeQuery();

        if(!rs.wasNull()){
            while(rs.next()){
                idLivre = rs.getString("ID_LIVRE");
                System.out.println("ID_LIVRE : " + idLivre);
            }
        }else{
            System.out.println("Echec de l'insertion du livre " + titre);
        }
        
        rs.close();
        statement.close();

        return idLivre;
    }


    public Livre findLivreById(Connection con,String idLivre) throws Exception{
        Livre temp = new Livre();
        String sql = "SELECT * FROM LIVRE WHERE ID_LIVRE = '" + idLivre + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            temp.setIdLivre(rs.getString("ID_LIVRE"));
            temp.setLivreEdition(rs.getString("LIVRE_EDITION"));
            temp.setDateEdition(rs.getDate("DATE_EDITION"));
            temp.setIsbn(rs.getString("ISBN"));
            temp.setNumeroCote(rs.getString("NUMERO_COTE"));
            temp.setTitre(rs.getString("TITRE"));
            temp.setAuteur(rs.getString("AUTEUR"));
            temp.setLivreCollection(rs.getString("LIVRE_COLLECTION"));
            temp.setLivreResume(rs.getString("LIVRE_RESUME"));
            temp.setLangue(rs.getString("LANGUE"));
            temp.setNombrePages(rs.getInt("NOMBRE_PAGES"));
            temp.setTheme(rs.getString("THEME"));
            temp.setCouveture(rs.getString("COUVERTURE"));
            temp.setAgeLimite(rs.getInt("AGE_LIMITE"));
            temp.setDisponibilite(rs.getInt("DISPONIBILITE"));

            temp.setCategorieLivre(findCategorieLivre(con,rs.getString("ID_LIVRE")));
            temp.setLectureAutorise(findLectureAutorise(con,rs.getString("ID_LIVRE")));
            temp.setEmpruntAutorise(findEmpruntAutorise(con,rs.getString("ID_LIVRE")));

            System.out.println("model : " + temp.getTitre());
        }
        
        statement.close();
        rs.close();
        return temp;
    }





}
