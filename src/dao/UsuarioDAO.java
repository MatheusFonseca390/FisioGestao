
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;
import util.Conexao;
import util.Funcoes;

public class UsuarioDAO {

    public boolean autenticar(Usuario usuario) {

        String sql = "SELECT id FROM usuarios " +
                     "WHERE login = ? AND senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getLogin());
            stmt.setString(
                    2,
                    Funcoes.gerarHashSHA256(usuario.getSenha())
            );

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Erro ao consultar o banco de dados", e
            );
        }
    }
}
