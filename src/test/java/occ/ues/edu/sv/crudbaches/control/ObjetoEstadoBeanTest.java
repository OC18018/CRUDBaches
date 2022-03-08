/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package occ.ues.edu.sv.crudbaches.control;

import java.util.List;
import javax.persistence.EntityManager;
import occ.ues.edu.sv.crudbaches.entity.ObjetoEstado;
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
public class ObjetoEstadoBeanTest {

    public ObjetoEstadoBeanTest() {
    }

    @Test
    public void testCreate() {
        System.out.println("create");
        ObjetoEstado objeto_estado = new ObjetoEstado();
        objeto_estado.setObservaciones("creado desde test " + System.currentTimeMillis());
        ObjetoEstadoBean instance = new ObjetoEstadoBean();

        boolean expResult = true;
        boolean result = instance.create(objeto_estado);
        assertEquals(expResult, result);
    }

    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        ObjetoEstado objeto_estado;
        ObjetoEstadoBean instance = new ObjetoEstadoBean();
        long id = 2;
        objeto_estado = instance.findObjetoEstado(id);

        objeto_estado.setObservaciones("editado desde test" + System.currentTimeMillis());
        instance.edit(objeto_estado);
    }
    
//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        long id = 1;//id a eliminar
//        ObjetoEstadoBean instance = new ObjetoEstadoBean();
//        instance.destroy(id);
//    }
    
    
    @Test
    public void testFindObjetoEstado() {
        System.out.println("findEstado");
        long id = 2;
       ObjetoEstadoBean instance = new ObjetoEstadoBean();
        ObjetoEstado expResult = new ObjetoEstado();
        ObjetoEstado result = instance.findObjetoEstado(id);
        assertEquals(result.getClass(), ObjetoEstado.class);
        System.out.println(result.getObservaciones());
    }

}
