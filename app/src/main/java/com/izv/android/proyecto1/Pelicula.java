package com.izv.android.proyecto1;

/**
 * Created by Alejandro on 10/10/2014.
 */
public class Pelicula {
    private String titulo ;
    private Integer anio ;
    private String genero ;

    public Pelicula(String titulo, String genero, Integer anio) {
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
    }

    public Pelicula() {
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
}
