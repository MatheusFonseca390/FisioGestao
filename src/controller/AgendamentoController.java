
package controller;

import dao.AgendamentoDAO;
import factory.AgendamentoFactory;
import factory.AvaliacaoFactory;
import factory.SessaoFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import model.Agendamento;
import observer.GerenciadorAgendamento;
import observer.NotificacaoAgendamento;

public class AgendamentoController {

    private final AgendamentoDAO agendamentoDAO =
            new AgendamentoDAO();

    private final GerenciadorAgendamento gerenciador =
            new GerenciadorAgendamento();

    public AgendamentoController() {
        gerenciador.adicionarObserver(
                new NotificacaoAgendamento()
        );
    }

    private AgendamentoFactory escolherFactory(String tipo) {

        if ("AVALIACAO".equalsIgnoreCase(tipo)) {
            return new AvaliacaoFactory();
        }

        if ("SESSAO".equalsIgnoreCase(tipo)) {
            return new SessaoFactory();
        }

        return null;
    }

    public boolean cadastrar(int pacienteId, int fisioterapeutaId,
                             String data, String horario) {

        return cadastrar(pacienteId, fisioterapeutaId,
                data, horario, "SESSAO");
    }

    public boolean cadastrar(int pacienteId, int fisioterapeutaId,
                             String data, String horario, String tipo) {

        if (pacienteId <= 0 || fisioterapeutaId <= 0) {
            return false;
        }

        AgendamentoFactory factory = escolherFactory(tipo);

        if (factory == null) {
            return false;
        }

        try {
            LocalDate dataSessao = LocalDate.parse(data);
            LocalTime horaSessao = LocalTime.parse(horario);

            Agendamento agendamento = factory.criarAgendamento(
                    pacienteId,
                    fisioterapeutaId,
                    dataSessao,
                    horaSessao
            );

            boolean sucesso = agendamentoDAO.inserir(agendamento);

            if (sucesso) {
                gerenciador.notificarObservers(
                        "AGENDAMENTO CADASTRADO",
                        agendamento
                );
            }

            return sucesso;

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

        if (id <= 0) {
            return false;
        }

        for (Agendamento agendamento : listar()) {
            if (agendamento.getId() == id) {
                return atualizar(id, pacienteId, fisioterapeutaId,
                        data, horario, agendamento.getTipo());
            }
        }

        return false;
    }

    public boolean atualizar(int id, int pacienteId,
                             int fisioterapeutaId,
                             String data, String horario, String tipo) {

        if (id <= 0 || pacienteId <= 0 || fisioterapeutaId <= 0) {
            return false;
        }

        AgendamentoFactory factory = escolherFactory(tipo);

        if (factory == null) {
            return false;
        }

        try {
            LocalDate dataSessao = LocalDate.parse(data);
            LocalTime horaSessao = LocalTime.parse(horario);

            Agendamento agendamento = factory.criarAgendamento(
                    pacienteId,
                    fisioterapeutaId,
                    dataSessao,
                    horaSessao
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

        Agendamento encontrado = null;

        for (Agendamento agendamento : listar()) {
            if (agendamento.getId() == id) {
                encontrado = agendamento;
                break;
            }
        }

        if (encontrado == null) {
            return false;
        }

        boolean sucesso = agendamentoDAO.cancelar(id);

        if (sucesso) {
            encontrado.setStatus("CANCELADO");

            gerenciador.notificarObservers(
                    "AGENDAMENTO CANCELADO",
                    encontrado
            );
        }

        return sucesso;
    }
}
