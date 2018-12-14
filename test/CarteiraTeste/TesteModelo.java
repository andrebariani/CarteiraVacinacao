/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CarteiraTeste;

import carteiraVacinacao.bean.Modelo;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author lucca
 */
public class TesteModelo {
    private Modelo m;
    private Modelo model;
    //Testa cadastrar modelo não existente no banco
    @Test
    public void Teste1CadastrarModelo(){
        m = new Modelo();
        m.excluirMod("Calopsita", "Arlequim");
        assertTrue(m.cadastrarMod("Calopsita", "Arlequim"));
    }
    
    //Testa cadastrar modelo que existe no banco
    @Test
    public void Teste2CadastrarModelo(){
        m = new Modelo();
        m.cadastrarMod("Cachorro", "Pincher");
        assertFalse(m.cadastrarMod("Cachorro", "Pincher"));
    }
    
    //Testa importar modelo que existe no banco
    @Test
    public void Teste3ImportarMod(){
        m = new Modelo();
        m.cadastrarMod("Gato", "Siames");
        assertTrue(m.importarMod("Gato", "Siames")); 
    }
    
    //Testa importar um modelo que não existe
    @Test
    public void Teste4ImportarMod(){
        m = new Modelo();
        m.excluirMod("Camundongo", "Albino");
        assertFalse(m.importarMod("Camundongo", "Albino")); 
    }
    //Teste para adicionar vacina 
    @Test
    public void Teste5AddVacina(){     
        m = new Modelo();
        m.delVacina("Cachorro", "Golden", "Panleucopenia");
        assertTrue(m.addVacina("Cachorro", "Golden", "Panleucopenia")); 
    }
    
    //Teste para adicionar mais uma vacina e getVetorVacina
    @Test
    public void Teste6AddVacina(){
        model = new Modelo();
        model.delVacina("Cachorro", "Pincher", "Rinotraqueíte");
        model.delVacina("Cachorro", "Pincher", "Calicivirose");
        model.delVacina("Cachorro", "Pincher", "Clamidiose");
        model.delVacina("Cachorro", "Pincher", "Raiva");
        
        model.addVacina("Cachorro", "Pincher", "Rinotraqueíte");
        model.addVacina("Cachorro", "Pincher", "Calicivirose");
        model.addVacina("Cachorro", "Pincher", "Clamidiose");
        model.addVacina("Cachorro", "Pincher", "Raiva");
        model.addVacina("Cachorro", "Pincher", "Raiva");
        
        model.importarMod("Cachorro", "Pincher");
                
        assertEquals("Rinotraqueíte;Calicivirose;Clamidiose;Raiva;" ,  model.getVetorVacina()); 
        
    }
    
    //Teste para adicionar vacina repetida 
    @Test
    public void Teste7AddVacina(){
        m = new Modelo();
        m.addVacina("V4", "Gato", "Siames");
        assertFalse(m.addVacina("V4", "Gato", "Siames")); 
    }
    
    //Testa remover uma vacina ja inserida
    @Test
    public void Teste8DelVacina(){
        m = new Modelo();
        m.addVacina("Gato", "Siames", "V10");
        
        assertTrue(m.delVacina("Gato", "Siames", "V10"));
    }
    
    //Testa remover uma vacina não inserida
    @Test
    public void Teste9DelVacina(){
        m = new Modelo();
        m.delVacina("Gato", "Siames", "IssoNãoÉVacina");
        assertFalse(m.delVacina("Gato", "Siames", "IssoNãoÉVacina"));
    }
    
    //Testa Excluir um modelo que existe
    @Test
    public void Teste10ExcluirMod(){
        m = new Modelo();
        m.cadastrarMod("Cachorro", "Poodle");
        assertTrue(m.excluirMod("Cachorro", "Poodle"));
    }
    
     //Testa Excluir um modelo que não existe
    @Test
    public void Teste11ExcluirMod(){
        m = new Modelo();
        m.excluirMod("Cachorro", "Poodle");
        assertTrue(m.excluirMod("Cachorro", "Poodle"));
    }
}
