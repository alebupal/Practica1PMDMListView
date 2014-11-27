package com.izv.android.Practica1PMDMListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alejandro on 10/10/2014.
 */
public class AdaptadorArrayList extends ArrayAdapter<Pelicula> {
    private MyActivity padre = null; //Para poder acceder a los métodos de MyActivity
    private Context contexto;
    private ArrayList<Pelicula> lista;
    private int recurso;
    private static LayoutInflater i;

    public static class ViewHolder {
        public TextView tvTitulo, tvAnio, tvGenero;
        public ImageView ivCaratula;
        public int posicion;
    }
    public AdaptadorArrayList(Context context, int resource, ArrayList<Pelicula> objects, MyActivity padre) {
        super(context, resource, objects);
        this.padre = padre;
        this.contexto = context;
        this.lista = objects;
        this.recurso = resource;
        this.i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList getLista() {
        return lista;
    }

    public void setLista(ArrayList lista) {
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = i.inflate(recurso, null);
        }
        vh = new ViewHolder();
        vh.tvTitulo = (TextView) convertView.findViewById(R.id.tvTitulo);
        vh.tvGenero = (TextView) convertView.findViewById(R.id.tvGenero);
        vh.tvAnio = (TextView) convertView.findViewById(R.id.tvAnio);
        convertView.setTag(vh);


        vh.ivCaratula = (ImageView) convertView.findViewById(R.id.ivCaratula);
        vh.ivCaratula.setImageResource(R.drawable.img_gen);
        vh = (ViewHolder) convertView.getTag();


        vh.posicion = position;
        vh.tvTitulo.setText(lista.get(position).getTitulo());
        vh.tvAnio.setText(lista.get(position).getAnio().toString());
        vh.tvGenero.setText(lista.get(position).getGenero());


        if (lista.get(position).uriCaratula == null) {
            String ruta = "drawable://" + lista.get(position).getCaratula();
            MyActivity.cargarImagen(padre.cargadorImagenes, vh.ivCaratula, ruta);
        } else {
            MyActivity.cargarImagen(padre.cargadorImagenes, vh.ivCaratula, "file://" + lista.get(position).uriCaratula);
        }
        return convertView;
    }

}
