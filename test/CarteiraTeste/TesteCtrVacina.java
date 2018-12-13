/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CarteiraTeste;

import carteiraVacinacao.bean.CtrVacina;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
/**
 *
 * @author LauanS
 */
public class TesteCtrVacina {
    private CtrVacina c;
    @Before
    public void setup(){
        c = new CtrVacina();
    }
    
    /** Testa se insera uma vacina */
    @Test
    public void TesteSetVacina(){
        c.setVacina("V8");
        assertEquals("V8", c.getVacina());
    }
    
    /** Testa se insera uma data */
    @Test
    public void TesteSetData(){
        c.setData("10/08/2017");
        assertEquals("10/08/2017", c.getData());
    }
    
    /** Testa se aplica a vacina */
    @Test
    public void TesteSetAplicada(){
        c.setAplicada(false);
        assertTrue(c.isAplicada());
    }
    
}
