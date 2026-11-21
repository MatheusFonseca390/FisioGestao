
package view;

import controller.AgendamentoController;
import controller.PacienteController;
import controller.FisioterapeutaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Agendamento;
import model.Paciente;
import model.Fisioterapeuta;

public class TelaAgendamento extends JFrame {

    private JComboBox<String> cbPaciente;
    private JComboBox<String> cbFisioterapeuta;
    private JComboBox<String> cbTipo;
    private JTextField txtData;
    private JTextField txtHorario;

    private final AgendamentoController controller =
            new AgendamentoController();

    private final PacienteController pacienteController =
            new PacienteController();

    private final FisioterapeutaController fisioterapeutaController =
            new FisioterapeutaController();

    public TelaAgendamento() {

        setTitle("FisioGestao - Agendamentos");
        setSize(520, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(7, 2, 10, 12));
        painel.setBackground(new Color(230, 240, 250));
        painel.setBorder(BorderFactory.createEmptyBorder(
                20, 25, 20, 25));

        painel.add(new JLabel("Paciente:"));
        cbPaciente = new JComboBox<>();
        painel.add(cbPaciente);

        painel.add(new JLabel("Fisioterapeuta:"));
        cbFisioterapeuta = new JComboBox<>();
        painel.add(cbFisioterapeuta);

        painel.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{
            "SESSAO", "AVALIACAO"
        });
        painel.add(cbTipo);

        painel.add(new JLabel("Data (AAAA-MM-DD):"));
        txtData = new JTextField();
        painel.add(txtData);

        painel.add(new JLabel("Horario (HH:MM):"));
        txtHorario = new JTextField();
        painel.add(txtHorario);

        JButton btnAgendar = new JButton("Agendar");
        JButton btnConsultar = new JButton("Consultar");
        JButton btnEditar = new JButton("Editar");
        JButton btnCancelar = new JButton("Cancelar");

        JButton[] botoes = {
            btnAgendar, btnConsultar, btnEditar, btnCancelar
        };

        for (JButton botao : botoes) {
            botao.setBackground(new Color(70, 130, 180));
            botao.setForeground(Color.WHITE);
            botao.setFont(new Font("Arial", Font.BOLD, 14));
            botao.setFocusPainted(false);
            botao.setOpaque(true);
        }

        btnCancelar.setBackground(new Color(200, 70, 70));

        painel.add(btnAgendar);
        painel.add(btnConsultar);
        painel.add(btnEditar);
        painel.add(btnCancelar);

        add(painel);

        carregarPessoas();

        btnAgendar.addActionListener(e -> agendar());
        btnConsultar.addActionListener(e -> consultar());
        btnEditar.addActionListener(e -> editar());
        btnCancelar.addActionListener(e -> cancelar());
    }

    private void carregarPessoas() {

        cbPaciente.removeAllItems();
        cbFisioterapeuta.removeAllItems();

        for (Paciente p : pacienteController.listar()) {
            cbPaciente.addItem(p.getId() + " - " + p.getNome());
        }

        for (Fisioterapeuta f : fisioterapeutaController.listar()) {
            cbFisioterapeuta.addItem(
                    f.getId() + " - " + f.getNome()
            );
        }
    }

    private int obterId(JComboBox<String> combo) {

        String selecionado = (String) combo.getSelectedItem();

        if (selecionado == null) {
            return -1;
        }

        return Integer.parseInt(selecionado.split(" - ")[0]);
    }

    private void agendar() {

        String tipo = (String) cbTipo.getSelectedItem();

        boolean sucesso = controller.cadastrar(
                obterId(cbPaciente),
                obterId(cbFisioterapeuta),
                txtData.getText(),
                txtHorario.getText(),
                tipo
        );

        JOptionPane.showMessageDialog(
                this,
                sucesso ? "Agendamento realizado!"
                        : "Nao foi possivel agendar."
        );
    }

    private void consultar() {

        List<Agendamento> lista = controller.listar();

        StringBuilder mensagem = new StringBuilder();

        for (Agendamento a : lista) {

            mensagem.append("ID: ").append(a.getId())
                    .append("\nPaciente ID: ")
                    .append(a.getPacienteId())
                    .append("\nFisioterapeuta ID: ")
                    .append(a.getFisioterapeutaId())
                    .append("\nTipo: ")
                    .append(a.getTipo())
                    .append("\nData: ")
                    .append(a.getDataSessao())
                    .append("\nHorario: ")
                    .append(a.getHorario())
                    .append("\nStatus: ")
                    .append(a.getStatus())
                    .append("\n\n");
        }

        if (lista.isEmpty()) {
            mensagem.append("Nenhum agendamento cadastrado.");
        }

        JTextArea area = new JTextArea(mensagem.toString());
        area.setEditable(false);

        JScrollPane painel = new JScrollPane(area);
        painel.setPreferredSize(new Dimension(400, 250));

        JOptionPane.showMessageDialog(
                this,
                painel,
                "Agendamentos",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void editar() {

        String textoId = JOptionPane.showInputDialog(
                this, "Digite o ID do agendamento:"
        );

        if (textoId == null) {
            return;
        }

        try {
            int id = Integer.parseInt(textoId.trim());

            Agendamento encontrado = null;

            for (Agendamento a : controller.listar()) {
                if (a.getId() == id) {
                    encontrado = a;
                    break;
                }
            }

            if (encontrado == null) {
                JOptionPane.showMessageDialog(
                        this, "Agendamento nao encontrado."
                );
                return;
            }

            if ("CANCELADO".equals(encontrado.getStatus())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nao e possivel editar um agendamento cancelado."
                );
                return;
            }

            String data = JOptionPane.showInputDialog(
                    this, "Nova data (AAAA-MM-DD):",
                    encontrado.getDataSessao().toString()
            );

            if (data == null) return;

            String horario = JOptionPane.showInputDialog(
                    this, "Novo horario (HH:MM):",
                    encontrado.getHorario().toString()
            );

            if (horario == null) return;

            String[] tipos = {"SESSAO", "AVALIACAO"};

            String tipo = (String) JOptionPane.showInputDialog(
                    this,
                    "Selecione o tipo:",
                    "Editar Agendamento",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tipos,
                    encontrado.getTipo()
            );

            if (tipo == null) return;

            boolean sucesso = controller.atualizar(
                    id,
                    encontrado.getPacienteId(),
                    encontrado.getFisioterapeutaId(),
                    data,
                    horario,
                    tipo
            );

            JOptionPane.showMessageDialog(
                    this,
                    sucesso ? "Agendamento atualizado!"
                            : "Nao foi possivel atualizar."
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Digite um ID valido."
            );
        }
    }

    private void cancelar() {

        String textoId = JOptionPane.showInputDialog(
                this, "Digite o ID do agendamento:"
        );

        if (textoId == null) {
            return;
        }

        try {
            int id = Integer.parseInt(textoId.trim());

            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja cancelar o agendamento ID " + id + "?",
                    "Confirmar Cancelamento",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao != JOptionPane.YES_OPTION) {
                return;
            }

            boolean sucesso = controller.cancelar(id);

            JOptionPane.showMessageDialog(
                    this,
                    sucesso ? "Agendamento cancelado!"
                            : "Nao foi possivel cancelar."
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Digite um ID valido."
            );
        }
    }
}
