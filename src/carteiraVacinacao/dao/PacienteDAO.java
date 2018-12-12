/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.dao;

import carteiraVacinacao.bean.Paciente;
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
public class PacienteDAO {
    public ArrayList<Paciente> readPaciente(long cpfDono){
        Paciente p = null;
        ArrayList<Paciente> pacientes = new ArrayList<>() ;
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM paciente WHERE cpf_dono = ?");
            stmt.setLong(1, cpfDono);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                new Paciente();
                p.setNome(rs.getString("nome"));
                p.setEspecie(rs.getString("especie"));
                p.setRaca(rs.getString("raca"));
                p.setCpfDono(rs.getLong("cpf_dono"));
                
                pacientes.add(p);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha na leitura");
            Conexao.closeConnection(con, stmt, rs);
            return null;
        }
        Conexao.closeConnection(con, stmt, rs);
        return  pacientes;
    }
    
    public Paciente readPaciente(long cpf, String nome){
        Paciente p = new Paciente();
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM paciente WHERE cpf_dono = ? AND nome_paciente = ?");
            stmt.setLong(1, cpf);
            stmt.setString(1, nome);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                p.setNome(rs.getString("nome"));
                p.setEspecie(rs.getString("especie"));
                p.setRaca(rs.getString("raca"));
                p.setCpfDono(rs.getLong("cpf_dono"));
               
                return p;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha na leitura");
            Conexao.closeConnection(con, stmt, rs);
            return null;
        }finally{
            Conexao.closeConnection(con, stmt, rs);
        }
    }
}
