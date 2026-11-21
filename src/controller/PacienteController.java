
package controller;

import dao.PacienteDAO;
import java.util.List;
import model.Paciente;

public class PacienteController {

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    public boolean cadastrar(String nome, String cpf, String telefone) {

        if (!validarDados(nome, cpf, telefone)) {
            return false;
        }

        Paciente paciente = new Paciente(
                nome.trim(),
                cpf.trim(),
                telefone.trim()
        );

        return pacienteDAO.inserir(paciente);
    }

    public List<Paciente> listar() {
        return pacienteDAO.listar();
    }

    public boolean atualizar(int id, String nome,
                             String cpf, String telefone) {

        if (id <= 0 || !validarDados(nome, cpf, telefone)) {
            return false;
        }

        Paciente paciente = new Paciente(
                nome.trim(),
                cpf.trim(),
                telefone.trim()
        );

        paciente.setId(id);

        return pacienteDAO.atualizar(paciente);
    }

    public boolean excluir(int id) {

        if (id <= 0) {
            return false;
        }

        return pacienteDAO.excluir(id);
    }

    private boolean validarDados(String nome, String cpf,
                                 String telefone) {

        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        if (cpf == null || !cpf.trim().matches("\\d{11}")) {
            return false;
        }

        if (telefone == null || telefone.trim().isEmpty()) {
            return false;
        }

        return true;
    }
}
