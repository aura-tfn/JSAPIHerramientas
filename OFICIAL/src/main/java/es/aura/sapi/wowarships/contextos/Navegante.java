package es.aura.sapi.wowarships.contextos;

import es.aura.casa.sapi.oyentedev.GrupoAcciones;
import es.aura.casa.sapi.oyentedev.Sapi;
import es.aura.sapi.wowarships.TipoPotenciaMotor;
import es.aura.sapi.wowarships.TipoRumbo;
import es.aura.sapi.wowarships.contextos.barco.Barco;

public class Navegante extends GrupoAcciones {

    private TipoPotenciaMotor potenciaMotor = TipoPotenciaMotor.STOP;
    private TipoRumbo rumbo = TipoRumbo.RECTO;


    public Navegante(Sapi sapi, Barco barco) {
        super(sapi);

        put("1/4", (comando) -> {
            mueve(TipoPotenciaMotor.UN_CUARTO);
            return null;
        });
        put("1/2", (comando) -> {
            mueve(TipoPotenciaMotor.MEDIA);
            return null;
        });
        put("3/4", (comando) -> {
            mueve(TipoPotenciaMotor.TRES_CUARTOS);
            return null;
        });
        put("FULL", (comando) -> {
            mueve(TipoPotenciaMotor.TODA);
            return null;
        });
        put("STOP", (comando) -> {
            mueve(TipoPotenciaMotor.STOP);
            return null;
        });
        put("ATRAS", (comando) -> {
            mueve(TipoPotenciaMotor.ATRAS_TODA);
            return null;
        });
        put("todo a babor", (comando) -> {
            gira(TipoRumbo.TODO_BABOR);
            return null;
        });
        put("todo a estribor", (comando) -> {
            gira(TipoRumbo.TODO_ESTRIBOR);
            return null;
        });
        put("media a babor", (comando) -> {
            gira(TipoRumbo.MEDIA_BABOR);
            return null;
        });
        put("media a estribor", (comando) -> {
            gira(TipoRumbo.MEDIA_ESTRIBOR);
            return null;
        });
        put("a proa", (comando) -> {
            gira(TipoRumbo.RECTO);
            return null;
        });

        put("FINBATALLA", (comando) -> {
            sapi.di("Volviendo al puerto");
            barco.atracar();
            sapi.procesa("ESC");
            sapi.activaRegla("inicio", "warships.puerto");
            return null;
        });

    }

    private void mueve(TipoPotenciaMotor nuevo) {
        int orden = nuevo.compareTo(potenciaMotor);
        potenciaMotor = nuevo;
        if (orden > 0) {
            for (int i = 0; i < orden; i++) {
                getSapi().procesa("w");
            }
        } else {
            for (int i = 0; i < -orden; i++) {
                getSapi().procesa("s");
            }
        }
    }

    private void gira(TipoRumbo nuevo) {
        int orden = nuevo.compareTo(rumbo);
        rumbo = nuevo;
        if (orden > 0) {
            for (int i = 0; i < orden; i++) {
                getSapi().procesa("e");
            }
        } else {
            for (int i = 0; i < -orden; i++) {
                getSapi().procesa("q");
            }
        }
    }

}
