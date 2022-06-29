package com.example.movilesproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edit_nombrereporte = findViewById(R.id.edit_nombrereporte);
        final EditText edit_fechareporte = findViewById(R.id.edit_fechareporte);
        final EditText edit_severidad = findViewById(R.id.edit_severidad);
        final EditText edit_estado = findViewById(R.id.edit_estado);
        Button btn = findViewById(R.id.btn_submit);
        Button btn_open = findViewById(R.id.btn_open);
        btn_open.setOnClickListener(v->
        {
            Intent intent = new Intent(MainActivity.this, RVActivity.class);
            startActivity(intent);

        });

        DAOReporte dao= new DAOReporte();
        Reporte rep_edit = (Reporte)getIntent().getSerializableExtra("EDIT");
        if (rep_edit !=null)
        {
            btn.setText("UPDATE");
            edit_nombrereporte.setText(rep_edit.getNombreReporte());
            edit_fechareporte.setText(rep_edit.getFechaReporte());
            edit_severidad.setText(rep_edit.getSeveridad());
            edit_estado.setText(rep_edit.getEstado());
            btn_open.setVisibility(View.GONE);
        }else
        {
            btn.setText("SUBMIT");
            btn_open.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(v-> {
            Reporte rep = new Reporte(edit_nombrereporte.getText().toString(), edit_fechareporte.getText().toString(),edit_severidad.getText().toString(),
                    edit_estado.getText().toString());
            if(rep_edit==null) {
                dao.add(rep).addOnSuccessListener(suc -> {
                    Toast.makeText(this, "Agregado correctamente", Toast.LENGTH_SHORT).show();
                    edit_nombrereporte.setText("");
                    edit_fechareporte.setText("");
                    edit_severidad.setText("");
                    edit_estado.setText("");
                }).addOnFailureListener(er -> {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }else
            {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("nombreReporte", edit_nombrereporte.getText().toString());
                hashMap.put("fechaReporte", edit_fechareporte.getText().toString());
                hashMap.put("severidad", edit_severidad.getText().toString());
                hashMap.put("estado", edit_estado.getText().toString());
                dao.update(rep_edit.getKey(), hashMap).addOnSuccessListener(suc ->{
                    Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, RVActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(er ->{
                    Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

        });

    }
}