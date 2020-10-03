package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;

public class Consumibles extends GrupoAcciones {

    public Consumibles(Sapi sapi) {
        super(sapi);
        sapi.loadGrammar("warships/consumibles.gram");

    }

}
