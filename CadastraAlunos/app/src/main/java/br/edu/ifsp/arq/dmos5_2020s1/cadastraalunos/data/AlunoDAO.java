package br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.data;

import java.util.LinkedList;
import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.model.Aluno;

public class AlunoDAO {

    private List<Aluno> mAlunoList;
    private static AlunoDAO sAlunoDAO = null;

    private AlunoDAO(){
        mAlunoList = new LinkedList<>();
    }

    public static AlunoDAO getInstante(){
        if(sAlunoDAO == null){
            sAlunoDAO = new AlunoDAO();
        }
        return sAlunoDAO;
    }

    public void add(Aluno aluno) throws NullPointerException{
        if(aluno == null){
            throw new NullPointerException("Aluno inválido");
        }

        mAlunoList.add(aluno);
    }

    public List<Aluno> all(){
        return mAlunoList;
    }

}
