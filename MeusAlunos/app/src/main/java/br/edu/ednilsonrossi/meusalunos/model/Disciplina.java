package br.edu.ednilsonrossi.meusalunos.model;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String sigla;
    private String disciplina;
    private List<Aluno> alunoList;

    public Disciplina(String sigla, String disciplina) {
        this.sigla = sigla.toUpperCase();
        this.disciplina = disciplina;
        alunoList = new ArrayList<>();
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla.toUpperCase();
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void addAluno(Aluno aluno){
        if(aluno != null){
            alunoList.add(aluno);
        }
    }

    public void addAluno(List<Aluno> alunos){
        if(alunos != null){
            alunoList.addAll(alunos);
        }
    }

    public List<Aluno> getAlunoList() {
        return alunoList;
    }
}
