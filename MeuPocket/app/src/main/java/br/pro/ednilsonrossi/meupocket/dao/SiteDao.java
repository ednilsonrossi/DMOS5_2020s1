package br.pro.ednilsonrossi.meupocket.dao;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.exceptions.EmptyDatabaseException;
import br.pro.ednilsonrossi.meupocket.exceptions.InsertException;
import br.pro.ednilsonrossi.meupocket.exceptions.RecuperateException;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class SiteDao {

    public static final List<Site> recuperateAll(Context context) throws RecuperateException, EmptyDatabaseException {
        SharedPreferences mSharedPreferences;
        ArrayList<Site> mArrayList = new ArrayList<>();
        JSONObject mJsonObject;
        JSONArray mJsonArray;
        String mJsonString;
        Site mSite;

        mSharedPreferences = context.getSharedPreferences(context.getString(R.string.data_file), Context.MODE_PRIVATE);

        mJsonString = mSharedPreferences.getString(context.getString(R.string.table_sites), "");

        if(!mJsonString.isEmpty()){
            try {
                mJsonArray = new JSONArray(mJsonString);
                for(int i=0; i < mJsonArray.length(); i++){
                    mJsonObject = mJsonArray.getJSONObject(i);
                    mSite = new Site(
                            mJsonObject.getString(context.getString(R.string.column_titulo)),
                            mJsonObject.getString(context.getString(R.string.column_url)));
                    if(mJsonObject.getBoolean(context.getString(R.string.column_favorito))){
                        mSite.doFavotite();
                    }else{
                        mSite.undoFavorite();
                    }
                    mArrayList.add(mSite);
                }
            }catch (JSONException e){
                throw new RecuperateException("Sem dados");
            }
        }else{
            throw new EmptyDatabaseException("Não existe dados na String.");
        }

        /*arrayList.add(new Site("Google", "www.google.com.br"));
        arrayList.add(new Site("Terra", "www.terra.com.br"));
        arrayList.add(new Site("IFSP", "www.ifsp.edu.br"));
        arrayList.add(new Site("IFSP Câmpus Araraquara", "arq.ifsp.edu.br"));
        arrayList.add(new Site("Developers", "https://developer.android.com"));
        arrayList.add(new Site("Vida de Programador", "https://vidadeprogramador.com.br/"));
        arrayList.add(new Site("Stackoverflow", "https://stackoverflow.com/"));
        arrayList.add(new Site("Youtube", "www.youtube.com.br"));
        arrayList.add(new Site("Gmail", "www.gmail.com"));
        arrayList.add(new Site("Facebook", "www.facebook.com"));
        arrayList.add(new Site("Whatsapp", "www.whatsapp.com"));
        arrayList.add(new Site("Get Pocket", "www.getpocket.com"));*/

        return mArrayList;
    }

    public static final void addSite(Site site, Context context) throws InsertException, NullPointerException {
        SharedPreferences mSharedPreferences;
        SharedPreferences.Editor mEditor;
        JSONObject mJsonObject;
        JSONArray mJsonArray;
        List<Site> mSiteList;

        if(site == null){
            throw new NullPointerException("Site não pode ser nulo.");
        }

        try{
            mSiteList = SiteDao.recuperateAll(context);
        }catch (RecuperateException ex){
            throw new InsertException("Erro ao recuperar dados antigos");
        }catch (EmptyDatabaseException ex){
            mSiteList = new ArrayList<>(1);
        }

        mSiteList.add(site);
        mSharedPreferences = context.getSharedPreferences(context.getString(R.string.data_file), Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mJsonArray = new JSONArray();
        for(Site s : mSiteList){
            mJsonObject = new JSONObject();
            try{
                mJsonObject.put(context.getString(R.string.column_titulo), s.getTitulo());
                mJsonObject.put(context.getString(R.string.column_url), s.getEndereco());
                mJsonArray.put(mJsonObject);
            }catch (JSONException e){
                throw new InsertException(e.getMessage());
            }
        }

        mEditor.putString(context.getString(R.string.table_sites), mJsonArray.toString());
        mEditor.commit();
    }

    public static final void saveAll(List<Site> list, Context context) throws InsertException{
        SharedPreferences mSharedPreferences;
        SharedPreferences.Editor mEditor;
        JSONObject mJsonObject;
        JSONArray mJsonArray;

        mSharedPreferences = context.getSharedPreferences(context.getString(R.string.data_file), Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mJsonArray = new JSONArray();
        for(Site s : list){
            mJsonObject = new JSONObject();
            try{
                mJsonObject.put(context.getString(R.string.column_titulo), s.getTitulo());
                mJsonObject.put(context.getString(R.string.column_url), s.getEndereco());
                mJsonObject.put(context.getString(R.string.column_favorito), s.isFavorito());
                mJsonArray.put(mJsonObject);
            }catch (JSONException e){
                throw new InsertException(e.getMessage());
            }
        }

        mEditor.putString(context.getString(R.string.table_sites), mJsonArray.toString());
        mEditor.commit();
    }

}