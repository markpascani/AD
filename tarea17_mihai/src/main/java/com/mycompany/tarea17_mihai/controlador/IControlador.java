/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea17_mihai.controlador;

import com.mycompany.tarea17_mihai.models.dao.IAlumnoDAO;
import com.mycompany.tarea17_mihai.models.utils.IFichero;
import com.mycompany.tarea17_mihai.vista.IVista;

/**
 *
 * @author mihai
 */
public interface IControlador {

    void ejecutar();

    boolean bucleMenu(); 

    void insertarUnAlumnoNuevo();

    void insertarGrupos();

    void mostrarTodosLosAlumnos();

    void guardarTodosLosAlumnosEnUnFichero(IFichero fichero);

    void leerAlumnosDeUnFicheroYGuardarlosEnLaBD(IFichero fichero);

    void modificarNombreDeUnAlumnoPorNIA();

    void eliminarAlumnoPorNIA();

    void eliminarAlumnosQueContenganEnSuApellidoUnaPalabra();

    void eliminarAlumnosDelCursoIndicado();

    void guardarTodosLosGruposEnUnFichero(IFichero fichero);

    void leerFicheroDeGruposYGuardarlosEnLaBD(IFichero fichero);

    void mostrarTodosLosAlumnosDeUnGrupo();

    void mostrarAlumnoAPartirDeUnNIAElegido();

    void cambiarAlumnoDeGrupo();

    void guardarGrupoEnUnFichero(IFichero fichero);

}
