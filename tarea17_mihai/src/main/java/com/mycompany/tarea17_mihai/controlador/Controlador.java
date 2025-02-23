/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.controlador;

import com.mycompany.tarea17_mihai.models.dao.IAlumnoDAO;
import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import com.mycompany.tarea17_mihai.models.utils.FicheroBinario;
import com.mycompany.tarea17_mihai.models.utils.FicheroJson;
import com.mycompany.tarea17_mihai.models.utils.IFichero;
import com.mycompany.tarea17_mihai.vista.IVista;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */
public class Controlador implements IControlador {

    private static final String RUTA_FICHERO_BIN = "alumnos.bin";
    private static final String RUTA_FICHERO_JSON = "grupos.json";
    private static final Logger logger = LogManager.getLogger(Controlador.class);
    private IVista vista;
    private IAlumnoDAO alumnoDAO;

    public Controlador(IVista vista, IAlumnoDAO alumnoDAO) {
        this.vista = vista;
        this.alumnoDAO = alumnoDAO;
    }

    @Override
    public void ejecutar() {
        while (true) {
            boolean continuar = bucleMenu();

            if (!continuar) {
                vista.mostrarMensaje("Saliendo...");
                break;
            }
        }

    }

    /**
     * Metodo que maneja el bucle del programa mostrando manteniendo el programa
     * funcionando hasta que se devuelve true(terminado). Muestra el menu en la
     * vista, pide una entrada del usuario, controla que la entrada sea siempre
     * un int. Si la opcion es correcta (y no es 0) da paso al
     * manejadorSolicitud para seguir con el funcionamiento del programa.
     *
     * @return
     */
    @Override
    public boolean bucleMenu() {
        vista.mostrarMenu(); //Muestra el menu

        String opcionString = vista.pedirEntrada("Elige una opcion...");

        int opcion;
        //Intentamos parsear a int la respuesta.
        try {
            opcion = Integer.parseInt(opcionString);
        } catch (NumberFormatException e) {
            logger.error("Error en el formato de la opcion elegida del menu -> " + e);
            vista.mostrarMensaje("Tiene que introducir un numero valido");
            return true; //Para que el bucle del menu siga
        }
        if (opcion == 0) {
            return false;
        }

        //Maneja la solicitud que ha pedido el usuario
        manejadorSolicitud(opcion);

        //Si llega aqui es que ha terminado la ejecucion de una opcion, y seguimos el bucle del menu
        return true;
    }

    /**
     * Pide los datos del Alumno que se quiere insertar en la tabla Alumno. Para
     * el grupo se comprueba si existe (si no existe, se inicia ese atributo en
     * null, y mas tarde se podra asignar) Para el grupo tambien esta la opcion
     * 0 para iniciar sin grupo directamente. Una vez recopilados los datos del
     * alumno correctamente, se utiliza el DAO para insertarlo en la base de
     * datos mostrando en la vista si se ha realizado correctamente o no.
     *
     */
    @Override
    public void insertarUnAlumnoNuevo() {
        Grupo grupo = null;  //En la db el grupo del alumno puede ser null porque no esta matriculado
        try {
            String nombre = vista.pedirEntrada("Ingresa un nombre");
            String apellidos = vista.pedirEntrada("Ingresa los apellidos");
            char genero;
            do {
                String input = vista.pedirEntrada("Ingresa Genero (M/F)").toUpperCase();
                if (input.length() == 1 && (input.charAt(0) == 'M' || input.charAt(0) == 'F')) {
                    genero = input.charAt(0);
                    break;
                } else {
                    vista.mostrarMensaje("Error -> solo existe 'M' o 'F'. Intenta de nuevo.");
                }
            } while (true);
            LocalDate fechaNacimiento = LocalDate.parse(vista.pedirEntrada("Ingrese Fecha de Nacimiento (YYYY-MM-DD)"));

            //Logica para la seleccion del grupo antes de persistir el alumno
            int idGrupo = Integer.parseInt(vista.pedirEntrada("Ingrese ID del Grupo(0 si no quieres asignar grupo)"));
            if (idGrupo != 0) {
                grupo = alumnoDAO.obtenerGrupoPorId(idGrupo);
                if (grupo == null) {
                    vista.mostrarMensaje("El grupo no existe. No se asignara grupo, elija la opcion de asignar grupo del menu principal.");
                }
            } else if (idGrupo == 0) {
                vista.mostrarMensaje("Se creara el alumno sin asignarle un grupo.");
            }

            //Persistimos el alumno con el grupo asignado
            Alumno alumno = new Alumno(nombre, apellidos, genero, fechaNacimiento, grupo);
            alumnoDAO.insertarAlumno(alumno);
            vista.mostrarMensaje("Alumno insertado correctamente.");
        } catch (Exception e) {
            logger.error("Error al crear el nuevo alumno -> " + e);
            vista.mostrarMensaje("No se ha podido crear el nuevo alumno, intentalo de nuevo.");
        }

    }

