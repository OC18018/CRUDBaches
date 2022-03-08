/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package occ.ues.edu.sv.crudbaches.control;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import occ.ues.edu.sv.crudbaches.entity.Estado;
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
public class EstadoBeanTest {

    public EstadoBeanTest() {
    }
    @Test
    public void testCreate() {
        System.out.println("create");
        Estado nuevo = new Estado();
        nuevo.setFechaCreacion(new Date());
        nuevo.setNombre("Creando prueba" + System.currentTimeMillis());
        EstadoBean instance = new EstadoBean();
        boolean expResult = true;
        boolean result = instance.create(nuevo);
        assertEquals(expResult, result);
    }

    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Estado estado;
        EstadoBean instance = new EstadoBean();
        estado = instance.findEstado(1);
        estado.setNombre("probando");
        instance.edit(estado);
        
    }
    
    
//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        Integer id = 8;
//        EstadoBean instance = new EstadoBean();
//        instance.destroy(id);
//    }
    
    @Test
    public void testFindEstado() {
        System.out.println("findEstado");
        Integer id = 2;
        EstadoBean instance = new EstadoBean();
        Estado expResult = new Estado();
        Estado result = instance.findEstado(id);
        assertEquals(result.getClass(), Estado.class);
        System.out.println(result.getNombre());
    }
}
