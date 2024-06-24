package com.example.bibliotheque.bean;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Categorie {
    String idCategorie;
    String nomCategorie;

    public Categorie findCategorieById(Connection con,String idCateg) throws Exception{
        Categorie val = new Categorie();

        String sql = "SELECT * FROM CATEGORIE WHERE ID_CATEGORIE = '" + idCateg + "'";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            val.setIdCategorie(rs.getString("ID_CATEGORIE"));
            val.setNomCategorie(rs.getString("NOM"));
        }
        statement.close();
        rs.close();
        
        return val;
    }

    public List<Categorie> getAllCategorie(Connection con) throws Exception{
        List<Categorie> liste = new ArrayList<Categorie>();

        String sql = "SELECT * FROM CATEGORIE";
        Statement statement =  con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            Categorie temp = new Categorie();
            temp.setIdCategorie(rs.getString("ID_CATEGORIE"));
            temp.setNomCategorie(rs.getString("NOM"));
            liste.add(temp);
        }
        
        rs.close();
        statement.close();
        return liste;
    }
}
