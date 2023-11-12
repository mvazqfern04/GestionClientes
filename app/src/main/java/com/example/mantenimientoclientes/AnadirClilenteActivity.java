package com.example.mantenimientoclientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.mantenimientoclientes.entidades.Cliente;
import com.example.mantenimientoclientes.entidades.Provincia;

import java.util.ArrayList;

public class AnadirClilenteActivity extends AppCompatActivity {
    private BDAyuda bd;
    private EditText edNombre,edApellidos,edDni,edCord1,edCord2;
    private CheckBox chVip;
    private Spinner spiProvincia;
    private Button guardar,abrirMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_clilente);

        edDni=findViewById(R.id.edDni);
        edNombre=findViewById(R.id.edNombre);
        edApellidos=findViewById(R.id.edApellidos);
        edCord1=findViewById(R.id.edCord1);
        edCord2=findViewById(R.id.edCord2);
        chVip=findViewById(R.id.chVip);

        guardar=findViewById(R.id.btGuardar);
        abrirMaps=findViewById(R.id.btAccionaMaps);

        abrirMaps.setOnClickListener(v -> verMapa());
        guardar.setOnClickListener(v -> guardarCliente());

        spiProvincia = findViewById(R.id.spProvincias);
        bd = new BDAyuda(this);
        ArrayList<Provincia> provincias = bd.getAllProvincias();

        ArrayAdapter<Provincia> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, provincias);

        spiProvincia.setAdapter(arrayAdapter);

    }

    private void verMapa() {
        try {
            String latitud = edCord1.getText().toString();
            String longitud = edCord2.getText().toString();
            String URI = "geo:"+latitud+","+longitud+"?z=17";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI));
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this,"Faltan campos por rellenar",Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarCliente() {
        Cliente cliente = generarCliente();
        if(cliente!=null){
            bd.anadirCliente(cliente);
            Toast.makeText(this,
                    "Cliente: "+cliente.getNombre().toString()+" guardado con exito",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"No ha sido posible guardar," +
                    "revise los campos",Toast.LENGTH_SHORT).show();
        }
    }

    private Cliente generarCliente() {
        if (edDni.getText()!=null &&
        edNombre.getText()!=null &&
        edApellidos.getText()!=null &&
        edCord1.getText()!=null &&
        edCord2.getText()!=null){
            Provincia selecProvincia = (Provincia) spiProvincia.getSelectedItem();
            return new Cliente(edDni.getText().toString(),
                    edNombre.getText().toString(),
                    edApellidos.getText().toString(),
                    selecProvincia.getIdProvincia(),
                    chVip.isChecked(),
                    Double.parseDouble(edCord1.getText().toString()),
                    Double.parseDouble(edCord2.getText().toString()));
        }else {
            return null;
        }
    }

}