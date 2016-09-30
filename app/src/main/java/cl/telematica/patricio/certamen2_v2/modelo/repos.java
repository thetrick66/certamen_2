package cl.telematica.patricio.certamen2_v2.modelo;

/**
 * Created by Patricio on 30-09-2016.
 */

public class repos {

    private int id;
    private String name;
    private String description;
    private String actual;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }
}