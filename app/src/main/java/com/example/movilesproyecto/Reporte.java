package com.example.movilesproyecto;

import android.widget.EditText;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Reporte implements Serializable {
    @Exclude
    private String key;
    private String nombreReporte;
    private String fechaReporte;
    private String severidad;
    private String estado;
    public Reporte(){}
    public Reporte(String nombreReporte, String fechaReporte, String severidad, String estado) {
        this.nombreReporte = nombreReporte;
        this.fechaReporte = fechaReporte;
        this.severidad = severidad;
        this.estado = estado;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public String getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(String fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
