package es.aura.casa.sapi.oyentedev.teclado;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_0;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_9;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_ALT;
import static java.awt.event.KeyEvent.VK_ALT_GRAPH;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_COMMA;
import static java.awt.event.KeyEvent.VK_CONTROL;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DEAD_ACUTE;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_I;
import static java.awt.event.KeyEvent.VK_INVERTED_EXCLAMATION_MARK;
import static java.awt.event.KeyEvent.VK_J;
import static java.awt.event.KeyEvent.VK_K;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_LESS;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_MINUS;
import static java.awt.event.KeyEvent.VK_N;
import static java.awt.event.KeyEvent.VK_NUMPAD0;
import static java.awt.event.KeyEvent.VK_NUMPAD1;
import static java.awt.event.KeyEvent.VK_NUMPAD2;
import static java.awt.event.KeyEvent.VK_NUMPAD3;
import static java.awt.event.KeyEvent.VK_NUMPAD4;
import static java.awt.event.KeyEvent.VK_NUMPAD5;
import static java.awt.event.KeyEvent.VK_NUMPAD6;
import static java.awt.event.KeyEvent.VK_NUMPAD7;
import static java.awt.event.KeyEvent.VK_NUMPAD8;
import static java.awt.event.KeyEvent.VK_NUMPAD9;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_PERIOD;
import static java.awt.event.KeyEvent.VK_PLUS;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_QUOTE;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_T;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.awt.event.KeyEvent.VK_U;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;
import static java.awt.event.KeyEvent.VK_Z;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Teclado {

    private static final String CTRL = "CTRL";
    private static final String LCTRL = "LCTRL";
    private static final String RCTRL = "RCTRL";
    private static final String MAY = "SHIFT";
    private static final String LMAY = "LSHIFT";
    private static final String RMAY = "RSHIFT";
    private static final String ALT = "ALT";
    private static final String ALTGR = "ALTGR";
    private static final String ESC = "ESC";

    private static final String MANTENER = "KEEP";
    private static final String LIBERAR = "LEAVE";
    private final Map<String, Boolean> flags = new TreeMap<String, Boolean>() {
        {
            put(CTRL, false);
            put(MAY, false);
            put(ALT, false);
            put(ALTGR, false);
        }
    };
    private final Map<String, Integer> teclasCtrl = new TreeMap<String, Integer>() {
        {
            put(MAY, VK_SHIFT);
            put(ALT, VK_ALT);
            put(ALTGR, VK_ALT_GRAPH);
        }
    };
    private final Map<String, Integer> teclasEspCtrl = new TreeMap<String, Integer>() {
        {
            put(LCTRL, SendKeyNative.L_CTRL);
            put(CTRL, SendKeyNative.L_CTRL);
            put(RCTRL, SendKeyNative.R_CTRL);
            put(LMAY, SendKeyNative.L_SHIFT);
            put(MAY, SendKeyNative.L_SHIFT);
            put(RMAY, SendKeyNative.R_SHIFT);
            put(ESC , 0);
        }
    };
    private Robot robot;
    private final Teclas teclas;
    private boolean conmay = false;
    private boolean todomay = false;
    private boolean min = false;
    private boolean keep = false; //KEEP
    private final List<Integer> ultTeclas = new ArrayList<>();
    private final List<Integer> ultTeclasEsp = new ArrayList<>();
    private final SendKeyNative keyNative;
    public final ExecutorService exec = Executors.newCachedThreadPool();

    public String pulsa(String comando) {
        if (comando.isEmpty()) {
            return comando;
        }
        String resultado = comando.trim();
        if (comando.contentEquals(LIBERAR)) {
            flagsOff();
            ultTeclas.forEach(hk -> keyNative.keyup(hk));
            ultTeclas.clear();
            ultTeclasEsp.forEach(t -> keyNative.keyup(t));
            ultTeclasEsp.clear();
            return "";
        } else if (teclasEspCtrl.containsKey(comando)) {
            int tecla = teclasEspCtrl.get(comando);
            if (keep) {
                keyNative.keydown(tecla);
                ultTeclasEsp.add(tecla);
            } else {
                keyNative.keyType(tecla);
            }
            return "";
        } else if (comando.contentEquals(MANTENER)) {
            keep = true;
            return "";
        } else if (teclas.containsKey(comando)) {
            Object termino = teclas.get(comando);
            if (termino instanceof String) {
                resultado = resultado.concat((String) termino);
            } else if (termino instanceof Integer) {
                Integer tecla = (Integer) termino;
                robot.keyPress(tecla);
                try {
                    robot = new Robot();
                } catch (AWTException ex) {
                    Logger.getLogger(Teclado.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "";
            }
        }
        if (comando.contentEquals("TODOMAY")) {
            todomay = true;
        }
        if (comando.contentEquals("TODOMIN")) {
            min = true;
        }
        if (comando.contentEquals("CONMAY")) {
            conmay = true;
        }
        return resultado;
    }

    public void escribe(String palabra) {
        if (palabra.isEmpty()) {
            return;
        }

        if (conmay) {
            palabra = (palabra.length() > 1)
                    ? palabra.substring(0, 1).toUpperCase().concat(palabra.substring(1))
                    : palabra.toUpperCase();
            palabra.chars().forEach(c -> type((char) c));
            conmay = false;
        } else if (min) {
            palabra = palabra.toLowerCase();
            palabra.chars().forEach(c -> type((char) c));
            min = false;
        } else if (todomay) {
            Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
            palabra.chars().forEach(c -> type((char) c));
            Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
            todomay = false;
        } else {
            palabra.chars().forEach(c -> type((char) c));
        }
    }

    public void flagsOff() {
        flags.keySet().stream().filter((tecla) -> (flags.get(tecla))).forEachOrdered((tecla) -> {
            robot.keyRelease(teclasCtrl.get(tecla));
            flags.put(tecla, false);
        });
        keep = false;
        conmay = false;
    }

    public void type(char character) {
        switch (character) {
            case 'a':
                doType(VK_A);
                break;
            case 'b':
                doType(VK_B);
                break;
            case 'c':
                doType(VK_C);
                break;
            case 'd':
                doType(VK_D);
                break;
            case 'e':
                doType(VK_E);
                break;
            case 'f':
                doType(VK_F);
                break;
            case 'g':
                doType(VK_G);
                break;
            case 'h':
                doType(VK_H);
                break;
            case 'i':
                doType(VK_I);
                break;
            case 'j':
                doType(VK_J);
                break;
            case 'k':
                doType(VK_K);
                break;
            case 'l':
                doType(VK_L);
                break;
            case 'm':
                doType(VK_M);
                break;
            case 'n':
                doType(VK_N);
                break;
            case 'ñ':
                typeUnicode("0241");
                break;
            case 'o':
                doType(VK_O);
                break;
            case 'p':
                doType(VK_P);
                break;
            case 'q':
                doType(VK_Q);
                break;
            case 'r':
                doType(VK_R);
                break;
            case 's':
                doType(VK_S);
                break;
            case 't':
                doType(VK_T);
                break;
            case 'u':
                doType(VK_U);
                break;
            case 'v':
                doType(VK_V);
                break;
            case 'w':
                doType(VK_W);
                break;
            case 'x':
                doType(VK_X);
                break;
            case 'y':
                doType(VK_Y);
                break;
            case 'z':
                doType(VK_Z);
                break;
            case 'A':
                doType(VK_SHIFT, VK_A);
                break;
            case 'B':
                doType(VK_SHIFT, VK_B);
                break;
            case 'C':
                doType(VK_SHIFT, VK_C);
                break;
            case 'D':
                doType(VK_SHIFT, VK_D);
                break;
            case 'E':
                doType(VK_SHIFT, VK_E);
                break;
            case 'F':
                doType(VK_SHIFT, VK_F);
                break;
            case 'G':
                doType(VK_SHIFT, VK_G);
                break;
            case 'H':
                doType(VK_SHIFT, VK_H);
                break;
            case 'I':
                doType(VK_SHIFT, VK_I);
                break;
            case 'J':
                doType(VK_SHIFT, VK_J);
                break;
            case 'K':
                doType(VK_SHIFT, VK_K);
                break;
            case 'L':
                doType(VK_SHIFT, VK_L);
                break;
            case 'M':
                doType(VK_SHIFT, VK_M);
                break;
            case 'N':
                doType(VK_SHIFT, VK_N);
                break;
            case 'Ñ':
                typeUnicode("0209");
                break;
            case 'O':
                doType(VK_SHIFT, VK_O);
                break;
            case 'P':
                doType(VK_SHIFT, VK_P);
                break;
            case 'Q':
                doType(VK_SHIFT, VK_Q);
                break;
            case 'R':
                doType(VK_SHIFT, VK_R);
                break;
            case 'S':
                doType(VK_SHIFT, VK_S);
                break;
            case 'T':
                doType(VK_SHIFT, VK_T);
                break;
            case 'U':
                doType(VK_SHIFT, VK_U);
                break;
            case 'V':
                doType(VK_SHIFT, VK_V);
                break;
            case 'W':
                doType(VK_SHIFT, VK_W);
                break;
            case 'X':
                doType(VK_SHIFT, VK_X);
                break;
            case 'Y':
                doType(VK_SHIFT, VK_Y);
                break;
            case 'Z':
                doType(VK_SHIFT, VK_Z);
                break;
            case '0':
                doType(VK_0);
                break;
            case '1':
                doType(VK_1);
                break;
            case '2':
                doType(VK_2);
                break;
            case '3':
                doType(VK_3);
                break;
            case '4':
                doType(VK_4);
                break;
            case '5':
                doType(VK_5);
                break;
            case '6':
                doType(VK_6);
                break;
            case '7':
                doType(VK_7);
                break;
            case '8':
                doType(VK_8);
                break;
            case '9':
                doType(VK_9);
                break;
            case '-':
                doType(VK_MINUS);
                break;
            case '=':
                doType(VK_SHIFT, VK_0);
                break;
            case '~':
                doType(VK_CONTROL, VK_ALT, VK_4);
                break;
            case '!':
                doType(VK_SHIFT, VK_1);
                break;
            case '@':
                doType(VK_ALT, VK_CONTROL, VK_2);
                break;
            case '#':
                doType(VK_ALT, VK_CONTROL, VK_3);
                break;
            case '$':
                doType(VK_SHIFT, VK_4);
                break;
            case '%':
                doType(VK_SHIFT, VK_5);
                break;
            case '^':
                doType(VK_SHIFT, 128);
                doType(VK_SPACE);
                break;
            case '`':
                doType(128);
                break;
            case '´':
                doType(129);
                doType(VK_SPACE);
                break;
            case '¨':
                doType(VK_SHIFT, 129);
                doType(VK_SPACE);
                break;
            case '{':
                doType(VK_CONTROL, VK_ALT, VK_DEAD_ACUTE);
                break;
            case '}':
                typeUnicode("125");
                break;
            case '&':
                doType(VK_SHIFT, VK_6);
                break;
            case '*':
                doType(VK_SHIFT, VK_PLUS);
                break;
            case '(':
                doType(VK_SHIFT, VK_8);
                break;
            case ')':
                doType(VK_SHIFT, VK_9);
                break;
            case '_':
                doType(VK_SHIFT, VK_MINUS);
                break;
            case '+':
                doType(VK_PLUS);
                break;
            case '\t':
                doType(VK_TAB);
                break;
            case '\n':
                doType(VK_ENTER);
                break;
            case '[':
                doType(VK_ALT, VK_CONTROL, 128);
                break;
            case ']':
                doType(VK_ALT, VK_CONTROL, VK_PLUS);
                break;
            case '\\':
                typeUnicode("0092");
                break;
            case '|':
                doType(VK_CONTROL, VK_ALT, VK_1);
                break;
            case ';':
                doType(VK_SHIFT, VK_COMMA);
                break;
            case '\'':
                typeUnicode("0092");
                break;
            case '"':
                doType(VK_SHIFT, VK_2);
                break;
            case ',':
                doType(VK_COMMA);
                break;
            case '<':
                doType(VK_LESS);
                break;
            case '.':
                doType(VK_PERIOD);
                break;
            case ':':
                doType(VK_SHIFT, VK_PERIOD);
                break;
            case '>':
                doType(VK_SHIFT, VK_LESS);
                break;
            case '/':
                doType(VK_SHIFT, VK_7);
                break;
            case '?':
                doType(VK_SHIFT, VK_QUOTE);
                break;
            case '¿':
                doType(VK_SHIFT, VK_INVERTED_EXCLAMATION_MARK);
                break;
            case '¡':
                doType(VK_INVERTED_EXCLAMATION_MARK);
                break;
            case ' ':
                doType(VK_SPACE);
                break;
            case '·':
                doType(VK_SHIFT, VK_3);
                break;
            default:
                typeUnicode(character);
        }
    }

    public void doType(int... keyCodes) {
        doType(keyCodes, 0, keyCodes.length);
    }

    private void doType(int[] keyCodes, int offset, int length) {
        if (length == 0) {
            return;
        }
        try {
            if (keep) {
                keyNative.keydown(keyCodes[offset]);
                ultTeclas.add(keyCodes[offset]);
            } else {
                robot.keyPress(keyCodes[offset]);
                 robot.keyRelease(keyCodes[offset]);
               doType(keyCodes, offset + 1, length - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void typeUnicode(char character) {
        String unicodeDigits = String.valueOf(Character.getNumericValue(character));
        robot.keyPress(VK_ALT);
        for (int i = 0; i < unicodeDigits.length(); i++) {
            typeNumPad(Integer.parseInt(unicodeDigits.substring(i, i + 1)));
        }
        robot.keyRelease(VK_ALT);
    }

    public void typeUnicode(String unicodeDigits) {
        robot.keyPress(VK_ALT);
        for (int i = 0; i < unicodeDigits.length(); i++) {
            typeNumPad(Integer.parseInt(unicodeDigits.substring(i, i + 1)));
        }
        robot.keyRelease(VK_ALT);
    }

    private void typeNumPad(int digit) {
        switch (digit) {
            case 0:
                doType(VK_NUMPAD0);
                break;
            case 1:
                doType(VK_NUMPAD1);
                break;
            case 2:
                doType(VK_NUMPAD2);
                break;
            case 3:
                doType(VK_NUMPAD3);
                break;
            case 4:
                doType(VK_NUMPAD4);
                break;
            case 5:
                doType(VK_NUMPAD5);
                break;
            case 6:
                doType(VK_NUMPAD6);
                break;
            case 7:
                doType(VK_NUMPAD7);
                break;
            case 8:
                doType(VK_NUMPAD8);
                break;
            case 9:
                doType(VK_NUMPAD9);
                break;
        }
    }

    public Teclado(Robot robot) {
        this.teclas = new Teclas();
        this.robot = robot;
        keyNative = new SendKeyNative();
        conmay = false;
        Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);
    }

}
