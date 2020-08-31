package br.pro.ednilsonrossi.meupocket.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.dao.SiteDao;
import br.pro.ednilsonrossi.meupocket.exceptions.EmptyDatabaseException;
import br.pro.ednilsonrossi.meupocket.exceptions.InsertException;
import br.pro.ednilsonrossi.meupocket.exceptions.RecuperateException;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class SitesActivity extends AppCompatActivity{

    private static final int REQUESTCODE_NOVO_SITE = 32;


    //Referência para o elemento de RecyclerView
    private RecyclerView sitesRecyclerView;
    private ImageView listaVaziaTextView;

    //Fonte de dados, essa lista possue os dados que são apresentados
    //na tela dos dispositivo.
    private List<Site> siteList;


    private ItemSiteAdapter siteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);

        //Recupera a referência do elemento no layout
        sitesRecyclerView = findViewById(R.id.recycler_lista_sites);
        listaVaziaTextView = findViewById(R.id.textview_lista_vazia);

        //Ao contrário do ListView um RecyclerView necessita de um LayoutManager (gerenciador de
        // layout) para gerenciar o posicionamento de seus itens. Utilizarei um LinearLayoutManager
        // que fará com que nosso RecyclerView se pareça com um ListView.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        sitesRecyclerView.setLayoutManager(layoutManager);

        //Carrega a fonte de dados
        try {
            siteList = SiteDao.recuperateAll(this);
        }catch (RecuperateException e){
            siteList = new ArrayList<>();
            Log.e(getString(R.string.tag), "Erro ao recuperar lista de dados.");
        }catch (EmptyDatabaseException ex){
            siteList = new ArrayList<>();
            Log.e(getString(R.string.tag), "String vazia no sharedPreferences");
        }
        siteAdapter = new ItemSiteAdapter(siteList);

        sitesRecyclerView.setAdapter(siteAdapter);
        siteAdapter.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String url = corrigeEndereco(siteList.get(position).getEndereco());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        configuraApresentacao();

    }


    private void configuraApresentacao(){
        if(siteList.size() == 0){
            listaVaziaTextView.setVisibility(View.VISIBLE);
            sitesRecyclerView.setVisibility(View.GONE);
        }else{
            listaVaziaTextView.setVisibility(View.GONE);
            sitesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private String corrigeEndereco(String endereco) {
        String url = endereco.trim().replace(" ","");
        if (!url.startsWith("http://")) {
            return "http://" + url;
        }
        return url;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_mypocket, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_adicionar:
                /*
                Quando solicitada uma nova Activity para inserir um site iremos chamar
                a activity de uma forma diferente, agora vamos utilizar o método
                startActivityForResult(), isso porque a activity chamada irá produzir um resultado
                para a SitesActivity, no caso, um novo site para ser cadastrado.

                Os parâmetros do startActivityForResult() são a intent, da mesma forma que o
                startActivity() e também o código da requisição. Esse código será necessário
                no momento de tratar o resultado esperado. Observo que o código de requisição
                é um valor inteiro que você (programador) deve controlar, no exemplo, foi criada
                uma constante nesta activity.
                 */
                Intent intent = new Intent(this, NovoSiteActivity.class);
                startActivityForResult(intent, REQUESTCODE_NOVO_SITE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    O método que trata o resultado de activity recebe três argumentos:
        - requestCode: é o mesmo código de requisição informado na chamada do startActivityForResult(),
            aqui é o momento de verificar qual o requisição é que preduziu resultado.
        - resultCode: é o resultado da requisição, isso pode ser um RESULT_OK ou um RESULT_CANCELED, conforme
            esse exemplo. No caso, só nos interessa o RESULT_OK pois significa que existe um novo site para
            ser inserido na lista.
        - data: Essa é a Intent que possui os dados de resultado. Lembro que esses dados são gerados na
            outra activity e devolvidos pelo Bundle da Intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUESTCODE_NOVO_SITE){
            if(resultCode == Activity.RESULT_OK){
                /*
                O RESULT_OK indica que temos um novo site, assim, vamos recuperar os dados do Bundle e
                criar um novo objeto do tipo Site. Esse objeto é inserido na lista.
                Depois de inserir o objeto na lista devemos informar o adapter que houve atualização
                na lista, para isso realizamos a chamada no método updateDataSet() que foi implementado
                no adapter.
                 */
                String titulo = data.getStringExtra(getString(R.string.column_titulo));
                String endereco = data.getStringExtra(getString(R.string.column_url));
                Site site = new Site(titulo, endereco);
                siteList.add(site);
                sitesRecyclerView.getAdapter().notifyDataSetChanged();
                configuraApresentacao();
            }
        }
    }

    @Override
    protected void onDestroy() {
        /*
        Como a lista de sites está em memória só precisamos salvar os dados quando a activity for destruída,
        assim, aproveitando o ciclo de vida a activity podemos salvar todos os sites no SharedPreferences
        nesse momento.
         */
        try {
            SiteDao.saveAll(siteList, this);
        }catch (InsertException ex){
            Log.e(getString(R.string.tag), getString(R.string.erro_insercao_dados));
        }
        super.onDestroy();
    }
}
