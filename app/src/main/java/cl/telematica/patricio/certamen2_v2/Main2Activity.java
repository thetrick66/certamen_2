package cl.telematica.patricio.certamen2_v2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.telematica.patricio.certamen2_v2.conexion.HttpServer;
import cl.telematica.patricio.certamen2_v2.modelo.repos;

public class Main2Activity extends AppCompatActivity {

    TextView texto;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        texto = (TextView) findViewById(R.id.title2);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (extras != null) {
            String dato = extras.getString("DATO");
            texto.setText("Lista de repositorios del usuario " + dato);
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
                    mAdapter = new UIAdapter(getLista(result));
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        };

        task.execute();

    }

    private List<repos> getLista(String result){
        List<repos> listaRepos = new ArrayList<repos>();
        try {
            JSONArray lista = new JSONArray(result);

            int size = lista.length();
            for(int i = 0; i < size; i++){
                repos repo = new repos();
                JSONObject objeto = lista.getJSONObject(i);

                repo.setId(objeto.getInt("id"));
                repo.setNombre(objeto.getString("name"));
                if (objeto.getString("description")=="null" || objeto.getString("description").isEmpty()){
                    repo.setDescription("Sin descripción disponible");
                }
                else{
                    repo.setDescription(objeto.getString("description"));
                }
                String fecha = objeto.getString("updated_at");
                String[] partfecha = fecha.split("T");
                fecha = partfecha[0];
                repo.setActual("Última actualización: " + fecha);

                listaRepos.add(repo);
            }
            return listaRepos;
        } catch (JSONException e) {
            e.printStackTrace();
            return listaRepos;
        }
    }
}
