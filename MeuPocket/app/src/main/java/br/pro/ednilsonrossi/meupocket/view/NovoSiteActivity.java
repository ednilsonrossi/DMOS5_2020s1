package br.pro.ednilsonrossi.meupocket.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.dao.SiteDao;
import br.pro.ednilsonrossi.meupocket.exceptions.InsertException;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class NovoSiteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText tituloEditText;
    private EditText enderecoEditText;
    private Button salvarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_site);

        //Habilita o botão voltar na activity.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tituloEditText = findViewById(R.id.edittext_titulo);
        enderecoEditText = findViewById(R.id.edittext_endereco);
        salvarButton = findViewById(R.id.button_salvar);

        salvarButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == salvarButton){
            //Ao clicar no botão verifica-se se os dois campos possuem dados, caso afirmativo é
            // é realizado o processamento.
            if(!tituloEditText.getText().toString().isEmpty() && !enderecoEditText.getText().toString().isEmpty()){

                // Salvar um site significa coletar os dados e devolver os valores para a SiteActivity.java,
                // que realizou a chamada da activity.
                //
                // Iremos criar uma nova Intent denominada result e vamos inserir os valores preenchidos
                // pelo usuário no Bundle (putExtra) dessa Intent, depois disso é definido qual o resultado
                // de NovoSiteActivity.java pelo método serResult().
                Intent result = new Intent();
                result.putExtra(getString(R.string.column_titulo), tituloEditText.getText().toString());
                result.putExtra(getString(R.string.column_url), enderecoEditText.getText().toString());
                setResult(Activity.RESULT_OK, result);

                Toast.makeText(this, R.string.sucesso, Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, getString(R.string.prencher_dados), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            /*
            Essa activity é chamada com a espera de um resultado, caso o usuário não cadastre um
            novo site o resultado será RESULT_CANCELED, ou seja, a activity não produziu nenhum
            resultado para ser devolvido para a activity que a chamou.
             */
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
