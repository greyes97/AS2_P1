package com.parcial1.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDao implements ICouseDao {
    @Override
    public CourseEntity getCourseDao(String whatCourse) {

        ConectionSingleton conection = ConectionSingleton.getInstance();
        CourseEntity entity = new CourseEntity();

        String coour = "'"+whatCourse+"'";

        try {
            conection.abrirConexion();
            String query = "select * from t2_course where name =  "+coour;
            System.out.println(query);
            ResultSet rs = conection.conexionBD.createStatement().executeQuery(query);

            while (rs.next()){
                entity.setId_course(Integer.parseInt(rs.getString("id_course")));
                entity.setName(rs.getString("name"));
                entity.setDescription(rs.getString("description"));
            }
            System.out.println(entity.getDescription());
            if(entity.getDescription()==null){
                return null;
            }

            return entity;

        }catch (Exception ex){
            System.out.println(ex);
        }

        return null;
    }

    @Override
    public List<ActivityEntity> getListActividadCourse(String whatCourse) {
        ConectionSingleton conection = ConectionSingleton.getInstance();
        String coour = "'"+whatCourse+"'";

        List<ActivityEntity>actividades = new ArrayList<>();
        try{
            conection.abrirConexion();
            String query = "select ac.id_activity,ac.name,ac.description,ac.qualification from t2_activity ac inner join t2_course as co on " +
                    "ac.id_course = co.id_course where co.name = "+coour;
            System.out.println(query);
            ResultSet rs = conection.conexionBD.createStatement().executeQuery(query);

            while (rs.next()){
                ActivityEntity nuevo = new ActivityEntity();
                nuevo.setId_activity(Integer.parseInt(rs.getString(1)));
                nuevo.setName(rs.getString("name"));
                nuevo.setDescription(rs.getString("description"));
                nuevo.setQualification(Float.parseFloat(rs.getString("qualification")));
                actividades.add(nuevo);
            }
            if(actividades.isEmpty()){
                return null;
            }else {
                return actividades;
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
        return actividades;
    }
}
