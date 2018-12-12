/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

import carteiraVacinacao.dao.CarteiraDAO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    
    public void excluirCart(long cpf, String nome) {
        this.carteiraBD.remove(cpf, nome);
    }
    
    public void imprimir() throws DocumentException, FileNotFoundException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Carteira_Vacinacao.pdf"));
 
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);
 
        document.add(chunk);
        document.close();
    }
    
    public String getVetorVacina(){
        String strVacina = null;
        
        for(CtrVacina temp : carteiraVacina) {
            strVacina += temp.getVacina();
            
            strVacina += ";";
            
            SimpleDateFormat s = new SimpleDateFormat(dateFormat);
            strVacina += s.format(temp.getData());

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
            
            Date dataAux;
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            
            try {
                dataAux = sdf.parse(vVacina[1+i]);
            } catch(Exception e){
                return;
            }
            
            aux.setData(dataAux);
            
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

    public boolean agendarVacina(String vacina, Date data) {
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
    
    public void buscarCart(int id_cli, String nome) {
        this.carteiraBD.read( id_cli, nome, this );
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
    
    public ArrayList<CtrVacina> getALVacina() {
        return this.carteiraVacina;
    }
    
    public ArrayList<CtrVacina> getCarteiraVacina() {
        return carteiraVacina;
    }

    public void setCarteiraVacina(ArrayList<CtrVacina> carteiraVacina) {
        this.carteiraVacina = carteiraVacina;
    }
}
