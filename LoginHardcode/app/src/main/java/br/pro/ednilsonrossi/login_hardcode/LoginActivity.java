package br.pro.ednilsonrossi.login_hardcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usuarioEditText;
    private EditText senhaEditText;
    private Button loginButton;
    public static final String USUARIO = "usuario";
    public static final String SENHA = "senha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioEditText = findViewById(R.id.edittext_usuario);
        senhaEditText = findViewById(R.id.edittext_senha);
        loginButton = findViewById(R.id.button_login);

        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == loginButton){
            if(usuarioEditText.getText().toString().isEmpty() || senhaEditText.getText().toString().isEmpty()){
                Toast.makeText(this, R.string.msg_preencher_dados, Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(this, BemVindoActivity.class);
                Bundle parametros = new Bundle();
                parametros.putString(USUARIO, usuarioEditText.getText().toString());
                parametros.putString(SENHA, senhaEditText.getText().toString());
                intent.putExtras(parametros);
                startActivity(intent);
            }
        }
    }
}
