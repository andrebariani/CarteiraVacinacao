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
import javax.swing.JOptionPane;

/**
 *
 * @author lucca
 */
public class ClienteDAO {
    public Cliente readCliente(long cpf){
        Cliente c = new Cliente();
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM cliente WHERE cpf = ?");
            stmt.setLong(1, cpf);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                c.setCpf(rs.getLong("cpf"));
                c.setNome(rs.getString("nome_cliente"));
                
                return c;
            }else{
                JOptionPane.showMessageDialog(null, "CPF não cadastrado");
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha na leitura");
            return null;
        }finally{
            Conexao.closeConnection(con, stmt, rs);
        }
    }
    
     public ArrayList<Cliente> read(){
        Cliente c = new Cliente();
        ArrayList<Cliente> clientes = new ArrayList<>();
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
            
            return clientes;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha na leitura");
            return null;
        }finally{
            Conexao.closeConnection(con, stmt, rs);
        }
    
     } 
}
