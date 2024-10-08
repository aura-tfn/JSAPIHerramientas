
package es.aura.casa.sapi.test;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

/**
 * Created by Vellotis on 2.02.2016.
 */
public class User32Test
{
    public static void main( String[] args )
    {
        // Loop all windows
        User32.INSTANCE.EnumWindows(( hWnd, data ) -> {
            char[] name = new char[512];

            User32.INSTANCE.GetWindowText( hWnd, name, name.length );

            // Find window with title starting with downcase "keyb" string
            if ( Native.toString( name ).toLowerCase().startsWith( "keyb" ) )
            {
                // Bring the window to the front
                User32.INSTANCE.SetForegroundWindow( hWnd );

                // Prepare input reference
                WinUser.INPUT input = new WinUser.INPUT(  );

                input.type = new WinDef.DWORD( WinUser.INPUT.INPUT_KEYBOARD );
                input.input.setType("ki"); // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
                input.input.ki.wScan = new WinDef.WORD( 0 );
                input.input.ki.time = new WinDef.DWORD( 0 );
                input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR( 0 );

                // Press "a"
                input.input.ki.wVk = new WinDef.WORD( 'A' ); // 0x41
                input.input.ki.dwFlags = new WinDef.DWORD( 0 );  // keydown

                User32.INSTANCE.SendInput( new WinDef.DWORD( 1 ), 
                        ( WinUser.INPUT[] ) input.toArray( 1 ), input.size() );

                // Release "a"
                input.input.ki.wVk = new WinDef.WORD( 'A' ); // 0x41
                input.input.ki.dwFlags = new WinDef.DWORD( 2 );  // keyup

                User32.INSTANCE.SendInput( new WinDef.DWORD( 1 ), ( WinUser.INPUT[] ) input.toArray( 1 ), input.size() );

                return false; // Found
            }

            return true; // Keep searching
        }, null );
    }
}