
package es.aura.casa.sapi.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;


public class Detector extends JFrame implements KeyListener{
    public Detector(){
        super("Detector");
        this.addKeyListener(this);
        this.setVisible(true);
    }
    
    
    
    public static void main(String[] args){
        new Detector();
    }
    
    public void keyReleased(KeyEvent e){
    }
     
    public void keyPressed(KeyEvent e){
        int teclaPresionada=e.getKeyCode();
        System.out.println("Tecla Presionada: code: "+teclaPresionada+
         " char:"+ e.getKeyChar());    
    } 
     
    public void keyTyped(KeyEvent e){
    }
}     

