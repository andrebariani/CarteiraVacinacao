/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CarteiraTeste;

import carteiraVacinacao.bean.Carteira;
import carteiraVacinacao.bean.Cliente;
import carteiraVacinacao.bean.Paciente;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author lauanS
 */
public class TesteCarteira {
    private Carteira c;
    private Cliente clienteModExterno;
    private Paciente pacienteModExterno;
    @Before
    public void setup(){
        c = new Carteira();
        clienteModExterno = new Cliente();
        pacienteModExterno = new Paciente();
        
        clienteModExterno.setCpf(54123698740L);
        
        pacienteModExterno.setNome("Freddy");
        pacienteModExterno.setCpfDono(54123698740L);
        pacienteModExterno.setEspecie("Gato");
        pacienteModExterno.setEspecie("Tonquines");
    }
    
    /**Testa cadastrar carteira não existente no banco */
    @Test
    public void Teste1CadastrarCarteira(){
        long cpf = 40510036805L;                
        c.excluirCart(cpf, "Bigode");  
        assertTrue(c.cadastrarCart(cpf, "Bigode")); 
        
    }
    
    /**Testa cadastrar carteiras para o mesmo cliente com animais diferentes */
    @Test
    public void Teste2CadastrarCarteira(){
        long cpf = 40510036805L;  
        
        c.excluirCart(cpf, "Pandora"); 
        c.excluirCart(cpf, "Nero"); 
        c.excluirCart(cpf, "Marley");               
        
        boolean result = c.cadastrarCart(cpf, "Pandora") && 
                c.cadastrarCart(cpf, "Nero") &&  c.cadastrarCart(cpf, "Marley");
        
        assertTrue(result); 
        
    }
    
    /**Testa cadastrar carteiras ja existente no banco */
    @Test
    public void Teste3CadastrarCarteira(){
        long cpf = 40510036805L; 
        
        c.excluirCart(40510036805L, "Bigode");
        
        c.cadastrarCart(cpf, "Bigode");
        
        assertFalse(c.cadastrarCart(cpf, "Bigode")); 
        
    }
    
    /**Testa cadastrar carteira e buscar elas no banco */
    @Test
    public void Teste4CadastrarCarteira(){
        long cpf = 40510036805L; 
        
        c.excluirCart(40510036805L, "Marley");
        c.cadastrarCart(cpf, "Marley");
                    
        assertTrue(c.buscarCart(cpf, "Marley"));      
    }
    
    
    /**Testa cadastrar carteira a um cliente que n existe */
    @Test
    public void Teste5CadastrarCarteira(){
        long cpf = 48721554296L;                    
          
        assertFalse(c.cadastrarCart(cpf, "Petucho")); 
        
    }
    
     /**Testa cadastrar carteira a um paciente que n existe */
    @Test
    public void Teste6CadastrarCarteira(){
        long cpf = 48721554296L;                    
          
        assertFalse(c.cadastrarCart(cpf, "Dolby")); 
        
    }
    
    /** Testa adicionar uma vacina */
    @Test
    public void Teste1AddVacina()
    {
       c.excluirCart(54123698740L, "Freddy");
       
       c.cadastrarCart(54123698740L, "Freddy");
       
       c.setClienteModExterno(clienteModExterno);
       c.setPacienteModExterno(pacienteModExterno); 
       
       assertTrue(c.addVacina("antirrabica"));               
    }
    
    /** Testa adicionar varias vacina */
    @Test
    public void Teste2AddVacina()
    {      
       c.excluirCart(54123698740L, "Freddy");
       
       c.cadastrarCart(54123698740L, "Freddy");
       
       c.setClienteModExterno(clienteModExterno);
       c.setPacienteModExterno(pacienteModExterno); 
       
       c.addVacina("V8");
       c.addVacina("V10");      
        
       assertEquals("V8;null;F;V10;null;F;", c.getVetorVacina());               
    }
    
    /** Testa adicionar uma vacina ja adicionada*/
    @Test
    public void Teste3AddVacina()
    {
       c.excluirCart(54123698740L, "Freddy");
       
       c.cadastrarCart(54123698740L, "Freddy");
       
       c.setClienteModExterno(clienteModExterno);
       c.setPacienteModExterno(pacienteModExterno);  
       
       c.addVacina("V8");
       c.addVacina("V10");
       
       c.addVacina("V5");
       c.addVacina("V5");      
        
       assertEquals("V8;null;F;V10;null;F;V5;null;F;", c.getVetorVacina());               
    }
    
    /** Testa adicionar uma vacina em uma carteira nao cadastrada */
    @Test
    public void Teste4AddVacina()
    {
        
       c.excluirCart(54123698740L, "Freddy");     
       
       c.setClienteModExterno(clienteModExterno);
       c.setPacienteModExterno(pacienteModExterno);  
       
       assertFalse(c.addVacina("antirrabica"));               
    }
    
