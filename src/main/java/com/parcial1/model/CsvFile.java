package com.parcial1.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFile implements IFileControl{
    @Override
    public void createFile(ActaEntity acta,String nameCourse) throws IOException {
        String cadena = "";
        FileWriter flwriter = null;
        cadena = "id_activity,"+acta.getId_activity()+",cantidad,"+acta.getCantidad()+",media,"+acta.getMedia()+",maximo,"+acta.getMaximo()+",minimo,"+acta.getMinimo();
        flwriter = new FileWriter("acta_"+nameCourse+"_"+acta.getId_activity()+".csv");
        String replace = cadena.replace(" ", ",");
        try (BufferedWriter bfwriter = new BufferedWriter(flwriter)) {
            bfwriter.write(replace);
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }
}