    /**
     * Pide los datos de los grupos que se quieren insertar en la tabla Grupo.
     * (cuantos y sus datos) Hace una comprobacion por cada entrada de ciclo y
     * curso que se haga para saber si existe, y asi controlar que no se
     * dupliquen cursos. Si nuevosGrupos esta vacio, no inserta nada, en caso de
     * no estarlo, utiliza el dao para insertar los grupos.
     */
    @Override
    public void insertarGrupos() {
        try {
            int cantidad = Integer.parseInt(vista.pedirEntrada("¿Cuantos grupos quieres insertar?"));

            List<Grupo> gruposExistentes = alumnoDAO.obtenerGrupos();
            List<Grupo> nuevosGrupos = new ArrayList<>();

            for (int i = 0; i < cantidad; i++) {
                String ciclo = vista.pedirEntrada("Ingresa el nombre del ciclo para el grupo " + (i + 1) + ":");
                String curso = vista.pedirEntrada("Ingrese el nombre del curso para el grupo " + (i + 1) + ":");

                boolean existe = gruposExistentes.stream()
                        .anyMatch(g -> g.getCiclo().equalsIgnoreCase(ciclo) && g.getCurso().equalsIgnoreCase(curso));

                if (existe) {
                    vista.mostrarMensaje("El grupo [" + ciclo + " - " + curso + "] ya existe. No se insertará.");
                } else {
                    nuevosGrupos.add(new Grupo(ciclo, curso, new ArrayList<>()));
                }
            }

            if (!nuevosGrupos.isEmpty()) {
                alumnoDAO.insertarGrupos(nuevosGrupos);
                vista.mostrarMensaje(nuevosGrupos.size() + " grupo(s) insertado(s) correctamente.");
            } else {
                vista.mostrarMensaje("No se insertaron nuevos grupos.");
            }

        } catch (NumberFormatException e) {
            logger.error("No se ha podido insertar por un error en el formato de la entrada -> " + e);
            vista.mostrarMensaje("Error: ingresa un numero valido");
        } catch (Exception e) {
            logger.error("Error no identificado -> " + e);
            vista.mostrarMensaje("Error: "+ e.getMessage());
        }

    }

    /**
     * Carga en la vista una lista de alumnos obtenido con el alumnoDAO.
     */
    @Override
    public void mostrarTodosLosAlumnos() {
        List<Alumno> alumnos = alumnoDAO.obtenerTodosLosAlumnos();
        if (alumnos.isEmpty()) {
            vista.mostrarMensaje("No hay alumnos registrados.");
        } else {
            vista.mostrarAlumnos(alumnos);
        }
    }

    /**
     * Guarda todos los alumnos obtenidos por alumnoDAO en un fichero
     *
     * @param fichero
     */
    @Override
    public void guardarTodosLosAlumnosEnUnFichero(IFichero fichero) {
        List<Alumno> alumnos = alumnoDAO.obtenerTodosLosAlumnos();
        fichero.escribirEnUnFicheroTodosLosAlumnos(alumnos);
    }

