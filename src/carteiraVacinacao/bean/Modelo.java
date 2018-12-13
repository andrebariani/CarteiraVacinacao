/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

import carteiraVacinacao.dao.ModeloDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author lauanS
 */

/** Classe responsavel pela gerencia de Modelos de carteira de vacinacao */
public class Modelo {
    private String especie;
    private String raca;
    private int qtdVacinas;
    private final List<String> vacinas;
    
    /** Instancia um modelo */
    public Modelo()
    {
        this.especie = "";
        this.raca = "";
        this.vacinas = new ArrayList<>();
        this.qtdVacinas = 0;
    }
    
    
    // gets e sets
    public String getEspecie() {
        return especie;
    }
    
    /** Atualiza a especie do modelo 
      *  @param especie Somento letras de a-z e espaços e '-'
      */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    /** Retorna a raca do modelo
     * @return  Nome da raca */
    public String getRaca() {
        return raca;
    }
    
    /** Atualiza a raca do modelo 
     *  @param raca Somento letras de a-z e espaços e '-'
     */
    public void setRaca(String raca) {
        this.raca = raca;
    }
    
    /** Retorna a quantidade vacina
     * @return  */
    public int getQtdVacinas() {
        return qtdVacinas;
    }
    
    /** Atualiza a quantidade de vacina
     * @param qtdVacinas Nova quantidade de vacinas
     */
    public void setQtdVacinas(int qtdVacinas) {
        if(qtdVacinas >= 0)
        {
            this.qtdVacinas = qtdVacinas;
        }    
    }
    
    
    /** Retorna a vacina da posicao indicada 
     * @param posicao Posicao no array 
     * @return O nome da vacina
     */
    public String getVacina(int posicao)
    {
        if(posicao < this.vacinas.size())
        {
            return this.vacinas.get(posicao);
        }
        else
        {
            return null;
        }
              
    }
    
    public void setVetorVacina(String v){
        this.vacinas.clear();
        String m[] = v.split(";");
        this.vacinas.addAll(Arrays.asList(m));
    }
    
    //<OBS> troca de retorno
    /** Busca todos os modelos para a especie indicada 
     *  @param e Especie do animal
     *  @return Um arraylist com objetos modelos preenchidos
     */
    public List<Modelo> buscarMod(String e)
    {
        ModeloDAO mBD = new ModeloDAO();
       
        List<Modelo> m = mBD.readEspecie(e);
        
        return m;    
        
    }

    //<OBS> Troca de parametros
    /** Salva o modelo atual
     * @param e
     * @param r
     * @return  */
    public boolean cadastrarMod(String e, String r)
    {
        ModeloDAO mBD = new ModeloDAO();
        this.especie = e;
        this.raca = r;
        return mBD.create(this);
    }
    
    //<OBS> Troca de parametros
     /** Exclui o modelo especificado 
     * @param e especie do animal
     * @param r raca do animal
     * @return true se conseguir excluir ou false se nao conseguir excluir
     */
    public boolean excluirMod(String e, String r)
    {
        ModeloDAO mBD = new ModeloDAO();
        return mBD.remove(e, r);
    }

    
    //<OBS> trocou o retorno (void -> int)
    /** Carrega o modelo especificado 
     * @param e especie do animal
     * @param r raca do animal
     * @return retorna 1 se deu certo, 0 se deu errado
     */
    public boolean importarMod(String e, String r)
    {
        ModeloDAO mBD = new ModeloDAO();
        return mBD.readModelo(e, r, this);
    }

      
     /** retorna o vetor vacina formatados em uma string
      *  @return Retorna todas as vacinas separadas por ";"  
      */
    public String getVetorVacina()
    {
        String v = "";
        for(int i = 0; i < this.vacinas.size(); i++)
        {
            v += this.vacinas.get(i) + ";";
        }
        
        return v;
    }
    
    /** Adiciona uma nova vacina
     * @param e
     * @param r
      * @param vacina nome da vacina
     * @return true se conseguir adicionar a vacina, false se nao conseguir
      */
    public boolean addVacina(String e, String r, String vacina)
    {
        
        if(this.importarMod(e, r)){
            //Buscando a vacina
            int search = this.vacinas.indexOf(vacina);
            
            if(search == -1)
            {
                //Se nao encontrou nenhuma vacina, adiciona
                this.vacinas.add(vacina);
                this.qtdVacinas++;

                ModeloDAO mBD = new ModeloDAO();

                return mBD.update(this);
            }
            else
            {
                //Vacina ja foi adicionada
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    /** Delete uma nova vacina
     * @param e
     * @param r
      * @param vacina nome da vacina
     * @return 
      */
    public boolean delVacina(String e, String r, String vacina)
    {
        
        if(this.importarMod(e, r)){
            //Buscando a vacina
            int search = this.vacinas.indexOf(vacina);
            if(search != -1)
            {
                //Se nao encontrou nenhuma vacina, adiciona
                this.vacinas.remove(search);
                this.qtdVacinas--;

                ModeloDAO mBD = new ModeloDAO();

                return mBD.update(this);
            }
            else
            {
                //Vacina ja foi adicionada
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public void limpar(){
        this.qtdVacinas = 0;
        this.vacinas.clear();
    }
    
    
}
