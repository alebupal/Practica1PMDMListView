package com.izv.android.proyecto1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MyActivity extends Activity {
    private int INTENT_GALERIA = 1;
    private int INTENT_GALERIA_EDITAR = 2;
    private int posicionEditar=0;
    private Pelicula peliActual = null;
    private ArrayList<Pelicula> peliculas;
    private AdaptadorArrayList ad;
    private String rutaFotoElegida=null;
    private Boolean seleccion=false;
    public ImageLoader cargadorImagenes; //cargador de imagenes
    /****************************************************/
    /*                                                  */
    /*                  metodos on                      */
    /*                                                  */
    /****************************************************/
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        if (id == R.id.action_borrar) {
            borrar(index);
        } else if (id == R.id.action_editar) {
            editar(index);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        cargadorImagenes = initImageLoader(this);
        initComponents();
    }

    //al hacer long click sobre item de listview
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }

    //tecla menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    //opcion del menu
    //si proceso el click: true y si no no el que lo devuelve es el super
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_anadir) {
            return anadir();
        } else if (id == R.id.action_fecha) {
            ordenarFecha();
        } else if (id == R.id.action_nombre) {
            ordenarNombre();
        } else if (id == R.id.action_genero) {
            ordenarGenero();
        }
        return super.onOptionsItemSelected(item);
    }

