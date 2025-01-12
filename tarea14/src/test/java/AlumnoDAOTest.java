/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.tarea14.modelo.dao.clases.AlumnoDAOImpl;
import com.mycompany.tarea14.modelo.dao.interfaces.AlumnoDAO;
import com.mycompany.tarea14.modelo.entidades.Alumno;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 *
 * @author mihai
 */
public class AlumnoDAOTest {

    static AlumnoDAO dao;

    @BeforeAll
    public static void setup() {
        dao = AlumnoDAOImpl.getInstance();
    }

    @Test
    public void testAdd() {
        Alumno alu = new Alumno();
        alu.setNombre("Miguel");
        alu.setApellidos("Aleksandrov");
        alu.setGenero('M');
        alu.setFechaNacimiento(LocalDate.of(2004, 1, 1));
        alu.setCiclo("DAM");
        alu.setCurso("2");
        alu.setGrupo(1);

        boolean inserted = dao.add(alu);
        Assertions.assertTrue(inserted, "No se pudo insertar el alumno");
    }
    
    //ES IMPORTANTE AJUSTAR EL ID PARA QUE NO HAYA ERROR A LA HORA DE BUSCAR (POR EL AUTOINCRMENET)
    @Test
    public void testGetById() {
        Alumno alu = dao.getById(11);
        if (alu != null) {
            Assertions.assertEquals(11, alu.getNia());
        } else {
            Assertions.fail("No se encontró el alumno con nia = 11");
        }
    }

    @Test
    public void testGetAll() {
        List<Alumno> lista = dao.getAll();
        Assertions.assertNotNull(lista);
        Assertions.assertFalse(lista.isEmpty(), "La lista de alumnos está vacía");
    }
    
    /*
    ES IMPORTANTE AJUSTAR EL ID PARA QUE NO HAYA ERROR A LA HORA DE BUSCAR (POR EL AUTOINCREMENT Y EL ORDEN DE EJECUCIOS DE LOS TEST,
    QUE NO ESTA GARANTIZADO). ENTONCES PONGO EL ID DE UN USUARIO QUE SI EXISTE. 
    */
    @Test
    public void testUpdate() {
        Alumno alu = dao.getById(8); 
        if (alu != null) {
            alu.setNombre("mIHAI");
            boolean updated = dao.update(alu);
            Assertions.assertTrue(updated, "No se pudo actualizar el alumno");
        } else {
            Assertions.fail("No existe el alumno para actualizar.");
        }
    }

    //ES IMPORTANTE AJUSTAR EL ID PARA QUE NO HAYA ERROR A LA HORA DE BUSCAR (POR EL AUTOINCRMENET)
    @Test
    public void testDelete() {
        boolean deleted = dao.delete(11); 
        Assertions.assertTrue(deleted, "No se pudo eliminar el alumno");
    }
}
