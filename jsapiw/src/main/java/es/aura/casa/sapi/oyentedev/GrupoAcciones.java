package es.aura.casa.sapi.oyentedev;

import java.util.TreeMap;
import java.util.function.Function;

public class GrupoAcciones extends TreeMap<String, Function<String, String>> {

    private  Sapi sapi = null;

    public GrupoAcciones(Sapi sapi) {
        this.sapi = sapi;
    }

    public GrupoAcciones() {
    }

    public Sapi getSapi() {
        return sapi;
    }

    public void setSapi(Sapi sapi) {
        this.sapi = sapi;
    }
}
