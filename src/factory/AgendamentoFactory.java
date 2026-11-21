
package factory;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Agendamento;

public abstract class AgendamentoFactory {

    public abstract Agendamento criarAgendamento(
            int pacienteId,
            int fisioterapeutaId,
            LocalDate data,
            LocalTime horario
    );
}
