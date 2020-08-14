package com.parcial1.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddQualificationDao implements IAddQualificationDao {
    ConectionSingleton conection = ConectionSingleton.getInstance();
    PreparedStatement parametro;
    @Override
    public List<StudentEntity> getListStudent(String whatCourse) {
        String coour = "'"+whatCourse+"'";

        List<StudentEntity>listStudent = new ArrayList<>();


        try{
            conection.abrirConexion();
            String query = "select st.id_student, concat(st.name,' ',st.surname)as name from t2_student_rel_course sc inner join t2_student as st on " +
                    "st.id_student = sc.id_student inner join t2_course as co on " +
                    "co.id_course = sc.id_course where co.name = "+coour;
            System.out.println(query);
            ResultSet rs = conection.conexionBD.createStatement().executeQuery(query);

            while (rs.next()){
                StudentEntity student = new StudentEntity();
                student.setId_student(Integer.parseInt(rs.getString("id_student")));
                student.setName(rs.getString("name"));
                listStudent.add(student);
            }


            if(listStudent.isEmpty()){
                return null;
            }else {
                return listStudent;
            }



        }catch (Exception ex){
            System.out.println(ex);
        }

        return listStudent;
    }

    @Override
    public List<ActivityEntity> getListActivity(String whatCourse) {
        String coour = "'"+whatCourse+"'";

        List<ActivityEntity>listActiviy = new ArrayList<>();


        try{
            conection.abrirConexion();
            String query = "SELECT ac.id_activity, ac.name,ac.qualification FROM t2_activity ac inner join t2_course as co on " +
                    "co.id_course = ac.id_course where co.name = "+coour;
            System.out.println(query);
            ResultSet rs = conection.conexionBD.createStatement().executeQuery(query);

            while (rs.next()){
                ActivityEntity activityEntity = new ActivityEntity();
                activityEntity.setId_activity(Integer.parseInt(rs.getString("id_activity")));
                activityEntity.setName(rs.getString("name"));
                activityEntity.setQualification(Float.parseFloat(rs.getString("qualification")));
                listActiviy.add(activityEntity);
            }


            if(listActiviy.isEmpty()){
                return null;
            }else {
                return listActiviy;
            }



        }catch (Exception ex){
            System.out.println(ex);
        }

        return listActiviy;
    }

    @Override
    public boolean saveResult(ResultEntity entity) {
        try
        {

            conection.abrirConexion();
            String query="insert into t2_result(id_student,id_activity,result) values(?,?,?)";

            parametro= (PreparedStatement) conection.conexionBD.prepareStatement(query);
            parametro.setInt(1, entity.getId_student());
            parametro.setInt(2, entity.getId_activity());
            parametro.setFloat(3, entity.getResult());
            parametro.executeUpdate();
            System.out.println("Se ejecuto el insert perfectamente "+ parametro);
            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            return false;
        }

    }

    @Override
    public float validationAdd(int id_course) {
        float valor =0;
        try {
            conection.abrirConexion();
            String query = "SELECT qualification FROM t2_activity where id_activity = "+id_course;
            ResultSet rs = conection.conexionBD.createStatement().executeQuery(query);
            System.out.println(query);
            while (rs.next()){
                valor = Float.parseFloat(rs.getString("qualification"));
            }
            System.out.println(valor);
            return valor;

        }catch (Exception ex){
            return 0;
        }
    }

}
