package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;

public class OficialSubmarino extends GrupoAcciones {

    public OficialSubmarino(Sapi sapi) {
        super(sapi);

        put("fuego", (comando) -> {
            sapi.procesa("CLICK_ABSOLUTO");
            return null;
        });
        put("torpedos", (comando) -> {
            sapi.procesa("2");
            return null;
        });
        put("ping", (comando) -> {
            sapi.procesa("1");
            return null;
        });

        put("periscopio", (comando) -> {
            sapi.procesa("RSHIFT");
            return null;
        });
        put("ZOOMIN", (comando) -> {
            sapi.procesa("RUEDA+");
            return null;
        });
        put("ZOOMOUT", (comando) -> {
            sapi.procesa("RUEDA-");
            return null;
        });
        put("EXTERIOR", (comando) -> {
            sapi.procesa("RCLICK_ABSOLUTO");
            return null;
        });
        put("objetivo primario", (comando) -> {
            sapi.procesa("x");
            return null;
        });

    }

}
