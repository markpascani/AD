/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycompany.tarea17.model.entities.Alumno;
import com.mycompany.tarea17.model.entities.Grupo;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author mihai
 */
public class FicheroJsonImpl {

    private static final String FILE_PATH = "grupos.json";
    private final ObjectMapper objectMapper;

    public FicheroJsonImpl() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public boolean guardarGrupoYAlumnos(Grupo grupo, List<Alumno> alumnos) {
        try {


            // Crear la estructura para escribir en JSON
            ObjectNode root = objectMapper.createObjectNode();

            // Agregar el grupo al JSON
            ArrayNode gruposNode = objectMapper.createArrayNode();
            gruposNode.add(objectMapper.valueToTree(grupo));
            root.set("grupos", gruposNode);

            // Agregar los alumnos al JSON
            ArrayNode alumnosNode = objectMapper.createArrayNode();
            for (Alumno alumno : alumnos) {
                if (alumno.getGrupo().getGrupo() == grupo.getGrupo()) {
                    alumnosNode.add(objectMapper.valueToTree(alumno));
                }
            }
            root.set("alumnos", alumnosNode);

            // Escribir directamente al archivo, sobrescribiéndolo
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), root);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
