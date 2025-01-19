/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea15.controller;

import com.mycompany.tarea15.model.dao.AlumnoDAOImplDB;
import com.mycompany.tarea15.model.dao.GrupoDAOImplDB;
import com.mycompany.tarea15.model.dao.IAlumnoDAO;
import com.mycompany.tarea15.model.dao.IGrupoDAO;
import com.mycompany.tarea15.model.entidades.Alumno;
import com.mycompany.tarea15.model.entidades.Grupo;
import com.mycompany.tarea15.view.VistaConsola;
import com.mycompany.tarea15.view.interfaces.IVista;
import java.time.LocalDate;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */
public class Controller {

    private static final Logger logger = LogManager.getLogger(Controller.class);

    public static void main(String[] args) {
        // Inicializar modelo y vista
        IAlumnoDAO alumnoDAO = new AlumnoDAOImplDB();
        IVista vista = new VistaConsola();
        IGrupoDAO grupoDAO = new GrupoDAOImplDB();

        logger.info("Información de prueba");
        logger.error("Error de prueba");
        // Ejecutar el controlador
        // Lanzar el controlador
        new Controller().ejecutar(alumnoDAO, grupoDAO, vista);
    }

    public void ejecutar(IAlumnoDAO alumnoDAO, IGrupoDAO grupoDAO, IVista vista) {
        boolean salir = false;

        while (!salir) {
            vista.mostrarMenu(); 
            try {
                int opcion = Integer.parseInt(vista.solicitarEntrada("Seleccione una opción"));

                switch (opcion) {
                    case 1 ->
                        insertarAlumno(alumnoDAO, vista);
                    case 2 ->
                        insertarGrupo(grupoDAO, vista);
                    case 3 ->
                        mostrarTodosLosAlumnos(alumnoDAO, vista);
                    case 4 ->
                        guardarAlumnosEnFichero(alumnoDAO, vista);
                    case 5 ->
                        leerAlumnosDeFicheroYGuardarEnBD(alumnoDAO, vista);
                    case 6 ->
                        modificarAlumnoPorPK(alumnoDAO, vista);
                    case 7 ->
                        eliminarAlumnoPorPK(alumnoDAO, vista);
                    case 8 ->
                        eliminarAlumnosPorCurso(alumnoDAO, grupoDAO, vista);
                    case 9 ->
                        guardarGruposEnJson(grupoDAO, vista);
                    case 10 ->
                        leerGruposDeJsonYGuardarEnBD(grupoDAO, vista);
                    default -> {
                        // Si se ingresa 0 u otro valor, se asume salida.
                        if (opcion == 0) {
                            vista.mostrarMensaje("Saliendo del programa...");
                            salir = true;
                        } else {
                            vista.mostrarMensaje("Opción no válida. Intente de nuevo.");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                vista.mostrarMensaje("Error: Debe ingresar un número válido.");
            }
        }
    }

    // --------------------------------------------------------------------------------
    // 1. Insertar alumno nuevo
    // --------------------------------------------------------------------------------
    private void insertarAlumno(IAlumnoDAO alumnoDAO, IVista vista) {
        try {
            vista.mostrarFormularioAlumnoNuevo();
            int nia = Integer.parseInt(vista.solicitarEntrada("Ingrese NIA"));
            String nombre = vista.solicitarEntrada("Ingrese Nombre");
            String apellidos = vista.solicitarEntrada("Ingrese Apellidos");
            String ciclo = vista.solicitarEntrada("Ingrese Ciclo");
            String curso = vista.solicitarEntrada("Ingrese Curso");
            char genero = vista.solicitarEntrada("Ingrese Género (M/F)")
                    .toUpperCase()
                    .charAt(0);
            LocalDate fechaDeNacimiento = LocalDate.parse(
                    vista.solicitarEntrada("Ingrese Fecha de Nacimiento (YYYY-MM-DD)")
            );
            int idGrupo = Integer.parseInt(vista.solicitarEntrada("Ingrese id del Grupo (FK)"));

            // Crear alumno y guardar en BD
            Alumno alumno = new Alumno(nia, nombre, apellidos, genero,
                    fechaDeNacimiento, ciclo, curso, idGrupo);

            if (alumnoDAO.insertarAlumno(alumno)) {
                vista.mostrarMensaje("Alumno insertado correctamente.");
            } else {
                vista.mostrarMensaje("Error al insertar el alumno.");
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error al procesar los datos: " + e.getMessage());
        }
    }

    // --------------------------------------------------------------------------------
    // 2. Insertar grupo nuevo
    // --------------------------------------------------------------------------------
    private void insertarGrupo(IGrupoDAO grupoDAO, IVista vista) {
        try {
            vista.mostrarMensaje("Formulario para insertar un nuevo grupo:");
            int id = Integer.parseInt(vista.solicitarEntrada("Ingrese ID del grupo"));
            String nombre = vista.solicitarEntrada("Ingrese Nombre del grupo");

            Grupo grupo = new Grupo(id, nombre);

            if (grupoDAO.insertarGrupo(grupo)) {
                vista.mostrarMensaje("Grupo insertado correctamente.");
            } else {
                vista.mostrarMensaje("Error al insertar el grupo.");
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error al procesar los datos: " + e.getMessage());
        }
    }

    // --------------------------------------------------------------------------------
    // 3. Mostrar todos los alumnos
    // --------------------------------------------------------------------------------
    private void mostrarTodosLosAlumnos(IAlumnoDAO alumnoDAO, IVista vista) {
        var alumnos = alumnoDAO.getAlumnos();
        if (alumnos.isEmpty()) {
            vista.mostrarMensaje("No hay alumnos en la base de datos.");
        } else {
            vista.mostrarAlumnos(alumnos);
        }
    }

    // --------------------------------------------------------------------------------
    // 4. Guardar alumnos en fichero (placeholder)
    // --------------------------------------------------------------------------------
    private void guardarAlumnosEnFichero(IAlumnoDAO alumnoDAO, IVista vista) {

        vista.mostrarMensaje("Funcionalidad no implementada: Guardar alumnos en fichero.");
    }

    // --------------------------------------------------------------------------------
    // 5. Leer alumnos de un fichero y guardarlo en BD (placeholder)
    // --------------------------------------------------------------------------------
    private void leerAlumnosDeFicheroYGuardarEnBD(IAlumnoDAO alumnoDAO, IVista vista) {

        vista.mostrarMensaje("Funcionalidad no implementada: Leer fichero y guardar alumnos en BD.");
    }

    // --------------------------------------------------------------------------------
    // 6. Modificar el nombre de un alumno por NIA
    // --------------------------------------------------------------------------------
    private void modificarAlumnoPorPK(IAlumnoDAO alumnoDAO, IVista vista) {
        try {
            vista.mostrarFormularioActualizacionPorPk();
            int nia = Integer.parseInt(vista.solicitarEntrada("Ingrese NIA del alumno a actualizar"));
            String nuevoNombre = vista.solicitarEntrada("Ingrese el nuevo nombre");

            if (alumnoDAO.actualizarAlumnoPorPK(nia, nuevoNombre)) {
                vista.mostrarMensaje("Alumno actualizado correctamente.");
            } else {
                vista.mostrarMensaje("No se encontró alumno o error al actualizar.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: El NIA debe ser un número válido.");
        }
    }

    // --------------------------------------------------------------------------------
    // 7. Eliminar alumno por NIA
    // --------------------------------------------------------------------------------
    private void eliminarAlumnoPorPK(IAlumnoDAO alumnoDAO, IVista vista) {
        try {
            vista.mostrarFormularioEliminacionPorPk();
            int nia = Integer.parseInt(vista.solicitarEntrada("Ingrese NIA del alumno a eliminar"));

            if (alumnoDAO.eliminarAlumnoPorPK(nia)) {
                vista.mostrarMensaje("Alumno eliminado correctamente.");
            } else {
                vista.mostrarMensaje("No se encontró el alumno o error al eliminar.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: El NIA debe ser un número válido.");
        }
    }

    // --------------------------------------------------------------------------------
    // 8. Eliminar alumnos por curso
    // --------------------------------------------------------------------------------
    private void eliminarAlumnosPorCurso(IAlumnoDAO alumnoDAO, IGrupoDAO grupoDAO, IVista vista) {
        
        vista.mostrarGrupos(grupoDAO.getGrupos());
        int curso = Integer.parseInt(vista.solicitarEntrada("Ingrese el curso cuyos alumnos desea eliminar"));
        // Asegúrate de haber implementado el método eliminarAlumnosPorCurso(curso) en IAlumnoDAO / AlumnoDAOImplDB
        if (alumnoDAO.eliminarAlumnosPorCurso(curso)) {
            vista.mostrarMensaje("Alumnos del curso '" + curso + "' eliminados correctamente.");
        } else {
            vista.mostrarMensaje("Error al eliminar o no había alumnos de ese curso.");
        }
    }

    // --------------------------------------------------------------------------------
    // 9. Guardar todos los grupos (con alumnos) en fichero JSON (placeholder)
    // --------------------------------------------------------------------------------
    private void guardarGruposEnJson(IGrupoDAO grupoDAO, IVista vista) {

        vista.mostrarMensaje("Funcionalidad no implementada: Guardar grupos (con alumnos) en JSON.");
    }

    // --------------------------------------------------------------------------------
    // 10. Leer fichero JSON de grupos y guardarlos en BD (placeholder)
    // --------------------------------------------------------------------------------
    private void leerGruposDeJsonYGuardarEnBD(IGrupoDAO grupoDAO, IVista vista) {

        vista.mostrarMensaje("Funcionalidad no implementada: Leer grupos de JSON y guardarlos en BD.");
    }
}
