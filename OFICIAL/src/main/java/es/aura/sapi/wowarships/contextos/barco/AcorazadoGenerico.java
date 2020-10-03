package es.aura.sapi.wowarships.contextos.barco;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;
import es.aura.sapi.wowarships.contextos.Artillero;

public class AcorazadoGenerico extends Barco {

    Contexto artillero;
    Artillero accionesArtillero;
    GrupoAcciones accionesConsumibles;
    Contexto consumibles;

    public AcorazadoGenerico(Contexto puerto, Sapi sapi) {
        super(puerto, sapi);
        accionesArtillero = new Artillero(sapi);
        artillero = Contexto.crea(sapi, accionesArtillero,
                "warships/artillero.gram");
        accionesConsumibles = new GrupoAcciones(sapi);
        consumibles = Contexto.crea(sapi,
                accionesConsumibles, "warships/consumiblesacorazado.gram");
    }

    @Override
    public void atracar() {
        super.atracar();
        sapi.delContexto("artillero");
        sapi.delContexto("consumiblesacorazado");
    }

}
