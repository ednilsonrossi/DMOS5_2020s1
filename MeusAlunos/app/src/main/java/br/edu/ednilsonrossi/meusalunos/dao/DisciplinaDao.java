package br.edu.ednilsonrossi.meusalunos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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

    public Disciplina recupera(String sigla) throws SQLException {
        Disciplina mDisciplina;
        Cursor mCursor;
        AlunoDao alunoDao = new AlunoDao(mHelper.getContext());

        String colunas[] = new String[]{
                SQLiteHelper.COLUMN_SIGLA,
                SQLiteHelper.COLUMN_DISCIPLINA
        };

        String clausulaWhere = SQLiteHelper.COLUMN_SIGLA + " = ?";

        String argumentos[] = new String[]{
                sigla
        };

        mSqLiteDatabase = mHelper.getReadableDatabase();

        mCursor = mSqLiteDatabase.query(
                SQLiteHelper.TABLE_NAME_DISCIPLINAS,
                colunas,
                clausulaWhere,
                argumentos,
                null,
                null,
                null
        );

        if(mCursor.moveToNext()){
            mDisciplina = new Disciplina(mCursor.getString(0), mCursor.getString(1));
            mDisciplina.addAluno(alunoDao.recuperaTodosAlunos(mDisciplina));
        }else{
            throw new SQLException("Disicplina não encontrada.");
        }

        return mDisciplina;
    }

    public List<Disciplina> recuperaTodos(){
        List<Disciplina> mDisciplinaList;
        Disciplina mDisciplina;
        Cursor mCursor;
        AlunoDao mAlunoDao;

        mDisciplinaList = new ArrayList<>();
        mAlunoDao = new AlunoDao(mHelper.getContext());

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
            mDisciplina.addAluno(mAlunoDao.recuperaTodosAlunos(mDisciplina));
            mDisciplinaList.add(mDisciplina);
        }

        mCursor.close();
        mSqLiteDatabase.close();
        return mDisciplinaList;
    }

    public List<Disciplina> recuperaTodasDisciplinas(Aluno aluno){
        List<Disciplina> mDisciplinaList;
        Disciplina mDisciplina;
        Cursor mCursor;

        mDisciplinaList = new ArrayList<>();

        String sql = "SELECT " +
                SQLiteHelper.TABLE_NAME_DISCIPLINAS + "." + SQLiteHelper.COLUMN_SIGLA + ", " +
                SQLiteHelper.TABLE_NAME_DISCIPLINAS + "." + SQLiteHelper.COLUMN_DISCIPLINA +
                " FROM " + SQLiteHelper.TABLE_NAME_DISCIPLINAS +
                " INNER JOIN " + SQLiteHelper.TABLE_NAME_MATRICULAS +
                " WHERE " + SQLiteHelper.TABLE_NAME_DISCIPLINAS + "." + SQLiteHelper.COLUMN_SIGLA + " = " +
                SQLiteHelper.TABLE_NAME_MATRICULAS + "." + SQLiteHelper.COLUMN_SIGLA +
                " AND " + SQLiteHelper.TABLE_NAME_MATRICULAS + "." + SQLiteHelper.COLUMN_PRONTUARIO + " = ?";

        String argumentos[] = new String[]{
                aluno.getProntuario()
        };

        mSqLiteDatabase = mHelper.getReadableDatabase();

        mCursor = mSqLiteDatabase.rawQuery(sql, argumentos);

        while (mCursor.moveToNext()){
            mDisciplina = new Disciplina(mCursor.getString(0), mCursor.getString(1));
            mDisciplinaList.add(mDisciplina);
        }

        mCursor.close();
        mSqLiteDatabase.close();
        return mDisciplinaList;
    }

    public void matricular(Disciplina disciplina, Aluno aluno){
        if(disciplina == null || aluno == null){
            throw new NullPointerException();
        }

        ContentValues valores = new ContentValues();
        valores.put(SQLiteHelper.COLUMN_SIGLA, disciplina.getSigla());
        valores.put(SQLiteHelper.COLUMN_PRONTUARIO, aluno.getProntuario());

        mSqLiteDatabase = mHelper.getWritableDatabase();
        if(mSqLiteDatabase.insert(SQLiteHelper.TABLE_NAME_MATRICULAS, null, valores) == -1){
            throw new ChavePrimariaDuplicadaException("Erro ao adicionar aluno");
        }

        mSqLiteDatabase.close();
    }
}
