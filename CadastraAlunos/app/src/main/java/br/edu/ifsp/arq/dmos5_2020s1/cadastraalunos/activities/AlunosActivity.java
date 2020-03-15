package br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.R;
import br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.constants.Constantes;
import br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.data.AlunoDAO;
import br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.model.Aluno;

public class AlunosActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView alunosListView;
    private TextView semDadosTextView;
    private List<Aluno> alunoList;
    private ArrayAdapter<Aluno> arrayAdapter;
    private FloatingActionButton adicionarActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);

        alunosListView = findViewById(R.id.listview_alunos);
        semDadosTextView = findViewById(R.id.textview_sem_dados);
        adicionarActionButton = findViewById(R.id.fab_add_aluno);


        alunoList = AlunoDAO.getInstante().all();
        arrayAdapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunoList);


        alunosListView.setAdapter(arrayAdapter);
        adicionarActionButton.setOnClickListener(this);

        atualizaTela();
    }

    @Override
    public void onClick(View v) {
        if(v == adicionarActionButton){
            Intent novoAluno = new Intent(this, AdicionarAlunoActivity.class);
            startActivityForResult(novoAluno, Constantes.NEW_ALUNO_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constantes.NEW_ALUNO_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    alunoList = AlunoDAO.getInstante().all();
                    arrayAdapter.notifyDataSetChanged();
                }else{
                    if(resultCode == RESULT_CANCELED){
                        Toast.makeText(this, "Nenhum aluno adicionado.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
        atualizaTela();
    }

    private void atualizaTela(){
        if(alunoList.size() == 0){
            semDadosTextView.setVisibility(View.VISIBLE);
            alunosListView.setVisibility(View.GONE);
        }else{
            semDadosTextView.setVisibility(View.GONE);
            alunosListView.setVisibility(View.VISIBLE);
        }
    }
}
