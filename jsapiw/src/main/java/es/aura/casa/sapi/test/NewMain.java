/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aura.casa.sapi.test;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 *
 * @author Usuario1
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                // Prepare input reference
                WinUser.INPUT input = new WinUser.INPUT(  );

                input.type = new WinDef.DWORD( WinUser.INPUT.INPUT_KEYBOARD );
                input.input.setType("ki"); // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
                input.input.ki.wScan = new WinDef.WORD( 0 );
                input.input.ki.time = new WinDef.DWORD( 0 );
                input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR( 0 );

                // Press "SH"
                input.input.ki.wVk = new WinDef.WORD( 161 ); // 0x41
                input.input.ki.dwFlags = new WinDef.DWORD( 0 );  // keydown

                User32.INSTANCE.SendInput( new WinDef.DWORD( 1 ), 
                        ( WinUser.INPUT[] ) input.toArray( 1 ), input.size() );
                // Press "a"
                input.input.ki.wVk = new WinDef.WORD( 'A' ); // 0x41
                input.input.ki.dwFlags = new WinDef.DWORD( 0 );  // keydown

                User32.INSTANCE.SendInput( new WinDef.DWORD( 1 ), 
                        ( WinUser.INPUT[] ) input.toArray( 1 ), input.size() );

                // Release "a"
                input.input.ki.wVk = new WinDef.WORD( 'A' ); // 0x41
                input.input.ki.dwFlags = new WinDef.DWORD( 2 );  // keyup

                User32.INSTANCE.SendInput( new WinDef.DWORD( 1 ), ( WinUser.INPUT[] ) input.toArray( 1 ), input.size() );
                // Release "SH"
                input.input.ki.wVk = new WinDef.WORD( 161 ); // 0x41
                input.input.ki.dwFlags = new WinDef.DWORD( 2 );  // keyup

                User32.INSTANCE.SendInput( new WinDef.DWORD( 1 ), ( WinUser.INPUT[] ) input.toArray( 1 ), input.size() );
    }
    
}
