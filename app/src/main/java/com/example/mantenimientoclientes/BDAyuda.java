package com.example.mantenimientoclientes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mantenimientoclientes.entidades.Cliente;
import com.example.mantenimientoclientes.entidades.Provincia;

import java.util.ArrayList;

public class BDAyuda extends SQLiteOpenHelper {
    private Context context;

    public final static String BD_CLIENTES = "bd_clientes";

    public final static String TB_CLIENTES = "clientes";
    //12345678a
    public final static String DNI = "dni";
    public final static String NOMBRE = "nombre";
    public final static String APELLIDOS = "epellidos";
    public final static String ID_PROVINCIA = "id_provincia";
    public final static String VIP = "vip";
    public final static String CORD_1 = "cord_1";
    public final static String CORD_2 = "cord_2";

    public final static String TB_PROVINCIAS = "provincias";
    public final static String NOM_PROVINCIA = "nom_provincia";

    public BDAyuda(@Nullable Context context) {
        super(context, BD_CLIENTES, null, 1);
        this.context=context;
    }

    private boolean isCreating = false;
    protected SQLiteDatabase actBd = null;

    @Override
    public void onCreate(SQLiteDatabase db) {

        actBd = db;
        isCreating = true;

        Log.i("DBManager", BD_CLIENTES + " creating " + TB_CLIENTES + " and " + TB_PROVINCIAS);


        try {
            db.beginTransaction();
            db.execSQL("create table if not exists " +
                    TB_CLIENTES + " (" +
                    DNI + " varchar(9) primary key not null, " +
                    NOMBRE + " varchar(25) not null, " +
                    APELLIDOS + " varchar(25) not null, " +
                    ID_PROVINCIA + " INTEGER not null, " +
                    VIP + " boolean not null, " +
                    CORD_1 + " real, " +
                    CORD_2 + " real, " +
                    "foreign key (" + ID_PROVINCIA + ") references " +
                    TB_PROVINCIAS + "(" + ID_PROVINCIA + ")" +
                    ")"
            );
            db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    TB_PROVINCIAS + " (" +
                    ID_PROVINCIA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOM_PROVINCIA + " string(25) NOT NULL UNIQUE )");

            db.setTransactionSuccessful();

        } catch (SQLException eSQL) {
            Log.e("DBManagerOnCreate ", eSQL.getMessage());
        } finally {
            db.endTransaction();
        }

        anadirProvincias();

        isCreating = false;
        actBd = null;
    }

    //Introduce un cliente, si existe, lo actualiza
    public void anadirCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues valores = new ContentValues();

        //Rellena un contentValue con los datos de un cliente
        clienteToContentValue(valores,cliente);

        try{
            db.beginTransaction();

            cursor = db.query(TB_CLIENTES,new String[]{DNI},
                    DNI + "= ?",
                    new String[]{cliente.getDni()},
                    null,
                    null,
                    null,
                    "1");

            cursor.moveToFirst();

            if(cursor.getCount()>0){
                db.update(TB_CLIENTES,
                        valores,
                        DNI+" = ?",
                        new String[] {cursor.getString(0)});
                Toast.makeText(context,"Anctualizando cliente",Toast.LENGTH_SHORT).show();
            }else{
                db.insert(TB_CLIENTES,
                        null,
                        valores);
                Toast.makeText(context,"Insertando cliente",Toast.LENGTH_SHORT).show();
            }

            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.e("ErrorSQLInsertarCLiente","CLiente no insertado "+e.getMessage());
        }finally {
            db.endTransaction();
            cursor.close();
        }
    }

    private void clienteToContentValue(ContentValues cv, Cliente cliente) {
        cv.put(DNI,cliente.getDni());
        cv.put(NOMBRE,cliente.getNombre());
        cv.put(APELLIDOS,cliente.getApellidos());
        cv.put(ID_PROVINCIA,cliente.getidProvincia());
        cv.put(VIP,cliente.isVip());
        cv.put(CORD_1,cliente.getCord1());
        cv.put(CORD_2,cliente.getCord2());
    }

    //Crea las posibles provincias
    private void anadirProvincias() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues values = new ContentValues();

        String[] provinciasEspana = {
                "Álava", "Albacete", "Alicante", "Almería", "Asturias", "Ávila", "Badajoz",
                "Barcelona", "Burgos", "Cáceres", "Cádiz", "Cantabria", "Castellón", "Ciudad Real",
                "Córdoba", "Cuenca", "Gerona", "Granada", "Guadalajara", "Guipúzcoa", "Huelva",
                "Huesca", "Islas Balears", "Jaén", "La Coruña", "La Rioja", "Las Palmas", "León",
                "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Orense", "Palencia",
                "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia", "Sevilla", "Soria",
                "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"
        };
        try {
            db.beginTransaction();

            cursor = db.query(TB_PROVINCIAS, new String[]{ID_PROVINCIA}, null,
                    null, null, null, null, null);
            if (cursor.getCount() > 0) {
                db.delete(TB_PROVINCIAS, null, null);
            }
            for (String provincia : provinciasEspana) {
                values.put(NOM_PROVINCIA, provincia);
                db.insert(TB_PROVINCIAS, null, values);
            }


            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("SQLiteProvinciasBasicas", "Error creando las provincias basicas, " + e.getMessage());
        } finally {
            cursor.close();
            db.endTransaction();
        }
    }

    public ArrayList<Cliente> getAllClientes(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Cliente> clientes = new ArrayList<>();

        cursor = db.query(TB_CLIENTES,
                null,
                null,
                null,
                null,
                null,
                DNI);

        while (cursor.moveToNext()){
            clientes.add(new Cliente(cursor.getString(0)
                    ,cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    Boolean.parseBoolean(cursor.getString(4)),
                    cursor.getDouble(5),
                    cursor.getDouble(6)));
        }

        return clientes;
    }

    @SuppressLint("Range")
    public ArrayList<Provincia> getAllProvincias() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<Provincia> provincias = new ArrayList();

        cursor = db.query(TB_PROVINCIAS, null, null, null, null, null, ID_PROVINCIA);

        while (cursor.moveToNext()) {
            provincias.add(new Provincia(cursor.getString(cursor.getColumnIndex(NOM_PROVINCIA)), cursor.getInt(cursor.getColumnIndex(ID_PROVINCIA))));
        }
        cursor.close();
        return provincias;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db;

        if (isCreating && actBd != null) {
            db = actBd;
        } else {
            db = super.getWritableDatabase();
        }

        return db;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase db;

        if (isCreating && actBd != null) {
            db = actBd;
        } else {
            db = super.getReadableDatabase();
        }

        return db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBManager", BD_CLIENTES + " par la nueva version: " + oldVersion + " -> " + newVersion);
        db.beginTransaction();

    }
}
