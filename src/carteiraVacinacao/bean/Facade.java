/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

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
 * @author lauanS
 */
public class Facade {
    private Modelo modelo;
    private Carteira carteira;
    
    private Cliente cliModExterno;
    private Paciente pacModExterno;
    
    public Facade(){
        modelo = new Modelo();
        carteira = new Carteira();
        cliModExterno = new Cliente();
        pacModExterno = new Paciente();
    }
    
    //Métodos para a classse carteira
    public void cadastrarCart() {
        carteira.carteiraBD.create(carteira);
    }
    
    public void excluirCart(long cpf, String nome) {
        carteira.carteiraBD.remove(cpf, nome);
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
        
        for(CtrVacina temp : carteira.getCarteiraVacina()) {
            strVacina += temp.getVacina();
            
            strVacina += ";";
            
            SimpleDateFormat s = new SimpleDateFormat(carteira.dateFormat);
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
            SimpleDateFormat sdf = new SimpleDateFormat(carteira.dateFormat);
            
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
            
            carteira.getCarteiraVacina().add(aux);
        }
        
    }
    
    public boolean aplicarVacina(String vacina) {
        int index = carteira.getCarteiraVacina().indexOf(vacina);
        
        if(index == -1) {
            carteira.getCarteiraVacina().get(index).setAplicada(true);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Vacina nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean agendarVacina(String vacina, Date data) {
        int index = carteira.getCarteiraVacina().indexOf(vacina);
        
        if(index == -1) {
            carteira.getCarteiraVacina().get(index).setData(data);
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Vacina nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
     
    public boolean delVacina(String vacina) {
        int index = carteira.getCarteiraVacina().indexOf(vacina);
        
        if(carteira.getCarteiraVacina().remove(carteira.getCarteiraVacina().get(index))) {
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Vacina nao encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public void buscarCart(int id_cli, String nome) {
        carteira.carteiraBD.read( id_cli, nome, carteira );
    }
    
    //Métodos para a classse modelo
    
    //ObS:  Troca de paramentro (especie -> e)
    //      Troca de retorno (void -> String)
    
    /** Retorna uma string formatada com as racas daquela especie
     *  @param e Especie a ser buscada no banco
     *  @return Uma string contendo todas as racas daquela especie
     *          Cada raca estara separada por ';'
     *          Retorna NULL se nao encontrar
     */
    public String buscarMod(String e)
    {
        
        ArrayList<Modelo> m = modelo.buscarMod(e);   
        String retorno = "";
   
        //Verifica se o campo esta vazio
        if(m.isEmpty())
            return retorno;
        
        //Pegando todas as especie e racas
        for(int i = 0; i < m.size(); i++)
        {
            retorno += m.get(i).getEspecie();
            retorno += ";";
            retorno += m.get(i).getRaca();
        }
        //Adicionando o ponto e virgula final
        retorno += ";";
        
        return retorno;
    }
    
     /** Cadastra um novo modelo no banco de dados
     *   @param e Especie do animal
     *   @param r Raca do animal 
     */
    public void cadastrarMod(String e, String r)
    {
        if(this.importarMod(e, r) == 1)
        {
            modelo.cadastrarMod();
        }
    }
    
     /** Remove o modelo especificado
     *   @param e Especie do animal
     *   s@param r Raca do animal 
     */
    public void excluirMod(String e, String r)
    {
        if(this.importarMod(e, r) == 1)
        {
            modelo.excluirMod(e, r);
        }
    }
    
    /** Obtem o as vacinas do modelo especificado, e preenche 
     *  o modelo para usar outros metodos, como excluirMod()
     * @param e Especie do animal
     * @param r Raca do animal
     * @return Uma string formatada com as vacinas
     */
    public int importarMod(String e, String r)
    {
        return modelo.importarMod(e, r);
    }
    
     /** Adiciona a vacina no modelo especificado
     * @param e Especie do animal
     * @param r Raca do animal
     * @param vacina Nome da vacina, uma mensagem de erro aparece
     *          ao tentar adicionar uma vacina ja existente
     */
    public void addVacina(String e, String r, String vacina)
    {
        if(this.importarMod(e, r) == 1)
        {
            modelo.addVacina(vacina);
        }
    }    
     /** Remove a vacina no modelo especificado
     * @param e Especie do animal
     * @param r Raca do animal
     * @param vacina Nome da vacina, uma mensagem de erro aparece
     *          ao tentar remover uma vacina ja que nao existe no modelo
     */  
    public void delVacina(String e, String r, String vacina)
    {
        if(this.importarMod(e, r) == 1)
        {
            modelo.delVacina(vacina);
        }
    }
    
    
}
