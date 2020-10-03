package es.aura.sapi.wowarships.contextos.barco;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;
import es.aura.sapi.wowarships.contextos.Artillero;

public class Crucero extends Barco {

    Contexto artillero;
    Artillero accionesArtillero;
    GrupoAcciones accionesConsumibles;
    Contexto consumibles;
    String nombreCrucero;

    public Crucero(Contexto puerto, Sapi sapi, String nombreCrucero) {
        super(puerto, sapi);
        this.nombreCrucero = nombreCrucero;
        accionesArtillero = new Artillero(sapi);
        artillero = Contexto.crea(sapi, accionesArtillero,
                "warships/artillero.gram");
        accionesConsumibles = new GrupoAcciones(sapi);
        String patron = "warships/consumibles%s.gram";
        String nombre = String.format(patron, nombreCrucero);
        consumibles = Contexto.crea(sapi, accionesConsumibles, nombre);
    }

    @Override
    public void atracar() {
        super.atracar();
        sapi.delContexto("artillero");
        String patron = "consumibles%s";
        String nombre = String.format(patron, nombreCrucero);
        sapi.delContexto(nombre);
    }

}
