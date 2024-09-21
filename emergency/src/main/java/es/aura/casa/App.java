package es.aura.casa;

import java.util.logging.Logger;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.Sapi;


public class App 
{
    private static final Logger LOG = Logger.getLogger(App.class.getName());
    public static void main( String[] args )
    {
        AccionesEmergency accionesEmergency = new AccionesEmergency();
        Contexto contexto = Contexto.crea(accionesEmergency, "gram/comandos.gram");
        Sapi sapi = contexto.getSapi();
        sapi.di("Todo preparado");
        LOG.info("Todo preparado");
    }
}
