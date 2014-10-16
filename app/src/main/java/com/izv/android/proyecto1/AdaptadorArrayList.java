package com.izv.android.proyecto1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.util.ArrayList;
/**
 * Created by Alejandro on 10/10/2014.
 */
public class AdaptadorArrayList extends ArrayAdapter<Pelicula> {
    private MyActivity padre= null; //Para poder acceder a los métodos de MyActivity
    private Context contexto;
    private ArrayList<Pelicula> lista;
    private int recurso;
    private static LayoutInflater i;

    public static class ViewHolder{
        public TextView tvTitulo,tvAnio,tvGenero;
        public ImageView ivCaratula;
        public int posicion;
    }

    public AdaptadorArrayList(Context context, int resource, ArrayList<Pelicula> objects,MyActivity padre) {
        super(context, resource, objects);
        this.padre = padre;
        this.contexto = context;
        this.lista=objects;
        this.recurso=resource;
        this.i=(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList getLista() {
        return lista;
    }

    public void setLista(ArrayList lista) {
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(convertView==null){
            convertView=i.inflate(recurso,null);
        }
            vh=new ViewHolder();
            vh.tvTitulo=(TextView)convertView.findViewById(R.id.tvTitulo);
            vh.tvGenero=(TextView)convertView.findViewById(R.id.tvGenero);
            vh.tvAnio=(TextView)convertView.findViewById(R.id.tvAnio);
            convertView.setTag(vh);


            vh.ivCaratula=(ImageView)convertView.findViewById(R.id.ivCaratula);
            vh.ivCaratula.setImageResource(R.drawable.img_gen);
            vh=(ViewHolder)convertView.getTag();


        vh.posicion=position;
        vh.tvTitulo.setText(lista.get(position).getTitulo());
        vh.tvAnio.setText(lista.get(position).getAnio().toString());
        vh.tvGenero.setText(lista.get(position).getGenero());
        if(lista.get(position).uriCaratula==null) {
            String ruta = "drawable://" + lista.get(position).getCaratula();
            MyActivity.cargarImagen(padre.cargadorImagenes, vh.ivCaratula, ruta);
        }else{
            MyActivity.cargarImagen(padre.cargadorImagenes, vh.ivCaratula, "file://" + lista.get(position).uriCaratula);
        }
        return convertView;
    }
    private void borrar(final int pos){
        AlertDialog.Builder alert= new AlertDialog.Builder(contexto);
        alert.setTitle("Borrar");
        LayoutInflater inflater= LayoutInflater.from(contexto);
        alert.setPositiveButton("Borrar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        lista.remove(pos);
                        notifyDataSetChanged();
                    }
                });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();

    }




}
