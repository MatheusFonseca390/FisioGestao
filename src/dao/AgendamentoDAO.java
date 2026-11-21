
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Agendamento;
import util.Conexao;

public class AgendamentoDAO {

    public boolean inserir(Agendamento agendamento) {

        String sql = "INSERT INTO agendamentos " +
                "(paciente_id, fisioterapeuta_id, data_sessao, horario, status, tipo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, agendamento.getPacienteId());
            stmt.setInt(2, agendamento.getFisioterapeutaId());
            stmt.setDate(3, Date.valueOf(agendamento.getDataSessao()));
            stmt.setTime(4, Time.valueOf(agendamento.getHorario()));
            stmt.setString(5, agendamento.getStatus());
            stmt.setString(6, agendamento.getTipo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar agendamento: "
                    + e.getMessage());
            return false;
        }
    }

    public List<Agendamento> listar() {

        List<Agendamento> agendamentos = new ArrayList<>();

        String sql = "SELECT * FROM agendamentos ORDER BY data_sessao, horario";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Agendamento agendamento = new Agendamento(
                        rs.getInt("paciente_id"),
                        rs.getInt("fisioterapeuta_id"),
                        rs.getDate("data_sessao").toLocalDate(),
                        rs.getTime("horario").toLocalTime()
                );

                agendamento.setId(rs.getInt("id"));
                agendamento.setStatus(rs.getString("status"));
                agendamento.setTipo(rs.getString("tipo"));

                agendamentos.add(agendamento);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar agendamentos: "
                    + e.getMessage());
        }

        return agendamentos;
    }

    public boolean atualizar(Agendamento agendamento) {

        String sql = "UPDATE agendamentos " +
                "SET paciente_id = ?, fisioterapeuta_id = ?, " +
                "data_sessao = ?, horario = ?, tipo = ? " +
                "WHERE id = ? AND status = 'AGENDADO'";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, agendamento.getPacienteId());
            stmt.setInt(2, agendamento.getFisioterapeutaId());
            stmt.setDate(3, Date.valueOf(agendamento.getDataSessao()));
            stmt.setTime(4, Time.valueOf(agendamento.getHorario()));
            stmt.setString(5, agendamento.getTipo());
            stmt.setInt(6, agendamento.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar agendamento: "
                    + e.getMessage());
            return false;
        }
    }

    public boolean cancelar(int id) {

        String sql = "UPDATE agendamentos SET status = 'CANCELADO' " +
                "WHERE id = ? AND status = 'AGENDADO'";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cancelar agendamento: "
                    + e.getMessage());
            return false;
        }
    }
}
