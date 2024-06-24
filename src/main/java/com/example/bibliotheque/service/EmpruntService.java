package com.example.bibliotheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.example.bibliotheque.bean.DBCPDataSource;
import com.example.bibliotheque.bean.Emprunt;
import com.example.bibliotheque.bean.Livre;

@Service
public class EmpruntService {
    
    // @Autowired
    // Connection connection;

    public void insertEmprunt(String idUtilisateur,Date dateEmprunt,String[] listeLivre) throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        connection.setAutoCommit(false);
        try{
            Emprunt emp = new Emprunt();
            String idEmprunt = "";
            idEmprunt = emp.insertEmprunt(connection, idUtilisateur, dateEmprunt);
            emp.insertLivreEmprunter(connection, idEmprunt, listeLivre,idUtilisateur);
            connection.commit();
        }catch(Exception e){
            connection.rollback();
            throw e;
        }finally{
            connection.close();
        }
    }

    public List<Emprunt> getListeEmprunt() throws Exception{
        List<Emprunt> liste = new ArrayList<Emprunt>();
        Connection connection = DBCPDataSource.getConnection();
        connection.setAutoCommit(false);
        try{
            Emprunt emp = new Emprunt();
            liste = emp.getListeEmprunt(connection);
            connection.commit();
        }catch(Exception e){
            connection.rollback();
            e.printStackTrace();
        }finally{
            connection.close();
        }
        return liste;
    }


    public List<Livre> getListeLivreEmprunte(String idUtilisateur,String idEmprunt) throws Exception{
        List<Livre> liste = new ArrayList<Livre>();
        Connection connection = DBCPDataSource.getConnection();
        
        try{
            Emprunt emp = new Emprunt();
            liste = emp.getListeEmpruntByUtil(connection, idUtilisateur,idEmprunt);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            connection.close();
        }

        return liste;
    }


    public Emprunt getDernierEmpruntUtil(String idUtilisateur) throws Exception{
        Emprunt emp = new Emprunt();
        Connection connection = DBCPDataSource.getConnection();

        try{
            emp = new Emprunt().getDernierEmprunt(connection, idUtilisateur);
            System.out.println("Service : " + emp.getDateRetour());
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return emp;
    }


    public List<Emprunt> getListeEmpruntEnCours() throws Exception{
        List<Emprunt> emp = new ArrayList<Emprunt>();
        Connection connection = DBCPDataSource.getConnection();

        try{
            emp = new Emprunt().getEmpruntEnCours(connection);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return emp;
    }

    public Emprunt findEmpruntById(String id) throws Exception{
        Emprunt emp = new Emprunt();
        Connection connection = DBCPDataSource.getConnection();
        try{
            emp = new Emprunt().findEmpruntById(connection, id);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return emp;
    }

    
}
