package com.example.movilesproyecto;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edit_nombrereporte = findViewById(R.id.edit_nombrereporte);
        final EditText edit_fechareporte = findViewById(R.id.edit_fechareporte);
        final EditText edit_severidad = findViewById(R.id.edit_severidad);
        final EditText edit_estado = findViewById(R.id.edit_estado);
        LinearLayout layoutBtnCamera = (LinearLayout) findViewById(R.id.layoutBtnCamera);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        //for (int i=0; i<numBotones; i++){
        img = findViewById(R.id.img);
        Button button = new Button(this);
        //Asignamos propiedades de layout al boton
        button.setLayoutParams(lp);
        //Asignamos Texto al botón
        button.setText("Cámara");
        //Añadimos el botón a la botonera
        layoutBtnCamera.addView(button);
        //}
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

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

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                Bundle extras =  result.getData().getExtras();
                Bitmap imgBitmap = (Bitmap) extras.get("data");
                img.setImageBitmap(imgBitmap);
            }
        }
    });

}