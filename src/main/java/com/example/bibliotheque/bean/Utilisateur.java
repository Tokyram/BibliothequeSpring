package com.example.bibliotheque.bean;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.pattern.Util;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Utilisateur {
    String idUtilisateur;
    String nom;
    String prenom;
    Date dateNaissance;
    String adresse;
    Date dateInscription;
    TypeMembre type;
    String photo;
    int isAdmin;

    public boolean verifUtilisateur(Connection connection,String util) throws Exception{
        boolean val = false;
        String sql = "SELECT * FROM UTILISATEUR WHERE ID_UTILISATEUR = '" + util + "'";
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);   
        // System.out.println(sql);
        
        if(!rs.wasNull()){
            val = true;
        }

        rs.close();
        statement.close();
        return val;
    }

    public boolean verifAdmin(Connection con,String util,String nom) throws Exception{
        boolean val = false;
        String sql = "SELECT * FROM UTILISATEUR WHERE ID_UTILISATEUR = '" + util + "' AND NOM = '" + nom + "' AND IS_ADMIN = 1";

        Statement statement = con.createStatement();
        // System.out.println(statement);
        ResultSet rs = statement.executeQuery(sql);   
        if(!rs.wasNull()){
            val = true;
        }
        rs.close();
        statement.close();

        return val;
    }

    public int calculAge(Date dtn){

        LocalDate dateOfBirth = dtn.toLocalDate(); 
        LocalDate today = LocalDate.now();
        System.out.println("Ajd : " + today);

        Period period = Period.between(dateOfBirth,today);

        System.out.println("Date anniv " + dateOfBirth);
        int years = period.getYears();
        System.out.println("Years : " + years);

        int age = years;

        if (today.getMonthValue() > dateOfBirth.getMonthValue()) {
            if (today.getDayOfMonth() >= dateOfBirth.getDayOfMonth()) {
                age++;
            }
        }

        return age;
    }


    public Utilisateur insertUtilisateur(Connection con,String nom,String prenom,Date dateNaissance,String adresse,String typeMembre,Date dateInscription) throws Exception{
        Utilisateur util = new Utilisateur();
        TypeMembre typeM = new TypeMembre();

        String idUtil = typeM.findTypeById(con, typeMembre).getNomType().substring(0, 2).toUpperCase();

        String default_photo = "avatar/default_image.jpg";

        String sql = "INSERT INTO UTILISATEUR(ID_UTILISATEUR,NOM,PRENOM,DATE_NAISSANCE,ADRESSE,TYPE_MEMBRE,DATE_INSCRIPTION,PHOTO,IS_ADMIN) VALUES ('"+ idUtil +"'||nextval('seq_utilisateur'),?,?,?,?,?,?,?,0) RETURNING ID_UTILISATEUR,NOM,PRENOM,DATE_NAISSANCE,ADRESSE,TYPE_MEMBRE,DATE_INSCRIPTION,PHOTO,IS_ADMIN";

        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1,nom);
        statement.setString(2,prenom);
        statement.setDate(3,dateNaissance);
        statement.setString(4,adresse);
        statement.setString(5, typeMembre);
        statement.setDate(6, dateInscription);
        statement.setString(7, default_photo);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            util.setIdUtilisateur(rs.getString("ID_UTILISATEUR")); 
            util.setNom(rs.getString("NOM"));
            util.setPrenom(rs.getString("PRENOM"));
            util.setDateNaissance(rs.getDate("DATE_NAISSANCE"));
            util.setAdresse(rs.getString("ADRESSE"));   
            
            String idType = rs.getString("TYPE_MEMBRE");
            util.setType(typeM.findTypeById(con, idType));

            util.setDateInscription(rs.getDate("DATE_INSCRIPTION"));
            util.setPhoto(rs.getString("PHOTO"));
            util.setIsAdmin(rs.getInt("IS_ADMIN"));
        }

        // System.out.println(statement);
        rs.close();
        statement.close();
        return util;
    }   

    public Utilisateur findUtilisateurById(Connection con,String idUtil) throws Exception{
        Utilisateur val = new Utilisateur();
        TypeMembre type = new TypeMembre();
        String sql = "SELECT * FROM UTILISATEUR WHERE ID_UTILISATEUR = '" + idUtil + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            val.setIdUtilisateur(rs.getString("ID_UTILISATEUR"));
            val.setNom(rs.getString("NOM"));
            val.setPrenom(rs.getString("PRENOM"));
            val.setDateNaissance(rs.getDate("DATE_NAISSANCE"));
            val.setAdresse(rs.getString("ADRESSE"));
            val.setPhoto(rs.getString("PHOTO"));
            val.setDateInscription(rs.getDate("DATE_INSCRIPTION"));
            val.setIsAdmin(rs.getInt("IS_ADMIN"));    
            
            val.setType(type.findTypeById(con,rs.getString("TYPE_MEMBRE")));
        }

        statement.close();
        rs.close();
        return val;
    }

    public List<Utilisateur> getListeUtilisateur() throws Exception{
        List<Utilisateur> liste = new ArrayList<>();
        Connection connection = DBCPDataSource.getConnection();

        String sql = "SELECT * FROM UTILISATEUR WHERE IS_ADMIN = 0";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            Utilisateur val = new Utilisateur().findUtilisateurById(connection,rs.getString("ID_UTILISATEUR"));
            liste.add(val);
        }

        statement.close();
        rs.close();
        connection.close();

        return liste;
    }



}
