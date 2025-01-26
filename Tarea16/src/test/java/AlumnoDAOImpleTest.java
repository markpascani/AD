/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.tarea16.model.dao.AlumnoDAOImpl;
import com.mycompany.tarea16.model.dao.GrupoDAOImpl;
import com.mycompany.tarea16.model.dao.utils.MyDataSource;
import com.mycompany.tarea16.model.entities.Alumno;
import com.mycompany.tarea16.model.entities.Grupo;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInstance;

/**
 *
 * @author mihai
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlumnoDAOImpleTest {

    private static final Logger logger = LogManager.getLogger(AlumnoDAOImpleTest.class);
    private AlumnoDAOImpl alumnoDAO;
    private GrupoDAOImpl grupoDAO;
    private Grupo grupo = new Grupo("DAM", "Segundo");
    private Grupo grupo2 = new Grupo("DAW", "Segundo");

    @BeforeAll
    public void setUpClass() {
        alumnoDAO = new AlumnoDAOImpl();
        grupoDAO = new GrupoDAOImpl();

        boolean resultado1 = grupoDAO.insertar(grupo2);
        boolean resultado2 = grupoDAO.insertar(grupo);

        if (!resultado1 || !resultado2) {
            throw new IllegalStateException("No se pudieron insertar los grupos iniciales.");
        }
    }

    @AfterAll
    public void tearDownClass() {
        grupoDAO.eliminarPorId(grupo.getGrupo());
        grupoDAO.eliminarPorId(grupo2.getGrupo());
        System.out.println("Finalizando todas las pruebas.");
    }

    @BeforeEach
    public void setUp() {
        try {
            MyDataSource.getConnection().createStatement().execute("DELETE FROM Alumno");
        } catch (SQLException e) {
            logger.error("No se ha podido eliminar de la bbdd." + e);
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            MyDataSource.getConnection().createStatement().execute("DELETE FROM Alumno");
        } catch (SQLException e) {
            logger.error("No se ha podido eliminar de la bbdd." + e);
        }

    }

    @Test
    public void insertarAlumno() {
        Alumno alumno = new Alumno("Mihai", "String", 'H', LocalDate.of(2000, Month.MARCH, 12), grupo);
        boolean resultado = alumnoDAO.insertar(alumno);

        assertTrue(resultado, "Deberia ser true porque se ha insertado el alumno");
        assertTrue(alumno.getNia() > 0, "Deberia haber generado corretamente el id del alumno");

    }

    @Test
    public void obtenerAlumnoPorId() {
        Alumno alumno = new Alumno("Mihai", "String", 'H', LocalDate.of(2000, Month.MARCH, 12), grupo2);
        alumnoDAO.insertar(alumno);
        int idGenerado = alumno.getNia();

        Alumno alumnoObtenido = alumnoDAO.obtenerPorId(idGenerado);
        assertNotNull(alumnoObtenido, "El alumno no dberia ser null");
        assertEquals("Mihai", alumnoObtenido.getNombre(), "El nombre del alumno obtenido deberia ser Mihai.");
    }

    @Test
    public void actualizarAlumno() {
        Alumno alumno = new Alumno("Mihai", "String", 'H', LocalDate.of(2000, Month.MARCH, 12), grupo);
        alumnoDAO.insertar(alumno);
        int idGenerado = alumno.getNia();

        alumno.setNombre("Jorge");
        alumno.setGrupo(grupo2);
        boolean resultado = alumnoDAO.actualizar(alumno);
        assertTrue(resultado, "Deberia ser true si el alumno se ha actualizado");

        Alumno alumnoObtenido = alumnoDAO.obtenerPorId(idGenerado);
        assertNotNull(alumnoObtenido, "El alumno no deberia ser null");
        assertEquals("Jorge", alumnoObtenido.getNombre(), "El nombre del alumno obtenido deberia ser Jorge.");
        assertEquals(grupo2.getGrupo(), alumnoObtenido.getGrupo().getGrupo(), "El grupo del alumno deberia ser 2");
    }

    @Test
    public void obtenerTodosLosAlumnos() {
        Alumno alumno = new Alumno("Mihai", "String", 'H', LocalDate.of(2000, Month.MARCH, 12), grupo);
        alumnoDAO.insertar(alumno);
        Alumno alumno2 = new Alumno("Jorge", "String", 'H', LocalDate.of(2000, Month.MARCH, 12), grupo);
        alumnoDAO.insertar(alumno2);

        List<Alumno> alumnos = alumnoDAO.obtenerTodos();
        assertEquals(2, alumnos.size(), "La longitud de la lista de alumnos deberia ser 2.");
    }

    @Test
    public void eliminarAlumno() {
        Alumno alumno = new Alumno("Mihai", "String", 'H', LocalDate.of(2000, Month.MARCH, 12), grupo);
        alumnoDAO.insertar(alumno);
        int idGenerado = alumno.getNia();

        boolean resultado = alumnoDAO.eliminarPorId(idGenerado);
        assertTrue(resultado, "Deberia ser true si se ha eliminado.");

        Alumno alumnoEliminado = alumnoDAO.obtenerPorId(idGenerado);
        assertNull(alumnoEliminado, "Deberia ser nulo si se ha eliminado.");

    }
}
