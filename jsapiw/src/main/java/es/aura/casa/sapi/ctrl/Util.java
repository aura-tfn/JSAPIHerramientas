package es.aura.casa.sapi.ctrl;

import es.aura.casa.sapi.oyentedev.Contexto;
import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;
import javax.speech.recognition.RuleGrammar;

@SuppressWarnings("unused")
public abstract class Util  {

    public static void addControlEspera(Sapi sapi) {
        GrupoAcciones acciones =  new GrupoAcciones(sapi) {
            {
                put("ESPERA", (comando) -> {                    
                    for (RuleGrammar gr : sapi.listGramaticas()) {
                        gr.setEnabled(false);
                    }
                    sapi.activaGramatica("espera", true);
                    return null;
                });
            }
            {
                put("CONTINUA", (comando) -> {
                   for (RuleGrammar gr : sapi.listGramaticas()) {
                        gr.setEnabled(true);
                    }
                    return null;
                });
            }
        };
        Contexto espera = Contexto.crea(sapi, acciones, "espera.gram");
    } 


}
