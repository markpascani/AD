/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.dao.utils;

import com.mycompany.tarea17.model.entities.Alumno;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Mihai Stinga
 */
public class FicheroBinario {

    private static final String FILE_PATH = "alumnos.dat";
    private static final Logger logger = LogManager.getLogger(FicheroBinario.class);

    public FicheroBinario() {
    }

    public boolean guardarAlumnosEnFicheroBinario(List<Alumno> alumnos) {

        try (FileOutputStream fos = new FileOutputStream(FILE_PATH); ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            alumnos.forEach(alumno -> {
                try {
                    oos.writeObject(alumno);
                } catch (IOException ex) {
                    logger.error("No se ha podido escribir en el fichero binario"+ex);
                }
            });
            return true;
        } catch (IOException e) {
            logger.error("No se ha podido escribir en el fichero binario"+e);
            return false;
        }
    }
    
    public List<Alumno> leerAlumnosDeFicheroBinario(){
        List<Alumno> alumnos = new ArrayList<Alumno>();
        
        try(FileInputStream fis = new FileInputStream(FILE_PATH);
                ObjectInputStream ois = new ObjectInputStream(fis)){
            while(ois.available() != -1){
                Alumno alumno = (Alumno) ois.readObject();
                alumnos.add(alumno);
            }
        }catch (IOException e) {
            logger.error("No se ha podido escribir en el fichero binario"+e);
        } catch (ClassNotFoundException ex) {
            logger.error("No se ha podido convertir a una clase compatible"+ex);
        }
        return alumnos;
    }

}
