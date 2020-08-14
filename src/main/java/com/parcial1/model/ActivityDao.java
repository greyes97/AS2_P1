package com.parcial1.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ActivityDao implements IActivityDao{
    ConectionSingleton conection = ConectionSingleton.getInstance();
    PreparedStatement parametro;

    @Override
    public void saveActivityDao(ActivityEntity activity) {
            try
            {

                conection.abrirConexion();
                String query="insert into t2_activity(name,description,qualification,id_course) values(?,?,?,?)";

                parametro= (PreparedStatement) conection.conexionBD.prepareStatement(query);
                parametro.setString(1, activity.getName());
                parametro.setString(2, activity.getDescription());
                parametro.setFloat(3, activity.getQualification());
                System.out.println(activity.getId_course());
                parametro.setInt(4, activity.getId_course());

                parametro.executeUpdate();
                System.out.println("Se ejecuto el insert perfectamente "+ parametro);
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
    }

    @Override
    public void deleteActivityDao(int id_activity) {
        conection.abrirConexion();
        String query = "delete from t2_activity where id_activity=?";
        try {
            parametro = (PreparedStatement) conection.conexionBD.prepareStatement(query);
            parametro.setInt(1, id_activity);
            parametro.executeUpdate();
            System.out.println("Se ha eliminado "+ parametro);
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    @Override
    public ActaEntity getActa(int id_activity) {
        ActaEntity acta = new ActaEntity();

        try {
            String query = "select ac.id_activity, count(ac.id_activity) as cantidad, avg(ac.qualification) as media,max(ac.qualification) as maximo, min(ac.qualification) as minimo " +
                    "from t2_activity ac  where ac.id_activity = "+id_activity+ " group by ac.id_activity";
            System.out.println(query);
            conection.abrirConexion();
            ResultSet rs = conection.conexionBD.createStatement().executeQuery(query);

            while (rs.next()){
                acta.setId_activity(Integer.parseInt(rs.getString("id_activity")));
                acta.setCantidad(Integer.parseInt(rs.getString("cantidad")));
                acta.setMedia(Float.parseFloat(rs.getString("media")));
                acta.setMaximo(Float.parseFloat(rs.getString("maximo")));
                acta.setMinimo(Float.parseFloat(rs.getString("minimo")));
            }
            return acta;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
