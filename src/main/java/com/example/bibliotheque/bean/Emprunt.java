package com.example.bibliotheque.bean;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Emprunt {
    String idEmprunt;
    Utilisateur membre;
    Date dateEmprunt;
    Date dateRetour;
    List<Livre> livreEmprunter;
    
    public boolean peutEmprunter(Connection con,String idLivre,String idUtilisateur) throws Exception{
        boolean val = false;

        Utilisateur utilisateur = new Utilisateur().findUtilisateurById(con, idUtilisateur);
        Livre livre = new Livre().findLivreById(con, idLivre);

        List<String> typesAutorises = new ArrayList<>();
        for (TypeMembre type : livre.getEmpruntAutorise()) {
            typesAutorises.add(type.getIdType());
            System.out.println("Type autorisé : " + type.getIdType() + " , " + type.getNomType());
        }

        if(typesAutorises.contains(utilisateur.getType().getIdType())){
                val = true;
        }else{
            Livre nomLivre = new Livre().findLivreById(con,idLivre);
            StringBuilder empruntAut = new StringBuilder();

            for(var emp : nomLivre.getEmpruntAutorise()){
                empruntAut.append(" " + emp.getNomType());
            }
            throw new Exception("Le livre : " + nomLivre.getTitre() + " ne peut etre emprunter que par " + empruntAut);
        }
        
        return val;
    }


    public void insertLivreEmprunter(Connection con,String idEmprunt,String[] idLivre,String idUtilisateur) throws Exception{
        String sql = "INSERT INTO EMPRUNT_LIVRE(ID_EMPRUNT,ID_LIVRE) VALUES (?,?)";
        Livre l = new Livre();

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, idEmprunt);

        for(int i = 0;i<idLivre.length;i++){
            if(peutEmprunter(con, idLivre[i], idUtilisateur) && l.verifAgePourLivre(con, idLivre[i],idUtilisateur)){
                statement.setString(2, idLivre[i]);
                statement.executeUpdate();
                l.updateDisponibilite(con, idLivre[i]);
            }
        }

        statement.close();
    }

    public List<Livre> findLivreEmprunter(Connection con,String idEmprunt) throws Exception{
        List<Livre> liste = new ArrayList<Livre>();
        List<String> listeId = new ArrayList<String>();

        String sql = "SELECT * FROM EMPRUNT_LIVRE WHERE ID_EMPRUNT = '" + idEmprunt + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            listeId.add(rs.getString("ID_LIVRE"));
        }

        for(String id : listeId){
            Livre finded = new Livre().findLivreById(con, id); 
            liste.add(finded);
        }
        return liste;
    }


    public Date generateDateRetour(Connection con,Date dateEmprunt , String idUtilisateur) throws Exception{

        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // java.util.Date utilDate = dateFormat.parse(dateEmprunt);
    
        // Date dateEmp = new Date(utilDate.getTime());  
            
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(dateEmprunt);

        Utilisateur util = new Utilisateur().findUtilisateurById(con, idUtilisateur);

        int jourAjouter = util.getType().getDureeEmprunt();

        calendar.add(Calendar.DAY_OF_MONTH, jourAjouter );
        Date dt = new Date(calendar.getTimeInMillis());

        System.out.println("Date rendu : " + dt);
        return dt;

    }

    public String insertEmprunt(Connection con,String idUtilisateur,Date dateEmprunt) throws Exception{
        String val = "";
    
        String sql = "INSERT INTO EMPRUNT(ID_EMPRUNT,ID_UTILISATEUR,DATE_EMPRUNT,DATE_RETOUR) VALUES ('EMP'||nextval('seq_emprunt'),?,?,?) RETURNING ID_EMPRUNT";

        PreparedStatement statement = con.prepareStatement(sql);
        Date drt = generateDateRetour(con, dateEmprunt, idUtilisateur);

        statement.setString(1,idUtilisateur);
        statement.setDate(2, dateEmprunt);
        statement.setDate(3, drt);

        ResultSet rs = statement.executeQuery();
        if(!rs.wasNull()){
            while(rs.next()){
                val = rs.getString("ID_EMPRUNT");
            }
        }else{
            throw new Exception("Emprunt non insérer");
        }

        rs.close();
        statement.close();
        return val;
    }


    public List<Emprunt> getListeEmprunt(Connection con) throws Exception{
        List<Emprunt> liste = new ArrayList<Emprunt>();

        String sql = "SELECT * FROM EMPRUNT ORDER BY DATE_EMPRUNT DESC";

        Statement statement = con.createStatement(); 
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            Emprunt temp = new Emprunt();
            temp.setIdEmprunt(rs.getString("ID_EMPRUNT"));
            
            String idUtil = rs.getString("ID_UTILISATEUR");
            temp.setMembre(new Utilisateur().findUtilisateurById(con, idUtil));
            temp.setDateEmprunt(rs.getDate("DATE_EMPRUNT"));
            temp.setDateRetour(rs.getDate("DATE_RETOUR"));
            temp.setLivreEmprunter(findLivreEmprunter(con, rs.getString("ID_EMPRUNT")));
            liste.add(temp);
        }

        rs.close();
        statement.close();

        return liste;
    }

    public Emprunt findEmprunt(Connection con,int id) throws Exception{
        Emprunt temp = new Emprunt();

        String sql = "SELECT * FROM EMPRUNT WHERE ID_EMPRUNT = '" + id + "'";

        Statement statement = con.createStatement(); 
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            temp.setIdEmprunt(rs.getString("ID_EMPRUNT"));
            
            String idUtil = rs.getString("ID_UTILISATEUR");
            temp.setMembre(new Utilisateur().findUtilisateurById(con, idUtil));
            temp.setDateEmprunt(rs.getDate("DATE_EMPRUNT"));
            temp.setDateRetour(rs.getDate("DATE_RETOUR"));
            temp.setLivreEmprunter(findLivreEmprunter(con, rs.getString("ID_EMPRUNT")));
        }

        rs.close();
        statement.close();

        return temp;
    }

    public List<Livre> getListeEmpruntByUtil(Connection con,String idUtilisateur,String idEmprunt) throws Exception{

        List<Livre> livres = new ArrayList<Livre>();

        String sql = "SELECT el.ID_LIVRE,l.TITRE,l.DISPONIBILITE FROM EMPRUNT e JOIN EMPRUNT_LIVRE el ON e.ID_EMPRUNT = el.ID_EMPRUNT JOIN LIVRE l ON el.ID_LIVRE = l.ID_LIVRE WHERE e.ID_UTILISATEUR = '" + idUtilisateur + "' AND l.DISPONIBILITE = 1 AND e.ID_EMPRUNT = '" + idEmprunt + "' ORDER BY DATE_EMPRUNT DESC ";

        Statement statement = con.createStatement(); 
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String idLivre = rs.getString("ID_LIVRE");
            Livre livrefinded = new Livre().findLivreById(con, idLivre);
            livres.add(livrefinded);
        }

        rs.close();
        statement.close();

        return livres;
    }

    public Emprunt getDernierEmprunt(Connection con,String idUtilisateur) throws Exception{
        Emprunt emprunt = new Emprunt();
        
        String sql = "SELECT * FROM EMPRUNT e WHERE e.ID_UTILISATEUR ='" + idUtilisateur + "' ORDER BY DATE_EMPRUNT DESC LIMIT 1";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        System.out.println(sql);

        while(rs.next()){
            emprunt.setIdEmprunt(rs.getString("ID_EMPRUNT"));
            emprunt.setMembre(new Utilisateur().findUtilisateurById(con, rs.getString("ID_UTILISATEUR")));
            emprunt.setDateEmprunt(rs.getDate("DATE_EMPRUNT"));
            emprunt.setDateRetour(rs.getDate("DATE_RETOUR"));
            emprunt.setLivreEmprunter(findLivreEmprunter(con, rs.getString("ID_EMPRUNT")));
        }

        System.out.println("Model : " + emprunt.getDateRetour());

        statement.close();
        rs.close();
        
        return emprunt;
    }

    public Emprunt findEmpruntById(Connection con,String idEmprunt) throws Exception{
        Emprunt emprunt = new Emprunt();
        String sql = "SELECT * FROM EMPRUNT WHERE ID_EMPRUNT ='" + idEmprunt + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            emprunt.setIdEmprunt(rs.getString("ID_EMPRUNT"));
            emprunt.setMembre(new Utilisateur().findUtilisateurById(con, rs.getString("ID_UTILISATEUR")));
            emprunt.setDateEmprunt(rs.getDate("DATE_EMPRUNT"));
            emprunt.setDateRetour(rs.getDate("DATE_RETOUR"));
            emprunt.setLivreEmprunter(findLivreEmprunter(con, rs.getString("ID_EMPRUNT")));
        }

        statement.close();
        rs.close();
        return emprunt;
    }


    public List<Emprunt> getEmpruntEnCours(Connection con) throws Exception{
        List<Emprunt> liste = new ArrayList<Emprunt>();
        String sql = "SELECT * FROM V_EMPRUNT_EN_COURS";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            liste.add(new Emprunt().findEmpruntById(con,rs.getString("ID_EMPRUNT")));
        }

        statement.close();
        rs.close();

        return liste;
    }


    public List<String[]> getStatEmprunt() throws Exception{
        List<String[]> liste = new ArrayList<String[]>();
        Connection connection = DBCPDataSource.getConnection();
        String sql = "SELECT * FROM V_STAT_EMPRUNT";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next())
        {
            String[] tab = new String[3];
            tab[0] = rs.getString("ID_MODELE");
            tab[1] = rs.getString("TITRE");
            tab[2] = String.valueOf(rs.getInt("NOMBRE")) ;
            liste.add(tab);
        }

        statement.close();
        rs.close();
        connection.close();
        return liste;
    }


    public List<String> getAllYear() throws Exception{
        List<String> liste = new ArrayList<String>();
        String sql = "SELECT DISTINCT EXTRACT(year FROM EMPRUNT.DATE_EMPRUNT) AS annee FROM EMPRUNT";
        Connection connection = DBCPDataSource.getConnection();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            liste.add(rs.getString("ANNEE"));
        }

        rs.close();
        statement.close();
        connection.close();

        return liste;
    }


    public List<String[]> getStatEmpruntAnnee(String annee) throws Exception{
        List<String[]> liste = new ArrayList<String[]>();
        Connection connection = DBCPDataSource.getConnection();
        String sql = "WITH mois_annee AS (\r\n" + //
                        "  SELECT to_char(DATE_TRUNC('month', current_date), 'Month') AS mois_fr\r\n" + //
                        "  UNION\r\n" + //
                        "  SELECT to_char(DATE_TRUNC('month', (current_date - INTERVAL '1 month' * (n-1))), 'Month') AS mois_fr\r\n" + //
                        "  FROM generate_series(1, 12) AS s(n)\r\n" + //
                        ")\r\n" + //
                        "SELECT\r\n" + //
                        "  mois_annee.mois_fr,\r\n" + //
                        "  COALESCE(COUNT(ID_EMPRUNT), 0) AS somme_mois\r\n" + //
                        "FROM mois_annee\r\n" + //
                        "LEFT JOIN EMPRUNT ON to_char(DATE_TRUNC('month', EMPRUNT.DATE_EMPRUNT), 'Month') = mois_annee.mois_fr\r\n" + //
                        "AND EXTRACT(year FROM EMPRUNT.DATE_EMPRUNT) = ? \r\n" + //
                        "GROUP BY mois_annee.mois_fr,EXTRACT(year FROM EMPRUNT.DATE_EMPRUNT)\r\n" + //
                        "ORDER BY EXTRACT(MONTH FROM to_date(mois_annee.mois_fr, 'Month'));\r\n" + //
                        " ";

        PreparedStatement statement = connection.prepareStatement(sql);
        int year = Integer.parseInt(annee);
        statement.setInt(1, year);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String[] temp = new String[2];
            temp[0] = rs.getString("MOIS_FR");
            temp[1] = rs.getString("SOMME_MOIS");
            liste.add(temp);
        }

        rs.close();
        statement.close();
        connection.close();

        return liste;
    }


    
}
