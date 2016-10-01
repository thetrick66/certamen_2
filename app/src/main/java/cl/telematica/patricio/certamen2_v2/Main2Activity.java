package cl.telematica.patricio.certamen2_v2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.telematica.patricio.certamen2_v2.conexion.HttpServer;
import cl.telematica.patricio.certamen2_v2.modelo.repos;

import static cl.telematica.patricio.certamen2_v2.R.id.title2;

public class Main2Activity extends AppCompatActivity implements ItemClickListener{

    TextView texto;
    String url="";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<repos> reposes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        texto = (TextView) findViewById(title2);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (extras != null) {
            String dato = extras.getString("DATO");
            if (dato.isEmpty()) {
                texto.setText("No existe el usuario ingresado");
                url = "https://api.github.com/users//repos";
            } else {
                texto.setText("Lista de repositorios del usuario " + dato);
                url = "https://api.github.com/users/" + dato + "/repos";
            }

        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute(){

            }

            @Override
            protected String doInBackground(Void... params) {
                String resultado = new HttpServer().connectToServer("http://www.mocky.io/v2/57eee3822600009324111202", 15000);
                return resultado;
            }

            @Override
            protected void onPostExecute(String result) {
                if(result != null){
                    System.out.println(result);
                    reposes = getLista(result);
                    mAdapter = new UIAdapter(reposes);
                    mRecyclerView.setAdapter(mAdapter);
                    ((UIAdapter) mAdapter).setOnClickListener(Main2Activity.this); // Bind the listener
                }
            }
        };

        task.execute();

    }
    @Override
    public void onClick(View view, int position) {
        // The onClick implementation of the RecyclerView item click
        final repos repo = reposes.get(position);
        //Intent i = new Intent(this, CityviewActivity.class);
        //i.putExtra("url", repo.getUrl());
        //startActivity(i);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getUrl()));
        startActivity(browserIntent);
    }
    private List<repos> getLista(String result){
        List<repos> listaRepos = new ArrayList<repos>();
        try {
            JSONObject objeto = new JSONObject(result);
            if(objeto.getString("message").equals("Not Found")){
                texto.setText("No existe el usuario ingresado");
            }
            //repos repo = new repos();
            //repo.setNombre("NO EXISTE");
            //repo.setDescription("");
            //repo.setActual("");
            //texto.setText("No existe el usuario ingresado");
            //listaRepos.add(repo);
            return listaRepos;
        }catch (JSONException e) {
            e.printStackTrace();

            try {
                JSONArray lista = new JSONArray(result);

                int size = lista.length();
                for (int i = 0; i < size; i++) {
                    repos repo = new repos();
                    JSONObject objeto = lista.getJSONObject(i);

                    repo.setId(objeto.getInt("id"));
                    repo.setNombre(objeto.getString("name"));
                    if (objeto.getString("description") == "null" || objeto.getString("description").isEmpty()) {
                        repo.setDescription("Sin descripción disponible");
                    } else {
                        repo.setDescription(objeto.getString("description"));
                    }
                    String fecha = objeto.getString("updated_at");
                    String[] partfecha = fecha.split("T");
                    fecha = partfecha[0];
                    repo.setActual("Última actualización: " + fecha);
                    repo.setUrl(objeto.getString("html_url"));
                    listaRepos.add(repo);
                }
                return listaRepos;
            }catch (JSONException e1) {
                e.printStackTrace();
                return listaRepos;
            }
        }

    }

}
