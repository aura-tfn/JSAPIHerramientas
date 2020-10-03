package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.Sapi;
import es.aura.sapi.wowarships.contextos.barco.Barco;

public class NaveganteSubmarino extends Navegante {

    private boolean giro = false;

    public NaveganteSubmarino(Sapi sapi, Barco barco) {
        super(sapi, barco);
        put("abajo", (comando) -> {
            if (giro) {
                sapi.procesa("LEAVE");
            }
            sapi.procesa("KEEP c");
            giro = true;
            return null;
        });
        put("arriba", (comando) -> {
            if (giro) {
                sapi.procesa("LEAVE");
            }
            sapi.procesa("KEEP f");
            giro = true;
            return null;
        });
        put("vale", (comando) -> {
            sapi.procesa("LEAVE");
            giro = false;
            return null;
        });
    }

}
