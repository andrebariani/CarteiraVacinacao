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
import javax.swing.JOptionPane;

/**
 *
 * @author André
 */
public class Carteira {   
    private int qtdCarteiras;
    private Cliente clienteModExterno;
    private Paciente pacienteModExterno;
    private ArrayList<CtrVacina> carteiraVacina;
    private final String dateFormat;
    
    private CarteiraDAO carteiraBD;
   
    public Carteira() { this.dateFormat = "dd/MM/yyyy"; }
    
    public void cadastrarCart() {
        this.carteiraBD.create(this);
    }
    
    public void excluirCart() {
        this.carteiraBD.remove(this);
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
            CtrVacina aux = new CtrVacina();
            
            aux.setVacina(vVacina[i]);
            
            Calendar calaux = new Calendar;
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            calaux.setTime(sdf.parse(vVacina[i+1]));
            
            aux.setData(calaux);
            
            if("T".equals(vVacina[2+i])) {
                aux.setAplicada(true);
            } else {
                aux.setAplicada(false);
            }
            
            carteiraVacina.add(aux);
        }
        
    }
    
    public boolean aplicarVacina(String vacina) {
        int index = carteiraVacina.indexOf(vacina);
        
        if(index == -1) {
            carteiraVacina.get(index).setAplicada(true);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Vacina nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean agendarVacina(String vacina, Calendar data) {
        int index = carteiraVacina.indexOf(vacina);
        
        if(index == -1) {
            carteiraVacina.get(index).setData(data);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Vacina nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
     
    public boolean delVacina(String vacina) {
        int index = carteiraVacina.indexOf(vacina);
        
        if(carteiraVacina.remove(carteiraVacina.get(index))) {
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Vacina nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void buscarCart(int id_cli, int id_pet) {
        this.carteiraBD.read( id_cli, id_pet);
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
