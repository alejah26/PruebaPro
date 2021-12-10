package com.example.prueba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.prueba.modelo.Producto;

import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Cliente(nombreC text, direccionC text, telefonoC text, emailC text, passwordC text);");
        db.execSQL("CREATE TABLE Negocio(nombreN text, direccionN text, telefonoN text, pagWebN text, passwordN text);");
        db.execSQL("CREATE TABLE Producto(codigoP int primary key, nombreP text, precioP double);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
