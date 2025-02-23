/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */
public class FicheroJson implements IFichero {

    private static final Logger logger = LogManager.getLogger(FicheroJson.class);
    private String ruta;

    public FicheroJson(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public void escribirEnUnFicheroTodosLosAlumnos(List<Alumno> alumnos) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        for (Alumno alumno : alumnos) {
            ObjectNode alumnoNode = mapper.createObjectNode();
            alumnoNode.put("NIA", alumno.getNIA());
            alumnoNode.put("Nombre", alumno.getNombre());
            alumnoNode.put("Apellidos", alumno.getApellidos());
            alumnoNode.put("Genero", String.valueOf(alumno.getGenero()));
            alumnoNode.put("FechaDeNacimiento", alumno.getFechaDeNacimiento().toString());
            if (alumno.getGrupo() != null) {
                ObjectNode grupoNode = mapper.createObjectNode();
                grupoNode.put("ID", alumno.getGrupo().getId());
                grupoNode.put("Ciclo", alumno.getGrupo().getCiclo());
                grupoNode.put("Curso", alumno.getGrupo().getCurso());
                alumnoNode.set("Grupo", grupoNode);
            }
            arrayNode.add(alumnoNode);
        }

        try (FileWriter file = new FileWriter(ruta)) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, arrayNode);
            logger.info("Se han guardado {} alumnos en el fichero JSON {}", alumnos.size(), ruta);
        } catch (IOException e) {
            logger.error("Error al escribir en el fichero JSON: {}", e.getMessage());
        }
    }

    @Override
    public List<Alumno> leerAlumnosDeUnFichero() {
        List<Alumno> alumnos = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try (FileReader file = new FileReader(ruta)) {
            JsonNode rootNode = mapper.readTree(file);

            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    Grupo grupo = null;
                    if (node.has("Grupo") && !node.get("Grupo").isNull()) {
                        JsonNode grupoNode = node.get("Grupo");
                        grupo = new Grupo(
                                grupoNode.get("ID").asInt(),
                                grupoNode.get("Ciclo").asText(),
                                grupoNode.get("Curso").asText(),
                                new ArrayList<>()
                        );
                    }

                    Alumno alumno = new Alumno(
                            node.get("NIA").asInt(),
                            node.get("Nombre").asText(),
                            node.get("Apellidos").asText(),
                            node.get("Genero").asText().charAt(0),
                            java.time.LocalDate.parse(node.get("FechaDeNacimiento").asText()),
                            grupo
                    );
                    alumnos.add(alumno);
                }
            }
            logger.info("Se han leído {} alumnos desde el fichero JSON {}", alumnos.size(), ruta);
        } catch (IOException e) {
            logger.error("Error al leer el fichero JSON: {}", e.getMessage());
        }

        return alumnos;
    }

    @Override
    public void escribirGruposEnFichero(List<Grupo> grupos) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        for (Grupo grupo : grupos) {
            ObjectNode grupoNode = mapper.createObjectNode();
            grupoNode.put("ID", grupo.getId());
            grupoNode.put("Ciclo", grupo.getCiclo());
            grupoNode.put("Curso", grupo.getCurso());

            ArrayNode alumnosNode = mapper.createArrayNode();
            for (Alumno alumno : grupo.getAlumnos()) {
                ObjectNode alumnoNode = mapper.createObjectNode();
                alumnoNode.put("NIA", alumno.getNIA());
                alumnoNode.put("Nombre", alumno.getNombre());
                alumnoNode.put("Apellidos", alumno.getApellidos());
                alumnoNode.put("Genero", String.valueOf(alumno.getGenero()));
                alumnoNode.put("FechaDeNacimiento", alumno.getFechaDeNacimiento().toString());
                alumnosNode.add(alumnoNode);
            }
            grupoNode.set("Alumnos", alumnosNode);
            arrayNode.add(grupoNode);
        }

        try (FileWriter file = new FileWriter(ruta)) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, arrayNode);
            logger.info("Se han guardado {} grupos en el fichero JSON {}", grupos.size(), ruta);
        } catch (IOException e) {
            logger.error("Error al escribir en el fichero JSON: {}", e.getMessage());
        }
    }

    @Override
    public List<Grupo> leerFicheroParaDevolverGrupos() {
        List<Grupo> grupos = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try (FileReader file = new FileReader(ruta)) {
            JsonNode rootNode = mapper.readTree(file);

            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    List<Alumno> alumnos = new ArrayList<>();
                    JsonNode alumnosNode = node.get("Alumnos");
                    if (alumnosNode != null && alumnosNode.isArray()) {
                        for (JsonNode alumnoNode : alumnosNode) {
                            Alumno alumno = new Alumno(
                                    alumnoNode.get("NIA").asInt(),
                                    alumnoNode.get("Nombre").asText(),
                                    alumnoNode.get("Apellidos").asText(),
                                    alumnoNode.get("Genero").asText().charAt(0),
                                    java.time.LocalDate.parse(alumnoNode.get("FechaDeNacimiento").asText()),
                                    null
                            );
                            alumnos.add(alumno);
                        }
                    }

                    Grupo grupo = new Grupo(
                            node.get("ID").asInt(),
                            node.get("Ciclo").asText(),
                            node.get("Curso").asText(),
                            alumnos
                    );

                    for (Alumno alumno : alumnos) {
                        alumno.setGrupo(grupo);
                    }

                    grupos.add(grupo);
                }
            }
            logger.info("Se han leído {} grupos desde el fichero JSON {}", grupos.size(), ruta);
        } catch (IOException e) {
            logger.error("Error al leer el fichero JSON: {}", e.getMessage());
        }

        return grupos;
    }
}
