
package es.aura.sapi.wowarships;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;


public class WindowHandler extends Handler {
  private LogWindow window = null;
  private static WindowHandler handler = null;

  private WindowHandler() {
    setLevel(Level.ALL);
      window = new LogWindow();
      setFormatter(new SimpleFormatter());
  }

  public static synchronized WindowHandler getInstance() {
    if (handler == null) {
      handler = new WindowHandler();
    }
    return handler;
  }

  @Override
  public synchronized void publish(LogRecord record) {
    String message = getFormatter().format(record);
    window.showInfo(message);
  }

  @Override
  public void close() {
  }

  @Override
  public void flush() {
  }
}
