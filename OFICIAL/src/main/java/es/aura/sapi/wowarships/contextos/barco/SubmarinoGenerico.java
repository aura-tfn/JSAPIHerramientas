package es.aura.sapi.wowarships.contextos.barco;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;
import es.aura.sapi.wowarships.contextos.Artillero;
import es.aura.sapi.wowarships.contextos.ConsumiblesSubmarinoGenerico;
import es.aura.sapi.wowarships.contextos.Navegante;
import es.aura.sapi.wowarships.contextos.NaveganteSubmarino;
import es.aura.sapi.wowarships.contextos.OficialSubmarino;

public class SubmarinoGenerico extends Barco {

    Contexto oficial;
    OficialSubmarino accionesOficial;
    GrupoAcciones accionesConsumibles;
    Contexto consumibles;

    public SubmarinoGenerico(Contexto puerto, Sapi sapi) {
        super(puerto, sapi);
        navegante = Contexto.crea(sapi, new NaveganteSubmarino(sapi,this), "warships/navegantesubm.gram");
        accionesOficial = new OficialSubmarino(sapi);
        oficial = Contexto.crea(sapi, accionesOficial,
                "warships/oficialsub.gram");
        accionesConsumibles = new ConsumiblesSubmarinoGenerico(sapi);
        consumibles = Contexto.crea(sapi,
                accionesConsumibles, "warships/consumiblessubmarino.gram");
    }

    @Override
    public void atracar() {
        sapi.procesa("LEAVE");
        super.atracar();
        sapi.delContexto("navegantesubm");
        sapi.delContexto("oficialsub");
        sapi.delContexto("consumiblessubmarino");
    }

}
