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
    private CtrVacinaDAO ctrdao;
    
    
    public boolean create(Carteira c){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        stmt = con.prepareStatement("INSERT INTO Cliente VALUES (?, ?, ?);");
        stmt.setLong(1, c.getClienteModExterno().getCpf());
        stmt.setString(2, c.getPacienteModExterno().getNome());
        stmt.setInt(3, c.getQtdVacinas());
        
        stmt.executeUpdate();
        
        return true;
        
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao inserir carteira");
             return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public boolean read(long cpf, String nome, Carteira c){
        
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = con.prepareStatement("Select * FROM carteira WHERE cpf_cliente = ? AND nome_paciente = ?");
            stmt.setLong(1, cpf);
            stmt.setString(2, nome);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                c.setClienteModExterno(cdao.readCliente(rs.getLong("cpf_cliente")));
                c.setPacienteModExterno(pdao.readPaciente(rs.getLong("cpf_cliente"), rs.getString("nome_paciente")));
                c.setQtdVacinas(rs.getInt("qtd"));
                c.setCarteiraVacina(ctrdao.read(rs.getLong("cpf_cliente"), rs.getString("nome_paciente")));
            }else{
                //JOptionPane.showMessageDialog(null, "Carteira não existe");
                return false;
            }
            
            return true;
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao buscar carteira");
             return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public void remove(Long cpfDono, String nomePaciente){
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        stmt = con.prepareStatement("DELETE FROM carteira WHERE cpf_cliente = ? AND nome_paciente = ?;");
        stmt.setLong(1, cpfDono);
        stmt.setString(2, nomePaciente);
        
        stmt.executeUpdate();
        
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao remover Modelo");
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
}
