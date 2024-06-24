package com.example.bibliotheque.service;

import org.springframework.stereotype.Service;

import com.example.bibliotheque.bean.DBCPDataSource;
import com.example.bibliotheque.bean.Modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ModeleService {
    
    public void insertModele(String nom,String auteur,String resume) throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        connection.setAutoCommit(false);
        try{
            Modele mod = new Modele();
            mod.insertModele(connection, nom, auteur, resume);
            connection.commit();
        }catch(Exception e){
            connection.rollback();
            throw e;
        }finally{
            connection.close();
        }
    }


    public Modele findModeleById(String idModele) throws Exception{
        Modele mod = new Modele();
        Connection connection = DBCPDataSource.getConnection();
        try{
            mod = new Modele().findModeleById(connection, idModele);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return mod;
    }

    public List<Modele> getAllModele() throws Exception {
        List<Modele> liste = new ArrayList<Modele>();
        Connection connection = DBCPDataSource.getConnection();
        try{
            liste = new Modele().getAllModele(connection);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return liste;
    }


}
