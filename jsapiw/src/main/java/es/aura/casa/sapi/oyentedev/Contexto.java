package es.aura.casa.sapi.oyentedev;

import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import javax.speech.recognition.FinalResult;
import javax.speech.recognition.FinalRuleResult;
import javax.speech.recognition.ResultAdapter;
import javax.speech.recognition.ResultEvent;
import javax.speech.recognition.ResultToken;
import javax.speech.recognition.RuleGrammar;

public class Contexto extends ResultAdapter {

    private static final Logger LOG = Logger.getLogger(Contexto.class.getName());

    private Sapi sapi;
    private Map<String, Function<String, String>> acciones;
    private RuleGrammar gramatica;
    private boolean enespera;

    public static Contexto crea(GrupoAcciones acciones,
            String fileName) {
        Contexto contexto = new Contexto();
        Sapi sapi = new Sapi();
        contexto.setSapi(sapi);
        contexto.setAcciones(acciones);
        String workDir = System.getProperty("user.dir");
        LOG.info("Current directory: " + workDir);
        contexto.setGramatica(sapi.loadGrammar( fileName));
        contexto.getGramatica().addResultListener(contexto);
        int barra = fileName.lastIndexOf("/");
        String nombreContexto = fileName.substring(++barra).replace(".gram", "");
        sapi.addContexto(nombreContexto, contexto);
        return contexto;
    }

    @Override
    public void resultAccepted(ResultEvent re) {
        if (enespera) {
            return;
        }
        final FinalResult r = (FinalResult) (re.getSource());
        if (r.getGrammar() instanceof RuleGrammar) {
            FinalRuleResult fr = (FinalRuleResult) r;
            String[] frase = null;
            String tokens = "";
            for (ResultToken bestToken : r.getBestTokens()) {
                tokens = tokens.concat(bestToken.getSpokenText() + " ");
            }
            tokens = tokens.trim();
            if (tokens != null && !tokens.isEmpty()) {
                LOG.info(tokens);
            }
            if (fr.getTags() != null && fr.getTags().length > 0) {
                frase = fr.getTags();

                for (String palabra : frase) {
                    if (acciones.containsKey(palabra)) {
                        LOG.info(palabra);
                        acciones.get(palabra).apply(palabra);
                    } else {
                        LOG.info(palabra);
                        sapi.procesa(palabra);
                    }
                }
            } else {
                acciones.get(tokens).apply(tokens);
            }
        }
    }

    public Sapi getSapi() {
        return sapi;
    }

    public void setSapi(Sapi sapi) {
        this.sapi = sapi;
    }

    public Map<String, Function<String, String>> getAcciones() {
        return acciones;
    }

    public void setAcciones(Map<String, Function<String, String>> acciones) {
        this.acciones = acciones;
    }

    public RuleGrammar getGramatica() {
        return gramatica;
    }

    public void setGramatica(RuleGrammar gramatica) {
        this.gramatica = gramatica;
    }

    public boolean isEnespera() {
        return enespera;
    }

    public void setEnespera(boolean enespera) {
        this.enespera = enespera;
    }

    public void setDesactivado(boolean desactivado) {
        gramatica.setEnabled(!desactivado);
        sapi.update();
        sapi.logeo();
    }

}
