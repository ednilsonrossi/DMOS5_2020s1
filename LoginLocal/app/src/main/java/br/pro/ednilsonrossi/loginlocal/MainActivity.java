package br.pro.ednilsonrossi.loginlocal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Objetos utilizados para armazenar e recuperar os dados
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText usuarioEditText;
    private EditText senhaEditText;
    private Button logarButton;
    private CheckBox lembrarCheckBox;
    private TextView novoUsuarioTextView;

    private String usuario, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() +  "| Método : onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioEditText = findViewById(R.id.edittext_usuario);
        senhaEditText = findViewById(R.id.edittext_senha);
        logarButton = findViewById(R.id.button_logar);
        lembrarCheckBox = findViewById(R.id.checkbox_lembrar);
        novoUsuarioTextView = findViewById(R.id.textview_novo);

        logarButton.setOnClickListener(this);
        novoUsuarioTextView.setOnClickListener(this);

        //Vamos instanciar as preferencias em modo privado, ou seja, somente acessíveis
        //ao próprio app.
        mSharedPreferences = this.getPreferences(MODE_PRIVATE);
        //mSharedPreferences = this.getSharedPreferences(getString(R.string.file_preferences), MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }



    @Override
    protected void onStart() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() +  "| Método : onStart()");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() +  "| Método : onRestart()");
        super.onRestart();
    }

    /*
    No onResumo() é um bom momento para verificar se o usuário possui dados armazenados ou não,
    lembre-se que o onCreate() só é executado uma vez.
     */
    @Override
    protected void onResume() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() +  "| Método : onResume()");

        //Vamos verificar se o usuário possui preferências
        verificarPreferencias();

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() +  "| Método : onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() +  "| Método : onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(getString(R.string.tag), "Classe: " + getClass().getSimpleName() +  "| Método : onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if(view == logarButton){

            usuario = usuarioEditText.getText().toString();
            senha = senhaEditText.getText().toString();
            if(usuario.isEmpty() || senha.isEmpty()){
                Toast.makeText(this, R.string.erro_entrada_msg, Toast.LENGTH_SHORT).show();
                return;
            }

            //Antes de abrir a outra tela se verifica se o usuário deseja armazenar
            //os dados de login para outros acessos.
            salvaPreferencias();
            abrirBoasVindas();

            return;
        }

        if(view == novoUsuarioTextView){
            Intent in = new Intent(this, NovoUsuarioActivity.class);
            startActivity(in);
            return;
        }

    }

    private void abrirBoasVindas(){
        Intent in = new Intent(this, BemVindoActivity.class);
        Bundle args = new Bundle();
        args.putString(getString(R.string.key_usuario), usuario);
        args.putString(getString(R.string.key_senha), senha);
        in.putExtras(args);
        startActivity(in);
    }

    private void salvaPreferencias(){

        //Caso o checkbox esteja marcado, armazenamos os dados no objeto,
        //caso contrário vamos apenas armazenar um vazio.
        if(lembrarCheckBox.isChecked()){
            mEditor.putString(getString(R.string.key_usuario), usuario);
            mEditor.commit();

            mEditor.putString(getString(R.string.key_senha), senha);
            mEditor.commit();

            mEditor.putBoolean(getString(R.string.key_lembrar), true);
            mEditor.commit();
        }else{
            mEditor.putString(getString(R.string.key_usuario), "");
            mEditor.commit();

            mEditor.putString(getString(R.string.key_senha), "");
            mEditor.commit();

            mEditor.putBoolean(getString(R.string.key_lembrar), false);
            mEditor.commit();
        }

    }

    /*
    Aqui recuperamos as preferências do usuário, e caso existam (boolean lembrar) atualizamos
    os dados na tela da activity.
     */
    private void verificarPreferencias() {
        usuario = mSharedPreferences.getString(getString(R.string.key_usuario), "");
        senha = mSharedPreferences.getString(getString(R.string.key_senha), "");
        boolean lembrar = mSharedPreferences.getBoolean(getString(R.string.key_lembrar), false);

        if(lembrar){
            usuarioEditText.setText(usuario);
            senhaEditText.setText(senha);
            lembrarCheckBox.setChecked(true);
        }
    }
}
