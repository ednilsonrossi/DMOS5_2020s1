package br.pro.ednilsonrossi.loginlocal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class BemVindoActivity extends AppCompatActivity {

    private TextView mensagemTextView;
    private int usuario;
    private int senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(MainActivity.TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);

        mensagemTextView = findViewById(R.id.textview_mensagem);

        //Apresenta navigation up
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        usuario = intent.getIntExtra(MainActivity.USUARIO, -1);
        senha = intent.getIntExtra(MainActivity.SENHA, -1);
        validarUsuario();
    }

    @Override
    protected void onStart() {
        Log.i(MainActivity.TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onStart()");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(MainActivity.TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(MainActivity.TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(MainActivity.TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(MainActivity.TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(MainActivity.TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onDestroy()");
        super.onDestroy();
    }


    private void validarUsuario(){
        if(usuario == 112744 && senha == 447211){
            mensagemTextView.setText("Bem vindo usuário");
        }else{
            mensagemTextView.setText("Erro no login");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
