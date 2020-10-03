package es.aura.casa.ratonchino;

import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;

public class AccionesRaton extends GrupoAcciones {

    public AccionesRaton(Sapi sapi) {
        super(sapi);

        put("CLICK", (command) -> {
            sapi.procesa("CLICK_ABSOLUTO");
            return null;
        });
        put("D_CLICK", (command) -> {
            sapi.procesa("D_CLICK_ABSOLUTO");
            return null;
        });
        put("R_CLICK", (command) -> {
            sapi.procesa("RCLICK_ABSOLUTO");
            return null;
        });
        put("T_CLICK", (command) -> {
            sapi.procesa("T_CLICK");
            return null;
        });
        put("DRAG", (command) -> {
            sapi.procesa("DRAG_ABSOLUTO");
            return null;
        });
        put("RDRAG", (command) -> {
            sapi.di("no implementado");
            return null;
        });
        put("MLEAVE", (command) -> {
            sapi.procesa("MLEAVE");
            return null;
        });
        put("FIN", (command) -> {
            sapi.di("adiós");
            System.exit(0);
            return null;
        });

    }

}
