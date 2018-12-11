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
    
    public void create(Modelo m){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        stmt = con.prepareStatement("INSERT INTO modelo VALUES (?, ?, ?, ?);");
        stmt.setString(1, m.getEspecie());
        stmt.setString(2, m.getRaca());
        stmt.setInt(3, m.getQtdVacinas());
        stmt.setString(4, m.getVetorVacina());
        
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao inserir Modelo");
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public ArrayList<Modelo> read(){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Modelo m = new Modelo();
        ArrayList<Modelo> modelos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM modelo");
        
            while(rs.next()){
                m.setEspecie(rs.getString("especie"));
                m.setRaca(rs.getString("Raca"));
                m.setQtdVacinas(rs.getInt("qtd"));
                m.setVetorVacina(rs.getString("vacinas"));
                
                modelos.add(m);
            }
            
            JOptionPane.showMessageDialog(null, "Modelo inserido com sucesso");
            return modelos;
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao inserir Modelo");
             return null;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
      
    public ArrayList<Modelo> readEspecie(String especie){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Modelo m = new Modelo();
        ArrayList<Modelo> modelos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM modelo WHERE especie = ?");
            stmt.setString(1, especie);
            
            while(rs.next()){
                m.setEspecie(rs.getString("especie"));
                m.setRaca(rs.getString("Raca"));
                m.setQtdVacinas(rs.getInt("qtd"));
                m.setVetorVacina(rs.getString("vacinas"));
                
                modelos.add(m);
            }
            
            return modelos;
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao inserir Modelo");
             return null;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public void readModelo(String especie, String raca , Modelo m){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        
        try {
            stmt = con.prepareStatement("SELECT * FROM modelo WHERE especie = ?  AND raca = ?");
            stmt.setString(1, especie);
            stmt.setString(2, raca);
            
            while(rs.next()){
                m.setEspecie(rs.getString("especie"));
                m.setRaca(rs.getString("Raca"));
                m.setQtdVacinas(rs.getInt("qtd"));
                m.setVetorVacina(rs.getString("vacinas"));
            }
           
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao ler Modelo");
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public void remove(String especie, String raca){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        stmt = con.prepareStatement("DELETE FROM modelo WHERE especie = ? AND raca = ?;");
        stmt.setString(1, especie);
        stmt.setString(2, raca);
        
        
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao inserir Modelo");
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public void update(Modelo m){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        stmt = con.prepareStatement("UPDATE modelo SET qtd = ?, vacinas = ? WHERE especie = ? AND raca = ?;");
        stmt.setInt(1, m.getQtdVacinas());
        stmt.setString(2, m.getVetorVacina());
        stmt.setString(3, m.getEspecie());
        stmt.setString(4, m.getRaca());
        
        
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao atualizar Modelo");
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
}
