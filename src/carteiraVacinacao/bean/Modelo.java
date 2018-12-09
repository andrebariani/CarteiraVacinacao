/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

import carteiraVacinacao.dao.ModeloDAO;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author lauanS
 */
public class Modelo {
    private String especie;
    private String raca;
    private int qtdVacinas;
    private final ArrayList<String> vacinas;

    
    
    public Modelo()
    {
        this.especie = "";
        this.raca = "";
        this.vacinas = new ArrayList();
        this.qtdVacinas = 0;
    }
    
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
        if(especie.matches("[\\Da-zA-Z -]"))
        {
            this.especie = especie;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Insira apenas letras e espaços",
                        "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getRaca() {
        return raca;
    }
    public void setRaca(String raca) {
        if(raca.matches("[\\Da-zA-Z -]"))
        {
            this.raca = raca;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Insira apenas letras e espaços",
                        "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getQtdVacinas() {
        return qtdVacinas;
    }
    
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
    public ArrayList<Modelo> buscarMod(String e)
    {
        ModeloDAO mBD = new ModeloDAO();
        ArrayList<Modelo> m = mBD.readEspecie(e);
        
        return m;    
        
    }

    // Salva o modelo atual
    //<OBS> Troca de parametros
    public void cadastrarMod()
    {
        ModeloDAO mBD = new ModeloDAO();
        Modelo m = mBD.readModelo(this.especie, this.raca);
        
        //Verifica se ja existe um modelo para a especie e raca especificada
        if(m == null)
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
    
    // Exclui o modelo especificado
    //<OBS> Troca de parametros
    public void excluirMod(String e, String r)
    {
        ModeloDAO mBD = new ModeloDAO();
        mBD.remove(e, r);
    }

    //Carrega o modelo
    //<OBS> trocou o retorno (void -> int)
    public int importarMod(String e, String r)
    {
        ModeloDAO mBD = new ModeloDAO();
        Modelo m = mBD.readModelo(e, r);
        //Verifica se foi encontrado algum modelo
        if(m != null)
        {
            //Atualiza o objeto atual com os dados obtidos
            this.setEspecie(m.getEspecie());
            this.setRaca(m.getRaca());
            this.setQtdVacinas(m.getQtdVacinas());
            this.setVetorVacina(m.getVetorVacina());
            return 1;
        }
        else
        {
            //Mensagem avisando que o modelo n foi encontrado
            JOptionPane.showMessageDialog(null, "Modelo não encontrada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    //Retorna todas as vacinas separadas por ";"    
    public String getVetorVacina()
    {
        String v = "";
        for(int i = 0; i < this.qtdVacinas; i++)
        {
            v += this.vacinas.get(i) + ";";
        }
        
        return v;
    }
    
    //Adiciona toda a string no array
    public void setVetorVacina(String v)
    {
        //Apaga todos os valores do ArrayList
        this.vacinas.clear();
        //Separa a string
        String m[] = v.split(";");
        //Adiciona todas as novas vacinas
        this.vacinas.addAll(Arrays.asList(m));
    }
    
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
