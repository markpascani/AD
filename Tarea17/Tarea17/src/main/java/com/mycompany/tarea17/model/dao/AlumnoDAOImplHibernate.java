/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.dao;

import com.mycompany.tarea17.model.dao.interfaces.IAlumnoDAO;
import com.mycompany.tarea17.model.dao.utils.HibernateUtil;
import com.mycompany.tarea17.model.entities.Alumno;
import com.mycompany.tarea17.model.entities.AlumnoHB;
import com.mycompany.tarea17.model.entities.Grupo;
import com.mycompany.tarea17.model.entities.GrupoHB;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */
public class AlumnoDAOImplHibernate implements IAlumnoDAO {

    private static final Logger logger = LogManager.getLogger(AlumnoDAOImpl.class);
    private final EntityManagerFactory emf;

    public AlumnoDAOImplHibernate() {
        emf = HibernateUtil.getEntityManagerFactory();
    }

    @Override
    public boolean insertar(Alumno entidad) {
        AlumnoHB alumnoHB = convertirAEntidad(entidad);
        
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(alumnoHB);
            em.getTransaction().commit();
            return true;
        }catch(Exception e){
            logger.error("No se ha podido insertar en la base de datos."+alumnoHB, e);
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            return false;
        }finally{
            em.close();
        }
    }

    @Override
    public Alumno obtenerPorId(int id) {
        EntityManager em = emf.createEntityManager();
        AlumnoHB alumnoHB;
        try{
            alumnoHB = em.find(AlumnoHB.class, id);
            
        }finally{
            em.close();
        }
        return convertirADominio(alumnoHB);
    }

    @Override
    public List<Alumno> obtenerTodos() {
        EntityManager em = emf.createEntityManager();
        List<AlumnoHB> alumnosHB;
        try{
            alumnosHB = em.createQuery("SELECT a FROM Alumno a", AlumnoHB.class).getResultList();
        }finally{
            em.close();
        }
        
        return alumnosHB.stream()
                .map(this::convertirADominio)
                .collect(Collectors.toList());
    }

    @Override
    public boolean actualizar(Alumno entidad) {
        AlumnoHB alumnoHB = convertirAEntidad(entidad);
        EntityManager em = emf.createEntityManager();
        
        try{
            em.getTransaction().begin();
            //Para actualizar tenemos que hacer un merge
            em.merge(alumnoHB);
            em.getTransaction().commit();
            return true;
        }catch(Exception e){
            logger.error("No se ha podido actualizar el alumno."+alumnoHB,e);
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            return false;
        }finally{
            em.close();
        }
    }

    @Override
    public boolean eliminarPorId(int id) {
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            AlumnoHB alumnoHB = em.find(AlumnoHB.class, id);
            if(alumnoHB != null){
                em.remove(alumnoHB);
                em.getTransaction().commit();
                return true;
            }else{
                em.getTransaction().rollback();
                return false;
            }
        }catch(Exception e){
            logger.error("No se ha podido eliminar el alumno."+ id, e);
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            return false;
        }finally{
            em.close();
        }
    }

    // ---------------------------------------------------
    // Métodos de conversión (de Alumno a AlumnoHB y viceversa)
    // ---------------------------------------------------
    private AlumnoHB convertirAEntidad(Alumno alumno) {
        if (alumno == null) {
            return null;
        }

        GrupoHB grupoHB = null;
        if (alumno.getGrupo() != null) {
            grupoHB = new GrupoHB(alumno.getGrupo().getGrupo(),
                    alumno.getGrupo().getCiclo(),
                    alumno.getGrupo().getCurso());
        }

        AlumnoHB alumnoHB = new AlumnoHB(
                alumno.getNia(),
                alumno.getNombre(),
                alumno.getApellidos(),
                alumno.getGenero(),
                alumno.getFechaDeNacimiento(),
                grupoHB
        );

        return alumnoHB;
    }

    private Alumno convertirADominio(AlumnoHB alumnoHB) {
        if (alumnoHB == null) {
            return null;
        }
        Grupo grupoDominio = null;
        if (alumnoHB.getGrupo() != null) {
            grupoDominio = new Grupo(
                    alumnoHB.getGrupo().getGrupo(),
                    alumnoHB.getGrupo().getCiclo(),
                    alumnoHB.getGrupo().getCurso()
            );
        }

        Alumno alumno = new Alumno(
                alumnoHB.getNia(),
                alumnoHB.getNombre(),
                alumnoHB.getApellidos(),
                alumnoHB.getGenero(),
                alumnoHB.getFechaDeNacimiento(),
                grupoDominio
        );

        return alumno;
    }
    
    
    @Override
    public boolean eliminarPorCurso(String ciclo, String curso) {
        EntityManager em = emf.createEntityManager();
        
        try{
            em.getTransaction().begin();
            
            int affectedRows = em.createQuery("""
                                              UPDATE Alumno a SET a.grupo = NULL 
                                              WHERE a.grupo IN (SELECT g FROM Grupo g WHERE g.ciclo = :ciclo AND g.curso = :curso)
                                              """)
                    .setParameter("ciclo", ciclo)
                    .setParameter("curso", curso)
                    .executeUpdate();
            
            em.getTransaction().commit();
            return affectedRows > 0;
        }catch(Exception e){
            logger.error("No se ha podido realizar la operacion"+ciclo, curso, e);
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
          
            }
            return false;
        }finally{
            em.close();
        }
    }

    @Override
    public boolean eliminarAlumnosCuyoApellidoContengaUnaPalabra(String palabra) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try{
            et.begin();
            int rowsAffected = em.createQuery("""
                                              DELETE FROM Alumno a WHERE a.Apellidos LIKE "%:palabra%"
                                              """)
                    .setParameter("palabra", palabra)
                    .executeUpdate();
            et.commit();
            return true;
        }catch(Exception e){
            logger.error("No se ha podido realizar la operación ->"+e);
            if(et.isActive()){
                et.rollback();
            }
            return false;
        }finally{
            em.close();
        }
    }
    
    
    

}
