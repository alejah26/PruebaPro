package com.example.prueba.modelo;

public class Producto {
    private String nombreP;
    private int codigoP;
    private double precioP;

    public Producto() {
    }

    public Producto(String nombreP, int codigoP, double precioP) {
        this.nombreP = nombreP;
        this.codigoP = codigoP;
        this.precioP = precioP;
    }

     @Override
    public String toString() {
        return "     "+codigoP +"                   "+nombreP+"       "+ precioP;
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
