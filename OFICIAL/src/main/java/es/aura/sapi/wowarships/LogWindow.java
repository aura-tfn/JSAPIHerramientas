
package es.aura.sapi.wowarships;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;


public class LogWindow extends JFrame {
  private JTextArea textArea = new JTextArea();
  
  JScrollPane pane;
  public LogWindow() {
    super("");
    textArea.setBackground(Color.BLACK);
    textArea.setForeground(Color.red);
    Font f = new Font("Comic Sans", Font.BOLD,14);
    textArea.setFont(f);
    setSize(800, 800);
    pane = new JScrollPane(textArea);
    add(pane);
    setVisible(true);
  }

  public void showInfo(String data) {
    textArea.append(data);
    textArea.setCaretPosition(textArea.getDocument().getLength() );
                    textArea.requestFocusInWindow();
    this.validate();
  }
}
