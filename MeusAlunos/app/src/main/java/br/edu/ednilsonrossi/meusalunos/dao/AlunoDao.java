package br.edu.ednilsonrossi.meusalunos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ednilsonrossi.meusalunos.exceptions.ChavePrimariaDuplicadaException;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;

public class AlunoDao {

    private SQLiteDatabase mSqLiteDatabase;
    private SQLiteHelper mHelper;

    public AlunoDao(Context context) {
        mHelper = new SQLiteHelper(context);
    }

    public void adicionar(Aluno aluno) throws NullPointerException, ChavePrimariaDuplicadaException {
        if(aluno == null){
            throw new NullPointerException();
        }
        ContentValues valores = new ContentValues();
        valores.put(SQLiteHelper.COLUMN_PRONTUARIO, aluno.getProntuario());
        valores.put(SQLiteHelper.COLUMN_NOME, aluno.getNome());

        mSqLiteDatabase = mHelper.getWritableDatabase();
        if(mSqLiteDatabase.insert(SQLiteHelper.TABLE_NAME_ALUNOS, null, valores) == -1){
            throw new ChavePrimariaDuplicadaException("Erro ao adicionar aluno");
        }

        mSqLiteDatabase.close();
    }

    public List<Aluno> recuperaTodos(){
        List<Aluno> mAlunoList;
        Aluno mAluno;
        Cursor mCursor;

        mAlunoList = new ArrayList<>();

        String colunas[] = new String[]{
                SQLiteHelper.COLUMN_PRONTUARIO,
                SQLiteHelper.COLUMN_NOME
        };

        mSqLiteDatabase = mHelper.getReadableDatabase();

        mCursor = mSqLiteDatabase.query(
                SQLiteHelper.TABLE_NAME_ALUNOS,
                colunas,
                null,
                null,
                null,
                null,
                SQLiteHelper.COLUMN_NOME
        );

        while (mCursor.moveToNext()){
            mAluno = new Aluno(
                    mCursor.getString(0),
                    mCursor.getString(1)
            );
            mAlunoList.add(mAluno);
        }

        mCursor.close();
        mSqLiteDatabase.close();
        return mAlunoList;
    }
}
