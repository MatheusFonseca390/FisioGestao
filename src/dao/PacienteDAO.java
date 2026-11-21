
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Paciente;
import util.Conexao;

public class PacienteDAO {

    public boolean inserir(Paciente paciente) {

        String sql = "INSERT INTO pacientes " +
                     "(nome, cpf, telefone) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getTelefone());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar paciente: "
                    + e.getMessage());
            return false;
        }
    }

    public List<Paciente> listar() {

        List<Paciente> pacientes = new ArrayList<>();

        String sql = "SELECT * FROM pacientes ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Paciente paciente = new Paciente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone")
                );

                paciente.setId(rs.getInt("id"));

                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pacientes: "
                    + e.getMessage());
        }

        return pacientes;
    }

    public boolean atualizar(Paciente paciente) {

        String sql = "UPDATE pacientes SET nome = ?, " +
                     "cpf = ?, telefone = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getTelefone());
            stmt.setInt(4, paciente.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar paciente: "
                    + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {

        String sql = "DELETE FROM pacientes WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir paciente: "
                    + e.getMessage());
            return false;
        }
    }
}
