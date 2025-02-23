/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.utils;

import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */
public class FicheroBinario implements IFichero{
    private static final Logger logger = LogManager.getLogger(FicheroBinario.class);
    private String ruta;
    
    public FicheroBinario(String ruta){
        this.ruta = ruta;
    }
    
 @Override
    public void escribirEnUnFicheroTodosLosAlumnos(List<Alumno> alumnos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(alumnos);
            logger.info("Se han guardado {} alumnos en el fichero {}", alumnos.size(), ruta);
        } catch (IOException e) {
            logger.error("Error al escribir en el fichero: {}", e.getMessage());
        }
    }

    @Override
    public List<Alumno> leerAlumnosDeUnFichero() {
        List<Alumno> alumnos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            alumnos = (List<Alumno>) ois.readObject();
            logger.info("Se han leído {} alumnos desde el fichero {}", alumnos.size(), ruta);
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error al leer el fichero: {}", e.getMessage());
        }
        return alumnos;
    }

    @Override
    public void escribirGruposEnFichero(List<Grupo> grupos) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Grupo> leerFicheroParaDevolverGrupos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
