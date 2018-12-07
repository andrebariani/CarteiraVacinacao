/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

import carteiraVacinacao.dao.CarteiraDAO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author André
 */
public class Carteira {   
    private int qtdCarteiras;
    private Cliente clienteModExterno;
    private Paciente pacienteModExterno;
    private ArrayList<CtrVacina> carteiraVacina;
    private String dateFormat = "dd/MM/yyyy";
    
    private CarteiraDAO carteiraBD;
   
    public Carteira(int qtdCarteiras, Cliente clienteModExterno, Paciente pacienteModExterno, ArrayList<CtrVacina> carteiraVacina, CarteiraDAO carteiraBD) {
        this.qtdCarteiras = qtdCarteiras;
        this.clienteModExterno = clienteModExterno;
        this.pacienteModExterno = pacienteModExterno;
        this.carteiraVacina = carteiraVacina;
        this.carteiraBD = carteiraBD;
    }
    
    public void cadastrarCart() {
        carteiraBD.create(this);
    }
    
    public void excluirCart() {
        
    }
    
    public void imprimir() {
        
    }
    
    public String getVetorVacina(){
        String strVacina = null;
        
        for(CtrVacina temp : carteiraVacina) {
            strVacina += temp.getVacina();
            
            strVacina += ";";
            
            SimpleDateFormat s = new SimpleDateFormat(dateFormat);
            strVacina += s.format(temp.getData().getTime());

            strVacina += ";";
            
            if(temp.isAplicada()) {
                strVacina += "T";
            } else {
                strVacina += "F";
            }
            
            strVacina += ";";
        }
        
        return strVacina;
    }
    
    public void setVetorVacina(String strVacina){
        String vVacina[] = strVacina.split(";");
        
        for(int i = 0 ; i < 9999 ; i+=3) {
            CtrVacina aux;
            
            aux.setVacina(vVacina[i]);
            
            Calendar calaux;
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            calaux.setTime(sdf.parse(vVacina[i+1]));
            
            aux.setData(calaux);
            
            if(vVacina[i+2] == "T") {
                aux.setAplicada(true);
            } else {
                aux.setAplicada(false);
            }
            
            carteiraVacina.add(aux);
        }
        
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
}
