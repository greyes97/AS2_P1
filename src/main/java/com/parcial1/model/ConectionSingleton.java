package com.parcial1.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionSingleton {

    public static ConectionSingleton conection;
    public Connection conexionBD;

    private ConectionSingleton(){

    }
    public static ConectionSingleton getInstance(){
        if(conection == null){
           conection = new ConectionSingleton();
        }
        return conection;
    }




    public void abrirConexion() {

        try {
            String jdbc = "com.mysql.cj.jdbc.Driver";
            Class.forName(jdbc);
            String urlConexion = "jdbc:mysql://192.185.4.65:3306/jbarilla_ingsoftware?serverTimezone=UTC";
            String usuario = "jbarilla_estudia";
            String contra = "2Ui!OssHDQGv";
            conexionBD = DriverManager.getConnection(urlConexion, usuario, contra);
            System.out.println("Conexion eexitosa");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}