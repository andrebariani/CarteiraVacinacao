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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
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
    private List<CtrVacina> carteiraVacina;
    
    public String dateFormat;
    
    public CarteiraDAO carteiraBD;
   
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
        Font smallfont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        
        String header = clienteModExterno.getNome() + "\n";
        header += clienteModExterno.getCpf() + "\n";
        
        header += "\n" + pacienteModExterno.getNome()+ "\n";
        header += pacienteModExterno.getEspecie() + "\n";
        header += pacienteModExterno.getRaca() + "\n";
        
        Chunk chunk = new Chunk(header, smallfont);
 
        document.add(chunk);
        
        Rectangle small = new Rectangle(290,100);
        PdfPTable table = new PdfPTable(3);
        
        table.setTotalWidth(new float[]{ 160, 120 });
        table.setLockedWidth(true);
        
        // first row
        PdfPCell cell = new PdfPCell(new Phrase("Some text here"));
        cell.setFixedHeight(30);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);
        
        // second row
        cell = new PdfPCell(new Phrase("Some more text", smallfont));
        cell.setFixedHeight(30);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        
        table.addCell(cell);
        // third row
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("and something else here", smallfont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);

        document.add(table);
        
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
    
    public List<CtrVacina> getCarteiraVacina() {
        return carteiraVacina;
    }

    public void setCarteiraVacina(List<CtrVacina> carteiraVacina) {
        this.carteiraVacina = carteiraVacina;
    }
}
