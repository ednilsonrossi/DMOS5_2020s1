package br.pro.ednilsonrossi.login_hardcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class BemVindoActivity extends AppCompatActivity {

    private TextView mensagemTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);

        //Habilita o botão de navegação
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mensagemTextView = findViewById(R.id.text_mensagem);

        Bundle parametros = getIntent().getExtras();
        String usuario = parametros.getString(LoginActivity.USUARIO);
        String senha = parametros.getString(LoginActivity.SENHA);

        if(usuario.equals("112744") && senha.equals("447211")){
            mensagemTextView.setText(getString(R.string.bem_vindo) + " " + usuario);
        }else{
            mensagemTextView.setText(R.string.erro_login);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
