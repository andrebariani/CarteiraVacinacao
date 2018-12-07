/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

/**
 *
 * @author André
 */
public class Carteira {
    int qtdCarteiras;
    Cliente clienteModExterno;
    Paciente pacienteModExterno;
    CtrVacina carteiraVacina;
    
    public void cadastrarCart() {
        
    }
    
    public void excluirCart() {
        
    }
    
    public void imprimir() {
        
    }
    
    public void getVetorVacina(){
    
    }
    
    public void setVetorVacina(){
    
    }
    
    public void aplicarVacina() {
        
    }

    public void agendarVacina(String vacina) {
        
    }
     
    public void delVacina(String vacina) {
        
    }
    
    public void buscarCart(int id_pet) {
        
    }
    
    public void setCliente(Cliente c) {
        
    }
    
    public void setPaciente(Paciente c) {
        
    }
    
    public int getQtdCarteiras() {
        return qtdCarteiras;
    }

    public void setQtdCarteiras(int qtdCarteiras) {
        this.qtdCarteiras = qtdCarteiras;
    }

    public Cliente getClienteModExterno() {
        return clienteModExterno;
    }

    public void setClienteModExterno(Cliente clienteModExterno) {
        this.clienteModExterno = clienteModExterno;
    }

    public Paciente getPacienteModExterno() {
        return pacienteModExterno;
    }

    public void setPacienteModExterno(Paciente pacienteModExterno) {
        this.pacienteModExterno = pacienteModExterno;
    }

    public CtrVacina getCarteiraVacina() {
        return carteiraVacina;
    }

    public void setCarteiraVacina(CtrVacina carteiraVacina) {
        this.carteiraVacina = carteiraVacina;
    }
           
}
