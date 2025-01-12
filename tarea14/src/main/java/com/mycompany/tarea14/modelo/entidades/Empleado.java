/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea14.modelo.entidades;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author mihai
 */
public class Empleado {
    private int id_empleado;
    private String nombre;
    private String apellidos;
    private LocalDate fecha_nacimiento;
    private String puesto;
    private String email;

    public Empleado(String nombre, String apellidos, LocalDate fecha_nacimiento, String puesto, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.puesto = puesto;
        this.email = email;
    }

    public Empleado(int id_empleado, String nombre, String apellidos, LocalDate fecha_nacimiento, String puesto, String email) {
        this(nombre, apellidos, fecha_nacimiento, puesto, email);
    }

    
    

    public Empleado() {
    }

    public int getId_empleado() {
        return id_empleado;
    }
    
    public void setId_empleado(int id_empleado){
        this.id_empleado = id_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id_empleado;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.apellidos);
        hash = 97 * hash + Objects.hashCode(this.fecha_nacimiento);
        hash = 97 * hash + Objects.hashCode(this.puesto);
        hash = 97 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Empleado other = (Empleado) obj;
        if (this.id_empleado != other.id_empleado) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        if (!Objects.equals(this.puesto, other.puesto)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return Objects.equals(this.fecha_nacimiento, other.fecha_nacimiento);
    }

    @Override
    public String toString() {
        return "Empleado{" + "id_empleado=" + id_empleado + ", nombre=" + nombre + ", apellidos=" + apellidos + ", fecha_nacimiento=" + fecha_nacimiento + ", puesto=" + puesto + ", email=" + email + '}';
    }
    
    
        
    
}
