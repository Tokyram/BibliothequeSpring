package com.example.bibliotheque.bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
public class Rendu {
    String idRendu;
    Emprunt emprunt;
    Date dateRendu;
    List<Livre> livreRendu;

    public void insertRenduLivre(Connection con,String idEmprunt,String[] idLivre,Date dateRendu) throws Exception{
        String sql = "INSERT INTO RENDU (ID_RENDU,ID_EMPRUNT,DATE_RENDU) VALUES ('RD'||nextval('seq_rendu'),?,?) RETURNING ID_RENDU";
        String idRendu = "";
        Livre l = new Livre();

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, idEmprunt);
        statement.setDate(2, dateRendu);

        ResultSet rs = statement.executeQuery();

        while(rs.next()){
            idRendu = rs.getString("ID_RENDU");
        }

        rs.close();
        statement.close();

        String sql2 = "INSERT INTO RENDU_LIVRE(ID_RENDU,ID_LIVRE) VALUES ('"+ idRendu + "',?)";
        PreparedStatement state = con.prepareStatement(sql2);

        for(String idL : idLivre){
            state.setString(1, idL);
            state.executeUpdate();    
            l.updateDisponibilite(con, idL);
        }

        state.close();
    }

    public List<Livre> findLivreRenduById(Connection con,String idRendu) throws Exception{
        List<Livre> liste = new ArrayList<Livre>();

        String sql = "SELECT * FROM RENDU_LIVRE WHERE ID_RENDU = '" + idRendu + "'";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        Livre livre = new Livre();

        while(rs.next()){
            liste.add(livre.findLivreById(con, rs.getString("ID_LIVRE")));
        }

        rs.close();
        statement.close();
        return liste;
    }

    public Rendu findRenduById(Connection con,String idRendu) throws Exception{
        Rendu rendu = new Rendu();

        String sql = "SELECT * FROM RENDU WHERE ID_RENDU = '" + idRendu + "'";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            rendu.setIdRendu(rs.getString("ID_RENDU"));
            rendu.setDateRendu(rs.getDate("DATE_RENDU"));
            rendu.setLivreRendu(findLivreRenduById(con, rs.getString("ID_RENDU")));
            rendu.setEmprunt(new Emprunt().findEmpruntById(con,rs.getString("ID_EMPRUNT")));
        }

        return rendu;
    }

    public List<Rendu> getListeRendu(Connection con) throws Exception{
        List<Rendu> listeRendu = new ArrayList<Rendu>();

        String sql = "SELECT ID_RENDU,ID_EMPRUNT,DATE_RENDU FROM RENDU ORDER BY DATE_RENDU DESC";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            Rendu rendu = new Rendu();
            rendu.setIdRendu(rs.getString("ID_RENDU"));
            rendu.setDateRendu(rs.getDate("DATE_RENDU"));
            rendu.setLivreRendu(findLivreRenduById(con, rs.getString("ID_RENDU")));
            rendu.setEmprunt(new Emprunt().findEmpruntById(con,rs.getString("ID_EMPRUNT")));
            listeRendu.add(rendu);
        }

        rs.close();
        statement.close();
        return listeRendu;
    }


    public boolean haveLivreNonRendu(Connection connection,String idUtilisateur) throws Exception{
        boolean val = false;

        String sql = "SELECT * FROM V_EMPRUNT_NON_RENDU WHERE ID_UTILISATEUR = '" + idUtilisateur + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        String result = "";

        while(rs.next()){
            result = rs.getString("ID_LIVRE");
        }
        
        if(result.compareTo("") != 0){
            val = true;
        }

        statement.close();
        rs.close();
        return val;
    }

    public boolean isEmpruntEnCours(Connection connection,String idUtilisateur) throws Exception{
        boolean val = false;
        String sql = "SELECT * FROM V_EMPRUNT_EN_COURS WHERE ID_UTILISATEUR = '" + idUtilisateur + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        String result = "";

        while(rs.next()){
            result = rs.getString("ID_UTILISATEUR");
        }

        if(result.compareTo("") != 0){
            val = true;
        }

        return val;
    }

    public List<Livre> findLivreNonRendu(Connection connection,String idUtilisateur) throws Exception{
        List<Livre> liste = new ArrayList<Livre>();
        String sql = "SELECT ID_LIVRE FROM V_EMPRUNT_NON_RENDU WHERE ID_UTILISATEUR = '" + idUtilisateur + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            liste.add(new Livre().findLivreById(connection,rs.getString("ID_LIVRE")));
        }

        statement.close();
        rs.close();
        return liste;
    }
    


    
    

}
