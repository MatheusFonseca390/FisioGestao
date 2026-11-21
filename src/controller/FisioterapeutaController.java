
package controller;

import dao.FisioterapeutaDAO;
import java.util.List;
import model.Fisioterapeuta;

public class FisioterapeutaController {

    private final FisioterapeutaDAO fisioterapeutaDAO =
            new FisioterapeutaDAO();

    public boolean cadastrar(String nome, String crefito,
                             String especialidade) {

        if (!validarDados(nome, crefito, especialidade)) {
            return false;
        }

        Fisioterapeuta fisioterapeuta = new Fisioterapeuta(
                nome.trim(),
                crefito.trim(),
                especialidade.trim()
        );

        return fisioterapeutaDAO.inserir(fisioterapeuta);
    }

    public List<Fisioterapeuta> listar() {
        return fisioterapeutaDAO.listar();
    }

    public boolean atualizar(int id, String nome,
                             String crefito, String especialidade) {

        if (id <= 0 || !validarDados(nome, crefito, especialidade)) {
            return false;
        }

        Fisioterapeuta fisioterapeuta = new Fisioterapeuta(
                nome.trim(),
                crefito.trim(),
                especialidade.trim()
        );

        fisioterapeuta.setId(id);

        return fisioterapeutaDAO.atualizar(fisioterapeuta);
    }

    public boolean excluir(int id) {

        if (id <= 0) {
            return false;
        }

        return fisioterapeutaDAO.excluir(id);
    }

    private boolean validarDados(String nome, String crefito,
                                 String especialidade) {

        return nome != null && !nome.trim().isEmpty()
                && crefito != null && !crefito.trim().isEmpty()
                && especialidade != null
                && !especialidade.trim().isEmpty();
    }
}