    /** Testa adicionar uma vacina com um nome maior do que o limite permitido
     * no banco
     */
    @Test
    public void Teste5AddVacina()
    {
        String vacina = "pneumoultramicroscopicossilicovulcanoconiótico com "
                + "Piperidinoetoxicarbometoxibenzofenona e "
                + "Hipopotomonstrosesquipedaliofobia ";
        
        c.excluirCart(54123698740L, "Freddy");     
       
       c.setClienteModExterno(clienteModExterno);
       c.setPacienteModExterno(pacienteModExterno);  
       
        assertFalse(c.addVacina(vacina));
    }
    
    /** Testa excluir carteira e busca ela no banco de dados */
    @Test
    public void Teste1ExcluirCarteira()
    {
        long cpf = 54123698740L;      
       
        c.setClienteModExterno(clienteModExterno);
        c.setPacienteModExterno(pacienteModExterno);  
       
        c.cadastrarCart(cpf, "Freddy");
          
        assertTrue(c.excluirCart(cpf, "Freddy")); 
    }
    
    /** Testa excluir carteira */
    @Test
    public void Teste2ExcluirCarteira()
    {
        long cpf = 54123698740L;      
       
        c.setClienteModExterno(clienteModExterno);
        c.setPacienteModExterno(pacienteModExterno);  
       
        c.cadastrarCart(cpf, "Freddy");
          
        c.excluirCart(cpf, "Freddy"); 
        
        assertFalse(c.buscarCart(cpf, "Freddy"));
    }  
       
     /** Testa excluir uma carteira de um cliente existente, e busca
      * ele no banco de dados
      */
    @Test
    public void Teste3ExcluirCarteira()
    {
        long cpf = 54123698740L;  
        
        c.cadastrarCart(cpf, "Freddy");
        c.excluirCart(cpf, "Freddy"); 
        
        assertFalse(c.buscarCart(cpf, "Freddy")); 
    }
    
    
    /** Testa aplicar uma vacina */
    @Test
    public void Teste1AplicarVacina()
    {   
        c.excluirCart(54123698740L, "Freddy");
       
        c.cadastrarCart(54123698740L, "Freddy");
       
        c.setClienteModExterno(clienteModExterno);
        c.setPacienteModExterno(pacienteModExterno);  
        
        c.addVacina("Parvovirus felino");
       
        assertTrue(c.aplicarVacina("Parvovirus felino"));  
    }
    
    /** Testa aplicar uma vacina e verifica se foi adicionada */
    @Test
    public void Teste2AplicarVacina()
    {   
        c.excluirCart(54123698740L, "Freddy");
       
        c.cadastrarCart(54123698740L, "Freddy");
       
        c.setClienteModExterno(clienteModExterno);
        c.setPacienteModExterno(pacienteModExterno); 
  
        c.addVacina("Parvovirus felino");
        
        c.aplicarVacina("Parvovirus felino");
                   
        assertTrue(c.getCarteiraVacina().get(0).isAplicada());  
    }
  
    
    /** Testa aplicar várias vacinas vacina */
    @Test
    public void Teste3AplicarVacina()
    {   
        c.excluirCart(54123698740L, "Freddy");
       
        c.cadastrarCart(54123698740L, "Freddy");
       
        c.setClienteModExterno(clienteModExterno);
        c.setPacienteModExterno(pacienteModExterno); 
        
        boolean v1,v2,v3,v4,v5,v6, result = true;
        
        c.addVacina("Parvovirus felino");
        c.addVacina("FPV");
        c.addVacina("FHV-1");
        c.addVacina("V8");
        c.addVacina("Anti-rabbia");
        c.addVacina("FCV");
        
        
        
        c.aplicarVacina("Parvovirus felino");
        c.aplicarVacina("FPV");
        c.aplicarVacina("FHV-1");
        c.aplicarVacina("V8");
        c.aplicarVacina("Anti-rabbia");
        c.aplicarVacina("FCV");
        
        for (int i = 0; i < 6; i++) {
            result = result && c.getCarteiraVacina().get(i).isAplicada();
        }   
        
        assertTrue(result);  
    }
    
     /** Testa Agendar uma vacina */
    @Test
    public void Teste1AgendarVacina1()
    {   
        c.excluirCart(54123698740L, "Freddy");
       
        c.cadastrarCart(54123698740L, "Freddy");
       
        c.setClienteModExterno(clienteModExterno);
        c.setPacienteModExterno(pacienteModExterno);
        
        c.addVacina("Parvovirus felino");

        c.agendarVacina("Parvovirus felino", "30/12/2018");
        
        assertTrue(c.aplicarVacina("Parvovirus felino"));  
    }
    
     /** Testa Agendar uma vacina nao existente*/
    @Test
    public void Teste2AgendarVacina1()
    {   
        c.excluirCart(54123698740L, "Freddy");
       
        c.cadastrarCart(54123698740L, "Freddy");
       
        c.setClienteModExterno(clienteModExterno);
        c.setPacienteModExterno(pacienteModExterno);       

        c.agendarVacina("Parvovirus felino", "30/12/2018");
        
        assertFalse(c.aplicarVacina("Parvovirus felino"));  
    }
    
}
