/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CarteiraTeste;

import carteiraVacinacao.bean.Modelo;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author lucca
 */
public class TesteModelo {
    private Modelo m;
    
    @Before
    public void setup(){
        m = new Modelo();
    }
    
    //Testa cadastrar modelo não existente no banco
    @Test
    public void Teste1CadastrarModelo(){
        m.setEspecie("Gato");
        m.setRaca("Siames");
        
        assertEquals(true , m.cadastrarMod());
        
    }
    
    //Testa cadastrar modelo que existe no banco
    @Test
    public void Teste2CadastrarModelo(){
        m.setEspecie("Gato");
        m.setRaca("Siames");
        
        assertEquals(false , m.cadastrarMod());
        
    }
    //Testa cadastrar modelo que existe no banco
    @Test
    public void Teste3CadastrarModelo(){
        m.setEspecie("Gato");
        m.setRaca("Siames");
        
        assertEquals(false , m.cadastrarMod());
        
    }
    
    @Test
    public void Teste4ImportarModelo(){
        m.setEspecie("Gato");
        m.setRaca("Siames");
        
        assertEquals(false , m.cadastrarMod());
        
    }
    
    
    
}
