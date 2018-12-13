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
    private Modelo model;
    
    @Before
    public void setup(){
        m = new Modelo();
        model = new Modelo();
    } 
    //Testa cadastrar modelo não existente no banco
    @Test
    public void Teste1CadastrarModelo(){
        assertTrue(m.cadastrarMod("Calopsita", "Arlequim"));
    }
    
    //Testa cadastrar modelo que existe no banco
    @Test
    public void Teste2CadastrarModelo(){
        assertFalse(m.cadastrarMod("Gato", "Siames"));
    }
    
    //Testa importar modelo que existe no banco
    @Test
    public void Teste3ImportarMod(){
        assertTrue(m.importarMod("Gato", "Siames")); 
    }
    
    //Testa importar um modelo que não existe
    @Test
    public void Teste4ImportarMod(){
        m.setEspecie("Camundongo");
        m.setRaca("Albino");
        
        assertTrue(m.importarMod("Camundongo", "Albino")); 
    }
    //Teste para adicionar vacina 
    @Test
    public void Teste5AddVacina(){
        m.addVacina("V4", "Gato", "Siames");
        
        assertTrue(m.addVacina("Panleucopenia", "Gato", "Siames")); 
    }
    
    //Teste para adicionar mais uma vacina e getVetorVacina
    @Test
    public void Teste6AddVacina(){
        m.addVacina("Rinotraqueíte", "Gato", "Siames");
        m.addVacina("Calicivirose", "Gato", "Siames");
        m.addVacina("Clamidiose", "Gato", "Siames");
        m.addVacina("Raiva", "Gato", "Siames");
        
        model.importarMod("Gato", "Siames");
                
        assertEquals("Panleucopenia;Rinotraqueíte;Calicivirose;Clamidiose;Raiva" ,  model.getVetorVacina()); 
        
    }
    
    //Teste para adicionar vacina repetida 
    @Test
    public void Teste7AddVacina(){
        m.addVacina("V4", "Gato", "Siames");
        
        assertFalse(m.addVacina("V4", "Gato", "Siames")); 
    }
    //Testa remover uma vacina ja inserida
    @Test
    public void Teste7DelVacina(){
        m.addVacina("V10", "Gato", "Siames");
        
        assertTrue(m.delVacina("V10", "Gato", "Siames"));
    }
    
    //Testa remover uma vacina não inserida
    @Test
    public void Teste8DelVacina(){
        assertFalse(m.delVacina("IssoNãoÉVacina", "Gato", "Siames"));
    }
    
    
}
