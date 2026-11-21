
package observer;

import model.Agendamento;

public interface AgendamentoObserver {

    void atualizar(String evento, Agendamento agendamento);

}
