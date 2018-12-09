/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.dao;

import carteiraVacinacao.bean.Carteira;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author lucca
 */
public class CarteiraDAO {
    private ClienteDAO cdao;
    private PacienteDAO pdao;
    
    
    public void create(Carteira c){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        stmt = con.prepareStatement("INSERT INTO Cliente VALUES (?, ?, ?, ?);");
        stmt.setLong(1, c.getClienteModExterno().getCpf());
        stmt.setInt(2, c.getPacienteModExterno().getId());
        stmt.setInt(3, c.getQtdCarteiras());
        stmt.setString(4, c.getVetorVacina());
        
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao inserir carteira");
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public void read(long cpf){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Carteira c = new Carteira();
        
        
        try {
            stmt = con.prepareStatement("Select * FROM carteira WHERE cpf_cliente = ?");
            stmt.setLong(1, cpf);
            
            if(rs.next()){
                c.setClienteModExterno(cdao.readCliente(rs.getLong("cpf_cliente")));
                c.setQtdCarteiras(rs.getInt("qtd"));
                c.setVetorVacina(rs.getString("vacinas"));
            }else{
                JOptionPane.showMessageDialog(null, "Carteira não existe");
            }
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao buscar carteira");
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
}
