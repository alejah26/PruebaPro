package com.example.prueba.modelo;

import java.util.List;

public class Producto {
    String nombreP;
    Integer codigoP;
    Double precioP;

    public Producto() {
    }

    public Producto(String nombreP, int codigoP, double precioP) {
        this.nombreP = nombreP;
        this.codigoP = codigoP;
        this.precioP = precioP;
    }

     @Override
    public String toString() {
        return "Producto{" +
                "nombreP='" + nombreP + '\'' +
                ", codigoP=" + codigoP +
                ", precioP=" + precioP +
                '}';
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public int getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(int codigoP) {
        this.codigoP = codigoP;
    }

    public double getPrecioP() {
        return precioP;
    }

    public void setPrecioP(double precioP) {
        this.precioP = precioP;
    }
}
