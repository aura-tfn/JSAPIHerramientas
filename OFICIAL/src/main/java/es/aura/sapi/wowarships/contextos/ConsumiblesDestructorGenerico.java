package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;

public class ConsumiblesDestructorGenerico extends GrupoAcciones {

    public ConsumiblesDestructorGenerico(Sapi sapi) {
        super(sapi);
        put("HUMO", (comando) -> {
            sapi.procesa("t");
            return null;
        });
        put("motor", (comando) -> {
            sapi.procesa("y");
            return null;
        });
        put("DANOS", (comando) -> {
            sapi.procesa("r");
            return null;
        });

    }

}
