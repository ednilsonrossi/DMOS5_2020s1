package br.edu.ednilsonrossi.meusalunos.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.ednilsonrossi.meusalunos.R;
import br.edu.ednilsonrossi.meusalunos.constantes.Constantes;
import br.edu.ednilsonrossi.meusalunos.dao.DisciplinaDao;
import br.edu.ednilsonrossi.meusalunos.model.Disciplina;

public class DetalhesDisciplinaActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUESTCODE_MATRICULA = 97;

    private TextView siglaTextView;
    private TextView nomeTextView;
    private RecyclerView alunosRecyclerView;
    private FloatingActionButton matricularButton;

    private AlunoAdapter alunoAdapter;

    private Disciplina mDisciplina;
    private DisciplinaDao mDisciplinaDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_disciplina);

        //Recuperar referências
        siglaTextView = findViewById(R.id.textview_detalhe_sigla);
        nomeTextView = findViewById(R.id.textview_detalhe_disciplina);
        alunosRecyclerView = findViewById(R.id.recyclerview_alunos_matriculados);
        matricularButton = findViewById(R.id.fab_matricular_aluno);
        matricularButton.setOnClickListener(this);

        //Recuperar dados do intent
        Intent intent = getIntent();
        String sigla = intent.getStringExtra(Constantes.KEY_SIGLA);

        //Recuperar dados do BD
        mDisciplinaDao = new DisciplinaDao(this);
        mDisciplina = mDisciplinaDao.recupera(sigla);

        //Configurar RecyclerView
        alunoAdapter = new AlunoAdapter(mDisciplina.getAlunoList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        alunosRecyclerView.setLayoutManager(layoutManager);
        alunosRecyclerView.setAdapter(alunoAdapter);

        //Configura apresentacao de dados
        siglaTextView.setText(mDisciplina.getSigla());
        nomeTextView.setText(mDisciplina.getDisciplina());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_matricular_aluno:
                Intent intent = new Intent(this, MatricularAlunoActivity.class);
                intent.putExtra(Constantes.KEY_SIGLA, mDisciplina.getSigla());
                startActivityForResult(intent, REQUESTCODE_MATRICULA);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_MATRICULA){
            if(resultCode == RESULT_OK){
                mDisciplina = mDisciplinaDao.recupera(mDisciplina.getSigla());
                alunoAdapter = new AlunoAdapter(mDisciplina.getAlunoList());
                alunosRecyclerView.setAdapter(alunoAdapter);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}