package com.example.bibliotheque.service;

import java.sql.Connection;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.bean.Categorie;
import com.example.bibliotheque.bean.DBCPDataSource;
import com.example.bibliotheque.bean.Livre;
import com.example.bibliotheque.bean.Modele;

@Service
public class LivreService {
    // @Autowired
    // Connection connection;


    public String insertLivre(Livre temp,String[] categorie,String idModele) throws Exception{
        Livre livre = new Livre();
        Modele model = new Modele();

        Connection connection = DBCPDataSource.getConnection();
        String idLivre = "";
        connection.setAutoCommit(false);
        try{
            String isbn = livre.generateUniqueISBN(connection);
            String numCote = livre.generateNumeroCote(connection, categorie); 

            idLivre = livre.insertLivre(connection, temp.getLivreEdition(), temp.getDateEdition() , isbn, numCote, temp.getTitre(), temp.getAuteur(), temp.getLivreCollection(), temp.getLivreResume(), temp.getNombrePages(), temp.getTheme(), temp.getCouveture(), temp.getAgeLimite(),temp.getLangue());
            model.insertModeleLivre(connection, idLivre, idModele);
            
            System.out.println(idLivre);
            connection.commit();
            return idLivre;

        }catch(Exception e){
            connection.rollback();
            throw e;
        }finally{
            connection.close();
        }
    }


    public void insertCategorie(String[] categorie,String idLivre) throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        try{
            Livre livre = new Livre();
            livre.insertCategorieLivre(connection, categorie, idLivre);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }
    }

    public void insertEmpruntAutorise(String[] emprunt,String idLivre) throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        try{
            Livre livre = new Livre();
            livre.insertEmpruntAutorise(connection, emprunt, idLivre);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }
    }

    public void insertLectureAutorise(String[] lecture,String idLivre)throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        try{    
            Livre livre = new Livre();
            livre.insertLectureAutorise(connection, lecture, idLivre);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }
    }

    public List<Livre> getListeLivres() throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        List<Livre> val = new ArrayList<Livre>();
        Livre livre = new Livre();
        try{
            val = livre.getListeLivres(connection);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return val;
    }

    public List<Livre> getListeLivreDispo() throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        List<Livre> val = new ArrayList<Livre>();
        Livre livre = new Livre();

        try{
            val = livre.getListeLivresDispo(connection);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return val;
    }

    public boolean peutLireLivre(String idUtilisateur,String idLivre) throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        boolean val = false;
        Livre livre = new Livre();

        try{
            if(livre.peutLireSurPlace(connection, idLivre, idUtilisateur) && livre.verifAgePourLivre(connection, idLivre, idUtilisateur) ){
                val = true;
            }
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }
        return val;    
    }

    public List<Categorie> getListeCategorie() throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        List<Categorie> val = new ArrayList<Categorie>();
        Categorie c = new Categorie();
        try{
            val = c.getAllCategorie(connection);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return val;
    }

    public Livre findLivreById(String idLivre) throws Exception{
        Livre livre = new Livre();
        Connection connection = DBCPDataSource.getConnection();

        try{
            livre = livre.findLivreById(connection, idLivre);
            System.out.println("Service" + livre.getTitre());
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }

        return livre;
    }
}
