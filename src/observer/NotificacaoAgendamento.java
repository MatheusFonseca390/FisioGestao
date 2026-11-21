
package observer;

import model.Agendamento;

public class NotificacaoAgendamento implements AgendamentoObserver {

    @Override
    public void atualizar(String evento, Agendamento agendamento) {

        System.out.println("=== NOTIFICACAO FISIOGESTAO ===");

        System.out.println("Evento: " + evento);

        System.out.println("Agendamento ID: "
                + agendamento.getId());

        System.out.println("Paciente ID: "
                + agendamento.getPacienteId());

        System.out.println("Tipo: "
                + agendamento.getTipo());

        System.out.println("Status: "
                + agendamento.getStatus());

        System.out.println("==============================");
    }
}
