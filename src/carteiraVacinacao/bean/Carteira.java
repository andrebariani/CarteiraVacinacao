/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

import carteiraVacinacao.dao.CarteiraDAO;
import carteiraVacinacao.dao.CtrVacinaDAO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.JOptionPane;

/**
 *
 * @author André
 */
public class Carteira {  
    private int qtdVacinas;
    private Cliente clienteModExterno;
    private Paciente pacienteModExterno;
    private List<CtrVacina> carteiraVacina;
    
    public String dateFormat;
    
    public CarteiraDAO carteiraBD;
    public CtrVacinaDAO CtrVacinaBD;
   
    public Carteira() { 
        this.dateFormat = "dd/MM/yyyy"; 
        qtdVacinas = 0;
        clienteModExterno = new Cliente();
        pacienteModExterno = new Paciente();
        carteiraVacina = new ArrayList<>();
        carteiraBD = new CarteiraDAO();
        CtrVacinaBD = new CtrVacinaDAO();
    }
    
    /** Importa vacinas de um modelo para serem
     *  armazenadas em carteira
     * @param Vacinas
     */
    public void importarModelo(List<String> Vacinas) {
        int i = 0;
	while (i < Vacinas.size()) {
            carteiraVacina.get(i).setVacina(Vacinas.get(i));
            i++;
	}
    }
    
    /** Realiza cadastro de carteira
     * @param cpf
     * @param nome_pet
     * @return 
     */
    public boolean cadastrarCart( long cpf, String nome_pet ) {
        Cliente cl = new Cliente();
        Paciente p = new Paciente();
        Carteira ca = new Carteira();
        
        cl.setCpf(cpf);
        p.setNome(nome_pet);
        
        ca.setClienteModExterno(cl);
        ca.setPacienteModExterno(p);
        
        return carteiraBD.create(ca);
    }
    
    /** Adiciona nova vacina a carteira
     *  retorna true se foi possível atualizar no banco de dados
     *  retorna false caso contrário
     * @param vacina
     * @return 
     */
    public boolean addVacina(String vacina) {
        CtrVacina va = new CtrVacina();
        va.setVacina(vacina);
        carteiraVacina.add(va);
        
        return this.CtrVacinaBD.create(va, clienteModExterno.getCpf(), pacienteModExterno.getNome());
    }
    
    /** Exclui carteira do banco de dados
     * @param cpf
     * @param nome
     */
    public void excluirCart(long cpf, String nome) {
        this.carteiraBD.remove(cpf, nome);
    }
    
    /** Gera Arquivo de extensão pdf contendo dados da carteira
     *  e incluindo uma tabela de todas as vacinas marcadas na carteira
     */
    public void imprimir() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("CV_" + clienteModExterno.getNome() + "_" + pacienteModExterno.getNome() + ".pdf"));
            
            document.open();
            Font smallfont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            
            Paragraph para = new Paragraph();
            para.add(new Chunk(clienteModExterno.getNome() + "\n"));
            para.add(new Chunk(clienteModExterno.getCpf() + "\n"));
            
            para.add(new Chunk("\n" + pacienteModExterno.getNome()+ "\n"));
            para.add(new Chunk(pacienteModExterno.getEspecie() + "\n"));
            para.add(new Chunk(pacienteModExterno.getRaca() + "\n" + "\n"));
            
            
            document.add(para);
            Rectangle small = new Rectangle(290,100);
            PdfPTable table = new PdfPTable(2);
            
            Stream.of("Vacina", "Foi Aplicada?").forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(columnTitle));
                table.addCell(header);
            });
            
            table.setTotalWidth(new float[]{ 180, 120 });
            table.setLockedWidth(true);
            
            for(int i = 0 ; i < carteiraVacina.size() ; i++) {
                // first row
                String p = carteiraVacina.get(i).getVacina();
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                p += "\n" + sdf.format(carteiraVacina.get(i).getData());
                PdfPCell cell = new PdfPCell(new Phrase(p));
                cell.setBorder(Rectangle.BOX);
                table.addCell(cell);
                
                // second row
                if(carteiraVacina.get(i).isAplicada())
                    cell = new PdfPCell(new Phrase("SIM!"));
                else
                    cell = new PdfPCell(new Phrase("NÃO!"));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            }
            
            document.add(table);
            document.close();
        } catch (Exception ex) {
            Logger.getLogger(Carteira.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getVetorVacina(){
        String strVacina = "";
        
        for(CtrVacina temp : carteiraVacina) {
            strVacina += temp.getVacina();
            
            strVacina += ";";
            
            if(temp.getData() != null) {
                SimpleDateFormat s = new SimpleDateFormat(dateFormat);
                strVacina += s.format(temp.getData());
            } else {
                strVacina += " ";
            }
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
    
    /** Define vacina de parâmetro como aplicada
     * @param vacina
     */
    public boolean aplicarVacina(String vacina) {
        int index = carteiraVacina.indexOf(vacina);
        
        if(index != -1) {
            carteiraVacina.get(index).setAplicada(true);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Vacina nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /** Define data para agendamento da vacina
     * @param vacina
     * @param data
     */
    public boolean agendarVacina(String vacina, Date data) {
        int index = carteiraVacina.indexOf(vacina);
        
        if(index != -1) {
            carteiraVacina.get(index).setData(data);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Vacina nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
     
    /** Deleta vacina da carteira
     * @param vacina
     * @return boolean
     */
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
    
    /** Busca carteira dado como parametro o cpf do cliente e o nome 
     *  do pet.
     * @param id_cli
     * @param nome
     */
    public boolean buscarCart(long id_cli, String nome) {
        return carteiraBD.read( id_cli, nome, this );
    }
    
    public int getQtdVacinas() {
        return qtdVacinas;
    }

    public void setQtdVacinas(int qtd) {
        this.qtdVacinas = qtd;
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
