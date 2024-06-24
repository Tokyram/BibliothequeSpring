package com.example.bibliotheque.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeMembre {
    String idType;
    String nomType;
    int dureeEmprunt;
    float coefficientRetard;

    public TypeMembre findTypeById(Connection con , String id) throws Exception{
        TypeMembre val = new TypeMembre();

        String sql = "SELECT * FROM TYPE_MEMBRE WHERE ID_TYPE = '" + id + "'";

        Statement state = con.createStatement();
        
        ResultSet rs = state.executeQuery(sql);
        while(rs.next()){
            val.setIdType(rs.getString("ID_TYPE"));
            val.setNomType(rs.getString("TYPE_MEMBRE"));
            val.setDureeEmprunt(rs.getInt("DUREE_EMPRUNT"));
            val.setCoefficientRetard(rs.getFloat("COEFFICIENT_RETARD"));
        }

        rs.close();
        state.close();
        
        return val;
    }

    public List<TypeMembre> getAllTypeMembre() throws Exception{
        List<TypeMembre> liste = new ArrayList<TypeMembre>();
        Connection con = DBCPDataSource.getConnection();
        String sql = "SELECT * FROM TYPE_MEMBRE";

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while(rs.next()){
            TypeMembre temp = new TypeMembre();
            temp.setIdType(rs.getString("ID_TYPE"));
            temp.setNomType(rs.getString("TYPE_MEMBRE"));
            temp.setDureeEmprunt(rs.getInt("DUREE_EMPRUNT"));
            temp.setCoefficientRetard(rs.getFloat("COEFFICIENT_RETARD"));
            liste.add(temp);
        }

        rs.close();
        state.close();
        return liste;
    }
    
}
