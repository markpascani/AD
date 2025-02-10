/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.dao;

import com.mycompany.tarea17.model.dao.interfaces.IGrupoDAO;
import com.mycompany.tarea17.model.dao.utils.HibernateUtil;
import com.mycompany.tarea17.model.entities.Alumno;
import com.mycompany.tarea17.model.entities.AlumnoHB;
import com.mycompany.tarea17.model.entities.Grupo;
import com.mycompany.tarea17.model.entities.GrupoHB;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */
public class GrupoDAOImplHibernate implements IGrupoDAO {

    private static final Logger logger = LogManager.getLogger(AlumnoDAOImpl.class);
    private final EntityManagerFactory emf;
    
    public GrupoDAOImplHibernate(){
        emf = HibernateUtil.getEntityManagerFactory();
    }

    @Override
    public boolean insertar(Grupo grupo) {
        EntityManager em = emf.createEntityManager();
        GrupoHB grupoHB = convertirAGrupoHB(grupo);
        try{
            em.getTransaction().begin();
            em.persist(grupoHB);
            em.getTransaction().commit();
            return true;
        }catch(Exception e){
            logger.error("No se ha podido insertar el grupo"+ grupoHB,e);
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            return false;
        }finally{
            em.close();
        }
    }

    @Override
    public Grupo obtenerPorId(int id) {
        EntityManager em = emf.createEntityManager();
        GrupoHB grupoHB;
        try{
           grupoHB =  em.find(GrupoHB.class, id);
        }finally{
            em.close();
        }
        
        return convertirAGrupo(grupoHB);
    }

    @Override
    public List<Grupo> obtenerTodos() {
        EntityManager em = emf.createEntityManager();
        List<GrupoHB> gruposHB;
        try{
            gruposHB = em.createQuery("SELECT g FROM Grupo g", GrupoHB.class).getResultList();
        }finally{
            em.close();
        }
        
        return gruposHB.stream()
                .map(this::convertirAGrupo)
                .collect(Collectors.toList());
        
    }

    @Override
    public boolean actualizar(Grupo entidad) {
        EntityManager em = emf.createEntityManager();
        GrupoHB grupoHB = convertirAGrupoHB(entidad);
        try{
            em.getTransaction().begin();
            em.merge(grupoHB);
            em.getTransaction().commit();
            return true;
        }catch(Exception e){
            logger.error("No se ha podido actualizar el grupo"+ grupoHB,e);
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
            GrupoHB grupoHB = em.find(GrupoHB.class, id);
            if(grupoHB != null){
                em.remove(grupoHB);
                em.getTransaction().commit();
                return true;
            }else{
                em.getTransaction().rollback();
                return false;
            }
        }catch(Exception e){
            logger.error("no se ha podido eliminar el grupo"+id, e);
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            return false;
        }finally{
            em.close();
        }

    }

    private GrupoHB convertirAGrupoHB(Grupo grupo) {
        if (grupo == null) {
            return null;
        }

        GrupoHB grupoHB = null;

        if (grupo.getGrupo() > 0) {
            grupoHB = new GrupoHB(grupo.getGrupo(),
                    grupo.getCiclo(),
                    grupo.getCurso());
        }

        return grupoHB;
    }

    private Grupo convertirAGrupo(GrupoHB grupoHB) {
        if (grupoHB == null) {
            return null;
        }

        Grupo grupo = null;
        if (grupoHB.getGrupo() > 0) {
            grupo = new Grupo(grupoHB.getGrupo(),
                    grupoHB.getCiclo(),
                    grupoHB.getCurso());
        }
        return grupo;
    }

}