    /**
     * Lee los alumnos de un fichero y los persiste en la db con alumnoDAO. Hace
     * comprobaciones antes de persistir, para no duplicar ni grupo ni alumnos
     * en caso de existir. No los busca por NIA o por id de grupo, los busca por
     * Nombre, apellidos y fechaNac en el caso del alumno y por curso y ciclo en
     * el caso del grupo.
     *
     * @param fichero
     */
    @Override
    public void leerAlumnosDeUnFicheroYGuardarlosEnLaBD(IFichero fichero) {
        try {
            List<Alumno> alumnosLeidos = fichero.leerAlumnosDeUnFichero();
            List<Alumno> alumnosExistentes = alumnoDAO.obtenerTodosLosAlumnos();
            List<Grupo> gruposExistentes = alumnoDAO.obtenerGrupos();

            List<Alumno> alumnosAInsertar = new ArrayList<>();
            List<Grupo> gruposAInsertar = new ArrayList<>();

            for (Alumno alumno : alumnosLeidos) {
                // Verificar si el grupo ya existe
                Grupo grupo = alumno.getGrupo();
                if (grupo != null) {
                    boolean grupoExiste = gruposExistentes.stream()
                            .anyMatch(g -> g.getCiclo().equalsIgnoreCase(grupo.getCiclo())
                            && g.getCurso().equalsIgnoreCase(grupo.getCurso()));

                    if (!grupoExiste) {
                        gruposAInsertar.add(grupo);
                        gruposExistentes.add(grupo); // Añadir al listado de existentes para futuras comparaciones
                    }
                }

                // Verificar si el alumno ya existe comparando nombre, apellidos y fecha de nacimiento
                boolean alumnoExiste = alumnosExistentes.stream()
                        .anyMatch(a -> a.getNombre().equalsIgnoreCase(alumno.getNombre())
                        && a.getApellidos().equalsIgnoreCase(alumno.getApellidos())
                        && a.getFechaDeNacimiento().equals(alumno.getFechaDeNacimiento()));

                if (alumnoExiste) {
                    vista.mostrarMensaje("Alumno " + alumno.getNombre()
                            + " " + alumno.getApellidos() + "ya existe. No se insertará.");
                } else {
                    alumnosAInsertar.add(alumno);
                }
            }

            // Insertar nuevos grupos primero (si hay)
            if (!gruposAInsertar.isEmpty()) {
                alumnoDAO.insertarGrupos(gruposAInsertar);
                vista.mostrarMensaje("Se han insertado " + gruposAInsertar.size() + " nuevos grupos.");
            }

            // Insertar alumnos después de asegurar que los grupos existen
            if (!alumnosAInsertar.isEmpty()) {
                alumnoDAO.insertarAlumnos(alumnosAInsertar);
                vista.mostrarMensaje("Se han insertado " + alumnosAInsertar.size() + " alumnos en la base de datos.");
            } else {
                vista.mostrarMensaje("No se han insertado nuevos alumnos.");
            }

        } catch (Exception e) {
            logger.error("Error al leer e insertar alumnos desde el fichero -> ", e.getMessage());
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    /**
     * Metodo que modifica en la db el nombre de un alumno segun el nia
     * seleccionado.
     */
    @Override
    public void modificarNombreDeUnAlumnoPorNIA() {
        int nia = Integer.parseInt(vista.pedirEntrada("Ingrese el NIA del alumno a modificar:"));
        Alumno alumno = alumnoDAO.buscarAlumnoPorNIA(nia);
        if (alumno != null) {
            String nuevoNombre = vista.pedirEntrada("Ingrese el nuevo nombre:");
            alumno.setNombre(nuevoNombre);
            alumnoDAO.actualizarAlumno(alumno);
            vista.mostrarMensaje("Nombre modificado correctamente.");
        } else {
            vista.mostrarMensaje("Alumno no encontrado.");
        }
    }

    /**
     * Metodo que elimina un alumno por el nia.
     */
    @Override
    public void eliminarAlumnoPorNIA() {
        int nia = Integer.parseInt(vista.pedirEntrada("Ingrese el NIA del alumno a eliminar:"));
        alumnoDAO.eliminarAlumnoPorNIA(nia);
        vista.mostrarMensaje("Alumno eliminado correctamente.");
    }

    /**
     * Metodo que elimina alumnos cuyo apellido contenga la palabra pedida por
     * teclado.
     */
    @Override
    public void eliminarAlumnosQueContenganEnSuApellidoUnaPalabra() {
        String palabra = vista.pedirEntrada("Ingrese la palabra a buscar en los apellidos:");
        alumnoDAO.eliminarAlumnosPorCoincidenciaDeApellido(palabra);
        vista.mostrarMensaje("Alumnos eliminados correctamente.");
    }

    /**
     * Metodo que elimina los alumnos de la tabla Alumno cuyo grupo coincida con
     * el id del grupo seleccionado.
     */
    @Override
    public void eliminarAlumnosDelCursoIndicado() {
        vista.mostrarGrupos(alumnoDAO.obtenerGrupos());
        String curso = vista.pedirEntrada("Ingrese el id del grupo del que desea eliminar alumnos:");
        alumnoDAO.eliminarAlumnosPorCurso(curso);
        vista.mostrarMensaje("Alumnos eliminados correctamente.");
    }

    @Override
    public void guardarTodosLosGruposEnUnFichero(IFichero fichero) {
        List<Grupo> grupos = alumnoDAO.obtenerGrupos();
        for (Grupo grupo : grupos) {
            List<Alumno> alumnosGrupo = alumnoDAO.obtenerAlumnosPorGrupo(grupo.getId());
            grupo.setAlumnos(alumnosGrupo);
        }
        fichero.escribirGruposEnFichero(grupos);
        vista.mostrarMensaje("Grupos guardados en el fichero correctamente.");
    }

    /**
     * Metodo que lee los grupos de un fichero y los guarda en la db.
     *
     * @param fichero
     */
    @Override
    public void leerFicheroDeGruposYGuardarlosEnLaBD(IFichero fichero) {
        List<Grupo> gruposLeidos = fichero.leerFicheroParaDevolverGrupos();
        List<Grupo> gruposExistentes = alumnoDAO.obtenerGrupos();

        for (Grupo grupo : gruposLeidos) {
            boolean existe = gruposExistentes.stream().anyMatch(g -> g.getCiclo().equalsIgnoreCase(grupo.getCiclo()) && g.getCurso().equalsIgnoreCase(grupo.getCurso()));
            if (!existe) {
                alumnoDAO.insertarGrupos(List.of(grupo));
            }

            for (Alumno alumno : grupo.getAlumnos()) {
                boolean alumnoExiste = alumnoDAO.buscarAlumnoPorNIA(alumno.getNIA()) != null;
                if (!alumnoExiste) {
                    alumnoDAO.insertarAlumno(alumno);
                }
            }
        }
        vista.mostrarMensaje("Grupos y alumnos leídos e insertados en la base de datos.");
    }

    @Override
    public void mostrarTodosLosAlumnosDeUnGrupo() {
        vista.mostrarGrupos(alumnoDAO.obtenerGrupos());
        int idGrupo = Integer.parseInt(vista.pedirEntrada("Ingrese el ID del grupo:"));
        List<Alumno> alumnos = alumnoDAO.obtenerAlumnosPorGrupo(idGrupo);
        if (alumnos.isEmpty()) {
            vista.mostrarMensaje("No hay alumnos en este grupo.");
        } else {
            vista.mostrarAlumnos(alumnos);
        }
    }

    @Override
    public void mostrarAlumnoAPartirDeUnNIAElegido() {
        vista.mostrarAlumnosPorNia(alumnoDAO.obtenerTodosLosAlumnos());
        int nia = Integer.parseInt(vista.pedirEntrada("Ingrese el NIA del alumno:"));
        Alumno alumno = alumnoDAO.buscarAlumnoPorNIA(nia);
        if (alumno != null) {
            vista.mostrarAlumno(alumno);
        } else {
            vista.mostrarMensaje("Alumno no encontrado.");
        }
    }

    @Override
    public void cambiarAlumnoDeGrupo() {
        vista.mostrarAlumnos(alumnoDAO.obtenerTodosLosAlumnos());
        int nia = Integer.parseInt(vista.pedirEntrada("Ingrese el NIA del alumno:"));
        vista.mostrarGrupos(alumnoDAO.obtenerGrupos());
        int nuevoGrupoId = Integer.parseInt(vista.pedirEntrada("Ingrese el ID del nuevo grupo:"));
        alumnoDAO.cambiarAlumnoDeGrupo(nia, nuevoGrupoId);
        vista.mostrarMensaje("Alumno cambiado de grupo correctamente.");
    }

    @Override
    public void guardarGrupoEnUnFichero(IFichero fichero) {
        vista.mostrarGrupos(alumnoDAO.obtenerGrupos());
        int idGrupo = Integer.parseInt(vista.pedirEntrada("Ingrese el ID del grupo a guardar:"));
        Grupo grupo = alumnoDAO.obtenerGrupoPorId(idGrupo);
        if (grupo != null) {
            List<Alumno> alumnosGrupo = alumnoDAO.obtenerAlumnosPorGrupo(idGrupo);
            grupo.setAlumnos(alumnosGrupo);
            fichero.escribirGruposEnFichero(List.of(grupo));
            vista.mostrarMensaje("Grupo guardado en el fichero correctamente.");
        } else {
            vista.mostrarMensaje("Grupo no encontrado.");
        }
    }

    //----------------
    //Metodos privados
    //----------------
    /**
     * Metodo que maneja la diferentes opciones del menu
     *
     * @param opcion
     */
    private void manejadorSolicitud(int opcion) {
        switch (opcion) {
            case 1 ->
                insertarUnAlumnoNuevo();
            case 2 ->
                insertarGrupos();
            case 3 ->
                mostrarTodosLosAlumnos();
            case 4 -> {
                IFichero fichero = new FicheroBinario(RUTA_FICHERO_BIN);
                guardarTodosLosAlumnosEnUnFichero(fichero);
            }
            case 5 -> {
                IFichero fichero = new FicheroBinario(RUTA_FICHERO_BIN);
                leerAlumnosDeUnFicheroYGuardarlosEnLaBD(fichero);
            }
            case 6 ->
                modificarNombreDeUnAlumnoPorNIA();
            case 7 ->
                eliminarAlumnoPorNIA();
            case 8 ->
                eliminarAlumnosQueContenganEnSuApellidoUnaPalabra();
            case 9 ->
                eliminarAlumnosDelCursoIndicado();
            case 10 -> {
                IFichero fichero = new FicheroJson(RUTA_FICHERO_JSON);
                guardarTodosLosGruposEnUnFichero(fichero);
            }
            case 11 -> {
                IFichero fichero = new FicheroJson(RUTA_FICHERO_JSON);
                leerFicheroDeGruposYGuardarlosEnLaBD(fichero);
            }
            case 12 ->
                mostrarTodosLosAlumnosDeUnGrupo();
            case 13 ->
                mostrarAlumnoAPartirDeUnNIAElegido();
            case 14 ->
                cambiarAlumnoDeGrupo();
            case 15 -> {
                IFichero fichero = new FicheroJson(RUTA_FICHERO_JSON);
                guardarGrupoEnUnFichero(fichero);
            }
            default ->
                vista.mostrarMensaje("Opción no válida. Inténtalo de nuevo.\n");
        }

    }

}
