package br.edu.ednilsonrossi.meusalunos.model;

import java.util.ArrayList;
import java.util.List;

public class Aluno {

    private String prontuario;
    private String nome;
    private String email;
    private List<Disciplina> disciplinaList;

    public Aluno(String prontuario, String nome) {
        this.prontuario = prontuario;
        this.nome = nome;
        disciplinaList = new ArrayList<>();
    }

    public Aluno(String prontuario, String nome, String email) {
        this.prontuario = prontuario;
        this.nome = nome;
        this.email = email;
        disciplinaList = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addDisciplina(Disciplina disciplina){
        if(disciplina != null){
            disciplinaList.add(disciplina);
        }
    }

    public void addDisciplina(List<Disciplina> disciplinas){
        if(disciplinas != null){
            disciplinaList.addAll(disciplinas);
        }
    }

    public List<Disciplina> getDisciplinaList() {
        return disciplinaList;
    }
}
