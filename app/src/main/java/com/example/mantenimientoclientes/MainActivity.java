package com.example.mantenimientoclientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.mantenimientoclientes.entidades.Cliente;
import com.example.mantenimientoclientes.entidades.Provincia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BDAyuda bd;

    public MediaPlayer clickSonido;

    private ListView lv;
    private FloatingActionButton botonAnadir;

    private ArrayList<Cliente> clientes;


    public void recargar() {
        clientes = bd.getAllClientes();
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,clientes);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Efecto sonido
        clickSonido=MediaPlayer.create(this,R.raw.yeah_boy);

        bd=new BDAyuda(this);
        botonAnadir= findViewById(R.id.btAnadir);
        botonAnadir.setOnClickListener(v -> irAnadir());

        Cliente cliente = new Cliente("12345678a", "Dakyem", "Molina del Campo", 1, true, 12.345, 0.21345);
        bd.anadirCliente(cliente);
        clientes = bd.getAllClientes();

        lv = findViewById(R.id.listaVista);
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,clientes);
        lv.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        recargar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(clickSonido!=null){
            clickSonido.release();
            clickSonido=null;
        }
    }

    private void irAnadir() {
        playYeahBoy();
        Intent intent = new Intent(this,AnadirClilenteActivity.class);
        startActivity(intent);
    }

    public void playYeahBoy() {
        clickSonido.seekTo(0);
        clickSonido.start();
    }
}