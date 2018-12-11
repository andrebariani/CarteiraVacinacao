/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.dao;

import carteiraVacinacao.bean.CtrVacina;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author lucca
 */
public class CtrVacinaDAO {
    
    public boolean create(CtrVacina ctr, long cpfDono, String nomePaciente){
        Connection con = null;
        con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO ctrVacina VALUES (?, ?, ?, ?, ?, ?);");
            stmt.setString(1, ctr.getVacina());
            stmt.setDate(2, (Date) ctr.getData());
            stmt.setBoolean(3, ctr.isAplicada());
            stmt.setLong(4, cpfDono);
            stmt.setString(5, nomePaciente);
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Vacina inserida com sucesso: " + ctr.getVacina());
            return true;
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao inserir vacina");
             return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    public ArrayList<CtrVacina> read(long cpf, String nomePaciente){
        Connection con = Conexao.getConnection();
        ArrayList<CtrVacina> controle = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CtrVacina ctr = new CtrVacina();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM ctrVacina WHERE cpf_dono = ? and nome_paciente = ?");
            stmt.setLong(1, cpf);
            stmt.setString(2, nomePaciente);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
               ctr.setVacina(rs.getString("nome_vacina"));
               ctr.setData(rs.getDate("data_vacina"));
               ctr.setAplicada(rs.getBoolean("aplicada"));
                
               controle.add(ctr);
            }
            
            return controle;
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao inserir Modelo");
             return null;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
}
