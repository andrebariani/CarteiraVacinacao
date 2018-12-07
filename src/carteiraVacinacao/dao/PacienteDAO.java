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
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author lucca
 */
public class PacienteDAO {
    public Paciente readPaciente(int cpfDono){
        Paciente p = new Paciente();
        List<Paciente> pacientes ;
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM paciente WHERE cpf_dono = ?");
            stmt.setLong(1, cpfDono);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEspecie(rs.getString("especie"));
                p.setRaca(rs.getString("raca"));
                p.setCpfDono(rs.getLong(cpf_dono));
                
                pacientes.add(p);
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
}
