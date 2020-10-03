package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;

public class Cartografo extends GrupoAcciones {

    Contexto cartografo;

    public Cartografo(Sapi sapi) {
        super(sapi);
        put("MAPA", (comando) -> {
            sapi.procesa("LEAVE");
            sapi.procesa("m");
            sapi.getExclusiva("cartografo");
            getSapi().activaRegla("ordenesmapa", "warships.cartografo");
            getSapi().activaRegla("finmapas", "warships.cartografo");
            getSapi().desactivaRegla("mapa", "warships.cartografo");
            sapi.di("calculando rumbo");
            return null;
        });
        put("nuevo destino", (comando) -> {
            sapi.procesa("CLICK");
            return null;
        });
        put("nuevo punto", (comando) -> {
            sapi.procesa("SHIFT CLICK");
            return null;
        });
        put("CIERRA", (comando) -> {
            sapi.procesa("m");
            sapi.liberaExclusiva();
            getSapi().activaRegla("mapa", "warships.cartografo");
            getSapi().desactivaRegla("ordenesmapa", "warships.cartografo");
            getSapi().desactivaRegla("finmapas", "warships.cartografo");
            sapi.di("rumbo fijado");
            return null;
        });
    }

    public void setContextos() {
        cartografo = getSapi().getContexto("cartografo");
        getSapi().desactivaRegla("ordenesmapa", "warships.cartografo");
        getSapi().desactivaRegla("finmapas", "warships.cartografo");
    }

}
