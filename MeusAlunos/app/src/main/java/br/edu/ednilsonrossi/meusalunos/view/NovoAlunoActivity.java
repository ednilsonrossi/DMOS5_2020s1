package br.edu.ednilsonrossi.meusalunos.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import br.edu.ednilsonrossi.meusalunos.R;
import br.edu.ednilsonrossi.meusalunos.dao.AlunoDao;
import br.edu.ednilsonrossi.meusalunos.exceptions.ChavePrimariaDuplicadaException;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;

public class NovoAlunoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText prontuarioEditText;
    private EditText nomeEditText;
    private EditText emailEditText;
    private Button salvarButton;

    private AlunoDao mAlunoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_aluno);

        mAlunoDao = new AlunoDao(this);

        //Recuperar referências do layout
        prontuarioEditText = findViewById(R.id.edittext_prontuario);
        nomeEditText = findViewById(R.id.edittext_aluno);
        emailEditText = findViewById(R.id.edittext_email);
        salvarButton = findViewById(R.id.button_save);
        salvarButton.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_save:
                salvarAluno();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finalizar(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarAluno(){
        String prontuario, nome, email;
        prontuario = prontuarioEditText.getText().toString();
        nome = nomeEditText.getText().toString();
        email = emailEditText.getText().toString();

        if(prontuario.isEmpty() || nome.isEmpty() || email.isEmpty()){
            showSnackbar(getString(R.string.erro_empty_fields));
        }else{
            mAlunoDao = new AlunoDao(this);
            try{
                mAlunoDao.adicionar(new Aluno(prontuario, nome, email));
                finalizar(true);
            }catch (ChavePrimariaDuplicadaException e){
                showSnackbar(getString(R.string.erro_duplicate_prontuario));
            }catch (NullPointerException e){
                showSnackbar(getString(R.string.erro_null_aluno));
            }
        }
    }

    private void finalizar(boolean sucesso){
        if(sucesso){
            setResult(Activity.RESULT_OK);
        }else{
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }

    private void showSnackbar(String mensagem){
        Snackbar snackbar;
        ConstraintLayout constraintLayout = findViewById(R.id.layout_novo_aluno);
        snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}