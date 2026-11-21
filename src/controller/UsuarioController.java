
package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        usuarioDAO = new UsuarioDAO();
    }

    public boolean autenticar(String login, String senha) {

        if (login == null || login.trim().isEmpty()) {
            return false;
        }

        if (senha == null || senha.isEmpty()) {
            return false;
        }

        Usuario usuario = new Usuario(login, senha);

        return usuarioDAO.autenticar(usuario);
    }
}
