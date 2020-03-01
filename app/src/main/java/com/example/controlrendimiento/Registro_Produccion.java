package com.example.controlrendimiento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.controlrendimiento.Modelo.Rendimiento;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Registro_Produccion extends AppCompatActivity {
    private List<Rendimiento> ListRendimiento = new ArrayList<>();
    ArrayAdapter<Rendimiento> arrayAdapterRendimiento;
    public Spinner spinner,spinner2,spinner3,spinner4;
    public EditText fecha,mallas;
    ListView listV_Rendimiento;
    Button bfecha;
    private  int dia,mes,ano;
    public String fech,bloqu,varieda,cortado,tallo,malla;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Rendimiento rendimientoSeleced;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__produccion);
        inicializarFirebase();
        listarDatos();
        bfecha=(Button)findViewById(R.id.bfecha);
        fecha=(EditText)findViewById(R.id.txtfecha);
        mallas = (EditText) findViewById(R.id.txtmallas);
        //Declaro los datos de mi spiner
        spinner = (Spinner) findViewById(R.id.spinner);
        String[] opciones = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        spinner.setAdapter(adapter);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        String[] opciones1 = {"Alva","Aubade","Cabaret","Carousel","Cherry Brandy","Cherry O","Cool Water","Deja Vu","Engagement","Esperance","Freedom","High Magic","Hot Prince","Iguana","Jade","Mondial"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones1);
        spinner2.setAdapter(adapter1);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        String[] opciones2 = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones2);
        spinner3.setAdapter(adapter2);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        String[] opciones3 = {"15","20","25"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones3);
        spinner4.setAdapter(adapter3);
        listV_Rendimiento=findViewById(R.id.lv_datos);
        listV_Rendimiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rendimientoSeleced=(Rendimiento)parent.getItemAtPosition(position);
                fecha.setText(rendimientoSeleced.getFecha());
                mallas.setText(rendimientoSeleced.getMallas());
            }
        });

    }

    private void listarDatos() {
        databaseReference.child("Registro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListRendimiento.clear();
                for(DataSnapshot objSnaptshot:dataSnapshot.getChildren()){
                    Rendimiento ren = objSnaptshot.getValue(Rendimiento.class);
                    ListRendimiento.add(ren);
                    arrayAdapterRendimiento= new ArrayAdapter<Rendimiento>(Registro_Produccion.this,android.R.layout.simple_list_item_1,ListRendimiento);
                    listV_Rendimiento.setAdapter(arrayAdapterRendimiento);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuregistro,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        bloqu=spinner.getSelectedItem().toString();
        varieda=spinner2.getSelectedItem().toString();
        cortado=spinner3.getSelectedItem().toString();
        tallo=spinner4.getSelectedItem().toString();
        malla=mallas.getText().toString();
        fech=fecha.getText().toString();
        switch (item.getItemId()){
            case R.id.icon_add:{
                if(fech.equals("")||malla.equals("")){
                    validacion();
                }
                else{
                    Rendimiento ren=new Rendimiento();
                    ren.setId(UUID.randomUUID().toString());
                    ren.setFecha(fech);
                    ren.setBloque(bloqu);
                    ren.setVariedad(varieda);
                    ren.setCortador(cortado);
                    ren.setTallos(tallo);
                    ren.setMallas(malla);
                    databaseReference.child("Registro").child(ren.getId()).setValue(ren);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    Limpiar();
                }
                break;

            }
            case R.id.icon_update:{
                Rendimiento ren=new Rendimiento();
                ren.setId(rendimientoSeleced.getId());
                ren.setFecha(fecha.getText().toString().trim());
                ren.setBloque(bloqu.trim());
                ren.setVariedad(varieda.trim());
                ren.setCortador(cortado.trim());
                ren.setTallos(tallo.trim());
                ren.setMallas(mallas.getText().toString().trim());
                databaseReference.child("Registro").child(ren.getId()).setValue(ren);
                Toast.makeText(this,"Actualizado",Toast.LENGTH_LONG).show();
                Limpiar();
                break;
            }
            case R.id.icon_delete:{
                Rendimiento ren=new Rendimiento();
                ren.setId(rendimientoSeleced.getId());
                databaseReference.child("Registro").child(ren.getId()).removeValue();
                Limpiar();
                Toast.makeText(this,"Eliminado",Toast.LENGTH_LONG).show();
                break;
            }
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Limpiar() {
        mallas.setText("");
    }

    private void validacion() {
        if(fech.equals("")){
            fecha.setError("Required");
        }
        else if(malla.equals("")){
            mallas.setError("Required");
        }
    }
    public void fecha(View view){
        final Calendar c= Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
            }
        }
                ,dia,mes,ano);
        datePickerDialog.show();
    }
}
