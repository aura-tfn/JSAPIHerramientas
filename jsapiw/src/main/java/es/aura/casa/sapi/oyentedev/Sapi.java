package es.aura.casa.sapi.oyentedev;

import java.awt.AWTException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.Engine;
import javax.speech.EngineException;
// import javax.speech.EngineList;
import javax.speech.EngineModeDesc;
import javax.speech.EngineStateError;
import javax.speech.recognition.GrammarException;
import javax.speech.recognition.Recognizer;
import javax.speech.recognition.ResultAdapter;
import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.RuleToken;
import javax.speech.synthesis.Synthesizer;

@SuppressWarnings("unused")
public class Sapi extends ResultAdapter {

    private static final Logger LOG = Logger.getLogger(Sapi.class.getName());
    boolean mudo = false;
    Synthesizer lector;
    Recognizer oyente;
    URL dir;
    RuleGrammar gramatica;
    Map<String, Contexto> contextos;
    Operador operador;
    boolean dictado;
    Map<String, String> reglas = new HashMap<>();
    RuleGrammar gramaticaDinamica = null;
    RuleToken reglaDinamica;
    private boolean espera = false;
    private List<String> esperando = new ArrayList<>();
    String gramaticaExterna;
    private Map<String, Function<String, String>> comandos;

    public Sapi() {
        try {
            contextos = new TreeMap<>();
            dictado = false;
            operador = new Operador();
            EngineModeDesc eng = new EngineModeDesc(new Locale("es"));
//            EngineList l = Central.availableSynthesizers(eng);
            lector = Central.createSynthesizer(eng);
            lector.allocate();
            lector.waitEngineState(Engine.ALLOCATED);
            oyente = Central.createRecognizer(new EngineModeDesc(new Locale("es")));
            oyente.allocate();
            oyente.waitEngineState(Recognizer.ALLOCATED);
        } catch (IllegalArgumentException | EngineException | InterruptedException
                | EngineStateError | AWTException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public void finaliza() {
        try {
            oyente.forceFinalize(true);
            oyente.deallocate();
            oyente.waitEngineState(Engine.DEALLOCATED);
            lector.deallocate();
            lector.waitEngineState(Engine.DEALLOCATED);
        } catch (EngineException | EngineStateError | InterruptedException
                | IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public final void addGramatica(String nomGramatica, String reglaInicio) throws GrammarException {
        try {
            this.gramatica = oyente.loadJSGF(dir, nomGramatica + '.' + reglaInicio);
            this.gramatica.setEnabled(true);
            for (String listRuleName : this.gramatica.listRuleNames()) {
                reglas.put(listRuleName, gramatica.getName());
            }
            update();
        } catch (IOException | EngineStateError ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public RuleGrammar[] listGramaticas() {
        return oyente.listRuleGrammars();
    }

    public void activaGramatica(String nombre, boolean activar) {
        oyente.getRuleGrammar(nombre).setEnabled(activar);
    }

    public RuleGrammar loadGrammar(String filename) {
        RuleGrammar g = null;
        try {
            g = oyente.loadJSGF(new FileReader(filename));
            g.setEnabled(true);
            for (String name : g.listRuleNames()) {
                reglas.put(name, g.getName());
            }

            update();
        } catch (GrammarException | EngineStateError | IOException ex) {
            Logger.getLogger(Sapi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;

    }

    public void addPalabra(String token)
            throws GrammarException, AudioException {
        RuleToken r = new RuleToken(token);
        gramaticaDinamica.setRule(token, r, true);
        gramaticaDinamica.setEnabled(true);
        update();

    }

    public void addPalabras(String[] palabras)
            throws GrammarException, AudioException {
        for (String palabra : palabras) {
            addPalabra(palabra);
        }
    }

    public void listaPalabras() {
        Arrays.asList(gramaticaDinamica.listRuleNames())
                .forEach(rn -> di(rn));
    }

    public void resetRegla(String regla) throws GrammarException, AudioException {
        Arrays.asList(gramaticaDinamica.listRuleNames())
                .forEach(rn -> gramaticaDinamica.deleteRule(rn));
        update();
    }

    public void desactivaRegla(String regla, String gramatica) {
        RuleGrammar rg = oyente.getRuleGrammar(gramatica);
        rg.setEnabled(regla, false);
        update();
    }

    public void activaRegla(String regla, String gramatica) {
        RuleGrammar rg = oyente.getRuleGrammar(gramatica);
        rg.setEnabled(regla, true);
        update();
    }

    public void cambiaRegla(String[] salientes, String[] entrantes) {
        for (String entrante : entrantes) {
            if (entrante.contentEquals("dinamica")) {
                gramaticaDinamica.setEnabled(true);
            } else {
                RuleGrammar rg = oyente.getRuleGrammar(reglas.get(entrante));
                rg.setEnabled(entrante, true);
            }
        }
        for (String saliente : salientes) {
            if (saliente.contentEquals("dinamica")) {
                gramaticaDinamica.setEnabled(false);
            } else {
                RuleGrammar rg = oyente.getRuleGrammar(reglas.get(saliente));
                rg.setEnabled(saliente, false);
            }
        }
        update();
    }

    public void update() {
        try {
            oyente.suspend();
            oyente.commitChanges();
            oyente.requestFocus();
            oyente.resume();
        } catch (GrammarException | EngineStateError | AudioException ex) {
            Logger.getLogger(Sapi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void procesa(String tokens) {
        operador.procesa(tokens);
    }

    public void di(String frase) {
        if (!mudo) {
            try {
                oyente.suspend();
                oyente.releaseFocus();
                lector.speakPlainText(frase, null);
                LOG.info("maquina -> " + frase);
                lector.waitEngineState(Synthesizer.QUEUE_EMPTY);
                Thread.sleep(100);
                oyente.resume();
                oyente.requestFocus();
            } catch (AudioException | IllegalArgumentException | EngineStateError | InterruptedException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        } else {
            LOG.log(Level.INFO, "(mudo) ->{0}", frase);
        }
    }

    public void logeo() {
        for (RuleGrammar listRuleGrammar : oyente.listRuleGrammars()) {
            LOG.log(Level.INFO, "gramatica {0}: {1}",
                    new Object[]{listRuleGrammar.getName(), listRuleGrammar.isEnabled()});
            for (String listRuleName : listRuleGrammar.listRuleNames()) {
                if (listRuleGrammar.isRulePublic(listRuleName)) {
                    LOG.log(Level.INFO, "{0} -> {1}",
                            new Object[]{listRuleName, listRuleGrammar.isEnabled(listRuleName)});
                }
            }
        }
        LOG.info("--------------------------------------------");
    }

    public void addContexto(String nombreContexto, Contexto contexto) {
        contextos.put(nombreContexto, contexto);
    }

    public Map<String, Contexto> getContextos() {
        return contextos;
    }

    public Contexto getContexto(String nombre) {
        return contextos.get(nombre);
    }

    public boolean isEspera() {
        return espera;
    }

    public void setEspera(boolean espera) {
        this.espera = espera;
    }

    public List<String> reglasActivas() {
        RuleGrammar[] rgs = oyente.listRuleGrammars();
        return Arrays.asList(rgs).stream()
                .filter(rg -> rg.isActive())
                .map(rg -> rg.getName())
                .collect(Collectors.toList());
    }

    public void getExclusiva(String nombreContexto) {
        esperando.clear();
        contextos.keySet().forEach(ctx -> {
            if (!ctx.contentEquals(nombreContexto)) {
                Contexto c = contextos.get(ctx);
                if (!c.isEnespera()) {
                    c.setEnespera(true);
                    esperando.add(ctx);
                }
            }
        });
    }

    public void liberaExclusiva() {
        esperando.forEach(c -> contextos.get(c).setEnespera(false));
        esperando.clear();
    }

    public void delContexto(String nombre) {
        Contexto c = contextos.remove(nombre);
        oyente.deleteRuleGrammar(c.getGramatica());
        update();
    }
}
