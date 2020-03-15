package br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.R;
import br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.data.AlunoDAO;
import br.edu.ifsp.arq.dmos5_2020s1.cadastraalunos.model.Aluno;

public class AdicionarAlunoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText prontuarioEditText;
    private EditText nomeEditText;
    private Button salvarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_aluno);

        prontuarioEditText = findViewById(R.id.edittext_prontuario);
        nomeEditText = findViewById(R.id.edittext_nome);
        salvarButton = findViewById(R.id.button_salvar);

        salvarButton.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onClick(View v) {
        if(v == salvarButton){
            salvaAluno();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            setResult(RESULT_CANCELED);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvaAluno(){
        String nome;
        int prontuario;

        nome = nomeEditText.getText().toString();
        try{
            prontuario = Integer.valueOf(prontuarioEditText.getText().toString());
            if(prontuario < 0){
                prontuario *= -1;
            }
        }catch (NumberFormatException ex){
            prontuario = -1;
        }

        if(nome.isEmpty() || prontuario < 0){
            Toast.makeText(this, "Dados inválidos.", Toast.LENGTH_SHORT).show();
        }else{
            AlunoDAO.getInstante().add(new Aluno(prontuario, nome));
            Toast.makeText(this, "Dados cadastrados.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
    }
}
