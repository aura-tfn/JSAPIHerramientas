package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;

public class Piloto extends GrupoAcciones {

    Contexto navegante;
    Contexto torreControl;
    Contexto piloto;
    boolean giro = false;

    public Piloto(Sapi sapi) {
        super(sapi);
        put("izquierda", (comando) -> {
            if (giro) {
                sapi.procesa("LEAVE");
            }
            sapi.procesa("KEEP a");
            giro = true;
            return null;
        });
        put("derecha", (comando) -> {
            if (giro) {
                sapi.procesa("LEAVE");
            }
            sapi.procesa("KEEP d");
            giro = true;
            return null;
        });
        put("acelera", (comando) -> {
            sapi.procesa("KEEP w");
            return null;
        });
        put("vale", (comando) -> {
            sapi.procesa("LEAVE");
            giro = true;
            return null;
        });
        put("centro", (comando) -> {
            sapi.procesa("LEAVE");
            giro = true;
            return null;
        });
        put("ATACA", (comando) -> {
            sapi.procesa("CLICK_ABSOLUTO");
            return null;
        });
        put("fuego", (comando) -> {
            sapi.procesa("CLICK_ABSOLUTO");
            return null;
        });
        put("motor", (comando) -> {
            sapi.procesa("r");
            return null;
        });
        put("cazas", (comando) -> {
            sapi.procesa("t");
            return null;
        });
        put("reparaciones", (comando) -> {
            sapi.procesa("y");
            return null;
        });
        put("REGRESA", (comando) -> {
            sapi.procesa("LEAVE");
            sapi.procesa("f");
            torreControl.setEnespera(false);
            piloto.setEnespera(true);
            navegante.setEnespera(false);
            sapi.di("abordo");
            return null;
        });
    }

    public void setContextos() {
        this.torreControl = getSapi().getContexto("torrecontrol");
        this.piloto = getSapi().getContexto("piloto");
        this.navegante = getSapi().getContexto("navegante");
    }

}
