package cl.telematica.patricio.certamen2_v2.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cl.telematica.patricio.certamen2_v2.R;
import cl.telematica.patricio.certamen2_v2.presenters.Certamen2PresenterImpl;
import cl.telematica.patricio.certamen2_v2.presenters.ItemClickListener;
import cl.telematica.patricio.certamen2_v2.presenters.UIAdapter;
import cl.telematica.patricio.certamen2_v2.presenters.modelo.repos;
import cl.telematica.patricio.certamen2_v2.views.Main2View;

import static cl.telematica.patricio.certamen2_v2.R.id.title2;

public class Main2Activity extends AppCompatActivity implements Main2View, ItemClickListener {

    TextView texto;
    String url="";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<repos> reposes;
    private Certamen2PresenterImpl presenter;
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
                textoChange("No existe el usuario ingresado");
                url = "https://api.github.com/users//repos";
            } else {
                textoChange("Lista de repositorios del usuario " + dato);
                url = "https://api.github.com/users/" + dato + "/repos";
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        presenter = new Certamen2PresenterImpl(url);
        //presenter.conectar();
        reposes = presenter.getReposes();
        if(!presenter.getEncontrado()){
            textoChange("No existe el usuario ingresado");
        }
        mAdapter = new UIAdapter(reposes);
        mRecyclerView.setAdapter(mAdapter);
        ((UIAdapter) mAdapter).setOnClickListener(Main2Activity.this); // Bind the listener
    }

    @Override
    public void onClick(View view, int position) {
        final repos repo = reposes.get(position);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getUrl()));
        startActivity(browserIntent);
    }

    public void textoChange(String texto){
        this.texto.setText(texto);
    }

}
