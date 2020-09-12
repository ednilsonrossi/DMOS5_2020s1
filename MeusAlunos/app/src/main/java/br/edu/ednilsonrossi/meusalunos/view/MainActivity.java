package br.edu.ednilsonrossi.meusalunos.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.ednilsonrossi.meusalunos.R;
import br.edu.ednilsonrossi.meusalunos.constantes.Constantes;
import br.edu.ednilsonrossi.meusalunos.dao.AlunoDao;
import br.edu.ednilsonrossi.meusalunos.dao.DisciplinaDao;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;
import br.edu.ednilsonrossi.meusalunos.model.Disciplina;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REQUESTCODE_NOVO_ALUNO = 99;
    public static final int REQUESTCODE_NOVA_DISCIPLINA = 98;
    boolean verAlunos = true;

    private RecyclerView alunosRecyclerView;
    private RecyclerView disciplinasRecyclerView;
    private ImageView mImageView;
    private FloatingActionButton adicionarAlunoButton;
    private FloatingActionButton alternarButton;
    private FloatingActionButton adicionarDisciplinaButton;


    private List<Aluno> mAlunoList;
    private AlunoDao mAlunoDao;
    private List<Disciplina> mDisciplinaList;
    private DisciplinaDao mDisciplinaDao;

    private AlunoAdapter mAlunoAdapter;
    private DisciplinaAdapter mDisciplinaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperar referências do layout
        alunosRecyclerView = findViewById(R.id.recylerview_alunos);
        disciplinasRecyclerView = findViewById(R.id.recylerview_disciplinas);
        mImageView = findViewById(R.id.image_vazia);
        adicionarAlunoButton = findViewById(R.id.fab_adicionar_aluno);
        adicionarAlunoButton.setOnClickListener(this);
        alternarButton = findViewById(R.id.fab_alternar);
        alternarButton.setOnClickListener(this);
        adicionarDisciplinaButton = findViewById(R.id.fab_adicionar_disciplina);
        adicionarDisciplinaButton.setOnClickListener(this);

        //Configurar fonte de dados
        mAlunoDao = new AlunoDao(this);
        mAlunoList = mAlunoDao.recuperaTodos();
        mDisciplinaDao = new DisciplinaDao(this);
        mDisciplinaList = mDisciplinaDao.recuperaTodos();


        //Configurar RecyclerView
        mAlunoAdapter = new AlunoAdapter(mAlunoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        alunosRecyclerView.setLayoutManager(layoutManager);
        alunosRecyclerView.setAdapter(mAlunoAdapter);

        mDisciplinaAdapter = new DisciplinaAdapter(mDisciplinaList);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        disciplinasRecyclerView.setLayoutManager(layoutManager2);
        disciplinasRecyclerView.setAdapter(mDisciplinaAdapter);

        mDisciplinaAdapter.setClickListener(new DisciplinaClickListener() {
            @Override
            public void onDisciplinaClick(int position) {
                Intent intent = new Intent(getApplicationContext(), DetalhesDisciplinaActivity.class);
                intent.putExtra(Constantes.KEY_SIGLA, mDisciplinaList.get(position).getSigla());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_adicionar_aluno:
                Intent in = new Intent(this, NovoAlunoActivity.class);
                startActivityForResult(in, REQUESTCODE_NOVO_ALUNO);
                break;

            case R.id.fab_alternar:
                alternar();
                break;

            case R.id.fab_adicionar_disciplina:
                Intent intent = new Intent(this, NovaDisciplinaActivity.class);
                startActivityForResult(intent, REQUESTCODE_NOVA_DISCIPLINA);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUESTCODE_NOVO_ALUNO:
                if(resultCode == RESULT_OK){
                    updateAlunoDataSet();
                    alunosRecyclerView.getAdapter().notifyDataSetChanged();
                }
                break;

            case REQUESTCODE_NOVA_DISCIPLINA:
                if(resultCode == RESULT_OK){
                    updateDisciplinaDataSet();
                    disciplinasRecyclerView.getAdapter().notifyDataSetChanged();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(){
        if(verAlunos) {
            if (mAlunoList.size() == 0) {
                mImageView.setVisibility(View.VISIBLE);
                alunosRecyclerView.setVisibility(View.GONE);
            } else {
                mImageView.setVisibility(View.GONE);
                alunosRecyclerView.setVisibility(View.VISIBLE);
            }
        }else {
            if (mDisciplinaList.size() == 0) {
                mImageView.setVisibility(View.VISIBLE);
                disciplinasRecyclerView.setVisibility(View.GONE);
            } else {
                mImageView.setVisibility(View.GONE);
                disciplinasRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void alternar(){
        if(verAlunos){
            disciplinasRecyclerView.setVisibility(View.VISIBLE);
            alunosRecyclerView.setVisibility(View.GONE);
            alternarButton.setImageDrawable(getDrawable(R.drawable.ic_face));
            adicionarDisciplinaButton.show();
            adicionarAlunoButton.hide();
        }else{
            disciplinasRecyclerView.setVisibility(View.GONE);
            alunosRecyclerView.setVisibility(View.VISIBLE);
            alternarButton.setImageDrawable(getDrawable(R.drawable.ic_discipline));
            adicionarDisciplinaButton.hide();
            adicionarAlunoButton.show();
        }
        verAlunos = !verAlunos;
        updateUI();
    }

    private void updateAlunoDataSet(){
        mAlunoList.clear();
        mAlunoList.addAll(mAlunoDao.recuperaTodos());
    }

    private void updateDisciplinaDataSet(){
        mDisciplinaList.clear();
        mDisciplinaList.addAll(mDisciplinaDao.recuperaTodos());
    }
}