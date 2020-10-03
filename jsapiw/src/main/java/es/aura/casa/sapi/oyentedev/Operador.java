/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aura.casa.sapi.oyentedev;

import es.aura.casa.sapi.oyentedev.mouse.Mouse;
import es.aura.casa.sapi.oyentedev.teclado.Teclado;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.logging.Logger;

public class Operador {

    private static final Logger LOG = Logger.getLogger(Operador.class.getName());

    private Robot robot;
    private Mouse mouse;
    private Teclado teclado;

    public Operador() throws AWTException {
        this.robot = new Robot();
        init();
    }

    public Operador(Robot robot) {
        this.robot = robot;
        init();
    }

    final void init() {
        robot.setAutoDelay(30);
        mouse = new Mouse(robot);
        teclado = new Teclado(robot);
    }

    public void procesa(CharSequence characters) {
        String fr = characters.toString().trim();
        LOG.info(fr);
        String[] comandos = fr.split(" ");
        for (String comando : comandos) {
            comando = teclado.pulsa(comando);
            comando = mouse.mueve(comando);
            teclado.escribe(comando);
        }
    }
}
