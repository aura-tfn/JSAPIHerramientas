package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;
import es.aura.sapi.wowarships.contextos.barco.AcorazadoGenerico;
import es.aura.sapi.wowarships.contextos.barco.Barco;
import es.aura.sapi.wowarships.contextos.barco.Crucero;
import es.aura.sapi.wowarships.contextos.barco.DestructorGenerico;
import es.aura.sapi.wowarships.contextos.barco.Portaviones;
import es.aura.sapi.wowarships.contextos.barco.SubmarinoGenerico;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccionesPuerto extends GrupoAcciones {

    Contexto puerto;
    Barco barco;

    public AccionesPuerto(Sapi sapi) {
        super(sapi);
        this.puerto = sapi.getContexto("puerto");
        put("zarpamos", (comando) -> {
            sapi.desactivaRegla("inicio", "warships.puerto");
            sapi.di("Tipo de barco");
            sapi.activaRegla("tipoBarco", "warships.puerto");
            return null;
        });
        put("portaviones", (comando) -> {
            saliendo("portaviones");
            barco = (Barco) new Portaviones(sapi, puerto);
            return null;
        });
        put("FINBATALLA", (comando) -> {
            sapi.desactivaRegla("finBatalla", "warships.puerto");
            sapi.di("Volviendo al puerto");
            barco.atracar();
            sapi.procesa("KEEP ESC");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(AccionesPuerto.class.getName()).log(Level.SEVERE, null, ex);
            }
             sapi.procesa("LEAVE");
           sapi.activaRegla("inicio", "warships.puerto");
            return null;
        });
        put("FINSESION", (comando) -> {
            sapi.di("Adios");
            sapi.finaliza();
            System.exit(0);
            return null;
        });
        put("DGENERICO", (comando) -> {
            barco = (Barco) new DestructorGenerico(puerto, sapi);
            saliendo("destructor");
            return null;
        });
        put("AGENERICO", (comando) -> {
            barco = (Barco) new AcorazadoGenerico(puerto, sapi);
            saliendo("acorazado");
            return null;
        });
        put("fiyi", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "fiji");
            saliendo("fiji");
            return null;
        });
        put("chapayev", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "chapayev");
            saliendo("chapayev");
            return null;
        });
        put("leander", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "leander");
            saliendo("leander");
            return null;
        });
        put("edimburgo", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "edimburgo");
            saliendo("edimburgo");
            return null;
        });
        put("mioko", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "myoko");
            saliendo("mioko");
            return null;
        });
        put("aoba", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "aoba");
            saliendo("aoba");
            return null;
        });
        put("alyeri", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "algerie");
            saliendo("alyerí");
            return null;
        });
        put("clivelan", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "cleveland");
            saliendo("clivelan");
            return null;
        });
        put("alaska", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "alaska");
            saliendo("alaska");
            return null;
        });
        put("york", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "york");
            saliendo("york");
            return null;
        });
        put("montecucholi", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "montecucholi");
            saliendo("montecucholi");
            return null;
        });
       put("trento", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "montecucholi");
            saliendo("trento");
            return null;
        });
       put("devonsaia", (comando) -> {
            barco = (Barco) new Crucero(puerto, sapi, "devonsaia");
            saliendo("devonsaia");
            return null;
        });
       put("submarino", (comando) -> {
            barco = (Barco) new SubmarinoGenerico(puerto, sapi);
            saliendo("submarino");
            return null;
        });
    }

    public void setPuerto(Contexto puerto) {
        this.puerto = puerto;
    }

    private void saliendo(String nombreBarco) {
        
        getSapi().desactivaRegla("tipoBarco", "warships.puerto");
        getSapi().di("a bordo del " + nombreBarco);
        getSapi().di("Oficial en el puente");
    }

}
