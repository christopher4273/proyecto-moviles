package com.example.movilesproyecto;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReporteVH extends RecyclerView.ViewHolder {
    public TextView txt_nombrereporte, txt_fechareporte, txt_severidad, txt_estado, txt_option;
    public ReporteVH(@NonNull View itemView) {
        super(itemView);
        txt_nombrereporte = itemView.findViewById(R.id.txt_nombrereporte);
        txt_fechareporte = itemView.findViewById(R.id.txt_fechareporte);
        txt_severidad = itemView.findViewById(R.id.txt_severidad);
        txt_estado = itemView.findViewById(R.id.txt_estado);
        txt_option = itemView.findViewById(R.id.txt_option);
    }
}
