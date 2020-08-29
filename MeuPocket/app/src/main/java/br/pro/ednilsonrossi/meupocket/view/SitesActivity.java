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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.dao.SiteDao;
import br.pro.ednilsonrossi.meupocket.exceptions.EmptyDatabaseException;
import br.pro.ednilsonrossi.meupocket.exceptions.InsertException;
import br.pro.ednilsonrossi.meupocket.exceptions.RecuperateException;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class SitesActivity extends AppCompatActivity{

    private static final int REQUEST_NOVO_SITE = 32;


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
            //siteList = new ArrayList<>();
            Log.e(getString(R.string.tag), "Erro ao recuperar lista de dados.");
        }catch (EmptyDatabaseException ex){
            //siteList = new ArrayList<>();
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
                Intent intent = new Intent(this, NovoSiteActivity.class);
                startActivityForResult(intent, REQUEST_NOVO_SITE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NOVO_SITE){
            if(resultCode == Activity.RESULT_OK){
                String titulo = data.getStringExtra(getString(R.string.column_titulo));
                String endereco = data.getStringExtra(getString(R.string.column_url));
                Site site = new Site(titulo, endereco);
                siteList.add(site);
                siteAdapter.updateDataSet();
            }
        }
    }

    @Override
    protected void onDestroy() {
        try {
            SiteDao.saveAll(siteList, this);
        }catch (InsertException ex){
            Log.e(getString(R.string.tag), getString(R.string.erro_insercao_dados));
        }
        super.onDestroy();
    }
}
