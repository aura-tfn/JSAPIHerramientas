
package es.aura.casa.sapi.oyentedev.teclado;

import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_DELETE;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_END;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_F1;
import static java.awt.event.KeyEvent.VK_F10;
import static java.awt.event.KeyEvent.VK_F11;
import static java.awt.event.KeyEvent.VK_F12;
import static java.awt.event.KeyEvent.VK_F2;
import static java.awt.event.KeyEvent.VK_F3;
import static java.awt.event.KeyEvent.VK_F4;
import static java.awt.event.KeyEvent.VK_F5;
import static java.awt.event.KeyEvent.VK_F6;
import static java.awt.event.KeyEvent.VK_F7;
import static java.awt.event.KeyEvent.VK_F8;
import static java.awt.event.KeyEvent.VK_F9;
import static java.awt.event.KeyEvent.VK_HOME;
import static java.awt.event.KeyEvent.VK_INSERT;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_PAGE_DOWN;
import static java.awt.event.KeyEvent.VK_PAGE_UP;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.util.HashMap;


public class Teclas extends HashMap<String, Object> {

    public Teclas() {
        put("INTRO", VK_ENTER);
        put("DEL", VK_BACK_SPACE);
        put("SUPR", VK_DELETE);
        put("SUPR", VK_INSERT);
        put("ESC", VK_ESCAPE);
        put("AV_PAG", VK_PAGE_UP);
        put("RE_PAG", VK_PAGE_DOWN);

        put("UP", VK_UP);
        put("DOWN", VK_DOWN);
        put("LEFT", VK_LEFT);
        put("RIGHT", VK_RIGHT);
        put("INICIO", VK_HOME);
        put("END", VK_END);


        put("F01", VK_F1);
        put("F02", VK_F2);
        put("F03", VK_F3);
        put("F04", VK_F4);
        put("F05", VK_F5);
        put("F06", VK_F6);
        put("F07", VK_F7);
        put("F08", VK_F8);
        put("F09", VK_F9);
        put("F10", VK_F10);
        put("F11", VK_F11);
        put("F12", VK_F12);

        put("TAB", "\t");
        put("ESPACIO", " ");
        put("PUNTOCOMA", ";");
        put("IGUAL", "=");
        put("MAS", "+");
        put("ASTERISCO", "*");
        put("ANGIZQUIERDO", "<");
        put("COMILLAS", "\"");
        put("ABRIRPARENTESIS", "(");
        put("CERRARPARENTESIS", ")");
        put("ABRIRLLAVE", "{");
        put("CERRARLLAVE", "}");
        put("ABRIRCORCHETE", "[");
        put("CERRARCORCHETE", "]");
        put("CERRARINTERROGACION", "?");
        put("ABRIRINTERROGACION", "¿");
        put("BARRAVERTICAL", "|");
        put("ANGDERECHO", ">");
    }
}
