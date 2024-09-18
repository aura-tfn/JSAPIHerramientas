package es.aura.casa.sapi.oyentedev.mouse;

import es.aura.casa.sapi.oyentedev.Operador;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
import static java.awt.event.InputEvent.BUTTON2_DOWN_MASK;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Mouse {

    private boolean arrastre = false;
    private boolean arrastre_d = false;
    private Robot robot;

    Runnable clickAbs = () -> {
        MouseNative.mouseLeftClick(-1, -1);
    };
    Runnable click = () -> {
        Point p = MouseInfo.getPointerInfo().getLocation();
        robot.mousePress(BUTTON1_DOWN_MASK);
        robot.mouseRelease(BUTTON1_DOWN_MASK);
    };
    private final Map<String, Runnable> mouseActions = new TreeMap<String, Runnable>() {
        {
            put("CLICK_ABSOLUTO", clickAbs);
            put("DRAG_ABSOLUTO", () -> {
                MouseNative.mouseAction(-1, -1, MouseNative.MOUSEEVENTF_LEFTDOWN);
                arrastre = true;
            });
            put("RCLICK_ABSOLUTO", () -> {
                MouseNative.mouseRightClick(-1, -1);
            });
            put("D_RCLICK_ABSOLUTO", () -> {
                MouseNative.mouseRightClick(-1, -1);
                espera(100);
                MouseNative.mouseRightClick(-1, -1);
            });
            put("D_CLICK_ABSOLUTO", () -> {
                clickAbs.run();
                espera(100);
                clickAbs.run();
            });
            put("CLICK", click);
            put("D_CLICK", () -> {
                click.run();
                espera(100);
                click.run();
            });
            put("T_CLICK", () -> {
                click.run();
                espera(100);
                click.run();
                espera(100);
                click.run();
            });
            put("R_CLICK", () -> {
                robot.mousePress(BUTTON2_DOWN_MASK);
                espera(10);
                robot.mouseRelease(BUTTON2_DOWN_MASK);
            });
            put("L_CLICK_DRAG", () -> {
                robot.mousePress(BUTTON1_DOWN_MASK);
                arrastre = true;
            });
            put("R_CLICK_DRAG", () -> {
                robot.mousePress(BUTTON2_DOWN_MASK);
                arrastre_d = true;
            });
            put("MLEAVE", () -> {
                if (arrastre) {
                    MouseNative.mouseAction(-1, -1, MouseNative.MOUSEEVENTF_LEFTUP);
                    robot.mouseRelease(BUTTON1_DOWN_MASK);
                    arrastre = false;
                } else if (arrastre_d) {
                    robot.mouseRelease(BUTTON2_DOWN_MASK);
                    arrastre_d = false;
                }
            });
            put("RUEDA+", () -> {
                MouseNative.mouseWheel(3);
            });
            put("RUEDA-", () -> {
                MouseNative.mouseWheel(-3);
            });
        }
    };

    public String mueve(String palabra) {
        if (palabra.isEmpty()) {
            return palabra;

        }
        String resultado = palabra;
        if (mouseActions.containsKey(palabra)) {
            Runnable r = mouseActions.get(palabra);
            r.run();
            resultado = "";
        }
        return resultado;
    }

    public Mouse(Robot robot) {
        this.robot = robot;
    }

    public Map<String, Runnable> getMouseActions() {
        return mouseActions;
    }

    private void espera(long tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Operador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {
            Mouse m = new Mouse(new Robot());
            m.espera(1000);
            m.clickAbs.run();
            m.getMouseActions().get("RUEDA+").run();
        } catch (AWTException ex) {
            Logger.getLogger(Mouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
