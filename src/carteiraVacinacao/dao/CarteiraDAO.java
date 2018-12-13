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

/**
 *
 * @author lucca
 */
public class CarteiraDAO {
    private final ClienteDAO cdao;
    private final PacienteDAO pdao;
    private final CtrVacinaDAO ctrdao;

    public CarteiraDAO() {
        this.cdao = new ClienteDAO();
        this.pdao = new PacienteDAO();
        this.ctrdao = new CtrVacinaDAO();
    }
    
    
    /** Cria uma nova carteira no Banco de dados
     * @param c Objeto da carteira que sera salva
     * @return Bollean se uma carteira foi inserida ou não
    */
    public boolean create(Carteira c){
        //Solicita conexao com banco
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        //Gera sql que sera executado no banco    
        stmt = con.prepareStatement("INSERT INTO carteira VALUES (?, ?, ?);");
        
        //Seta os valores que sera inserido na tabela de carteira
        stmt.setLong(1, c.getClienteModExterno().getCpf());
        stmt.setString(2, c.getPacienteModExterno().getNome());
        stmt.setInt(3, c.getQtdVacinas());
        
        //executa sql no statement
        stmt.executeUpdate();

        //Carteira inserida com sucesso
        return true;
        
        } catch (SQLException ex) {
             return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    /**
     * Faz a leitura de uma carteira e salva na cartei
     * @param cpf Atributo para pesquisa da carteira
     * @param nome Atributo para pesquisa da carteira
     * @param c Objeto para salvar carteira
     * @return (true) para carteira encontrada e (false) para carteira não encontrada
     */
    public boolean read(long cpf, String nome, Carteira c){
        //Solicita conexao com banco
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs;
        
        try {
            //Gera sql que sera executado no banco
            stmt = con.prepareStatement("Select * FROM carteira WHERE cpf_cliente = ? AND nome_paciente = ?");
            
            //Seta os valores que serao buscados
            stmt.setLong(1, cpf);
            stmt.setString(2, nome);
            
            //Executa sql setado no statement
            rs = stmt.executeQuery();
            
            //Se existir linhas no resultSet
            if(rs.next()){
                //Salva dados na carteira c
                c.setClienteModExterno(cdao.readCliente(rs.getLong("cpf_cliente")));
                c.setPacienteModExterno(pdao.readPaciente(rs.getLong("cpf_cliente"), rs.getString("nome_paciente")));
                c.setQtdVacinas(rs.getInt("qtd"));
                c.setCarteiraVacina(ctrdao.read(rs.getLong("cpf_cliente"), rs.getString("nome_paciente")));
                
                return true;
            }else{
                return false;
            }
        } catch (SQLException ex) {
             return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
    
    /**
     * Remove uma Carteira do banco de dados
     * @param cpfDono Atributo para a busca da carteira
     * @param nomePaciente Atributo para a busca da carteira
     * @return (true) para carteira removida com sucesso e (False) nao foi possivel remover carteira
     */
    public boolean remove(Long cpfDono, String nomePaciente){
        //Solicita conexao com banco
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
        stmt = con.prepareStatement("DELETE FROM carteira WHERE cpf_cliente = ? AND nome_paciente = ?;");
        stmt.setLong(1, cpfDono);
        stmt.setString(2, nomePaciente);
        
        //executa sql
        stmt.executeUpdate();
        
        //Removido com sucesso
        return true;
        
        } catch (SQLException ex) {
            return false;
        }finally{
            Conexao.closeConnection(con, stmt);
        }
    }
}
