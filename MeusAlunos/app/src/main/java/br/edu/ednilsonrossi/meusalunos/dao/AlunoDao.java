package br.edu.ednilsonrossi.meusalunos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

import br.edu.ednilsonrossi.meusalunos.exceptions.ChavePrimariaDuplicadaException;
import br.edu.ednilsonrossi.meusalunos.model.Aluno;
import br.edu.ednilsonrossi.meusalunos.model.Disciplina;

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
        valores.put(SQLiteHelper.COLUMN_EMAIL, aluno.getEmail());

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
        DisciplinaDao mDisciplinaDao = new DisciplinaDao(mHelper.getContext());

        mAlunoList = new ArrayList<>();

        String colunas[] = new String[]{
                SQLiteHelper.COLUMN_PRONTUARIO,
                SQLiteHelper.COLUMN_NOME,
                SQLiteHelper.COLUMN_EMAIL
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
                    mCursor.getString(1),
                    mCursor.getString(2)
            );
            mAluno.addDisciplina(mDisciplinaDao.recuperaTodasDisciplinas(mAluno));
            mAlunoList.add(mAluno);
        }

        mCursor.close();
        mSqLiteDatabase.close();
        return mAlunoList;
    }

    public List<Aluno> recuperaTodosAlunos(Disciplina disciplina){
        List<Aluno> mAlunoList;
        Aluno mAluno;
        Cursor mCursor;

        mAlunoList = new ArrayList<>();

        String sql = "SELECT A." + SQLiteHelper.COLUMN_PRONTUARIO + ", " +
                "A." + SQLiteHelper.COLUMN_NOME + ", " +
                "A." + SQLiteHelper.COLUMN_EMAIL +
                " FROM " + SQLiteHelper.TABLE_NAME_ALUNOS + " AS A " +
                " INNER JOIN " + SQLiteHelper.TABLE_NAME_MATRICULAS + " AS M " +
                " WHERE A." + SQLiteHelper.COLUMN_PRONTUARIO + " = M." + SQLiteHelper.COLUMN_PRONTUARIO +
                " AND " + SQLiteHelper.COLUMN_SIGLA + " = ? ORDER BY " + SQLiteHelper.COLUMN_NOME;

        String argumentos[] = new String[]{
                disciplina.getSigla()
        };

        mSqLiteDatabase = mHelper.getReadableDatabase();
        mCursor = mSqLiteDatabase.rawQuery(sql, argumentos);

        while(mCursor.moveToNext()){
            mAluno = new Aluno(mCursor.getString(0), mCursor.getString(1), mCursor.getString(2));
            mAlunoList.add(mAluno);
        }

        mCursor.close();
        mSqLiteDatabase.close();
        return mAlunoList;
    }

    public List<Aluno> recuperaTodosAlunosNaoMatriculados(Disciplina disciplina){
        List<Aluno> mAlunoList;
        Aluno mAluno;
        Cursor mCursor;

        mAlunoList = new ArrayList<>();

        String sql = "SELECT A." + SQLiteHelper.COLUMN_PRONTUARIO + ", " +
                "A." + SQLiteHelper.COLUMN_NOME + ", " +
                "A." + SQLiteHelper.COLUMN_EMAIL +
                " FROM " + SQLiteHelper.TABLE_NAME_ALUNOS + " AS A " +
                " WHERE " + SQLiteHelper.COLUMN_PRONTUARIO + " NOT IN (" +
                " SELECT " + SQLiteHelper.COLUMN_PRONTUARIO + " FROM " + SQLiteHelper.TABLE_NAME_MATRICULAS +
                " WHERE " + SQLiteHelper.COLUMN_SIGLA + " = ? )";

        String argumentos[] = new String[]{
                disciplina.getSigla()
        };

        mSqLiteDatabase = mHelper.getReadableDatabase();
        mCursor = mSqLiteDatabase.rawQuery(sql, argumentos);

        while(mCursor.moveToNext()){
            mAluno = new Aluno(mCursor.getString(0), mCursor.getString(1), mCursor.getString(2));
            mAlunoList.add(mAluno);
        }

        mCursor.close();
        mSqLiteDatabase.close();
        return mAlunoList;
    }


}
