package es.aura.casa.sapi.oyentedev.teclado;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HoldKey implements Runnable {

    boolean end = false;
    int tecla = 0;
    Robot robot;
    SendKeyNative kn = new SendKeyNative();

    public HoldKey(int tecla, Robot robot) {
        this.tecla = tecla;
        this.robot = robot;
        robot.setAutoDelay(10);

    }

    public void setEnd(boolean end) {
        this.end = end;
    }


    @Override
    public void run() {
        while (!end) {
            try {
                kn.keydown(tecla);
                Thread.sleep(200);
                kn.keyup(tecla);
            } catch (InterruptedException ex) {
                Logger.getLogger(HoldKey.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        try {
            HoldKey h = new HoldKey(KeyEvent.VK_0, new Robot());
            ExecutorService exec = Executors.newSingleThreadExecutor();
            exec.submit(h);
            Thread.sleep(3000);
            h.setEnd(true);
            exec.shutdown();
        } catch (AWTException ex) {
            Logger.getLogger(HoldKey.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(HoldKey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
