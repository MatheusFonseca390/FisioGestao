
package util;

public class ConfiguracaoSistema {

    private static ConfiguracaoSistema instancia;

    private String nomeSistema = "FisioGestao";

    private ConfiguracaoSistema() {
    }

    public static ConfiguracaoSistema getInstancia() {

        if (instancia == null) {
            instancia = new ConfiguracaoSistema();
        }

        return instancia;
    }

    public String getNomeSistema() {
        return nomeSistema;
    }
}
