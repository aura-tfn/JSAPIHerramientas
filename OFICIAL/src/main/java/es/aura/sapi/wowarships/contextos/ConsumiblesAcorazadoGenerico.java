package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;

public class ConsumiblesAcorazadoGenerico extends GrupoAcciones {

    public ConsumiblesAcorazadoGenerico(Sapi sapi) {
        super(sapi);
        put("reparaciones", (comando) -> {
            sapi.procesa("t");
            return null;
        });
        put("avion", (comando) -> {
            sapi.procesa("y");
            return null;
        });
        put("DANOS", (comando) -> {
            sapi.procesa("r");
            return null;
        });

    }

}
