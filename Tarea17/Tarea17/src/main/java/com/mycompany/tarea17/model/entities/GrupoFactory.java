/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.entities;

/**
 *
 * @author mihai
 */
public class GrupoFactory {
    
    private final String cicloPredeterminado;
    private final String cursoPredeterminado;

    // Constructor para definir valores predeterminados
    public GrupoFactory(String cicloPredeterminado, String cursoPredeterminado) {
        this.cicloPredeterminado = cicloPredeterminado;
        this.cursoPredeterminado = cursoPredeterminado;
    }

    // Crea un grupo con valores predeterminados
    public Grupo crear() {
        return new Grupo(cicloPredeterminado, cursoPredeterminado);
    }

    // Crea un grupo con valores específicos
    public Grupo crear(String ciclo, String curso) {
        return new Grupo(ciclo, curso);
    }
}
