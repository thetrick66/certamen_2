package cl.telematica.patricio.certamen2_v2.presenters.contract;

import java.util.List;

import cl.telematica.patricio.certamen2_v2.presenters.modelo.repos;

/**
 * Created by Patricio on 04-11-2016.
 */

public interface Certamen2Presenter {
    public List<repos> getLista(String result);
    public boolean getEncontrado();
    public void conectar();
    public List<repos> getReposes();
}
