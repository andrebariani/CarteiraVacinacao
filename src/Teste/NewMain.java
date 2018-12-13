/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste;

import carteiraVacinacao.bean.Modelo;
import carteiraVacinacao.dao.ModeloDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucca
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ModeloDAO dao = new ModeloDAO();
        
        List<Modelo> m = dao.readEspecie("Cachorro");
        
        for(Modelo j: m){
            System.out.println(j.getRaca());
        }
    }
    
}
