package br.edu.ednilsonrossi.meusalunos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ednilsonrossi.meusalunos.exceptions.ChavePrimariaDuplicadaException;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;
import br.edu.ednilsonrossi.meusalunos.model.Disciplina;

public class DisciplinaDao {

    private SQLiteDatabase mSqLiteDatabase;
    SQLiteHelper mHelper;

    public DisciplinaDao(Context context) {
        mHelper = new SQLiteHelper(context);
    }

    public void adicionar(Disciplina disciplina) throws NullPointerException, ChavePrimariaDuplicadaException {
        if(disciplina == null){
            throw new NullPointerException();
        }
        ContentValues valores = new ContentValues();
        valores.put(SQLiteHelper.COLUMN_SIGLA, disciplina.getSigla());
        valores.put(SQLiteHelper.COLUMN_DISCIPLINA, disciplina.getDisciplina());

        mSqLiteDatabase = mHelper.getWritableDatabase();
        if(mSqLiteDatabase.insert(SQLiteHelper.TABLE_NAME_DISCIPLINAS, null, valores) == -1){
            throw new ChavePrimariaDuplicadaException("Erro ao adicionar aluno");
        }

        mSqLiteDatabase.close();
    }

    public List<Disciplina> recuperaTodos(){
        List<Disciplina> mDisciplinaList;
        Disciplina mDisciplina;
        Cursor mCursor;

        mDisciplinaList = new ArrayList<>();

        String colunas[] = new String[]{
                SQLiteHelper.COLUMN_SIGLA,
                SQLiteHelper.COLUMN_DISCIPLINA
        };

        mSqLiteDatabase = mHelper.getReadableDatabase();

        mCursor = mSqLiteDatabase.query(
                SQLiteHelper.TABLE_NAME_DISCIPLINAS,
                colunas,
                null,
                null,
                null,
                null,
                SQLiteHelper.COLUMN_SIGLA
        );

        while (mCursor.moveToNext()){
            mDisciplina = new Disciplina(mCursor.getString(0), mCursor.getString(1));
            mDisciplinaList.add(mDisciplina);
        }

        mCursor.close();
        mSqLiteDatabase.close();
        return mDisciplinaList;
    }
}
