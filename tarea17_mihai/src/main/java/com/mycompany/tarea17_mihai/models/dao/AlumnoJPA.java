/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.dao;

import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.AlumnoHB;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import com.mycompany.tarea17_mihai.models.entidades.GrupoHB;
import com.mycompany.tarea17_mihai.models.utils.Transformador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mihai
 */
public class AlumnoJPA implements IAlumnoDAO {

    private EntityManagerFactory emf;

    public AlumnoJPA() {
        this.emf = Persistence.createEntityManagerFactory("HibernateAlumnos");
    }

    @Override
    public void insertarAlumno(Alumno alumno) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Transformar Alumno -> AlumnoHB
            AlumnoHB alumnoHB = Transformador.toAlumnoHB(alumno);
            alumnoHB.setNIA(0);
            em.persist(alumnoHB);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void insertarAlumnos(List<Alumno> alumnos) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Alumno al : alumnos) {

                AlumnoHB alumnoHB = Transformador.toAlumnoHB(al);
                // Si el alumno tiene grupo, buscar el grupo en la BD por sus atributos únicos
                if (alumnoHB.getGrupoHB() != null) {
                    GrupoHB grupo = alumnoHB.getGrupoHB();
                    GrupoHB grupoCorrecto = em.createQuery(
                            "SELECT g FROM GrupoHB g WHERE g.ciclo = :ciclo AND g.curso = :curso", GrupoHB.class)
                            .setParameter("ciclo", grupo.getCiclo())
                            .setParameter("curso", grupo.getCurso())
                            .getSingleResult();
                    alumnoHB.setGrupoHB(grupoCorrecto);
                }
                alumnoHB.setNIA(0);
                em.persist(alumnoHB);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Alumno> obtenerTodosLosAlumnos() {
        EntityManager em = emf.createEntityManager();
        try {
            // Consulta a la entidad AlumnoHB
            List<AlumnoHB> listaHB = em.createQuery("SELECT a FROM AlumnoHB a", AlumnoHB.class)
                    .getResultList();
            // Transformar AlumnoHB -> Alumno
            return listaHB.stream()
                    .map(Transformador::toAlumno)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    @Override
    public Alumno buscarAlumnoPorNIA(int nia) {
        EntityManager em = emf.createEntityManager();
        try {
            // Buscar AlumnoHB
            AlumnoHB alumnoHB = em.find(AlumnoHB.class, nia);
            // Transformar a Alumno
            return Transformador.toAlumno(alumnoHB);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarAlumno(Alumno alumno) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Convertir a AlumnoHB
            AlumnoHB alumnoHB = Transformador.toAlumnoHB(alumno);
            em.merge(alumnoHB);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminarAlumnoPorNIA(int nia) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Buscar AlumnoHB
            AlumnoHB alumnoHB = em.find(AlumnoHB.class, nia);
            if (alumnoHB != null) {
                em.remove(alumnoHB);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void insertarGrupos(List<Grupo> grupos) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Transformar cada Grupo -> GrupoHB y persistir
            for (Grupo g : grupos) {
                GrupoHB grupoHB = Transformador.toGrupoHB(g);
                grupoHB.setId(0);
                em.persist(grupoHB);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Grupo> obtenerGrupos() {
        EntityManager em = emf.createEntityManager();
        try {

            List<GrupoHB> listaHB = em.createQuery("SELECT g FROM GrupoHB g", GrupoHB.class)
                    .getResultList();
            // Convertir a Grupo
            return listaHB.stream()
                    .map(Transformador::toGrupo)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    @Override
    public Grupo obtenerGrupoPorId(int idGrupo) {
        EntityManager em = emf.createEntityManager();
        try {
            GrupoHB grupoHB = em.find(GrupoHB.class, idGrupo);
            return Transformador.toGrupo(grupoHB);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminarAlumnosPorCoincidenciaDeApellido(String palabra) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM AlumnoHB a WHERE a.apellidos LIKE :palabra")
                    .setParameter("palabra", "%" + palabra + "%")
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminarAlumnosPorCurso(String grupo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("""
                DELETE FROM AlumnoHB a
                WHERE a.grupoHB.id = :grupo
            """).setParameter("grupo", grupo)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
    } finally {
            em.close();
        }
    }

    @Override
    public List<Alumno> obtenerAlumnosPorGrupo(int idGrupo) {
        EntityManager em = emf.createEntityManager();
        try {
            List<AlumnoHB> alumnosHB = em.createQuery("SELECT a FROM AlumnoHB a WHERE a.grupoHB.id = :id", AlumnoHB.class)
                    .setParameter("id", idGrupo)
                    .getResultList();
            return alumnosHB.stream()
                    .map(Transformador::toAlumno)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    @Override
    public void cambiarAlumnoDeGrupo(int nia, int nuevoGrupoId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            AlumnoHB alumnoHB = em.find(AlumnoHB.class, nia);
            if (alumnoHB != null) {
                GrupoHB nuevoGrupoHB = em.find(GrupoHB.class, nuevoGrupoId);
                alumnoHB.setGrupoHB(nuevoGrupoHB);
                em.merge(alumnoHB);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
