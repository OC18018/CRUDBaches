/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package occ.ues.edu.sv.crudbaches.control;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import occ.ues.edu.sv.crudbaches.entity.Ruta;
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
public class RutaBeanTest {
    
    public RutaBeanTest() {
    }
 

    @Test
    public void testCreate() {
        System.out.println("create");
        Ruta nuevo = new Ruta();
        nuevo.setFechaCreacion(new Date());
        nuevo.setNombre("Creando prueba" + System.currentTimeMillis());
        RutaBean instance = new RutaBean();
        boolean expResult = true;
        boolean result = instance.create(nuevo);
        assertEquals(expResult, result);
    }


    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Ruta ruta;
        RutaBean instance = new RutaBean();
        long id = 2;
        ruta = instance.findRuta(id);
        ruta.setNombre("probando");
        instance.edit(ruta);
    }

    
//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        long id = 1;
//        RutaBean instance = new RutaBean();
//        instance.destroy(id);
//    }
 
    @Test
    public void testFindRuta() {
        System.out.println("findRuta");

        long id = 2;
        RutaBean instance = new RutaBean();
        Ruta expResult = new Ruta();
        Ruta result = instance.findRuta(id);
        assertEquals(result.getClass(), Ruta.class);
        System.out.println(result.getNombre());
    }    
}
