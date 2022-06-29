package com.example.movilesproyecto;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    ArrayList<Reporte> list = new ArrayList<>();
    public RVAdapter(Context ctx){
        this.context=ctx;
    }
    public void setItems(ArrayList<Reporte> rep)
    {
        list.addAll(rep);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.ver_reportes, parent, false);
        return new ReporteVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Reporte r=null;
        this.onBindViewHolder(holder, position,r);
    }


    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Reporte r) {
        ReporteVH vh = (ReporteVH) holder;
        Reporte rep = r==null? list.get(position):r;
        vh.txt_nombrereporte.setText(rep.getNombreReporte());
        vh.txt_fechareporte.setText(rep.getFechaReporte());
        vh.txt_severidad.setText(rep.getSeveridad());
        vh.txt_estado.setText(rep.getEstado());
        vh.txt_option.setOnClickListener(v->
        {
            PopupMenu popupMenu = new PopupMenu(context,vh.txt_option);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item->
            {
                switch (item.getItemId())
                {
                    case R.id.menu_edit:
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("EDIT", rep);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        DAOReporte dao = new DAOReporte();
                        dao.remove(rep.getKey()).addOnSuccessListener(suc->{
                            Toast.makeText(context, "eliminado correctamente", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            list.remove(rep);
                        }).addOnFailureListener(er->{
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                        break;
                }
                return false;
            });
            popupMenu.show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
