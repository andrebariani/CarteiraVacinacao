/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

import java.util.ArrayList;

/**
 *
 * @author lauanS
 */
public class Facade {
    private Modelo modelo;
    private Carteira carteira;
    
    private Cliente cliModExterno;
    private Paciente pacModExterno;
    
    
    
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
