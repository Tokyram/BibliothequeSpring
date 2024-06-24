package com.example.bibliotheque.service;

import java.sql.Connection;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.example.bibliotheque.bean.DBCPDataSource;
import com.example.bibliotheque.bean.Sanction;

@Service
public class SanctionService {
    
    public void addSanctionIfHave(String idEmprunt,Date dateRetour,String idUtilisateur) throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        connection.setAutoCommit(false);

        Sanction sanction = new Sanction();
        if(sanction.haveSanction(connection, dateRetour, idEmprunt)){
            Date datefin = sanction.calculDateSanction(connection, dateRetour, idEmprunt,idUtilisateur);
            sanction.inserSanction(connection, idUtilisateur, idEmprunt, dateRetour, datefin);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy"); 
            String formattedDate = datefin.toLocalDate().format(formatter);
            connection.commit();
            throw new Exception("Vous avez reçu une sanction !! Vous ne pouvez emprêter de livre que jusqu'à : " + formattedDate);
        }
        connection.close();
    }

    public boolean verifSanction(String idUtilisateur,Date dateEmprunt) throws Exception{
        Connection connection = DBCPDataSource.getConnection();
        boolean val = new Sanction().verifSanction(connection, dateEmprunt, idUtilisateur);
        connection.close();
        return val;
    }
}
