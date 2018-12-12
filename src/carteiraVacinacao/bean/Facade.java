/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

import carteiraVacinacao.dao.PacienteDAO;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Date;

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
    
    public boolean verificaModelo( long cpf, String nome ) {
        Paciente p;
        PacienteDAO pdao = new PacienteDAO();
        p = pdao.readPaciente(cpf, nome);
        
        if(p != null) {
            return true;
        } else {
            return false;
        }
    }
    
    public void importarModelo( long cpf, String nome ) {
        Paciente p;
        PacienteDAO pdao = new PacienteDAO();
        p = pdao.readPaciente(cpf, nome);
        
        carteira.importarModelo(modelo.getVacinas());
    }
    
   public void cadastrarCart( long cpf, String nome_pet ) {
        carteira.cadastrarCart(cpf, nome_pet);
    }
    
    public void excluirCart(long cpf, String nome) {
        carteira.carteiraBD.remove(cpf, nome);
    }
    
    public void imprimir() throws DocumentException, FileNotFoundException {
        carteira.imprimir();
    }
    
    public String getVetorVacina(){
        return carteira.getVetorVacina();
    }
    
    public void setVetorVacina(String strVacina){
        carteira.setVetorVacina(strVacina);
    }
    
    public boolean aplicarVacina(String vacina) {
        return carteira.aplicarVacina(vacina);
    }

    public boolean agendarVacina(String vacina, Date data) {
        return carteira.agendarVacina(vacina, data);
    }
     
    public boolean delVacina(String vacina) {
        return carteira.delVacina(vacina);
    }
    
    public boolean buscarCart(long id_cli, String nome) {
        return carteira.buscarCart(id_cli, nome);
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
        
        List<Modelo> m = modelo.buscarMod(e);   
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
            retorno += ";";
        }
        
        System.out.println(retorno); 
       return retorno;
    }
    
     /** Cadastra um novo modelo no banco de dados
     *   @param e Especie do animal
     *   @param r Raca do animal 
     */
    public void cadastrarMod(String e, String r)
    {
        if(this.importarMod(e, r) == 0)
        {
            modelo.setEspecie(e);
            modelo.setRaca(r);
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
        
            modelo.addVacina(vacina);
        
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
