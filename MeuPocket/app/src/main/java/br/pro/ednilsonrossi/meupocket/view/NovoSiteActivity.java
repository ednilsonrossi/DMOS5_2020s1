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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tituloEditText = findViewById(R.id.edittext_titulo);
        enderecoEditText = findViewById(R.id.edittext_endereco);
        salvarButton = findViewById(R.id.button_salvar);

        salvarButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == salvarButton){
            if(!tituloEditText.getText().toString().isEmpty() && !enderecoEditText.getText().toString().isEmpty()){

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
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
