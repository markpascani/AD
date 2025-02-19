/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.controller;

import com.mycompany.tarea17.model.dao.utils.FicheroJson;
import com.mycompany.tarea17.model.dao.interfaces.IAlumnoDAO;
import com.mycompany.tarea17.model.dao.interfaces.IGrupoDAO;
import com.mycompany.tarea17.model.entities.Alumno;
import com.mycompany.tarea17.model.entities.Grupo;
import com.mycompany.tarea17.model.entities.GrupoFactory;
import com.mycompany.tarea17.model.dao.utils.FicheroBinario;
import com.mycompany.tarea17.view.IVista;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author mihai
 */
public class Controller {

    public void ejecutar(IAlumnoDAO alumnoDAO, IGrupoDAO grupoDAO, IVista vista, FicheroJson ficheroJson, FicheroBinario ficheroBinario) {
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
                        guardarGrupoEnJson(grupoDAO, alumnoDAO, vista, ficheroJson);
                    case 10 ->
                        mostrarAlumno(alumnoDAO, vista);
                    case 11 ->
                        guardarAlumnosDeDBAFicheroBinario(alumnoDAO, vista, ficheroBinario);
                    case 12 ->
                        guardarAlumnosDeFicheroBinarioADB(alumnoDAO, grupoDAO, vista, ficheroBinario);
                    case 13 -> 
                        eliminarAlumnosPorApellido(alumnoDAO, vista);
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

    /**
     * Pide los datos del Alumno que se quiere insertar en la tabla Alumno. Para
     * el grupo se comprueba si existe (si no existe, se inicia ese atributo en
     * null, y mas tarde se podra asignar) Para el grupo tambien esta la opcion
     * 0 para iniciar sin grupo directamente. Una vez recopilados los datos del
     * alumno correctamente, se utiliza el DAO para insertarlo en la base de
     * datos mostrando en la vista si se ha realizado correctamente o no.
     *
     * @param alumnoDAO
     * @param grupoDAO
     * @param vista
     */
    private void insertarAlumno(IAlumnoDAO alumnoDAO, IGrupoDAO grupoDAO, IVista vista) {
        Grupo grupo = null;
        try {
            vista.mostrarFormularioAlumnoNuevo();

            String nombre = vista.solicitarEntrada("Ingrese Nombre");
            String apellidos = vista.solicitarEntrada("Ingrese Apellidos");
            char genero;
            do {
                String input = vista.solicitarEntrada("Ingresa Genero (M/F)").toUpperCase();
                if (input.length() == 1 && (input.charAt(0) == 'M' || input.charAt(0) == 'F')) {
                    genero = input.charAt(0);
                    break;
                } else {
                    vista.mostrarMensaje("Error: Solo se permite 'M' o 'F'. Intenta de nuevo.");
                }
            } while (true);

            LocalDate fechaNacimiento = LocalDate.parse(vista.solicitarEntrada("Ingrese Fecha de Nacimiento (YYYY-MM-DD)"));
            int idGrupo = Integer.parseInt(vista.solicitarEntrada("Ingrese ID del Grupo(0 si no quieres asignar grupo)"));

            if (idGrupo != 0) {

                grupo = grupoDAO.obtenerPorId(idGrupo);
                if (grupo == null) {
                    vista.mostrarMensaje("El grupo no existe. No se asignara grupo, elija la opcion de asignar grupo del menu principal.");
                }
            } else if (idGrupo == 0) {
                vista.mostrarMensaje("Se creara el alumno sin asignarle un grupo.");
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

    /**
     * Pide los datos del Grupo que se quiere insertar en la tabla Grupo. Se usa
     * un GrupoFactory para tener una instancia de Grupo. Se utiliza el DAO para
     * insertar el Grupo en la db mostrando en la vista si se ha realizado
     * correctamente o no.
     *
     * @param grupoDAO
     * @param vista
     */
    private void insertarGrupo(IGrupoDAO grupoDAO, IVista vista) {
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

    /**
     * Recibe una lista de alumnos y una instancia de la vista para poder
     * mostrar al usuario los alumnos.
     *
     * @param alumnoDAO
     * @param vista
     */
    private void mostrarTodosLosAlumnos(IAlumnoDAO alumnoDAO, IVista vista) {
        List<Alumno> alumnos = alumnoDAO.obtenerTodos();
        if (alumnos.isEmpty()) {
            vista.mostrarMensaje("No hay alumnos registrados.");
        } else {
            vista.mostrarAlumnos(alumnos);
        }
    }

    /**
     * Recibe una instancia del DAO de alumno y grupo y otra de la vista.
     * Muestra al usuario los grupos que ahora mismo existen en la tabla
     * Grupo(grupoDAO) y recoge el input del usuario. Utiliza alumnoDAO para
     * obtener todos los alumnos para posteriormente mediantes un stream() +
     * filter() filtrar y anadir a la lista solo los alumnos cuyo id del
     * atributo Grupo coincida con el id introducido. Muestra todos los alumnos
     * filtrados
     *
     * @param grupoDAO
     * @param alumnoDAO
     * @param vista
     */
    private void mostrarAlumnosDeUnGrupo(IGrupoDAO grupoDAO, IAlumnoDAO alumnoDAO, IVista vista) {
        List<Grupo> grupos = grupoDAO.obtenerTodos();
        vista.mostrarGrupos(grupos);

        try {
            int idGrupo = Integer.parseInt(vista.solicitarEntrada("Elija un grupo por ID"));

            // Filtrar alumnos pertenecientes al grupo seleccionado
            List<Alumno> alumnosDelGrupo = alumnoDAO.obtenerTodos().stream()
                    .filter(alumno -> alumno.getGrupo() != null && alumno.getGrupo().getGrupo() == idGrupo)
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

    /**
     * Recibe una instancia de alumnoDAO, grupoDAO y de vista. Primero obtiene
     * todos los alumnos utilizando el metodo obtenerTodos() de alumnoDAO y los
     * almacena en una lista. Utiliza el mostrarAlumnos(List<Alumno> alumnos) de
     * la vista para mostrarlos al usuario. Pide al usuario el alumno que quiere
     * modificar (su id) y recoge la respuesta. Con ese id utiliza
     * obtenerPorId(int id) de alumnoDAO para obtener Alumno de la tabla Alumno.
     * Lo siguiente es utilizar obtenerTodos() de grupoDAO para sacar la lista
     * de Grupos de la tabla Grupo. Muestra esa lista al usuario con
     * mostrarGrupos(List<Grupo> grupos) de la Vista. Pide al usuario elegir el
     * id del grupo nuevo para el Alumno y recoge la respueta. Obtiene el Grupo
     * con obtenerPorId(int id) de GrupoDAO. Actualiza el grupo del
     * Alumno.setGrupo(grupo) y utiliza AlumnoDAO.actualizar(alumno) para
     * persistir los nuevos datos en la tabla Alumno. Muestra al usuario si se
     * ha actualizado con exito o no.
     *
     * @param alumnoDAO
     * @param grupoDAO
     * @param vista
     */
    private void cambiarDeGrupoAUnAlumno(IAlumnoDAO alumnoDAO, IGrupoDAO grupoDAO, IVista vista) {
        List<Alumno> alumnos = alumnoDAO.obtenerTodos();
        vista.mostrarAlumnos(alumnos);
        String respuesta = vista.solicitarEntrada("Selecciona el alumno al que quieres modificarle el grupo");
        Alumno alumnoModificable = alumnoDAO.obtenerPorId(Integer.parseInt(respuesta));

        List<Grupo> grupos = grupoDAO.obtenerTodos();
        vista.mostrarGrupos(grupos);
        String respuesta2 = vista.solicitarEntrada("Selecciona el grupo nuevo");
        Grupo grupoNuevo = grupoDAO.obtenerPorId(Integer.parseInt(respuesta2));

        alumnoModificable.setGrupo(grupoNuevo);
        boolean respuestaServidor = alumnoDAO.actualizar(alumnoModificable);
        if (respuestaServidor) {
            vista.mostrarMensaje("Alumno modificado con éxito.");
        } else {
            vista.mostrarMensaje("No se ha modificado correctamente.");
        }

    }

    /**
     * Recibe una instancia de IAlumnoDAO y de IVista. Pide al usuario los datos
     * para buscar un Alumno en la db mediante AlumnoDAO.obtenerPorId(int id) y
     * su nombre para modificarlo. Recibe una instancia de alumno de la base de
     * datos con AlumnoDAO.obtenerPorId(int id) si existe y modifica el nombre
     * de esa instancia. Una vez modificado utiliza AlumnoDAO.actualizar(alumno)
     * para persistir el cambio en la tabla Alumno. Muetra al usuario si se ha
     * realizado con exito o no (y el problema).
     *
     *
     * @param alumnoDAO
     * @param vista
     */
    private void modificarAlumnoPorPK(IAlumnoDAO alumnoDAO, IVista vista) {
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

    /**
     * Permite eliminar un alumno de la base de datos por su clave primaria
     * (NIA). Solicita el NIA al usuario y lo busca en la base de datos. Si
     * existe, lo elimina y muestra un mensaje confirmando la operación. Si no
     * existe, muestra un mensaje de error.
     *
     * @param alumnoDAO
     * @param vista
     */
    private void eliminarAlumnoPorPK(IAlumnoDAO alumnoDAO, IVista vista) {
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

    /**
     * Elimina el grupo de los alumnos de un curso específico de la base de
     * datos.(los da de baja) Solicita el ciclo y el curso al usuario y da de
     * baja los alumnos del curso . Muestra un mensaje de confirmación si se
     * eliminan correctamente, o un error si no hay alumnos para ese curso.
     *
     * @param alumnoDAO
     * @param grupoDAO
     * @param vista
     */
    private void eliminarAlumnosPorCurso(IAlumnoDAO alumnoDAO, IGrupoDAO grupoDAO, IVista vista) {
        try {
            List<Grupo> grupo = grupoDAO.obtenerTodos();
            vista.mostrarGrupos(grupo);
            
            String ciclo = vista.solicitarEntrada("Ingrese Ciclo del curso");
            String curso = vista.solicitarEntrada("Ingrese Curso");
            boolean exito = alumnoDAO.eliminarPorCurso(ciclo, curso);

            if (exito) {
                vista.mostrarMensaje("Alumnos del curso eliminados correctamente.");
            } else {
                vista.mostrarMensaje("No se encontraron alumnos para el curso o no existe el grupo");
            }
        } catch (Exception e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    /**
     * Guarda la información de un grupo y sus alumnos en un archivo JSON.
     * Solicita al usuario que seleccione un grupo por ID. Si el grupo tiene
     * alumnos, los guarda en un fichero JSON. Si el grupo no existe o no tiene
     * alumnos, muestra un mensaje de error.
     *
     * @param grupoDAO
     * @param alumnoDAO
     * @param vista
     * @param fichero
     */
    private void guardarGrupoEnJson(IGrupoDAO grupoDAO, IAlumnoDAO alumnoDAO, IVista vista, FicheroJson fichero) {
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

    /**
     * Permite al usuario visualizar la información de un alumno en detalle.
     * Primero muestra todos los alumnos registrados con su ID y nombre. Luego
     * solicita al usuario seleccionar un alumno por ID y muestra su información
     * completa. Si el ID no es válido o el alumno no existe, muestra un mensaje
     * de error.
     *
     * @param alumnoDAO DAO para manejar alumnos
     * @param vista Interfaz de usuario
     */
    private void mostrarAlumno(IAlumnoDAO alumnoDAO, IVista vista) {
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
    
    /**
     * Metodo que obtiene mediante alumnoDAO la lista de todos los alumnos en la base de datos SQL
     * y en caso de no ser nula la lista, guarda los alumnos con la clase Fichero Binario en un .dat, mostrando
     * en la vista si se ha guardado correctamente, si no lo ha hecho o si no hay alumnos que guardar.
     * @param alumnoDAO
     * @param vista
     * @param ficheroBinario 
     */
    private void guardarAlumnosDeDBAFicheroBinario(IAlumnoDAO alumnoDAO, IVista vista, FicheroBinario ficheroBinario) {
        List<Alumno> alumnos = alumnoDAO.obtenerTodos();
        boolean guardado;
        if (alumnos != null) {
            guardado = ficheroBinario.guardarAlumnosEnFicheroBinario(alumnos);

            if (guardado) {
                vista.mostrarMensaje("Alumnos guardado correctamente en fichero binario.");
            } else {
                vista.mostrarMensaje("Error al guardar los alumnos en el fichero binario.");
            }
        } else {
            vista.mostrarMensaje("No hay alumnos que guardar en la db.");
        }
    }
    
    
    /**
     * Metodo que lee primero la lista de alumnos de un fichero binario con un metodo de la clase FicheroBinario,
     * comprueba que la lista no sea nula, y utiliza una lambda para comprobar cada alumno antes de guardarlo en 
     * la base de datos con alumnoDAO. La comprobación que ahcer es primero si el alumno tiene asignado un grupo,
     * en caso de no tenerlo, procede a guardar el alumno sin un grupo asignado (null). En caso de tener un grupo, 
     * comprueba (con grupoDAO) primero si existe por Curso y Ciclo, y en caso de existir, lo recupera de la base de datos
     * con el id de grupo correspondiente, y lo actualiza en el objeto alumno antes de guardarlo en la base 
     * de datos con el alumnoDAO.
     * 
     * @param alumnoDAO
     * @param grupoDAO
     * @param vista
     * @param ficheroBinario 
     */
    private void guardarAlumnosDeFicheroBinarioADB(IAlumnoDAO alumnoDAO, IGrupoDAO grupoDAO, IVista vista, FicheroBinario ficheroBinario) {
        List<Alumno> alumnos = ficheroBinario.leerAlumnosDeFicheroBinario();

        if (alumnos != null) {
            alumnos.forEach(alumno -> {
                alumno.setNia(0);
                Grupo grupo = null;
                if (alumno.getGrupo() == null) {
                    vista.mostrarMensaje("El alumno no esta matriculado en ningun grupo.");
                } else {
                    if (grupoDAO.obtenerPorCicloYCurso(alumno.getGrupo().getCiclo(), alumno.getGrupo().getCurso()) == null) {
                        grupo = alumno.getGrupo();
                        grupo.setGrupo(0);
                        grupoDAO.insertar(alumno.getGrupo());
                        grupo = grupoDAO.obtenerPorCicloYCurso(alumno.getGrupo().getCiclo(), alumno.getGrupo().getCurso());
                        vista.mostrarMensaje("No existe el grupo del alumno que se va a guardar. Creado nuevo grupo ->" + grupo);
                    } else {
                        grupo = grupoDAO.obtenerPorCicloYCurso(alumno.getGrupo().getCiclo(), alumno.getGrupo().getCurso());
                    }
                    alumno.setGrupo(grupo);
                }

                if (alumnoDAO.insertar(alumno)) {
                    vista.mostrarMensaje("Insertado alumno ->" + alumno.toString());
                } else {
                    vista.mostrarMensaje("No se ha podido insertar alumno, parando operacion ->" + alumno.toString());
                    throw new RuntimeException();
                }
            });
        } else {
            vista.mostrarMensaje("No hay alumnos a insertar.");
        }
        vista.mostrarMensaje("Terminado de insertar todos los alumnos.");
    }
    
    /**
     * Metodo que pedira una palabra al usuario para buscar posteriormente
     * alumnos cuyo apellido la contengan y los elimine. 
     * En caso de que la operacion no falle , devuelve true.
     * @param alumnoDAO
     * @param vista 
     */
    private void eliminarAlumnosPorApellido(IAlumnoDAO alumnoDAO, IVista vista) {
        String palabra = vista.solicitarEntrada("Escribe una palabra para eliminar los alumnos cuyo apellido la contengan");
        if(alumnoDAO.eliminarAlumnosCuyoApellidoContengaUnaPalabra(palabra)) vista.mostrarMensaje("Operacion realizada con exito.");
        else vista.mostrarMensaje("No se ha podido realizar la operacion.");
    }
}
