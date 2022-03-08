/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package occ.ues.edu.sv.crudbaches.control;

import java.util.List;
import javax.persistence.EntityManager;
import occ.ues.edu.sv.crudbaches.entity.TipoObjeto;
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
public class TipoObjetoBeanTest {

    public TipoObjetoBeanTest() {
    }

    @Test
    public void testCreate() {
        System.out.println("create");
        TipoObjeto tipo_objeto = new TipoObjeto();
        tipo_objeto.setActivo(true);
        TipoObjetoBean instance = new TipoObjetoBean();

        boolean expResult = true;
        boolean result = instance.create(tipo_objeto);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        TipoObjeto tipo_objeto;
        TipoObjetoBean instance = new TipoObjetoBean();
        int id = 2;
        tipo_objeto = instance.findTipoObjeto(id);

        tipo_objeto.setActivo(false);
        instance.edit(tipo_objeto);
    }
    
//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        int id = 1;//id a eliminar
//        TipoObjetoBean instance = new TipoObjetoBean();
//        instance.destroy(id);
//    }
    
    
    @Test
    public void testFindTipoObjeto() {
        System.out.println("findEstado");
        int id = 2;
        TipoObjetoBean instance = new TipoObjetoBean();
        TipoObjeto expResult = new TipoObjeto();
        TipoObjeto result = instance.findTipoObjeto(id);
        assertEquals(result.getClass(), TipoObjeto.class);
        System.out.println(result.getIdTipoObjeto());
    }
}
