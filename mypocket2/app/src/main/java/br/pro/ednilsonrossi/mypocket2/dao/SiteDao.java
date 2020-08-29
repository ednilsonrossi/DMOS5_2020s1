package br.pro.ednilsonrossi.mypocket2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.mypocket2.model.Site;

public class SiteDao {

    private SQLiteDatabase mDatabase;
    private SQLiteHelper mHelper;

    public SiteDao(Context context) {
        mHelper = new SQLiteHelper(context);
    }

    public List<Site> buscaTodos(){
        List<Site> mLista = new ArrayList<>();
        Site mSite;
        Cursor mCursor;

        mDatabase = mHelper.getReadableDatabase();

        String colunas[] = new String[] {
                SQLiteHelper.COLUMN_TITULO,
                SQLiteHelper.COLUMN_ENDERECO,
                SQLiteHelper.COLUMN_FAVORITO
        };

        mCursor = mDatabase.query(
                SQLiteHelper.TABLE_NAME,
                colunas,
                null,
                null,
                null,
                null,
                SQLiteHelper.COLUMN_TITULO
        );

        while (mCursor.moveToNext()){
            mSite = new Site(
                    mCursor.getString(0),
                    mCursor.getString(1)
            );
            if(mCursor.getInt(2) == 1)
                mSite.doFavotite();

            mLista.add(mSite);
        }

        mCursor.close();
        mDatabase.close();
        return mLista;
    }

    public void adiciona(Site site){
        mDatabase = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITULO, site.getTitulo());
        values.put(SQLiteHelper.COLUMN_ENDERECO, site.getEndereco());
        if(site.isFavorito()){
            values.put(SQLiteHelper.COLUMN_FAVORITO, 1);
        }else{
            values.put(SQLiteHelper.COLUMN_FAVORITO, 0);
        }

        mDatabase.insert(SQLiteHelper.TABLE_NAME, null, values);
        mDatabase.close();
    }

    public void atualiza(Site site){
        mDatabase = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITULO, site.getTitulo());
        values.put(SQLiteHelper.COLUMN_ENDERECO, site.getEndereco());
        if(site.isFavorito()){
            values.put(SQLiteHelper.COLUMN_FAVORITO, 1);
        }else{
            values.put(SQLiteHelper.COLUMN_FAVORITO, 0);
        }

        String where = SQLiteHelper.COLUMN_TITULO + " = '" + site.getTitulo() + "'";
        mDatabase.update(SQLiteHelper.TABLE_NAME, values, where, null);
        mDatabase.close();
    }
}
