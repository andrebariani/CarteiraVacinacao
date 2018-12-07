/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

import carteiraVacinacao.dao.ModeloDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author lauanS
 */
public class Modelo {
    private String especie;
    private String raca;
    private int qtdVacinas;
    private ArrayList<String> vacinas;

    private ModeloDAO modeloDAO;
    
    public Modelo(String especie, String raca) {
        this.especie = especie;
        this.raca = raca;
        this.vacinas = new ArrayList();
        this.qtdVacinas = 0;
    }
    // gets e sets
    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }
    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getQtdVacinas() {
        return qtdVacinas;
    }
    public void setQtdVacinas(int qtdVacinas) {
        this.qtdVacinas = qtdVacinas;
    }
    
    
    //Retorna a vacina da posicao indicada
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
    
    //busca todos os modelos para a especie indicada
    //<OBS> troca de retorno
    public String[] buscarMod(String e)
    {
        String modelos[] = this.modeloDAO.buscaModelo(e);
        
        return modelos;    
        
    }

    // Salva o modelo atual
    //<OBS> Troca de parametros
    public void cadastrarMod()
    {
        String modelos[] = this.modeloDAO.buscaModelo(this.especie, this.raca);
        
        if(modelos != null)
        {
            this.modeloDAO.salvar(this.especie, this.raca, this.vacinas, 
                    this.qtdVacinas);
        }

    }
    
    // Exclui o modelo especificado
    //<OBS> Troca de parametros
    public void excluirMod(String e, String r)
    {
        String modelos[] = this.modeloDAO.buscaModelo(e, r);
        
        if(modelos != null)
        {
            this.modeloDAO.excluir(e, r);
        }

    }

    //Carrega o modelo 
    public void importarMod(String e, String r)
    {
        String modelos[] = this.modeloDAO.buscaModelo(e, r);
        if(modelos != null)
        {
            String vacinas[] = buscarVacinas(e, r);
            //Prenchendo a array com os dados no banco
            // *************************************//
            for(int i = 0; i < vacinas.size(); i++) {
                this.vacinas.add(vacinas.get(i));
            }
        }

    }

    public void addVacina(String vacina)
    {
        //Buscando o elemento, se n encontrar, add
        int search = this.vacinas.indexOf(vacina);
        if(search == -1)
        {
            this.vacinas.add(vacina);
        }
        else
        {
            //Mensagem que a vacina ja foi adicionada
            JOptionPane.showMessageDialog(null, "Vacina ja adicionada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void delVacina(String vacina)
    {
        //Buscando o elemento, se encontrar, del
        int search = this.vacinas.indexOf(vacina);
        if(search != -1)
        {
            this.vacinas.remove(search);
        }
        else
        {
            //Mensagem que a vacina ja foi adicionada
            JOptionPane.showMessageDialog(null, "Vacina não encontrada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
	 }
    }
    
}
