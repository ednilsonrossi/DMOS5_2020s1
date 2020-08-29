package br.pro.ednilsonrossi.mypocket2.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    //Constantes do banco de dados
    public static final String DATABASE_NAME = "mypocket_db";
    public static final int DATABASE_VERSION = 1;

    //Constantes da tabela
    public static final String TABLE_NAME = "sites";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_ENDERECO = "endereco";
    public static final String COLUMN_FAVORITO = "favorito";



    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /*
    O método onCreate() é chamado apenas quando o aplicativo é instalado no dispositivo. Depois
    de instalado esse método não é mais invocado.

    Esse método é responsável pela criação de todas as tabelas no banco de dados. Observe
    que é muito simples a execução de um comando sql no banco de dados.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (";
        sql += COLUMN_TITULO + " TEXT NOT NULL, ";
        sql += COLUMN_ENDERECO + " TEXT NOT NULL, ";
        sql += COLUMN_FAVORITO + " INTEGER NOT NULL CHECK (" + COLUMN_FAVORITO + " IN (0,1) ) ";
        sql += ")";

        sqLiteDatabase.execSQL(sql);
    }


    /*
    Esse método é chamado quando a versão do banco de dados muda. Vamos falar dele no futuro.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
