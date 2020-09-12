package br.edu.ednilsonrossi.meusalunos.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    //Constantes do Banco de Dados
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "meus_alunos.db";

    //Constantes da tabela Alunos
    public static final String TABLE_NAME_ALUNOS = "alunos";
    public static final String COLUMN_PRONTUARIO = "prontuario";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_EMAIL = "email";

    //Constantes da tabela Disciplina
    public static final String TABLE_NAME_DISCIPLINAS = "disciplinas";
    public static final String COLUMN_SIGLA = "sigla";
    public static final String COLUMN_DISCIPLINA = "disciplina";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql;

        sql = "CREATE TABLE " + TABLE_NAME_ALUNOS + " (";
        sql += COLUMN_PRONTUARIO + " TEXT NOT NULL, ";
        sql += COLUMN_NOME + " TEXT NOT NULL, ";
        sql += COLUMN_EMAIL + " TEXT, ";
        sql += "PRIMARY KEY (" + COLUMN_PRONTUARIO + ") );";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_NAME_DISCIPLINAS + " (";
        sql += COLUMN_SIGLA + " TEXT NOT NULL, ";
        sql += COLUMN_DISCIPLINA + " TEXT, ";
        sql += "PRIMARY KEY (" + COLUMN_SIGLA + ") );";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql;
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE " + TABLE_NAME_ALUNOS + " ADD COLUMN " + COLUMN_EMAIL + " TEXT";
                sqLiteDatabase.execSQL(sql);

            case 2:
                sql = "CREATE TABLE " + TABLE_NAME_DISCIPLINAS + " (";
                sql += COLUMN_SIGLA + " TEXT NOT NULL, ";
                sql += COLUMN_DISCIPLINA + " TEXT, ";
                sql += "PRIMARY KEY (" + COLUMN_SIGLA + ") );";
                sqLiteDatabase.execSQL(sql);
        }
    }
}
