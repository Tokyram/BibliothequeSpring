package com.example.bibliotheque.service;

import java.sql.Connection;
import com.example.bibliotheque.bean.TypeMembre;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeMembreService {

    public List<TypeMembre> getListeType(){
        List<TypeMembre> liste = new ArrayList<TypeMembre>();
        try{
            liste = new TypeMembre().getAllTypeMembre();
        }catch(Exception e){
            e.printStackTrace();
        }
        return liste;
    }

    
}
