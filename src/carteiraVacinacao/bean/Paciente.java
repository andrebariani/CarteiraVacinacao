/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carteiraVacinacao.bean;

/**
 *
 * @author lucca
 */
public class Paciente {
    private String nome;
    private String especie;
    private String raca;
    private long cpfDono;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public long getCpfDono() {
        return cpfDono;
    }

    public void setCpfDono(long cpfDono) {
        this.cpfDono = cpfDono;
    }
    
    
}
