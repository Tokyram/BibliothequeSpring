package com.example.bibliotheque.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.*;

import com.example.bibliotheque.bean.DBCPDataSource;
import com.example.bibliotheque.bean.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {
    

    public Utilisateur loginUtilisateur(String id) throws Exception{
        Utilisateur val = new Utilisateur();
        Connection connection = DBCPDataSource.getConnection();
        try{
            Utilisateur utilisateur = new Utilisateur();

            if(utilisateur.verifUtilisateur(connection, id)){
                val = utilisateur.findUtilisateurById(connection, id);
            }else{
                throw new Exception("Utilisateur introuvable");
            }
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return val;
    }

    public Utilisateur loginAdmin(String id,String nom)throws Exception{
        Utilisateur val = new Utilisateur();
        Connection connection = DBCPDataSource.getConnection();
        try{
            Utilisateur utilisateur = new Utilisateur();

            if(utilisateur.verifAdmin(connection, id, nom)){
                val = utilisateur.findUtilisateurById(connection, id);
            }else{
                throw new Exception("Nom ou identifiant incorrect");
            }
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }
        
        return val;
    }

    public Utilisateur insertUtilisateur(String nom,String prenom,Date dateNaissance,String adresse,String typeMembre,Date dateInscription) throws Exception{
        Utilisateur val = new Utilisateur();
        Connection connection = DBCPDataSource.getConnection();
        connection.setAutoCommit(false);
        try {
            Utilisateur util = new Utilisateur();
            val = util.insertUtilisateur(connection, nom, prenom, dateNaissance, adresse, typeMembre, dateInscription);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        }finally{
            connection.close();
        }
        return val;
    }




    
}
