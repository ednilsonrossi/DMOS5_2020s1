package br.edu.ednilsonrossi.meusalunos.model;

public class Disciplina {
    private String sigla;
    private String disciplina;

    public Disciplina(String sigla, String disciplina) {
        this.sigla = sigla.toUpperCase();
        this.disciplina = disciplina;
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
}
