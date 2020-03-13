package br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.data;

import java.util.LinkedList;
import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.model.Aluno;

public class AlunoDAO {

    private List<Aluno> mList;
    private static AlunoDAO sAlunoDAO;

    private AlunoDAO(){
        mList = new LinkedList<>();
    }


}
