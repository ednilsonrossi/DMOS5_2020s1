package br.edu.ednilsonrossi.buscaendereco.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ednilsonrossi.buscaendereco.R;
import br.edu.ednilsonrossi.buscaendereco.api.RetrofitService;
import br.edu.ednilsonrossi.buscaendereco.model.Endereco;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSION = 64;
    private static final String BASE_URL = "https://viacep.com.br/ws/";

    private Retrofit mRetrofit;

    private EditText cepEditText;
    private Button buscarButton;
    private ConstraintLayout dadosConstraintLayout;
    private TextView cepTextView;
    private TextView logradouroTextView;
    private TextView complementoTextView;
    private TextView bairroTextView;
    private TextView localidadeTextView;
    private TextView ufTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLayoutElements();


    }

    private void getLayoutElements(){
        cepEditText = findViewById(R.id.edittext_cep);
        dadosConstraintLayout = findViewById(R.id.constraint_dados);
        cepTextView = findViewById(R.id.textview_cep);
        logradouroTextView = findViewById(R.id.textview_logradouro);
        complementoTextView = findViewById(R.id.textview_complemento);
        bairroTextView = findViewById(R.id.textview_bairro);
        localidadeTextView = findViewById(R.id.textview_localidade);
        ufTextView = findViewById(R.id.textview_uf);
        buscarButton = findViewById(R.id.button_buscar);
        buscarButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_buscar:
                if(temPermissao()){
                    buscarEndereco();
                }else{
                    solicitaPermissao();
                }
        }
    }

    private void buscarEndereco(){
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        String cepString = cepEditText.getText().toString();
        if(cepString.length() != 8){
            Toast.makeText(this, getString(R.string.cep_invalido), Toast.LENGTH_SHORT).show();
        }else{
            cepString += "/json/";

            RetrofitService mRetrofitService = mRetrofit.create(RetrofitService.class);

            Call<Endereco> call = mRetrofitService.getDados(cepString);

            call.enqueue(new Callback<Endereco>() {
                @Override
                public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                    if(response.isSuccessful()){
                        Endereco endereco = response.body();
                        updateUI(endereco);
                    }
                }

                @Override
                public void onFailure(Call<Endereco> call, Throwable t) {
                    Toast.makeText(MainActivity.this, getString(R.string.erro_api), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean temPermissao(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitaPermissao(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            final Activity activity = this;
            new AlertDialog.Builder(this)
                    .setMessage(R.string.explicacao_permissao)
                    .setPositiveButton(R.string.botao_fornecer, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, REQUEST_PERMISSION);
                        }
                    })
                    .setNegativeButton(R.string.botao_nao_fornecer, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.INTERNET
                    },
                    REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {

                if (permissions[i].equalsIgnoreCase(Manifest.permission.INTERNET) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    buscarEndereco();
                }

            }
        }
    }

    private void updateUI(Endereco endereco){
        if(endereco != null){
            dadosConstraintLayout.setVisibility(View.VISIBLE);
            cepTextView.setText(endereco.getCep());
            logradouroTextView.setText(endereco.getLogradouro());
            ufTextView.setText(endereco.getUf());
            bairroTextView.setText(endereco.getBairro());
            complementoTextView.setText(endereco.getComplemento());
            localidadeTextView.setText(endereco.getLocalidade());
        }
    }
}