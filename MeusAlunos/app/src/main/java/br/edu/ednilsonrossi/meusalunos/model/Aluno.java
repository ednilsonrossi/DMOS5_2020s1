package br.edu.ednilsonrossi.meusalunos.model;

public class Aluno {

    private String prontuario;
    private String nome;

    public Aluno(String prontuario, String nome) {
        this.prontuario = prontuario;
        this.nome = nome;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
