package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prueba.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etnombre, etcodigo, etprecio;
    ListView lv_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etnombre = (EditText) findViewById(R.id.et_nombre);
        etcodigo = (EditText) findViewById(R.id.et_codigo);
        etprecio = (EditText) findViewById(R.id.et_precio);
        lv_producto = (ListView) findViewById(R.id.lv_pro);
    }

    //metodo para activar menu en cel
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.producto_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Guardar:
                Registrar(null);
                break;
            case R.id.Buscar:
                Buscar(null);
                break;
            case R.id.Editar:
                Modificar(null);
                break;
            case R.id.Eliminar:
                Eliminar(null);
                break;
        }
        return true;
    }

    public void Registrar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "enTuBarrio", null, 1);
        //Debemos abrir la base de datos en modo lectura/escritura
        SQLiteDatabase db = admin.getWritableDatabase();

        //capturar datos
        String codigoP = etcodigo.getText().toString();
        String nombreP = etnombre.getText().toString();
        String precioP = etprecio.getText().toString();

        //validar campos
        if (!codigoP.isEmpty() && !nombreP.isEmpty() && !precioP.isEmpty()) {
            ContentValues registro = new ContentValues();
            //Pasar los datos al contenedor
            registro.put("codigoP", codigoP);
            registro.put("nombreP", nombreP);
            registro.put("precioP", precioP);
            //Insertar los datos en la tabla
            db.insert("Producto", null, registro);
            listar();
            //cerrar base de datos
            db.close();
            //Limpiar los campos del formulario
            etcodigo.setText("");
            etnombre.setText("");
            etprecio.setText("");


            Toast.makeText(this, "El producto se ha registrado correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Debes digitar info en todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    //Metodo buscar
    public void Buscar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "enTuBarrio", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigoP = etcodigo.getText().toString();

        if (!codigoP.isEmpty()) {
            Cursor fila = db.rawQuery("select nombreP,precioP from Producto where codigoP =" + codigoP, null);
            if (fila.moveToFirst()) {
                etnombre.setText(fila.getString(0));
                etprecio.setText(fila.getString(1));
                listar();
                db.close();

            } else {
                Toast.makeText(this, "El producto no existe", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Debe digitar un palabra clave del producto a buscar", Toast.LENGTH_LONG).show();
        }
    }

    //metodo eliminar
    public void Eliminar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "enTuBarrio", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String codigoP = etcodigo.getText().toString();
        if (!codigoP.isEmpty()) {
            int cantidad = db.delete("Producto", "codigoP=" + codigoP, null);
            db.close();
            etcodigo.setText("");
            etnombre.setText("");
            etprecio.setText("");
            if (cantidad == 1) {
                Toast.makeText(this, "El producto se eliminó correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "El producto no existe", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Debe digitar el codigo del producto a eliminar", Toast.LENGTH_LONG).show();
        }
    }

    public void Modificar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "enTuBarrio", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        //capturar datos
        String codigoP = etcodigo.getText().toString();
        String nombreP = etnombre.getText().toString();
        String precioP = etprecio.getText().toString();
        if (!codigoP.isEmpty() && !nombreP.isEmpty() && !precioP.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigoP", codigoP);
            registro.put("nombreP", nombreP);
            registro.put("precioP", precioP);

            int cantidad = db.update("Producto", registro, "codigoP=" + codigoP, null);
            db.close();
            etcodigo.setText("");
            etnombre.setText("");
            etprecio.setText("");

            if (cantidad == 1) {
                Toast.makeText(this, "La información del producto se modificó correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "El producto no existe", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Debes digitar info en todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<Producto>obtenerProducto(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "enTuBarrio", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor datos=db.rawQuery("select * from Producto ", null);
        ArrayList<Producto>lista=new ArrayList<Producto>();
        while(datos.moveToNext()) {
            Producto p = new Producto();
            p.setCodigoP(datos.getInt(0));
            p.setNombreP(datos.getString(1));
            p.setPrecioP(datos.getDouble(2));
            lista.add(p);
        }
        db.close();
        return lista;
        }

    public void listar(){
        ArrayList<Producto>lista=obtenerProducto();
        if(lista.isEmpty()){
            lv_producto.setVisibility(View.VISIBLE);
            ArrayAdapter<Producto> adaptador=new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1,lista);
            lv_producto.setAdapter(adaptador);

            lv_producto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Producto p=lista.get(position);
                    etnombre.setText(p.getNombreP());
                    etcodigo.setText(Integer.toString(p.getCodigoP()));
                    etprecio.setText(Double.toString(p.getPrecioP()));
                }
            });
        }else{
            lv_producto.setVisibility(View.INVISIBLE);
        }
    }

}
