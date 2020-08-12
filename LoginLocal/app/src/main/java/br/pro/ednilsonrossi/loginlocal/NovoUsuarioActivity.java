package br.pro.ednilsonrossi.loginlocal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NovoUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText novoUsuarioEditText;
    private EditText novaSenhaEditText;
    private EditText confirmaSenhaEditText;
    private Button salvarButton;


    private User mUser;
    private List<User> userList = null;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        novoUsuarioEditText = findViewById(R.id.edittext_novo_usuario);
        novaSenhaEditText = findViewById(R.id.edittext_nova_senha);
        confirmaSenhaEditText = findViewById(R.id.edittext_confirme_senha);
        salvarButton = findViewById(R.id.button_salvar);

        salvarButton.setOnClickListener(this);

        mSharedPreferences = this.getSharedPreferences(getString(R.string.file_usuario), MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        recuperaUsuarios();
    }


    @Override
    public void onClick(View view) {
        if(view == salvarButton){
            String usuario;
            String senha;
            String confirma;
            usuario = novoUsuarioEditText.getText().toString();
            senha = novaSenhaEditText.getText().toString();
            confirma = confirmaSenhaEditText.getText().toString();
            if(!senha.equals(confirma)){
                Toast.makeText(this, getString(R.string.erro_confirma_senha), Toast.LENGTH_SHORT).show();
                novaSenhaEditText.setText("");
                confirmaSenhaEditText.setText("");
                return;
            }
            mUser = new User(usuario, senha);
            adicionarBD();
            finish();
        }
    }

    private void recuperaUsuarios(){
        String users = mSharedPreferences.getString(getString(R.string.key_usuarios_db), "");
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray;
        userList = new ArrayList<>();
        try{
            jsonArray = new JSONArray(users);
            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                mUser = new User(jsonObject.getString("username"), jsonObject.getString("password"));
                userList.add(mUser);
            }
        } catch (JSONException ex){
            userList = null;
        }
        if (userList != null) {
            for (User u : userList) {
                Log.i(getString(R.string.tag), u.toString());
            }
        }
    }

    private void adicionarBD(){
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();
        if(userList == null){
            userList = new ArrayList<>(1);
        }
        userList.add(mUser);
        for(User u : userList){
            jsonObject = new JSONObject();
            try {
                jsonObject.put("username", u.getUsername());
                jsonObject.put("password", u.getPassword());
                jsonArray.put(jsonObject);
            }catch (JSONException e){
                Log.e(getString(R.string.tag), getString(R.string.erro_recupera_lista));
            }
        }
        String users = jsonArray.toString();
        mEditor.putString(getString(R.string.key_usuarios_db), users);
        mEditor.commit();
    }
}