//
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK && requestCode == INTENT_GALERIA) {
        peliActual = new Pelicula();
        rutaFotoElegida = getRealPathFromUri(data.getData());
        peliActual.setUriCaratula(rutaFotoElegida);
    } else if (resultCode == Activity.RESULT_OK && requestCode == INTENT_GALERIA_EDITAR) {
        rutaFotoElegida = getRealPathFromUri(data.getData());
        if (peliculas.get(posicionEditar).getUriCaratula() == null) {
            peliculas.get(posicionEditar).setUriCaratula(rutaFotoElegida);
            peliculas.get(posicionEditar).setCaratula(0);
        }
        peliculas.get(posicionEditar).setUriCaratula(rutaFotoElegida);
    } else if (resultCode == 0) {
        rutaFotoElegida = null;
        seleccion = false;
    }
    super.onActivityResult(requestCode, resultCode, data);
}
    /****************************************************/
    /*                                                  */
    /*               auxiliares                         */
    /*                                                  */
    /****************************************************/
    private void initComponents() {
        Pelicula peli0 = new Pelicula("Pulp Fiction", "Thriller. Acción | Crimen. Historias cruzadas. Película de culto", 1994, R.drawable.img_0,null);
        Pelicula peli1 = new Pelicula("Atrápame si puedes", "Drama. Comedia | Basado en hechos reales. Años 60. Años 70. Robos & Atracos", 2002, R.drawable.img_1,null);
        Pelicula peli2 = new Pelicula("El efecto mariposa", "Fantástico. Thriller. Drama | Viajes en el tiempo", 2004, R.drawable.img_2,null);
        Pelicula peli3 = new Pelicula("El silencio de los corderos", "Thriller. Intriga | Crimen. Thriller psicológico. Policíaco. Película de culto. Asesinos en serie. Secuestros / Desapariciones", 1991, R.drawable.img_3,null);
        Pelicula peli4 = new Pelicula("Valkiria", "Acción. Bélico | Histórico. II Guerra Mundial. Nazismo. Basado en hechos reales", 2008, R.drawable.img_4,null);
        Pelicula peli5 = new Pelicula("Un ciudadano ejemplar", "Thriller. Intriga | Venganza", 2009, R.drawable.img_5,null);
        Pelicula peli6 = new Pelicula("Luces rojas", "Intriga. Thriller. Terror | Sobrenatural", 2012, R.drawable.img_6,null);
        Pelicula peli7 = new Pelicula("Malditos bastardos", "Bélico. Aventuras. Acción. Comedia | II Guerra Mundial. Nazismo. Venganza", 2009, R.drawable.img_7,null);
        Pelicula peli8 = new Pelicula("Infiltrados", "Thriller. Drama. Acción | Mafia. Remake. Crimen. Policíaco", 2006, R.drawable.img_8,null);
        Pelicula peli9 = new Pelicula("Buried", "Intriga. Thriller | Thriller psicológico. Secuestros / Desapariciones", 2010, R.drawable.img_9,null);
        Pelicula datos[] = {peli0, peli1, peli2, peli3, peli4, peli5, peli6, peli7, peli8, peli9};
        peliculas = new ArrayList<Pelicula>();
        for (Pelicula s : datos) {
            peliculas.add(s);
        }
        ad = new AdaptadorArrayList(this, R.layout.lista_detalle, peliculas,this);
        final ListView lv = (ListView) findViewById(R.id.lvLista);
        lv.setAdapter(ad);
        visualizarCaratulas(lv);
        registerForContextMenu(lv);
    }
    public void ordenarFecha(){
        Collections.sort(peliculas, new Comparator<Pelicula>() {
            @Override
            public int compare(Pelicula o1, Pelicula o2) {
                return o1.getAnio().compareTo(o2.getAnio());
            }
        });
        ad.notifyDataSetChanged();
    }
    public void ordenarGenero(){
        Collections.sort(peliculas, new Comparator<Pelicula>() {
            @Override
            public int compare(Pelicula o1, Pelicula o2) {
                return o1.getGenero().compareToIgnoreCase(o2.getGenero());
            }
        });
        ad.notifyDataSetChanged();
    }
    public void ordenarNombre(){
        Collections.sort(peliculas, new Comparator<Pelicula>() {
            @Override
            public int compare(Pelicula o1, Pelicula o2) {
                return o1.getTitulo().compareToIgnoreCase(o2.getTitulo());
            }
        });
        ad.notifyDataSetChanged();
    }
    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    /****************************************************/
    /*                                                  */
    /*               metodos click                      */
    /*                                                  */
    /****************************************************/
    private boolean anadir() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Añadir Película");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.anadir, null);
        Button btfoto = (Button) vista.findViewById(R.id.btFoto);
        seleccion=false;
        btfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccion=true;
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                MyActivity.this.startActivityForResult(i, INTENT_GALERIA);
            }
        });
        alert.setView(vista);
        alert.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int whichButton) {
                EditText etTitulo, etAnio, etGenero;
                etTitulo = (EditText) vista.findViewById(R.id.etTitulo);
                etAnio = (EditText) vista.findViewById(R.id.etAnio);
                etGenero = (EditText) vista.findViewById(R.id.etGenero);
                if(peliActual==null)
                peliActual = new Pelicula();
                if(etTitulo.getText().toString().equals("")==true || etAnio.getText().toString().equals("")==true || etGenero.getText().toString().equals("")==true){
                    tostada("Algun/os campos vacíos");
                }else{
                    if (peliActual.getUriCaratula() == null || seleccion==false){
                        tostada("Selecciona alguna carátula");  //evito que no se seleccione foto
                    }
                    else {
                        peliActual.setTitulo(etTitulo.getText().toString());
                        peliActual.setAnio(Integer.parseInt(etAnio.getText().toString()));
                        peliActual.setGenero(etGenero.getText().toString());

                        peliculas.add(peliActual);
                        ad.notifyDataSetChanged();
                        tostada("Película añadida");
                    }
                }
            }
        });
        alert.setNegativeButton("Cancelar", null);
        alert.show();
        return true;
    }
    private boolean borrar(final int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Seguro que desea borrarlo?");
        LayoutInflater inflater = LayoutInflater.from(this);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                peliculas.remove(pos);
                ad.notifyDataSetChanged();
                tostada("Película borrada");
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }
    private boolean editar(final int index) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Editar");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.editar, null);
        Button btCambiarFoto = (Button) vista.findViewById(R.id.btCambiarFoto);
        btCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                MyActivity.this.startActivityForResult(i, INTENT_GALERIA_EDITAR);
            }
        });
        alert.setView(vista);
        final EditText etTitulo, etAnio, etGenero;
        etTitulo = (EditText) vista.findViewById(R.id.etTitulo2);
        etAnio = (EditText) vista.findViewById(R.id.etAnio2);
        etGenero = (EditText) vista.findViewById(R.id.etGenero2);
        posicionEditar = index;
        etTitulo.setText(peliculas.get(index).getTitulo().toString());
        etAnio.setText(peliculas.get(index).getAnio().toString());
        etGenero.setText(peliculas.get(index).getGenero().toString());
        alert.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Pelicula peli = new Pelicula();
                if(etTitulo.getText().toString().equals("")==true || etAnio.getText().toString().equals("")==true || etGenero.getText().toString().equals("")==true){
                    tostada("Algun/os campos vacíos");
                }else{
                    peli.setTitulo(etTitulo.getText().toString());
                    peli.setAnio(Integer.parseInt(etAnio.getText().toString()));
                    peli.setGenero(etGenero.getText().toString());
                    if (peliculas.get(index).getCaratula() == 0) {
                        String caratula = peliculas.get(index).getUriCaratula().toString();
                        peli.setUriCaratula(caratula);
                        peli.setCaratula(0);
                    } else if (peliculas.get(index).getUriCaratula() == null) {
                        int caratula = peliculas.get(index).getCaratula();
                        peli.setUriCaratula(null);
                        peli.setCaratula(caratula);
                    }
                    peliculas.set(index, peli);
                    ad.notifyDataSetChanged();
                    tostada("Película modificada");
                }
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }
    private void visualizarCaratulas(ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = view.getTag();
                AdaptadorArrayList.ViewHolder vh;
                vh = (AdaptadorArrayList.ViewHolder) o;
                final ImageView img = (ImageView) findViewById(R.id.img);
                img.setVisibility(View.VISIBLE);
                img.setImageDrawable(((AdaptadorArrayList.ViewHolder) o).ivCaratula.getDrawable());
                img.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        img.setVisibility(View.GONE);
                        return true;
                    }
                });
            }
        });
    }

//
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = this.getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    //
    static public ImageLoader initImageLoader(Activity activity) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(activity)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        ImageLoader cargadorImagenes = ImageLoader.getInstance();
        cargadorImagenes.init(config);
        return cargadorImagenes;
    }
    //
    static public void cargarImagen(ImageLoader cargadorImagenes ,final ImageView imagen,final String ruta){
        //Ruta es una uri file:// o http:// ...
        try{
            cargadorImagenes.displayImage(ruta,imagen);
        }catch(Exception e){e.printStackTrace();}
    }

}
