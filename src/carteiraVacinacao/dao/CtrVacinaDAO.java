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
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author lucca
 */
public class CtrVacinaDAO {
    /**
     * Cria uma Vacina em um carteira
     * @param ctr objeto da vacina que será inserido
     * @param cpfDono chave da carteira que a vacina referencia
     * @param nomePaciente chave da carteira que a vacina referencia
     * @return (true) Vacina inserida com sucesso (false) Não foi possivel inserir vacina
     */
    public boolean create(CtrVacina ctr, long cpfDono, String nomePaciente){
        // Solicida conxao banco de dados
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
            //Gera sql que sera executado
            stmt = con.prepareStatement("INSERT INTO ctrVacina VALUES (?, ?, ?, ?, ?);");
            
            //seta valores que serao inseridos no banco de dados
            stmt.setString(1, ctr.getVacina());
            stmt.setDate(2, ctr.getData());
            stmt.setBoolean(3, ctr.isAplicada());
            stmt.setLong(4, cpfDono);
            stmt.setString(5, nomePaciente);
            
            //executa sql no statement
            stmt.executeUpdate();
            
            //vacina inserida com sucesso
            return true;
        } catch (SQLException ex) {
             return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    
    /**
     * Consulta todas as vacinas de uma certa carteira
     * @param cpf Chave da vacina que referencia Carteira
     * @param nomePaciente chave da vacina que referencia carteira
     * @return uma lista de vacinas de uma certa carteira
     */
    public List<CtrVacina> read(long cpf, String nomePaciente){
        //Solicita conexao com banco de dados
        Connection con = Conexao.getConnection();
        ArrayList<CtrVacina> controle = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CtrVacina ctr;
        
        try {
            //gera o sql que sera executado no banco de dados
            stmt = con.prepareStatement("SELECT * FROM ctrVacina WHERE cpf_dono = ? and nome_paciente = ?");
            
            //seta os valores que serao buscados
            stmt.setLong(1, cpf);
            stmt.setString(2, nomePaciente);
            
            //executa o sql no statement 
            rs = stmt.executeQuery();
            
            //enquanto  existir linhas no result set
            while(rs.next()){
                //cria nova instancia de CtrVacina
               ctr = new CtrVacina();
               
               //Seta valores obtidos no resultSet
               ctr.setVacina(rs.getString("nome_vacina"));
               ctr.setData(rs.getDate("data_vacina"));
               ctr.setAplicada(rs.getBoolean("aplicada"));
                
               //Adiciona vacina na lista
               controle.add(ctr);
            }
         
            return controle;
            
        } catch (SQLException ex) {
             return null;
        }finally{
            Conexao.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Atualiza uma vacina na carteira
     * @param ctr Objeto com vacina atualizada
     * @param cpfDono Atributo chave a carteira que referencia uma vacina
     * @param nomePaciente Atributo chave a carteira que referencia uma vacina
     * @return (true) vacina alterada com sucesso e (false) não foi possivel alterar vacina
     */
    public boolean update(CtrVacina ctr, long cpfDono, String nomePaciente){
        //solicita conexao com banco de dados
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
            //Gera sql que sera executado no banco
            stmt = con.prepareStatement("UPDATE ctrvacina SET data_vacina = ?, aplicada = ? WHERE cpf_dono = ? AND nome_paciente = ? AND nome_vacina = ?");
            
            //Seta os valores que serao alualizados na tabela de ctrvacina
            stmt.setBoolean(1, ctr.isAplicada());
            stmt.setDate(2, (Date) ctr.getData());
            //Seta os valores para a busca da vacina
            stmt.setLong(3, cpfDono);
            stmt.setString(4, nomePaciente);
            stmt.setString(5, ctr.getVacina());
            
            //executa sql no statement
            stmt.executeUpdate();
            
            //vacina atualizada com sucesso
            return true;
        
        } catch (SQLException ex) {
            System.out.println("oii");
             return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
}
