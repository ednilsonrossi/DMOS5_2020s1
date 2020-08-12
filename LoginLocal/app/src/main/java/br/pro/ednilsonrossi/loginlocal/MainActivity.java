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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "LOGIN_LOCAL";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText usuarioEditText;
    private EditText senhaEditText;
    private Button logarButton;
    private CheckBox lembrarCheckBox;

    private String usuario, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioEditText = findViewById(R.id.edittext_usuario);
        senhaEditText = findViewById(R.id.edittext_senha);
        logarButton = findViewById(R.id.button_logar);
        lembrarCheckBox = findViewById(R.id.checkbox_lembrar);

        logarButton.setOnClickListener(this);

        mSharedPreferences = this.getPreferences(MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }



    @Override
    protected void onStart() {
        Log.i(TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onStart()");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onResume()");
        verificarPreferencias();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Classe: " + getClass().getSimpleName() +  "| Método : onDestroy()");
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

            salvaPreferencias();
            abrirBoasVindas();


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

        if(lembrarCheckBox.isChecked()){
            Log.i(TAG, "salvando");
            mEditor.putString(getString(R.string.key_usuario), usuario);
            mEditor.commit();

            mEditor.putString(getString(R.string.key_senha), senha);
            mEditor.commit();

            mEditor.putBoolean(getString(R.string.key_lembrar), true);
            mEditor.commit();
        }else{
            Log.i(TAG, "não salvando");
            mEditor.putString(getString(R.string.key_usuario), "");
            mEditor.commit();

            mEditor.putString(getString(R.string.key_senha), "");
            mEditor.commit();

            mEditor.putBoolean(getString(R.string.key_lembrar), false);
            mEditor.commit();
        }

    }

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
