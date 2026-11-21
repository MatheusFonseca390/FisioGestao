
package controller;

import dao.AgendamentoDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import model.Agendamento;

public class AgendamentoController {

    private final AgendamentoDAO agendamentoDAO =
            new AgendamentoDAO();

    public boolean cadastrar(int pacienteId, int fisioterapeutaId,
                             String data, String horario) {

        if (pacienteId <= 0 || fisioterapeutaId <= 0) {
            return false;
        }

        try {
            LocalDate dataSessao = LocalDate.parse(data);
            LocalTime horaSessao = LocalTime.parse(horario);

            Agendamento agendamento = new Agendamento(
                    pacienteId, fisioterapeutaId,
                    dataSessao, horaSessao
            );

            return agendamentoDAO.inserir(agendamento);

        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    public List<Agendamento> listar() {
        return agendamentoDAO.listar();
    }

    public boolean atualizar(int id, int pacienteId,
                             int fisioterapeutaId,
                             String data, String horario) {

        if (id <= 0 || pacienteId <= 0 || fisioterapeutaId <= 0) {
            return false;
        }

        try {
            LocalDate dataSessao = LocalDate.parse(data);
            LocalTime horaSessao = LocalTime.parse(horario);

            Agendamento agendamento = new Agendamento(
                    pacienteId, fisioterapeutaId,
                    dataSessao, horaSessao
            );

            agendamento.setId(id);

            return agendamentoDAO.atualizar(agendamento);

        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    public boolean cancelar(int id) {

        if (id <= 0) {
            return false;
        }

        return agendamentoDAO.cancelar(id);
    }
}
