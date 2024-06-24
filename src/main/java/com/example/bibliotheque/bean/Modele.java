package com.example.bibliotheque.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Modele {
    String idModele;
    String nomModele;
    String auteur;
    String resumeModele;
    List<Livre> listeExemplaire;

    public void insertModele(Connection con,String nom,String auteur,String resume) throws Exception{
        String sql = "INSERT INTO MODELE(ID_MODELE,NOM_MODELE,AUTEUR,RESUME_MODELE) VALUES ('MODL'||nextval('seq_modele'),?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, nom);
        statement.setString(2, auteur);
        statement.setString(3, resume);

        statement.executeUpdate();
        statement.close();
    }

    public void insertModeleLivre(Connection con,String idLivre,String idModele) throws Exception{
        String sql = "INSERT INTO MODELE_LIVRE(ID_MODELE,ID_LIVRE) VALUES (?,?)";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, idModele);
        statement.setString(2,idLivre);

        statement.executeUpdate();
        statement.close();
    }

    public List<Livre> getListeExemplaireById(Connection con,String idModele) throws Exception{
        List<Livre> liste = new ArrayList<Livre>();
        List<String> idLivre = new ArrayList<String>();

        String sql = "SELECT * FROM MODELE_LIVRE WHERE ID_MODELE = '" + idModele + "'";
        
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            idLivre.add(rs.getString("ID_LIVRE"));
        }

        for(String id : idLivre){
            liste.add(new Livre().findLivreById(con, id));
        }

        statement.close();
        rs.close();
        
        return liste;
    }

    public Modele findModeleById(Connection con,String idModele) throws Exception{
        Modele model = new Modele();

        String sql = "SELECT * FROM MODELE WHERE ID_MODELE = '" + idModele + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            model.setIdModele(rs.getString("ID_MODELE"));
            model.setNomModele(rs.getString("NOM_MODELE"));
            model.setAuteur(rs.getString("AUTEUR"));
            model.setResumeModele(rs.getString("RESUME_MODELE"));
            model.setListeExemplaire(getListeExemplaireById(con, rs.getString("ID_MODELE")));
        }

        statement.close();
        rs.close();

        return model;
    }

    public List<Modele> getAllModele(Connection con) throws Exception{
        List<Modele> liste = new ArrayList<Modele>();
        
        String sql = "SELECT * FROM MODELE";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            Modele model = new Modele();
            model.setIdModele(rs.getString("ID_MODELE"));
            model.setNomModele(rs.getString("NOM_MODELE"));
            model.setAuteur(rs.getString("AUTEUR"));
            model.setResumeModele(rs.getString("RESUME_MODELE"));
            model.setListeExemplaire(getListeExemplaireById(con, rs.getString("ID_MODELE")));
            liste.add(model);
        }

        statement.close();
        rs.close();
    
        return liste;
    }
}
