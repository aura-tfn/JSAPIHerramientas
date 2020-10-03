package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;

public class TorreControl extends GrupoAcciones {

    Contexto torreControl;
    Contexto piloto;
    Contexto navegante;
    int tipoAvion = 0;
    boolean vuelo = false;
    boolean giro = false;

    public TorreControl(Sapi sapi) {
        super(sapi);
        put("cohetes", (comando) -> {
            sapi.procesa("LEAVE");
            sapi.procesa("1");
            tipoAvion = 1;
            vuelo = true;
            return null;
        });
        put("torpederos", (comando) -> {
            sapi.procesa("LEAVE");
            sapi.procesa("2");
            tipoAvion = 2;
            vuelo = true;
            return null;
        });
        put("bombarderos", (comando) -> {
            sapi.procesa("LEAVE");
            sapi.procesa("3");
            tipoAvion = 3;
            vuelo = true;
            return null;
        });
        put("despegar", (comando) -> {
            sapi.procesa(Integer.toString(tipoAvion));
            piloto.setEnespera(false);
            torreControl.setEnespera(true);
            navegante.setEnespera(true);
            sapi.di("en vuelo");
            vuelo = true;
            return null;
        });
    }

    public void setContextos() {
        this.torreControl = getSapi().getContexto("torrecontrol");
        this.piloto = getSapi().getContexto("piloto");
        this.navegante = getSapi().getContexto("navegante");

    }

}
