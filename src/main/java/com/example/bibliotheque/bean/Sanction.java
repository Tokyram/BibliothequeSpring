package com.example.bibliotheque.bean;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sanction {
    String idSanction;
    Utilisateur utilisateur;
    Emprunt emprunt;
    Date debutSanction;
    Date finSanction;

    public Date calculDateSanction(Connection connection,Date dateRendu,String idEmprunt,String idUtilisateur) throws Exception{
        
        Emprunt dernier = new Emprunt().getDernierEmprunt(connection, idUtilisateur);

        Emprunt emprunt = new Emprunt().findEmpruntById(connection,dernier.getIdEmprunt());
        Utilisateur utilisateur = new Utilisateur().findUtilisateurById(connection, idUtilisateur);

        LocalDate dtRetour = emprunt.getDateRetour().toLocalDate();
        LocalDate dtRendu = dateRendu.toLocalDate();
        long nbJour = ChronoUnit.DAYS.between(dtRetour, dtRendu);
                
        int jourCoeff = Math.round( nbJour * utilisateur.getType().coefficientRetard);

        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(dateRendu);
        calendar.add(Calendar.DAY_OF_MONTH, jourCoeff );

        Date dt = new Date(calendar.getTimeInMillis());    
        return dt;
    }


    public Sanction inserSanction(Connection connection,String idUtilisateur,String idEmprunt,Date dateDebut,Date dateFin) throws Exception{
        Sanction sanction = new Sanction();

        String sql = "INSERT INTO SANCTION(ID_SANCTION,ID_UTILISATEUR,ID_EMPRUNT,DEBUT_SANCTION,FIN_SANCTION) VALUES ('SANC'||nextval('seq_sanction') , ? , ? , ? , ? )";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, idUtilisateur);
        statement.setString(2, idEmprunt);
        statement.setDate(3, dateDebut);
        statement.setDate(4, dateFin);

        statement.execute();
        statement.close();

        return sanction;
    }

    public boolean haveSanction(Connection connection,Date dateRendu,String idEmprunt) throws Exception {
        boolean val = false;
        Emprunt emprunt = new Emprunt().findEmpruntById(connection,idEmprunt);

        if(emprunt.getDateRetour().before(dateRendu)){
            val = true;
        }
        return val;
    }

    public Sanction getLastSanction(Connection connection,String idUtilisateur) throws Exception{
        Sanction sanction = new Sanction();
        
        String sql = "SELECT * FROM SANCTION WHERE ID_UTILISATEUR = '"+ idUtilisateur +"' ORDER BY FIN_SANCTION DESC LIMIT 1";
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            sanction.setIdSanction(rs.getString("ID_SANCTION"));
            sanction.setUtilisateur(new Utilisateur().findUtilisateurById(connection,rs.getString("ID_UTILISATEUR")));
            sanction.setEmprunt(new Emprunt().findEmpruntById(connection,rs.getString("ID_EMPRUNT")));
            sanction.setDebutSanction(rs.getDate("DEBUT_SANCTION"));
            sanction.setFinSanction(rs.getDate("FIN_SANCTION"));
        }
        statement.close();
        rs.close();

        return sanction;
    }

    public boolean verifSanction(Connection connection,Date dateEmprunt,String idUtilisateur) throws Exception{
        boolean val = false;
        
        Sanction dernierSanction = getLastSanction(connection, idUtilisateur);

        if(dernierSanction.getFinSanction() != null){
            if(dateEmprunt.before(dernierSanction.getFinSanction())){
                val = true;
            }    
            if(val == true){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy"); 
                String formattedDate = dernierSanction.getFinSanction().toLocalDate().format(formatter);
                throw new Exception("Vous avez encore une pénalité jusqu'au " + formattedDate );
            }    
        }

        return val;
    }

    public List<Sanction> getListeSanction() throws Exception{
        List<Sanction> liste = new ArrayList<Sanction>();
        Connection connection = DBCPDataSource.getConnection();
        String sql = "SELECT * FROM SANCTION ORDER BY FIN_SANCTION ASC";
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            Sanction sanc = new Sanction();
            sanc.setIdSanction(rs.getString("ID_SANCTION"));
            sanc.setUtilisateur(new Utilisateur().findUtilisateurById(connection, rs.getString("ID_UTILISATEUR")));
            sanc.setEmprunt(new Emprunt().findEmpruntById(connection,rs.getString("ID_EMPRUNT")));
            sanc.setDebutSanction(rs.getDate("DEBUT_SANCTION"));
            sanc.setFinSanction(rs.getDate("FIN_SANCTION"));

            liste.add(sanc);
        }

        return liste;
    }

}
