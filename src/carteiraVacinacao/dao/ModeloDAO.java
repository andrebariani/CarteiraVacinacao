/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.dao;

import carteiraVacinacao.bean.Modelo;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author lucca
 */
public class ModeloDAO {
    
    /** Insere um Modelo no banco dados 
      * @param m objeto da classe Modelo
     * @return 
      */
    public boolean create(Modelo m){
        //Solicita conexao com banco
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        //Gera sql que sera executado no banco
        stmt = con.prepareStatement("INSERT INTO modelo VALUES (?, ?, ?, ?);");
        
        //Seta os valores da tupla que sera inserida na tabela
        stmt.setString(1, m.getEspecie());
        stmt.setString(2, m.getRaca());
        stmt.setInt(3, m.getQtdVacinas());
        stmt.setString(4, m.getVetorVacina());
        
        //Executa sql setado no statement 
        stmt.executeUpdate();
        return true;
        
        } catch (SQLException ex) {
            return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    
    /** Lê todos os modelos cadastrados no banco de dados e retorna uma lista com os modelos
     * @return 
      */
    public List<Modelo> read(){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs;
        Modelo m;
        List<Modelo> modelos = new ArrayList<>();
        
        try {
            //Gera sql que sera executado no banco
            stmt = con.prepareStatement("SELECT * FROM modelo");
            
            //Executa sql setado no statement 
            rs = stmt.executeQuery();
        
            //Enquanto ainda existir linhas no resultSet
            while(rs.next()){
                //Cria nova instancia de modelo
                m = new Modelo();
                
                //Seta valores dessa instancia
                m.setEspecie(rs.getString("especie"));
                m.setRaca(rs.getString("Raca"));
                m.setQtdVacinas(rs.getInt("qtd"));
                m.setVetorVacina(rs.getString("vacinas"));
                
                //Adicioa na Lista
                modelos.add(m);
            }
            
            //retorna lista de modelos 
            return modelos;
            
        } catch (SQLException ex) { 
            return null;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    
    /** Lê todos os modelos cadastrados no banco de dados de uma certa especie e retorna uma lista com os modelos
     * @param especie String com a especie que deseja ser procurada  
     * @return   
      */
    public List<Modelo> readEspecie(String especie){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Modelo m;
        List<Modelo> modelos = new ArrayList();
        
        try {
            //Gera sql que sera executado no banco
            stmt = con.prepareStatement("SELECT * FROM modelo WHERE especie LIKE ?");
            stmt.setString(1, especie);
            
            //Executa sql setado no statement 
            rs = stmt.executeQuery();
            
            //Enquanto ainda existir linhas no resultSet
            while(rs.next()){
                //Cria nova instancia de modelo
                m = new Modelo();
                
                
                m.setEspecie(rs.getString("especie"));
                m.setRaca(rs.getString("raca"));
                m.setQtdVacinas(rs.getInt("qtd"));
                m.setVetorVacina(rs.getString("vacinas"));
               
                
                modelos.add(m);
            }
            return modelos;
            
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
             return null;
        }finally{
            Conexao.closeConnection(con, stmt, rs);
        }
    }
    
    /** Lê um modelo do banco de dados passando sua especie e modelo
     * @param especie String com a especie que deseja ser procurada
     * @param raca String com a raca do modelo a ser procurado
     * @param m Objeto da classe modelo que ira receber o modelo lido
     * @return boolean diz se foi realizada a leitura ou não existe modelo
      */
    public boolean readModelo(String especie, String raca , Modelo m){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs;

        System.out.println(especie+raca);
                
        try {
            //Gera sql que sera executado no banco
            stmt = con.prepareStatement("SELECT * FROM modelo WHERE especie = ?  AND raca = ?");
            stmt.setString(1, especie);
            stmt.setString(2, raca);
            
            //Executa sql setado no statement 
            rs = stmt.executeQuery();
            
            //Se existir linha no resultSet
            if(rs.next()){
                //Altera objeto Modelo m passado como parametro
                m.setEspecie(rs.getString("especie"));
                m.setRaca(rs.getString("Raca"));
                m.setQtdVacinas(rs.getInt("qtd"));
                m.setVetorVacina(rs.getString("vacinas"));
                System.out.println(rs.getString("especie"));
                //Retorna que foi feita a leitura de um modelo
                return true;
            }else{
                System.out.println("oii");
                //Retorna que o modelo não existe no banco de dados
                return false;
            }
           
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao ler Modelo");
             return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    /** Remove um modelo do banco de dados passando sua especie e raca
     * @param especie String com a especie que deseja ser removida  
     * @param raca String com a raca que deseja ser removida
     * @return boolean diz se foi removido com sucesso
      */
    public boolean remove(String especie, String raca){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        //Gera sql que sera executado no banco
        stmt = con.prepareStatement("DELETE FROM modelo WHERE especie = ? AND raca = ?;");
        stmt.setString(1, especie);
        stmt.setString(2, raca);
        
        //Executa sql setado no statement 
        stmt.executeUpdate();
        
        //removido com sucesso
        return true;
        
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao remover Modelo");
             return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    /** Atualiza um modelo no banco de dados
     * @param m Objeto de modelo que ira ser salvo no banco de dados  
     * @return boolean para se o modelo foi atualizado com sucesso ou não
      */
    public boolean update(Modelo m){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        //Gera sql que sera executado no banco
        stmt = con.prepareStatement("UPDATE modelo SET qtd = ?, vacinas = ? WHERE especie = ? AND raca = ?;");
        stmt.setInt(1, m.getQtdVacinas());
        stmt.setString(2, m.getVetorVacina());
        stmt.setString(3, m.getEspecie());
        stmt.setString(4, m.getRaca());
            System.out.println("oi" + m.getVetorVacina()+" " + m.getEspecie());
        //Executa sql setado no statement 
        stmt.executeUpdate();
        
        //Modelo foi alterado
        return true;
        } catch (SQLException ex) {
            return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
}
