package es.aura.sapi.wowarships;

import es.aura.sapi.wowarships.contextos.AccionesPuerto;
import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.Sapi;
import java.util.logging.Logger;

public class OficialWow {

    private WindowHandler handler = null;

    Contexto puerto;
    Contexto portaviones;
    Sapi sapi;
    private final AccionesPuerto accionesPuerto;
    private static final Logger LOG = Logger.getLogger(OficialWow.class.getName());

    public OficialWow() {
        handler = WindowHandler.getInstance();
        LOG.getParent().addHandler(handler);
        LOG.addHandler(handler);
        sapi = new Sapi();
        sapi.di("SAPI activado");
        sapi.loadGrammar("warships/tipoBarcos.gram");
        accionesPuerto = new AccionesPuerto(sapi);
        puerto = Contexto.crea(sapi, accionesPuerto, "warships/puerto.gram");
        accionesPuerto.setPuerto(puerto);
        sapi.desactivaRegla("tipoBarco", "warships.puerto");
        sapi.di("tripulación a la espera");
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new OficialWow();
    }

}
