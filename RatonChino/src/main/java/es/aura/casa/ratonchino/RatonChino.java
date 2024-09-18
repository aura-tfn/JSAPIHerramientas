
package es.aura.casa.ratonchino;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.Sapi;

@SuppressWarnings("unused")
public class RatonChino {
    
    
    public static void main(String[] args) {
        Sapi sapi = new Sapi();
        AccionesRaton raton = new AccionesRaton(sapi);
        Contexto ct = Contexto.crea(sapi, raton, "raton.gram");
        sapi.di("ratón chino");
    }

}


