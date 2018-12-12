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
    private final ArrayList<String> vacinas;

    
    /** Instancia um modelo */
    public Modelo()
    {
        this.especie = "";
        this.raca = "";
        this.vacinas = new ArrayList();
        this.qtdVacinas = 0;
    }
    //<OBS> troca de param (especie, raca)
    /** Instancia um modelo com a especie e raca informada 
     *  OBS: nao verifica no banco se ja existe um modelo para essa especie e raca
     * @param e Especie
     * @param r Raca
     */
    public Modelo(String e, String r) {
        this.especie = e;
        this.raca = r;
        this.vacinas = new ArrayList();
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
    
    /** Retorna a quantidade vacina */
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
        else
        {
            JOptionPane.showMessageDialog(null, "Quantidade de vacinas inválida",
                        "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    /** Retorna a vacina da posicao indicada 
     * @param posicao Posicao no array 
     * @return O nome da vacina
     */
    public String getVacina(int posicao)
    {
        if(posicao < this.qtdVacinas)
        {
            return this.vacinas.get(posicao);
        }
        else
        {
            //Mensagem que a vacina ja foi adicionada
            JOptionPane.showMessageDialog(null, "Vacina não encontrada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
              
    }
    
    
    //<OBS> troca de retorno
    /** Busca todos os modelos para a especie indicada 
     *  @param e Especie do animal
     *  @return Um arraylist com objetos modelos preenchidos
     */
    public List<Modelo> buscarMod(String e)
    {
        System.out.println("BuscarMod " + e);
        ModeloDAO mBD = new ModeloDAO();
       
        List<Modelo> m = mBD.readEspecie(e);
        
        System.out.println("BuscarMod " + e);
        for (Modelo model : m){
            System.out.println(model.getRaca());
        }
               
        return m;    
        
    }

    //<OBS> Troca de parametros
    /** Salva o modelo atual  */
    public void cadastrarMod()
    {
        ModeloDAO mBD = new ModeloDAO();
        
        
        //Verifica se ja existe um modelo para a especie e raca especificada
        if(!mBD.readModelo(this.especie, this.raca, this))
        {
            //Cadastrando o modelo no banco de dados
            mBD.create(this);
        }
        else
        {
            //Se ja existir, atualiza o modelo no BD com os dados atuais
            mBD.update(this);
        }
       
    }
    
    //<OBS> Troca de parametros
     /** Exclui o modelo especificado 
     * @param e especie do animal
     * @param r raca do animal
     */
    public void excluirMod(String e, String r)
    {
        ModeloDAO mBD = new ModeloDAO();
        mBD.remove(e, r);
    }

    
    //<OBS> trocou o retorno (void -> int)
    /** Carrega o modelo especificado 
     * @param e especie do animal
     * @param r raca do animal
     * @return retorna 1 se deu certo, 0 se deu errado
     */
    public int importarMod(String e, String r)
    {
        ModeloDAO mBD = new ModeloDAO();
        
        //Verifica se foi encontrado algum modelo
        if(mBD.readModelo(e, r, this))
        {
            // Modelo encotrado
            return 1;
        }
        else
        {
            // Modelo nao encontrado
            return 0;
        }
    }

      
     /** retorna o vetor vacina formatados em uma string
      *  @return Retorna todas as vacinas separadas por ";"  
      */
    public String getVetorVacina()
    {
        String v = "";
        for(int i = 0; i < this.qtdVacinas; i++)
        {
            v += this.vacinas.get(i) + ";";
        }
        
        return v;
    }
    
    /** Aparti de uma string formatada,  preenche o vetor vacinas 
      * @param v vacinas do animal, separando cada vacina com ';'
      */
    public void setVetorVacina(String v)
    {
        //Apaga todos os valores do ArrayList
        this.vacinas.clear();
        //Separa a string
        String m[] = v.split(";");
        //Adiciona todas as novas vacinas
        this.vacinas.addAll(Arrays.asList(m));
    }
    
    /** Adiciona uma nova vacina
      * @param vacina nome da vacina
      */
    public void addVacina(String vacina)
    {
        //Buscando o elemento, se n encontrar, add
        int search = this.vacinas.indexOf(vacina);
        //Se nao encontrou nenhuma vacina, adiciona
        if(search == -1)
        {
            this.vacinas.add(vacina);
            this.qtdVacinas++;
        }
        else
        {
            //Mensagem avisando que a vacina ja foi adicionada
            JOptionPane.showMessageDialog(null, "Vacina ja adicionada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    /** Delete uma nova vacina
      * @param vacina nome da vacina
      */
    public void delVacina(String vacina)
    {
        //Buscando o elemento, se encontrar, del
        int search = this.vacinas.indexOf(vacina);
        //Se encontrou a vacina, remove
        if(search != -1)
        {
            this.vacinas.remove(search);
            this.qtdVacinas--;
        }
        else
        {
            //Mensagem avisando que a vacina nao foi encontrada 
            JOptionPane.showMessageDialog(null, "Vacina não encontrada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
	}
    }
    
}
