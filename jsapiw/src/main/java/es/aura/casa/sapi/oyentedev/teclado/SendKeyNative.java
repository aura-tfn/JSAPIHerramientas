package es.aura.casa.sapi.oyentedev.teclado;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendKeyNative {

    private final WinUser.INPUT input;
    public  static final int L_SHIFT = 160;
    public  static final int R_SHIFT = 161;
    
    public  static final int L_CTRL = 162;
    public  static final int R_CTRL = 163;
   public  static final int ESC = 0x18;

    public SendKeyNative() {
        // Prepare input reference
        input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki"); // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(10000);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
    }

    
    public void keydown(int tecla) {
        input.input.ki.wVk = new WinDef.WORD(tecla);
        input.input.ki.dwFlags = new WinDef.DWORD(0);  // keydown
        User32.INSTANCE.SendInput(new WinDef.DWORD(1),
                (WinUser.INPUT[]) input.toArray(1), input.size());
    }
    
    public void keyType(int tecla) {
        keydown(tecla);
        keyup(tecla);
    }

    public void keyup(int tecla) {
        input.input.ki.wVk = new WinDef.WORD(tecla);
        input.input.ki.dwFlags = new WinDef.DWORD(2);  // keyup
        User32.INSTANCE.SendInput(new WinDef.DWORD(1),
                (WinUser.INPUT[]) input.toArray(1), input.size());
    }
    
    public static void main(String[] args) {
        SendKeyNative kn = new SendKeyNative();
        kn.keydown('A');
        try {
            Thread.sleep(9000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SendKeyNative.class.getName()).log(Level.SEVERE, null, ex);
        }
        kn.keyup('A');
   }
}
