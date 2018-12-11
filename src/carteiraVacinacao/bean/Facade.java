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
    Modelo modelo;
    Carteira carteira;
    
    Cliente cliModExterno;
    Paciente pacModExterno;
    
    
    
    //Métodos para a claase modelo
    
    //ObS:  Troca de paramentro (especie -> e)
    //      Troca de retorno (void -> String)
    
    // 
    public String buscarMod(String )
    {
        ArrayList<Modelo> m = modelo.buscarMod();   
        
        for(int i = 0; i < m.size(); i++)
        {
            m.get(i).importarMod(, );
        }
        
        
        return "Trocar";
    }
    
    public void cadastrarMod(String e, String r)
    {
        modelo.cadastrarMod();
    }
    
    public void excluirMod()
    {
    
    }
    
    public void importarMod(String e, String r)
    {
    
    }
    
    public void addVacina(String vacina)
    {
    
    }
    
    public void delVacina(String vacina)
    {
    
    }
    
    
}
