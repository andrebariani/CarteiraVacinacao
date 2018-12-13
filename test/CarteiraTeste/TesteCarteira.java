/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CarteiraTeste;

import carteiraVacinacao.bean.Carteira;
import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author lauanS
 */
public class TesteCarteira {
    private Carteira c;
    
    @Before
    public void setup(){
        c = new Carteira();
    }
    
    /**Testa cadastrar carteira não existente no banco */
    @Test
    public void Teste1CadastrarCarteira(){
        long cpf = 48721554296L;                    
          
        assertTrue(c.cadastrarCart(cpf, "Dolby")); 
        
    }
    
    /**Testa cadastrar carteiras para o mesmo cliente com animais diferentes */
    @Test
    public void Teste2CadastrarCarteira(){
        long cpf = 56941785211L;                    
        c.cadastrarCart(cpf, "Pegassus");
        c.cadastrarCart(cpf, "Hawk");
        c.cadastrarCart(cpf, "Jon");
        
        boolean result = c.cadastrarCart(cpf, "Pegassus") && 
                c.cadastrarCart(cpf, "Hawk") &&  c.cadastrarCart(cpf, "Jon");
        assertTrue(result); 
        
    }
    
    /**Testa cadastrar carteiras ja existente no banco */
    @Test
    public void Teste3CadastrarCarteira(){
        long cpf = 49717700211L;                    
        c.cadastrarCart(cpf, "Dolby");
        
        assertFalse(c.cadastrarCart(cpf, "Dolby"); 
        
    }
    
    /** Testa adicionar uma vacina */
    @Test
    public void Teste1AddVacina()
    {
       assertTrue(c.addVacina("antirrabica"));               
    }
    
    /** Testa adicionar varias vacina */
    @Test
    public void Teste1AddVacina()
    {            
       c.addVacina("V8");
       c.addVacina("V10");      
        
       assertEquals("V8;V10;", c.getVetorVacina());               
    }
    
    /** Testa adicionar uma vacina ja adicionada*/
    @Test
    public void Teste1AddVacina()
    {
        
       c.addVacina("V5");
       c.addVacina("V5");      
        
       assertEquals("V8;V10;V5", c.getVetorVacina());               
    }
    
    /** Testa excluir carteira */
    @Test
    public void TesteExcluirCarteira()
    {
        long cpf = 48721554296L;                    
          
        assertTrue(c.excluirCart(cpf, "Dolby")); 
    }
    
     /** Testa excluir uma carteira nao adicionada*/
    @Test
    public void TesteExcluirCarteira2()
    {
        long cpf = 45121474995L;                    
          
        assertFalse(c.excluirCart(cpf, "Rex")); 
    }
    
    /** Testa aplicar uma vacina*/
    @Test
    public void TesteAplicarVacina1()
    {   
        Carteira cart = new Carteira();
        
        cart.addVacina("Parvovirus felino");
       
        assertTrue(cart.aplicarVacina("Parvovirus felino"));  
    }
    
    /** Testa aplicar várias vacinas vacina*/
    @Test
    public void TesteAplicarVacina2()
    {   
        Carteira cart = new Carteira();
        
        boolean v1,v2,v3,v4,v5,v6, result;
        
        cart.addVacina("Parvovirus felino");
        cart.addVacina("FPV");
        cart.addVacina("FHV-1");
        cart.addVacina("V8");
        cart.addVacina("Anti-rabbia");
        cart.addVacina("FCV");
        
        
        
        v1 = cart.aplicarVacina("Parvovirus felino");
        v2 = cart.aplicarVacina("FPV");
        v3 = cart.aplicarVacina("FHV-1");
        v4 = cart.aplicarVacina("V8");
        v5 = cart.aplicarVacina("Anti-rabbia");
        v6 = cart.aplicarVacina("FCV");
        
        
        result = v1 && v2 && v3 && v4 && v5 && v6;
        
        assertTrue(result);  
    }
    
     /** Testa Agendar uma vacina */
    @Test
    public void TesteAgendarVacina1()
    {   
        Carteira cart = new Carteira();
        Date data = new Date();
        data.getTime();
        data.
        cart.addVacina("Parvovirus felino");

        cart.agendarVacina("Parvovirus felino", data);
        
        assertTrue(cart.aplicarVacina("Parvovirus felino"));  
    }
}
