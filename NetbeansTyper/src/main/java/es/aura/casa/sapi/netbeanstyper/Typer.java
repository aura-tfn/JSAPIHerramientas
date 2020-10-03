/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aura.casa.sapi.netbeanstyper;

import es.aura.casa.sapi.oyentedev.Sapi;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.speech.AudioException;
import javax.speech.recognition.GrammarException;
import javax.swing.JOptionPane;

/**
 *
 * @author Normal
 */
public class Typer  {

    private static final Logger LOG = Logger.getLogger(Typer.class.getName());
    final Object obj = this;
    private boolean completa = false;
    private Sapi sapi;
    private boolean deletreo = false;
    private String[] suspendidas;

    public Typer() {
        try {
//            sapi = new Sapi("netbeans");
//            sapi.addListener(this);
            LOG.info("Iniciando typer");
            sapi.di("Iniciando typer");
//            sapi.desactivaRegla("letra");
            sapi.di("Typer listo");
            synchronized (obj) {
                obj.wait();
            }
            System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(Typer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Map<String, Function<String, String>> acciones = new TreeMap<String, Function<String, String>>() {
        {
            put("FIN", (comandos) -> {
                sapi.di("Adiós");
                synchronized (obj) {
                    obj.notify();
                }
                return "";
            });
            put("ESPERA", (comandos) -> {
                sapi.setEspera(true);
                sapi.di("Typer a la espera");
                return "";
            });
            put("deletreo", (comandos) -> {
                if (!deletreo) {
                    deletreo = true;
                    String[] salientes = suspendidas;
                    String[] entrantes = new String[]{"letra"};
                    sapi.cambiaRegla(salientes, entrantes);
                    deletreo = true;
                    sapi.di("deletreando");
                }
                return "";
            });
            put("ESPERA", (comandos) -> {
                if (deletreo) {
                    deletreo = false;
                    String[] salientes = new String[]{"letra"};
                    String[] entrantes = suspendidas;
                    sapi.cambiaRegla(salientes, entrantes);
                    sapi.di("deletreado");
                    deletreo = false;
                }
                return "";
            });
            put("NUEVAVAR", (comandos) -> {
                try {
                    String name = "";
                    String[] entrantes = new String[]{"letra"};
                    String[] salientes = new String[]{"netbeans", "espera", "fin", "caracter", "dinamica"};
                    sapi.cambiaRegla(salientes, entrantes);
                    sapi.di("deletrea variable");
                    name = JOptionPane.showInputDialog(name);
                    sapi.addPalabra(name);
                    salientes = new String[]{"letra"};
                    entrantes = new String[]{"netbeans", "espera", "fin", "caracter", "dinamica"};
                    sapi.cambiaRegla(salientes, entrantes);
                    sapi.di(name);
                } catch (GrammarException | AudioException ex) {
                    sapi.di("Error al añadir palabra");
                }
                return "";
            });
            put("LISTVAR", (comandos) -> {
                sapi.listaPalabras();
                return "";
            });
            put("completa", (comandos) -> {
                completa = true;
                return "CTRL ESPACIO deletreo";
            });
            put(".", (res) -> {
                String r = "";
                if (completa) {
                    completa = false;
                    r = ". yaesta";
                }
                return r;
            });
            put("INTRO", (res) -> {
                String r = "";
                if (completa) {
                    completa = false;
                    r = "INTRO yaesta";
                }
                return r;
            });
            put("ESC", (res) -> {
                String r = "";
                if (completa) {
                    completa = false;
                    r = " yaesta";
                }
                return r;
            });
        }
    };

    public Function<String, String> nuevaVar = (comandos) -> {
        try {
            String name = "";
            String[] entrantes = new String[]{"letra"};
            String[] salientes = new String[]{"netbeans", "espera", "fin", "caracter", "dinamica"};
            sapi.cambiaRegla(salientes, entrantes);
            sapi.di("deletrea variable");
            name = JOptionPane.showInputDialog(name);
            sapi.addPalabra(name);
            salientes = new String[]{"letra"};
            entrantes = new String[]{"netbeans", "espera", "fin", "caracter", "dinamica"};
            sapi.cambiaRegla(salientes, entrantes);
            sapi.di(name);
        } catch (GrammarException | AudioException ex) {
            sapi.di("Error al añadir palabra");
        }
        return "";
    };

    public Function<String, String> listaVar = (comandos) -> {
        sapi.listaPalabras();
        return "";
    };

    public Map<String, Function<String, String>> getAcciones() {
        return acciones;
    }

}
