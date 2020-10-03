package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;

public class ConsumiblesSubmarinoGenerico extends GrupoAcciones {

    public ConsumiblesSubmarinoGenerico(Sapi sapi) {
        super(sapi);
        put("TERCERO", (comando) -> {
            sapi.procesa("y");
            return null;
        });
        put("evasion", (comando) -> {
            sapi.procesa("t");
            return null;
        });
        put("DANOS", (comando) -> {
            sapi.procesa("r");
            return null;
        });

    }

}
