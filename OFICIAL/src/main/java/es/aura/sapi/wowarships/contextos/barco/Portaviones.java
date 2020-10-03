package es.aura.sapi.wowarships.contextos.barco;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.Sapi;
import es.aura.sapi.wowarships.contextos.TorreControl;
import es.aura.sapi.wowarships.contextos.Piloto;

public class Portaviones extends Barco {

    Contexto torreControl;
    Contexto piloto;
    private final TorreControl accionessTorreControl;
    private final Piloto accionesPiloto;

    public Portaviones(Sapi sapi, Contexto puerto) {
        super(puerto, sapi);
        accionessTorreControl = new TorreControl(sapi);
        accionesPiloto = new Piloto(sapi);
        torreControl = Contexto.crea(sapi, accionessTorreControl, "warships/torrecontrol.gram");
        piloto = Contexto.crea(sapi, accionesPiloto, "warships/piloto.gram");
        piloto.setEnespera(true);
        accionessTorreControl.setContextos();
        accionesPiloto.setContextos();
    }

    @Override
    public void atracar() {
        super.atracar();
        sapi.delContexto("torrecontrol");
        sapi.delContexto("piloto");
    }

}
