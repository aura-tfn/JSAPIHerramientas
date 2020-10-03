package es.aura.sapi.wowarships.contextos.barco;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;
import es.aura.sapi.wowarships.contextos.Cartografo;
import es.aura.sapi.wowarships.contextos.Navegante;

public abstract class Barco {

    Contexto puerto;
    Sapi sapi;
    Contexto cartografo;
    Contexto navegante;
    Contexto radio;

    public Barco(Contexto puerto, Sapi sapi) {
        this.puerto = puerto;
        this.sapi = sapi;
        radio = Contexto.crea(sapi, new GrupoAcciones(sapi), "warships/radio.gram");
        cartografo = Contexto.crea(sapi, new Cartografo(sapi), "warships/cartografo.gram");
        navegante = Contexto.crea(sapi, new Navegante(sapi,this), "warships/navegante.gram");
    }

    public void atracar() {
        sapi.delContexto("cartografo");
        sapi.delContexto("navegante");
        sapi.delContexto("radio");

    }
    
}
