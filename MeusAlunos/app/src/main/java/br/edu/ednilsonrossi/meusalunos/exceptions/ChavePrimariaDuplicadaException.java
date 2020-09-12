package br.edu.ednilsonrossi.meusalunos.exceptions;

import android.database.sqlite.SQLiteException;

public class ChavePrimariaDuplicadaException extends SQLiteException {

    public ChavePrimariaDuplicadaException(String error) {
        super(error);
    }
}
