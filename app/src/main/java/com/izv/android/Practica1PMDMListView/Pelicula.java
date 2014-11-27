package com.izv.android.Practica1PMDMListView;

/**
 * Created by Alejandro on 10/10/2014.
 */
public class Pelicula {
    private String titulo;
    private String genero;
    private Integer anio;
    private int caratula;
    public String uriCaratula;


    public Pelicula(String titulo, String genero, Integer anio, int caratula, String uriCaratula) {
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.caratula = caratula;
        this.uriCaratula=null;
    }

    public Pelicula() {
    }

    public String getUriCaratula() {
        return uriCaratula;
    }

    public void setUriCaratula(String uriCaratula) {
        this.uriCaratula = uriCaratula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getCaratula() {
        return caratula;
    }

    public void setCaratula(int caratula) {
        this.caratula = caratula;
    }
}
