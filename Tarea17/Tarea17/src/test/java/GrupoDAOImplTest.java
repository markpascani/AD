/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.tarea17.model.dao.GrupoDAOImpl;
import com.mycompany.tarea17.model.dao.utils.MyDataSource;
import com.mycompany.tarea17.model.entities.Grupo;
import java.sql.SQLException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
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
public class GrupoDAOImplTest {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(GrupoDAOImplTest.class);

    private GrupoDAOImpl grupoDAO;

    @BeforeAll
    public void setUpClass() {
        grupoDAO = new GrupoDAOImpl();

    }

    @AfterAll
    public void tearDownClass() {
        System.out.println("Finalizando todas las pruebas.");
    }

    @BeforeEach
    public void setUp() {
        try {
            MyDataSource.getConnection().createStatement().execute("DELETE FROM Grupo");
        } catch (SQLException e) {
            logger.error("Error al limpiar las tablas: " + e);
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            MyDataSource.getConnection().createStatement().execute("DELETE FROM Grupo");
        } catch (SQLException e) {
            logger.error("Error al limpiar las tablas: " + e);
        }
    }

    @Test
    public void testInsertGrupo() {
        Grupo grupo = new Grupo("DAM", "Segundo");

        boolean resultado = grupoDAO.insertar(grupo);
        assertTrue(resultado, "El grupo debereria insertarse correctamente.");
        assertTrue(grupo.getGrupo() > 0, "El ID del grupo deberia haberse generado correctamente.");
    }

    @Test
    public void testObtenerGrupoPorId() {
        Grupo grupo = new Grupo("DAM", "Segundo");
        grupoDAO.insertar(grupo);
        int idGenerado = grupo.getGrupo();

        //Recuperamos el grupo por ID
        Grupo grupoObtenido = grupoDAO.obtenerPorId(idGenerado);
        assertNotNull(grupoObtenido, "El grupo deberia existir en la base de datos");
        assertEquals("DAM", grupoObtenido.getCiclo(), "El ciclo debería ser DAM.");
        assertEquals("Segundo", grupoObtenido.getCurso(), "El curso debería ser Segundo.");

    }

    @Test
    public void testActualizarGrupo() {
        Grupo grupo = new Grupo();
        grupo.setCiclo("DAM");
        grupo.setCurso("1º");

        // Insertar el grupo y recuperar el ID generado
        grupoDAO.insertar(grupo);
        int idGenerado = grupo.getGrupo();

        // Actualizar el grupo
        grupo.setCiclo("ASIR");
        grupo.setCurso("2º");
        boolean actualizado = grupoDAO.actualizar(grupo);
        assertTrue(actualizado, "El grupo debería actualizarse correctamente.");

        // Verificar los cambios
        Grupo grupoActualizado = grupoDAO.obtenerPorId(idGenerado);
        assertEquals("ASIR", grupoActualizado.getCiclo(), "El ciclo debería ser ASIR.");
        assertEquals("2º", grupoActualizado.getCurso(), "El curso debería ser 2º.");
    }
    
       @Test
    public void testObtenerTodosLosGrupos() {
        // Insertar varios grupos
        Grupo grupo1 = new Grupo();
        grupo1.setCiclo("DAM");
        grupo1.setCurso("1º");
        grupoDAO.insertar(grupo1);

        Grupo grupo2 = new Grupo();
        grupo2.setCiclo("ASIR");
        grupo2.setCurso("2º");
        grupoDAO.insertar(grupo2);

        // Recuperar todos los grupos
        List<Grupo> grupos = grupoDAO.obtenerTodos();
        assertEquals(2, grupos.size(), "Debería haber dos grupos en la base de datos.");
    }

    @Test
    public void testEliminarGrupo() {
        Grupo grupo = new Grupo();
        grupo.setCiclo("DAM");
        grupo.setCurso("1º");

        // Insertar el grupo y recuperar el ID generado
        grupoDAO.insertar(grupo);
        int idGenerado = grupo.getGrupo();

        // Eliminar el grupo
        boolean eliminado = grupoDAO.eliminarPorId(idGenerado);
        assertTrue(eliminado, "El grupo debería eliminarse correctamente.");

        // Verificar que ya no existe
        Grupo grupoEliminado = grupoDAO.obtenerPorId(idGenerado);
        assertNull(grupoEliminado, "El grupo eliminado no debería existir en la base de datos.");
    }

}
