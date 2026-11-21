
package view;

import controller.PacienteController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Paciente;

public class TelaCadastroPaciente extends JFrame {

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;

    private JButton btnSalvar;
    private JButton btnConsultar;
    private JButton btnEditar;
    private JButton btnExcluir;

    private final PacienteController controller =
            new PacienteController();

    public TelaCadastroPaciente() {

        setTitle("FisioGestao - Cadastro de Pacientes");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        add(txtCpf);

        add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        add(txtTelefone);

        btnSalvar = new JButton("Salvar");
        btnConsultar = new JButton("Consultar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");

        add(btnSalvar);
        add(btnConsultar);
        add(btnEditar);
        add(btnExcluir);

        btnSalvar.addActionListener(e -> cadastrarPaciente());
        btnConsultar.addActionListener(e -> consultarPacientes());
        btnEditar.addActionListener(e -> editarPaciente());
        btnExcluir.addActionListener(e -> excluirPaciente());
    }

    private void cadastrarPaciente() {

        boolean sucesso = controller.cadastrar(
                txtNome.getText(),
                txtCpf.getText(),
                txtTelefone.getText()
        );

        if (sucesso) {
            JOptionPane.showMessageDialog(
                    this, "Paciente cadastrado com sucesso!"
            );

            limparCampos();

        } else {
            JOptionPane.showMessageDialog(
                    this, "Nao foi possivel cadastrar o paciente."
            );
        }
    }

    private void consultarPacientes() {

        List<Paciente> pacientes = controller.listar();

        StringBuilder mensagem = new StringBuilder();

        for (Paciente paciente : pacientes) {

            mensagem.append("ID: ")
                    .append(paciente.getId())
                    .append("\nNome: ")
                    .append(paciente.getNome())
                    .append("\nCPF: ")
                    .append(paciente.getCpf())
                    .append("\nTelefone: ")
                    .append(paciente.getTelefone())
                    .append("\n\n");
        }

        if (pacientes.isEmpty()) {
            mensagem.append("Nenhum paciente cadastrado.");
        }

        JTextArea area = new JTextArea(mensagem.toString());
        area.setEditable(false);

        JScrollPane painel = new JScrollPane(area);
        painel.setPreferredSize(new Dimension(350, 200));

        JOptionPane.showMessageDialog(
                this,
                painel,
                "Pacientes Cadastrados",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void editarPaciente() {

        String textoId = JOptionPane.showInputDialog(
                this, "Digite o ID do paciente:"
        );

        if (textoId == null) {
            return;
        }

        try {
            int id = Integer.parseInt(textoId.trim());

            Paciente encontrado = null;

            for (Paciente paciente : controller.listar()) {
                if (paciente.getId() == id) {
                    encontrado = paciente;
                    break;
                }
            }

            if (encontrado == null) {
                JOptionPane.showMessageDialog(
                        this, "Paciente nao encontrado."
                );
                return;
            }

            String nome = JOptionPane.showInputDialog(
                    this, "Nome:", encontrado.getNome()
            );

            if (nome == null) {
                return;
            }

            String cpf = JOptionPane.showInputDialog(
                    this, "CPF:", encontrado.getCpf()
            );

            if (cpf == null) {
                return;
            }

            String telefone = JOptionPane.showInputDialog(
                    this, "Telefone:", encontrado.getTelefone()
            );

            if (telefone == null) {
                return;
            }

            boolean sucesso = controller.atualizar(
                    id, nome, cpf, telefone
            );

            if (sucesso) {
                JOptionPane.showMessageDialog(
                        this, "Paciente atualizado com sucesso!"
                );
            } else {
                JOptionPane.showMessageDialog(
                        this, "Nao foi possivel atualizar o paciente."
                );
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Digite um ID valido."
            );
        }
    }

    private void excluirPaciente() {

        String textoId = JOptionPane.showInputDialog(
                this, "Digite o ID do paciente:"
        );

        if (textoId == null) {
            return;
        }

        try {
            int id = Integer.parseInt(textoId.trim());

            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir o paciente ID " + id + "?",
                    "Confirmar Exclusao",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao != JOptionPane.YES_OPTION) {
                return;
            }

            boolean sucesso = controller.excluir(id);

            if (sucesso) {
                JOptionPane.showMessageDialog(
                        this, "Paciente excluido com sucesso!"
                );
            } else {
                JOptionPane.showMessageDialog(
                        this, "Paciente nao encontrado ou erro ao excluir."
                );
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Digite um ID valido."
            );
        }
    }

    private void limparCampos() {

        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
    }
}
