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
import br.edu.ednilsonrossi.meusalunos.dao.AlunoDao;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REQUESTCODE_NOVO_ALUNO = 99;

    private RecyclerView mRecyclerView;
    private ImageView mImageView;
    private FloatingActionButton adicionarButton;

    private List<Aluno> mAlunoList;
    private AlunoDao mAlunoDao;

    private ItemAlunoAdapter mItemAlunoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperar referências do layout
        mRecyclerView = findViewById(R.id.recylerview_alunos);
        mImageView = findViewById(R.id.image_vazia);
        adicionarButton = findViewById(R.id.fab_adicionar);
        adicionarButton.setOnClickListener(this);

        //Configurar fonte de dados
        mAlunoDao = new AlunoDao(this);
        mAlunoList = mAlunoDao.recuperaTodos();

        //Configurar RecyclerView
        mItemAlunoAdapter = new ItemAlunoAdapter(mAlunoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mItemAlunoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_adicionar:
                Intent in = new Intent(this, NovoAlunoActivity.class);
                startActivityForResult(in, REQUESTCODE_NOVO_ALUNO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUESTCODE_NOVO_ALUNO:
                if(resultCode == RESULT_OK){
                    updateDataSet();
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(){
        if(mAlunoList.size() == 0){
            mImageView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else{
            mImageView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDataSet(){
        mAlunoList.clear();
        mAlunoList.addAll(mAlunoDao.recuperaTodos());
    }
}