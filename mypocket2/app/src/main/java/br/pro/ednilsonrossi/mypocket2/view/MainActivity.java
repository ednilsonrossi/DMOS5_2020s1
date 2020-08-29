package br.pro.ednilsonrossi.mypocket2.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import br.pro.ednilsonrossi.mypocket2.R;
import br.pro.ednilsonrossi.mypocket2.constantes.Constantes;
import br.pro.ednilsonrossi.mypocket2.dao.SiteDao;
import br.pro.ednilsonrossi.mypocket2.model.Site;

public class MainActivity extends AppCompatActivity {

    private static final int REQUESTCODE_NOVO_SITE = 32;

    private RecyclerView mRecyclerView;
    private ImageView mImageView;

    private List<Site> mSiteList;
    private SiteDao siteDao;
    private ItemSiteAdapter mItemSiteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupera referências do layout
        mRecyclerView = findViewById(R.id.recycler_lista_sites);
        mImageView = findViewById(R.id.imageview_lista_vazia);

        //Carrega lista de dados
        siteDao = new SiteDao(this);
        mSiteList = siteDao.buscaTodos();

        //Configurar o recyclerview
        mItemSiteAdapter = new ItemSiteAdapter(mSiteList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mItemSiteAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInterface();
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
                startActivityForResult(intent, REQUESTCODE_NOVO_SITE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUESTCODE_NOVO_SITE){
            if(resultCode == Activity.RESULT_OK){
                String titulo = data.getStringExtra(Constantes.ATTR_TITULO);
                String endereco = data.getStringExtra(Constantes.ATTR_ENDERECO);
                Site site = new Site(titulo, endereco);

                //Salva o novo site no Banco de Dados
                siteDao.adiciona(site);

                mSiteList.add(site);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    private void updateUserInterface(){
        if(mSiteList.size() == 0){
            mImageView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else{
            mImageView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
