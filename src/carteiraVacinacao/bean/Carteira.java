/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

/**
 *
 * @author lucca
 */
public class Carteira {
    int qtd_carteiras;
    Cliente clienteModExterno;
    Paciente pacienteModExterno;
    CtrVacina carteira_vacina;
    
    
    public int getQtd_carteiras() {
        return qtd_carteiras;
    }

    public void setQtd_carteiras(int qtd_carteiras) {
        this.qtd_carteiras = qtd_carteiras;
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

    public CtrVacina getCarteira_vacina() {
        return carteira_vacina;
    }

    public void setCarteira_vacina(CtrVacina carteira_vacina) {
        this.carteira_vacina = carteira_vacina;
    }
           
}
