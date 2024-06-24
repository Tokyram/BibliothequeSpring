package com.example.bibliotheque.service;

import java.sql.Connection;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bibliotheque.bean.*;
@Service
public class RenduService {
    
    // @Autowired
    // Connection connection;


    public void insertRendu(String[] idLivre,String idEmprunt,Date dateRetour,String idUtilisateur) throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        connection.setAutoCommit(false);

        try{
            Rendu rendu = new Rendu();
            Sanction sanction = new Sanction();
            Emprunt emprunt = new Emprunt().findEmpruntById(connection,idEmprunt);

            if(dateRetour.before(emprunt.getDateEmprunt())){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy"); 
                String formattedDate = emprunt.getDateEmprunt().toLocalDate().format(formatter);

                throw new Exception("Ce livre a été emprunté le : " + formattedDate + " donc doit être rendu au dela de cela");
            }else{
                rendu.insertRenduLivre(connection, idEmprunt, idLivre, dateRetour);
                connection.commit();    
            }

        }catch(Exception e){
            connection.rollback();
            throw e;
        }finally{
            connection.close();
        }
    }

    public List<Rendu> getListeRendu() throws Exception{
        List<Rendu> liste = new ArrayList<Rendu>();
        Connection connection = DBCPDataSource.getConnection();
        try{
            liste = new Rendu().getListeRendu(connection);
        }catch(Exception e){
            // e.printStackTrace();
            throw e;
        }finally{
            connection.close();
        }
        return liste;
    }

    public boolean verifRenduLivre(String idUtilisateur) throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        boolean val = false;
        Rendu r = new Rendu();
        try{
            List<Livre> liste = new Rendu().findLivreNonRendu(connection, idUtilisateur);

            if(r.haveLivreNonRendu(connection, idUtilisateur)){
                val = true;

                if(liste.size() != 0){
                    StringBuilder name = new StringBuilder();
                    for(var livre : liste){
                        name.append(" " +livre.getTitre() + " ,");
                    }
                    throw new Exception("Veuillez d'abord rendre les livres que vous avez empruntés et non rendu : " + name );
                }
            }

            if(r.isEmpruntEnCours(connection, idUtilisateur)){
                Emprunt last = new Emprunt().getDernierEmprunt(connection, idUtilisateur);
                List<Livre> liste2 = new Emprunt().getListeEmpruntByUtil(connection, idUtilisateur, last.getIdEmprunt());

                val = true;

                if(liste2.size() != 0){
                    StringBuilder name = new StringBuilder();
                    for(var livre : liste2){
                        name.append(" " +livre.getTitre() + " ,");
                    }
                    throw new Exception("Veuillez d'abord rendre les livres que vous avez empruntés et non rendu : " + name );
                }
            }

        }catch(Exception e){
            throw e;
        }finally{
            connection.close();
        }
        return val;
    }

    public List<Livre> getLivreNonRendu(String idUtilisateur) throws Exception{
        List<Livre> liste = new ArrayList<Livre>();
        Connection connection = DBCPDataSource.getConnection();

        try{
            liste = new Rendu().findLivreNonRendu(connection, idUtilisateur);
        }catch(Exception e){
            throw e;
        }finally{
            connection.close();;
        }

        return liste;
    }

}
