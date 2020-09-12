package br.edu.ednilsonrossi.meusalunos.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

import br.edu.ednilsonrossi.meusalunos.R;
import br.edu.ednilsonrossi.meusalunos.constantes.Constantes;
import br.edu.ednilsonrossi.meusalunos.dao.AlunoDao;
import br.edu.ednilsonrossi.meusalunos.dao.DisciplinaDao;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;
import br.edu.ednilsonrossi.meusalunos.model.Disciplina;

public class MatricularAlunoActivity extends AppCompatActivity {

    private RecyclerView naoMatriculadosRecyclerView;
    private MatriculaAdapter matriculaAdapter;

    private Disciplina mDisciplina;
    private Aluno mAluno;
    private DisciplinaDao mDisciplinaDao;
    private AlunoDao mAlunoDao;

    private List<Aluno> alunoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricular_aluno);

        //Recuperar referencia do layout
        naoMatriculadosRecyclerView = findViewById(R.id.recyclerview_matricular_alunos);

        //Recuperar dados da intent
        Intent intent = getIntent();
        String sigla = intent.getStringExtra(Constantes.KEY_SIGLA);

        //Recuperar fonte de dados
        mDisciplinaDao = new DisciplinaDao(this);
        mAlunoDao = new AlunoDao(this);
        mDisciplina = mDisciplinaDao.recupera(sigla);
        alunoList = mAlunoDao.recuperaTodosAlunosNaoMatriculados(mDisciplina);

        //Configurar RecylerView e adapter
        matriculaAdapter = new MatriculaAdapter(alunoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        naoMatriculadosRecyclerView.setLayoutManager(layoutManager);
        naoMatriculadosRecyclerView.setAdapter(matriculaAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        matriculaAdapter.setClickListener(new MatriculaAlunoClickListener() {
            @Override
            public void onAlunoClick(int position) {
                matricular(position);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            setResult(RESULT_OK);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void matricular(int i){
        mAluno = alunoList.get(i);
        mAluno.addDisciplina(mDisciplina);
        mDisciplina.addAluno(mAluno);

        mDisciplinaDao.matricular(mDisciplina, mAluno);

        alunoList.remove(i);
        naoMatriculadosRecyclerView.getAdapter().notifyDataSetChanged();
    }
}