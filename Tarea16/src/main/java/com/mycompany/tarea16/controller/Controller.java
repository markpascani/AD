/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea16.controller;

import com.mycompany.tarea16.model.dao.AlumnoDAOImpl;
import com.mycompany.tarea16.model.dao.FicheroJsonDAOImpl;
import com.mycompany.tarea16.model.dao.GrupoDAOImpl;
import com.mycompany.tarea16.model.entities.Alumno;
import com.mycompany.tarea16.model.entities.Grupo;
import com.mycompany.tarea16.model.entities.GrupoFactory;
import com.mycompany.tarea16.view.IVista;
import com.mycompany.tarea16.view.VistaConsola;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author mihai
 */
public class Controller {

    public static void main(String[] args) {
        // Inicializar DAOs y vista
        AlumnoDAOImpl alumnoDAO = new AlumnoDAOImpl();
        GrupoDAOImpl grupoDAO = new GrupoDAOImpl();
        IVista vista = new VistaConsola();
        FicheroJsonDAOImpl ficheroDAO = new FicheroJsonDAOImpl();

        // Ejecutar el controlador
        new Controller().ejecutar(alumnoDAO, grupoDAO, vista, ficheroDAO);
    }

    public void ejecutar(AlumnoDAOImpl alumnoDAO, GrupoDAOImpl grupoDAO, IVista vista, FicheroJsonDAOImpl ficheroDAO) {
        boolean salir = false;

        while (!salir) {
            vista.mostrarMenu();
            try {
                int opcion = Integer.parseInt(vista.solicitarEntrada("Seleccione una opción"));

                switch (opcion) {
                    case 1 ->
                        insertarAlumno(alumnoDAO, grupoDAO, vista);
                    case 2 ->
                        insertarGrupo(grupoDAO, vista);
                    case 3 ->
                        mostrarTodosLosAlumnos(alumnoDAO, vista);
                    case 4 ->
                        mostrarAlumnosDeUnGrupo(grupoDAO, alumnoDAO, vista);
                    case 5 ->
                        cambiarDeGrupoAUnAlumno(alumnoDAO, grupoDAO, vista);
                    case 6 ->
                        modificarAlumnoPorPK(alumnoDAO, vista);
                    case 7 ->
                        eliminarAlumnoPorPK(alumnoDAO, vista);
                    case 8 ->
                        eliminarAlumnosPorCurso(alumnoDAO, grupoDAO, vista);
                    case 9 ->
                        guardarGrupoEnJson(grupoDAO, alumnoDAO, vista, ficheroDAO);
                    case 10 ->
                        mostrarAlumno(alumnoDAO, vista);
                    default -> {
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

    private void insertarAlumno(AlumnoDAOImpl alumnoDAO, GrupoDAOImpl grupoDAO, IVista vista) {
        try {
            vista.mostrarFormularioAlumnoNuevo();
            String nombre = vista.solicitarEntrada("Ingrese Nombre");
            String apellidos = vista.solicitarEntrada("Ingrese Apellidos");
            char genero = vista.solicitarEntrada("Ingrese Género (M/F)").toUpperCase().charAt(0);
            LocalDate fechaNacimiento = LocalDate.parse(vista.solicitarEntrada("Ingrese Fecha de Nacimiento (YYYY-MM-DD)"));
            int idGrupo = Integer.parseInt(vista.solicitarEntrada("Ingrese ID del Grupo"));

            Grupo grupo = grupoDAO.obtenerPorId(idGrupo);
            if (grupo == null) {
                vista.mostrarMensaje("El grupo no existe.");
                return;
            }

            Alumno alumno = new Alumno(nombre, apellidos, genero, fechaNacimiento, grupo);
            if (alumnoDAO.insertar(alumno)) {
                vista.mostrarMensaje("Alumno insertado correctamente.");
            } else {
                vista.mostrarMensaje("Error al insertar el alumno.");
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void insertarGrupo(GrupoDAOImpl grupoDAO, IVista vista) {
        try {
            GrupoFactory grupoFactory = new GrupoFactory("DAM", "1º"); // Valores predeterminados

            String ciclo = vista.solicitarEntrada("Ingrese Ciclo");
            String curso = vista.solicitarEntrada("Ingrese Curso");
            
            
            Grupo grupo = grupoFactory.crear(ciclo, curso); // Crear el grupo usando la fábrica

            if (grupoDAO.insertar(grupo)) {
                vista.mostrarMensaje("Grupo insertado correctamente.");
            } else {
                vista.mostrarMensaje("Error al insertar el grupo.");
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void mostrarTodosLosAlumnos(AlumnoDAOImpl alumnoDAO, IVista vista) {
        List<Alumno> alumnos = alumnoDAO.obtenerTodos();
        if (alumnos.isEmpty()) {
            vista.mostrarMensaje("No hay alumnos registrados.");
        } else {
            vista.mostrarAlumnos(alumnos);
        }
    }

    private void mostrarAlumnosDeUnGrupo(GrupoDAOImpl grupoDAO, AlumnoDAOImpl alumnoDAO, IVista vista) {
        List<Grupo> grupos = grupoDAO.obtenerTodos();
        vista.mostrarGrupos(grupos);

        try {
            int idGrupo = Integer.parseInt(vista.solicitarEntrada("Elija un grupo por ID"));

            // Filtrar alumnos pertenecientes al grupo seleccionado
            List<Alumno> alumnosDelGrupo = alumnoDAO.obtenerTodos().stream()
                    .filter(alumno -> alumno.getGrupo().getGrupo() == idGrupo)
                    .toList();

            // Mostrar los alumnos del grupo seleccionado
            if (alumnosDelGrupo.isEmpty()) {
                vista.mostrarMensaje("No hay alumnos en este grupo.");
            } else {
                vista.mostrarAlumnos(alumnosDelGrupo);
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: Debe ingresar un número válido.");
        }
    }

    private void cambiarDeGrupoAUnAlumno(AlumnoDAOImpl alumnoDAO, GrupoDAOImpl grupoDAO, IVista vista) {
        List<Alumno> alumnos = alumnoDAO.obtenerTodos();
        vista.mostrarAlumnos(alumnos);
        String respuesta = vista.solicitarEntrada("Selecciona el alumno al que quieres modificarle el grupo.");
        Alumno alumnoModificable = alumnoDAO.obtenerPorId(Integer.parseInt(respuesta));

        List<Grupo> grupos = grupoDAO.obtenerTodos();
        vista.mostrarGrupos(grupos);
        String respuesta2 = vista.solicitarEntrada("Selecciona el grupo nuevo: ");
        Grupo grupoNuevo = grupoDAO.obtenerPorId(Integer.parseInt(respuesta2));

        alumnoModificable.setGrupo(grupoNuevo);
        boolean respuestaServidor = alumnoDAO.actualizar(alumnoModificable);
        if (respuestaServidor) {
            vista.mostrarMensaje("Alumno modificado con éxito.");
        } else {
            vista.mostrarMensaje("No se ha modificado correctamente.");
        }

    }

    private void modificarAlumnoPorPK(AlumnoDAOImpl alumnoDAO, IVista vista) {
        try {
            int nia = Integer.parseInt(vista.solicitarEntrada("Ingrese NIA del alumno a modificar"));
            String nuevoNombre = vista.solicitarEntrada("Ingrese el nuevo nombre");

            Alumno alumno = alumnoDAO.obtenerPorId(nia);
            if (alumno == null) {
                vista.mostrarMensaje("Alumno no encontrado.");
                return;
            }

            alumno.setNombre(nuevoNombre);
            if (alumnoDAO.actualizar(alumno)) {
                vista.mostrarMensaje("Alumno actualizado correctamente.");
            } else {
                vista.mostrarMensaje("Error al actualizar el alumno.");
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void eliminarAlumnoPorPK(AlumnoDAOImpl alumnoDAO, IVista vista) {
        try {
            int nia = Integer.parseInt(vista.solicitarEntrada("Ingrese NIA del alumno a eliminar"));
            if (alumnoDAO.eliminarPorId(nia)) {
                vista.mostrarMensaje("Alumno eliminado correctamente.");
            } else {
                vista.mostrarMensaje("No se pudo eliminar el alumno o no existe.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: El NIA debe ser un número válido.");
        }
    }

    private void eliminarAlumnosPorCurso(AlumnoDAOImpl alumnoDAO, GrupoDAOImpl grupoDAO, IVista vista) {
        try {
            String ciclo = vista.solicitarEntrada("Ingrese Ciclo del curso");
            String curso = vista.solicitarEntrada("Ingrese Curso");
            List<Grupo> grupos = grupoDAO.obtenerTodos();

            for (Grupo grupo : grupos) {
                if (grupo.getCiclo().equalsIgnoreCase(ciclo) && grupo.getCurso().equalsIgnoreCase(curso)) {
                    alumnoDAO.obtenerTodos().removeIf(a -> a.getGrupo().equals(grupo));
                    vista.mostrarMensaje("Alumnos del curso eliminados correctamente.");
                    return;
                }
            }
            vista.mostrarMensaje("No se encontraron alumnos para el curso.");
        } catch (Exception e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void guardarGrupoEnJson(GrupoDAOImpl grupoDAO, AlumnoDAOImpl alumnoDAO, IVista vista, FicheroJsonDAOImpl fichero) {
        List<Grupo> grupos = grupoDAO.obtenerTodos();
        vista.mostrarGrupos(grupos);

        try {
            String respuesta2 = vista.solicitarEntrada("Selecciona el grupo por ID: ");
            Grupo grupoAGuardar = grupoDAO.obtenerPorId(Integer.parseInt(respuesta2));

            if (grupoAGuardar == null) {
                vista.mostrarMensaje("El grupo seleccionado no existe.");
                return;
            }

            // Filtrar alumnos que pertenezcan al grupo seleccionado
            List<Alumno> alumnosDelGrupo = alumnoDAO.obtenerTodos().stream()
                    .filter(alumno -> alumno.getGrupo() != null && alumno.getGrupo().getGrupo() == grupoAGuardar.getGrupo())
                    .toList();

            if (alumnosDelGrupo.isEmpty()) {
                vista.mostrarMensaje("No hay alumnos en el grupo seleccionado.");
            } else {
                fichero.guardarGrupoYAlumnos(grupoAGuardar, alumnosDelGrupo);
                vista.mostrarMensaje("Grupo y alumnos guardados correctamente en el fichero JSON.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: Debe ingresar un número válido.");
        } catch (Exception e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void mostrarAlumno(AlumnoDAOImpl alumnoDAO, IVista vista) {
        List<Alumno> alumnos = alumnoDAO.obtenerTodos();

        if (alumnos.isEmpty()) {
            vista.mostrarMensaje("No hay alumnos registrados.");
            return;
        }

        // Mostrar PK y nombre de cada alumno
        alumnos.forEach(alumno -> vista.mostrarMensaje("ID: " + alumno.getNia() + " - Nombre: " + alumno.getNombre()));

        try {
            int idSeleccionado = Integer.parseInt(vista.solicitarEntrada("Selecciona el ID del alumno que desea ver: "));
            Alumno alumnoSeleccionado = alumnos.stream()
                    .filter(alumno -> alumno.getNia() == idSeleccionado)
                    .findFirst()
                    .orElse(null);

            if (alumnoSeleccionado != null) {
                vista.mostrarAlumno(alumnoSeleccionado);
            } else {
                vista.mostrarMensaje("No se encontró un alumno con ese ID.");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: Debe ingresar un número válido.");
        }
    }
}
