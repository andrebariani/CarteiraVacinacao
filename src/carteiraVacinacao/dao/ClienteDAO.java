/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.dao;

import carteiraVacinacao.bean.Cliente;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lucca
 */
public class ClienteDAO {
    
    public void create(Cliente c){
        Connection con = null;
        con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO Cliente VALUES (?, ?);");
            stmt.setLong(1, c.getCpf());
            stmt.setString(2, c.getNome());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso: " + c.getNome());

        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao inserir cliente");
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public Cliente readCliente(long cpf){
        Cliente c = new Cliente();
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Cliente WHERE cpf = ?");
            stmt.setLong(1, cpf);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                c.setCpf(rs.getLong("cpf"));
                c.setNome(rs.getString("nome_cliente"));
            }else{
                JOptionPane.showMessageDialog(null, "CPF não cadastrado");
                Conexao.closeConnection(con, stmt, rs);
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha na leitura");
            Conexao.closeConnection(con, stmt, rs);
            return null;
        }
        Conexao.closeConnection(con, stmt, rs);
        return c;
    }
    
     public List<Cliente> read(){
        Cliente c = new Cliente();
        List<Cliente> clientes = new ArrayList<>();
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM cliente");
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                c.setCpf(rs.getInt("cpf"));
                c.setNome(rs.getString("nome_cliente"));
                
                clientes.add(c);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha na leitura");
            Conexao.closeConnection(con, stmt, rs);
            return null;
        }
        Conexao.closeConnection(con, stmt, rs);
        return clientes;
    }
    
    
}
