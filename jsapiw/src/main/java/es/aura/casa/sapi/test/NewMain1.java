/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aura.casa.sapi.test;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

public class NewMain1 {
    public static void main(String[] args) {
        // Get the locking state of the Caps Lock button. If it is "on" this method
        // return boolean true value.
        boolean isOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
        System.out.println("CapsLock button is " + (isOn ? "on" : "off"));
    }
}
