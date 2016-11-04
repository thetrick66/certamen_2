package cl.telematica.patricio.certamen2_v2.presenters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.telematica.patricio.certamen2_v2.presenters.modelo.repos;

/**
 * Created by Patricio on 04-11-2016.
 */

public class Certamen2PresenterImpl {
    public boolean encontrado = true;

    public List<repos> getLista(String result){
        List<repos> listaRepos = new ArrayList<repos>();
        try {
            JSONObject objeto = new JSONObject(result);
            if (objeto==null){
                encontrado = false;
            }
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
    public boolean getEncontrado(){
        return encontrado;
    }

}
