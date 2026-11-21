
package observer;

import java.util.ArrayList;
import java.util.List;
import model.Agendamento;

public class GerenciadorAgendamento {

    private final List<AgendamentoObserver> observadores =
            new ArrayList<>();

    public void adicionarObserver(AgendamentoObserver observer) {
        observadores.add(observer);
    }

    public void removerObserver(AgendamentoObserver observer) {
        observadores.remove(observer);
    }

    public void notificarObservers(String evento,
                                  Agendamento agendamento) {

        for (AgendamentoObserver observer : observadores) {
            observer.atualizar(evento, agendamento);
        }
    }
}
