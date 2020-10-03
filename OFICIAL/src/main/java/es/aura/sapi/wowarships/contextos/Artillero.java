package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Artillero extends GrupoAcciones {

    boolean marcando = false;

    enum TipoDisparo {
        UNITARIO, SECUENCIAL, SALVA;
    }

    enum TipoArma {
        OBUS, TORPEDO, CARGAS
    }
    TipoArma arma = TipoArma.OBUS;
    TipoDisparo tipoDisparo = TipoDisparo.SALVA;

    public Artillero(Sapi sapi) {
        super(sapi);

        put("secuencial", (comando) -> {
            tipoDisparo = TipoDisparo.SECUENCIAL;
            arma = TipoArma.OBUS;
            return null;
        });

        put("salva", (comando) -> {
            arma = TipoArma.OBUS;
            tipoDisparo = TipoDisparo.SALVA;
            return null;
        });

        put("unitario", (comando) -> {
            arma = TipoArma.OBUS;
            tipoDisparo = TipoDisparo.UNITARIO;
            return null;
        });

        put("fuego", (comando) -> {
            switch (arma) {
                case CARGAS:
                    sapi.procesa("g");
                    break;
                case TORPEDO:
                    sapi.procesa("CLICK_ABSOLUTO");
                    break;
                case OBUS:
                    switch (tipoDisparo) {
                        case UNITARIO:
                            sapi.procesa("CLICK_ABSOLUTO");
                            break;
                        case SECUENCIAL:
                            sapi.procesa("DRAG_ABSOLUTO");
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Artillero.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            sapi.procesa("MLEAVE");
                            break;
                        case SALVA:
                            sapi.procesa("D_CLICK_ABSOLUTO");
                            break;
                    }
                    break;
            }
            return null;
        });
        put("torpedos", (comando) -> {
            sapi.procesa("3");
            arma = TipoArma.TORPEDO;
            return null;
        });
        put("CARGAS", (comando) -> {            
            arma = TipoArma.CARGAS;
            return null;
        });
        put("explosivos", (comando) -> {
            sapi.procesa("1");
            arma = TipoArma.OBUS;
            return null;
        });
        put("penetrantes", (comando) -> {
            sapi.procesa("2");
            arma = TipoArma.OBUS;
            return null;
        });
        put("periscopio", (comando) -> {
            sapi.procesa("RSHIFT");
            return null;
        });
        put("ZOOMIN", (comando) -> {
            sapi.procesa("RUEDA+");
            return null;
        });
        put("ZOOMOUT", (comando) -> {
            sapi.procesa("RUEDA-");
            return null;
        });
        put("EXTERIOR", (comando) -> {
            sapi.procesa("RCLICK_ABSOLUTO");
            return null;
        });
        put("objetivo primario", (comando) -> {
            sapi.procesa("x");
            return null;
        });
        put("objetivo secundario", (comando) -> {
            sapi.procesa("KEEP CTRL");
            marcando = true;
            return null;
        });
        put("balance", (comando) -> {
            sapi.procesa("o");
            return null;
        });
        put("marca", (comando) -> {
            if (marcando) {
                marcando = false;
                sapi.procesa("CLICK LEAVE");
            }
            return null;
        });

    }

}
