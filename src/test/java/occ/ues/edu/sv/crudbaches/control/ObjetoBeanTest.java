/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package occ.ues.edu.sv.crudbaches.control;

import java.util.List;
import javax.persistence.EntityManager;
import occ.ues.edu.sv.crudbaches.entity.Objeto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author armandop444
 */
public class ObjetoBeanTest {

    public ObjetoBeanTest() {
    }

    @Test
    public void testCreate() {
        System.out.println("create");
        Objeto objeto = new Objeto();
        objeto.setNombre("Creando prueba" + System.currentTimeMillis());
        ObjetoBean instance = new ObjetoBean();

        boolean expResult = true;
        boolean result = instance.create(objeto);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Objeto objeto;
        ObjetoBean instance = new ObjetoBean();
        long id = 2;
        objeto = instance.findObjeto(id);

        objeto.setNombre("editado prueba" + System.currentTimeMillis());
        instance.edit(objeto);

    }
    
//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        long id = 1;//id a eliminar
//        ObjetoBean instance = new ObjetoBean();
//        instance.destroy(id);
//
//    }
    
    @Test
    public void testFindObjeto() {
        System.out.println("findEstado");
        long id = 2;
        ObjetoBean instance = new ObjetoBean();
        Objeto expResult = new Objeto();
        Objeto result = instance.findObjeto(id);
        assertEquals(result.getClass(), Objeto.class);
        System.out.println(result.getNombre());
    }
}
