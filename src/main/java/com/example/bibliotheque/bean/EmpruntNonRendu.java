package com.example.bibliotheque.bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmpruntNonRendu {
    Emprunt emprunt;
    Livre livre;
    Utilisateur utilisateur;
    Date dateEmprunt;


    public List<EmpruntNonRendu> getListeEmprunt() throws Exception{
        Connection connection = DBCPDataSource.getConnection();

        List<EmpruntNonRendu> liste = new ArrayList<EmpruntNonRendu>();
        
        String sql = "SELECT * FROM V_EMPRUNT_NON_RENDU";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            EmpruntNonRendu emp = new EmpruntNonRendu();
            emp.setEmprunt(new Emprunt().findEmpruntById(connection,rs.getString("ID_EMPRUNT")));
            emp.setLivre(new Livre().findLivreById(connection,rs.getString("ID_LIVRE")));
            emp.setUtilisateur(new Utilisateur().findUtilisateurById(connection, rs.getString("ID_UTILISATEUR")));
            emp.setDateEmprunt(rs.getDate("DATE_EMPRUNT"));
            liste.add(emp);
        }
        rs.close();
        statement.close();
        connection.close();
        return liste;
    }


}
