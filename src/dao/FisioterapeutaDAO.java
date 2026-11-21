
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Fisioterapeuta;
import util.Conexao;

public class FisioterapeutaDAO {

    public boolean inserir(Fisioterapeuta fisioterapeuta) {

        String sql = "INSERT INTO fisioterapeutas " +
                     "(nome, crefito, especialidade) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fisioterapeuta.getNome());
            stmt.setString(2, fisioterapeuta.getCrefito());
            stmt.setString(3, fisioterapeuta.getEspecialidade());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar fisioterapeuta: "
                    + e.getMessage());
            return false;
        }
    }

    public List<Fisioterapeuta> listar() {

        List<Fisioterapeuta> fisioterapeutas = new ArrayList<>();

        String sql = "SELECT * FROM fisioterapeutas ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Fisioterapeuta fisioterapeuta = new Fisioterapeuta(
                        rs.getString("nome"),
                        rs.getString("crefito"),
                        rs.getString("especialidade")
                );

                fisioterapeuta.setId(rs.getInt("id"));
                fisioterapeutas.add(fisioterapeuta);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar fisioterapeutas: "
                    + e.getMessage());
        }

        return fisioterapeutas;
    }

    public boolean atualizar(Fisioterapeuta fisioterapeuta) {

        String sql = "UPDATE fisioterapeutas SET nome = ?, " +
                     "crefito = ?, especialidade = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fisioterapeuta.getNome());
            stmt.setString(2, fisioterapeuta.getCrefito());
            stmt.setString(3, fisioterapeuta.getEspecialidade());
            stmt.setInt(4, fisioterapeuta.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar fisioterapeuta: "
                    + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {

        String sql = "DELETE FROM fisioterapeutas WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir fisioterapeuta: "
                    + e.getMessage());
            return false;
        }
    }
}
