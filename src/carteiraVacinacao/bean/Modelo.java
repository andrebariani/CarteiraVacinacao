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

    private ModeloDAO modeloDAO;//trocar
    
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
        
        if(m == null)
        {
            //Pegando todos os modelo
            this.modeloDAO.create(this);
        }
        else
        {
            mBD.update(this);
        }
       
    }
    
    // Exclui o modelo especificado
    //<OBS> Troca de parametros
    public void excluirMod(String e, String r)
    {
        this.modeloDAO.remove(e, r);
    }

    //Carrega o modelo 
    public void importarMod(String e, String r)
    {
        ModeloDAO mBD = new ModeloDAO();
        Modelo m = mBD.readModelo(e, r);
        
        this.setEspecie(m.getEspecie());
        this.setRaca(m.getRaca());
        this.setQtdVacinas(m.getQtdVacinas());
        this.setVetorVacina(m.getVetorVacina());
        /*
        
        Modelo m = this.modeloDAO.buscaModelo(e, r);
        if(m != null)
        {
            String vacinas[] = buscarVacinas(e, r);
            //Prenchendo a array com os dados no banco
            
            for(int i = 0; i < vacinas.size(); i++) {
            this.vacinas.add(vacinas.get(i));
        //    }
        }
        */
    }

    //criar funcao para transforma em string o vetor
    
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
        if(search == -1)
        {
            this.vacinas.add(vacina);
            this.qtdVacinas++;
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
            this.qtdVacinas--;
        }
        else
        {
            //Mensagem que a vacina ja foi adicionada
            JOptionPane.showMessageDialog(null, "Vacina não encontrada",
                        "Erro", JOptionPane.ERROR_MESSAGE);
	}
    }
    
}
