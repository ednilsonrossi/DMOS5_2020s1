package br.edu.ednilsonrossi.meusalunos.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import br.edu.ednilsonrossi.meusalunos.R;
import br.edu.ednilsonrossi.meusalunos.dao.DisciplinaDao;
import br.edu.ednilsonrossi.meusalunos.exceptions.ChavePrimariaDuplicadaException;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;
import br.edu.ednilsonrossi.meusalunos.model.Disciplina;

public class NovaDisciplinaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText siglaEditText;
    private EditText disciplinaEditText;
    private Button salvarButton;

    private DisciplinaDao mDisciplinaDao;
    private Disciplina mDisciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_disciplina);

        //Recuperar referências do layout
        siglaEditText = findViewById(R.id.edittext_sigla);
        disciplinaEditText = findViewById(R.id.edittext_disciplina);
        salvarButton = findViewById(R.id.button_save_disciplina);
        salvarButton.setOnClickListener(this);

        mDisciplinaDao = new DisciplinaDao(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_save_disciplina:
                salvarDisciplina();
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

    private void salvarDisciplina(){
        String sigla, disciplina;
        sigla = siglaEditText.getText().toString();
        disciplina = disciplinaEditText.getText().toString();

        if(sigla.isEmpty()){
            showSnackbar(getString(R.string.erro_empty_sigla));
        }else{
            try{
                mDisciplinaDao.adicionar(new Disciplina(sigla, disciplina));
                finalizar(true);
            }catch (ChavePrimariaDuplicadaException e){
                showSnackbar(getString(R.string.erro_duplicate_prontuario));
            }catch (NullPointerException e){
                showSnackbar(getString(R.string.erro_null_aluno));
            }
        }
    }

    private void showSnackbar(String mensagem){
        Snackbar snackbar;
        ConstraintLayout constraintLayout = findViewById(R.id.layout_nova_disciplina);
        snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void finalizar(boolean sucesso){
        if(sucesso){
            setResult(Activity.RESULT_OK);
        }else{
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }
}