
package factory;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Agendamento;

public class SessaoFactory extends AgendamentoFactory {

    @Override
    public Agendamento criarAgendamento(
            int pacienteId,
            int fisioterapeutaId,
            LocalDate data,
            LocalTime horario) {

        Agendamento agendamento = new Agendamento(
                pacienteId,
                fisioterapeutaId,
                data,
                horario
        );

        agendamento.setTipo("SESSAO");

        return agendamento;
    }
}
