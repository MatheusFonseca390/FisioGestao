
package view;

import controller.FisioterapeutaController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Fisioterapeuta;

public class TelaCadastroFisioterapeuta extends JFrame {

    private JTextField txtNome;
    private JTextField txtCrefito;
    private JTextField txtEspecialidade;

    private JButton btnSalvar;
    private JButton btnConsultar;
    private JButton btnEditar;
    private JButton btnExcluir;

    private final FisioterapeutaController controller =
            new FisioterapeutaController();

    public TelaCadastroFisioterapeuta() {

        setTitle("FisioGestao - Fisioterapeutas");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("CREFITO:"));
        txtCrefito = new JTextField();
        add(txtCrefito);

        add(new JLabel("Especialidade:"));
        txtEspecialidade = new JTextField();
        add(txtEspecialidade);

        btnSalvar = new JButton("Salvar");
        btnConsultar = new JButton("Consultar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");

        add(btnSalvar);
        add(btnConsultar);
        add(btnEditar);
        add(btnExcluir);

        btnSalvar.addActionListener(e -> cadastrar());
        btnConsultar.addActionListener(e -> consultar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
    }

    private void cadastrar() {

        boolean sucesso = controller.cadastrar(
                txtNome.getText(),
                txtCrefito.getText(),
                txtEspecialidade.getText()
        );

        if (sucesso) {
            JOptionPane.showMessageDialog(
                    this, "Fisioterapeuta cadastrado com sucesso!"
            );

            txtNome.setText("");
            txtCrefito.setText("");
            txtEspecialidade.setText("");

        } else {
            JOptionPane.showMessageDialog(
                    this, "Nao foi possivel cadastrar."
            );
        }
    }

    private void consultar() {

        List<Fisioterapeuta> lista = controller.listar();

        StringBuilder mensagem = new StringBuilder();

        for (Fisioterapeuta f : lista) {
            mensagem.append("ID: ").append(f.getId())
                    .append("\nNome: ").append(f.getNome())
                    .append("\nCREFITO: ").append(f.getCrefito())
                    .append("\nEspecialidade: ")
                    .append(f.getEspecialidade())
                    .append("\n\n");
        }

        if (lista.isEmpty()) {
            mensagem.append("Nenhum fisioterapeuta cadastrado.");
        }

        JTextArea area = new JTextArea(mensagem.toString());
        area.setEditable(false);

        JScrollPane painel = new JScrollPane(area);
        painel.setPreferredSize(new Dimension(350, 200));

        JOptionPane.showMessageDialog(
                this, painel, "Fisioterapeutas",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void editar() {

        String textoId = JOptionPane.showInputDialog(
                this, "Digite o ID do fisioterapeuta:"
        );

        if (textoId == null) {
            return;
        }

        try {
            int id = Integer.parseInt(textoId.trim());

            Fisioterapeuta encontrado = null;

            for (Fisioterapeuta f : controller.listar()) {
                if (f.getId() == id) {
                    encontrado = f;
                    break;
                }
            }

            if (encontrado == null) {
                JOptionPane.showMessageDialog(
                        this, "Fisioterapeuta nao encontrado."
                );
                return;
            }

            String nome = JOptionPane.showInputDialog(
                    this, "Nome:", encontrado.getNome()
            );

            if (nome == null) return;

            String crefito = JOptionPane.showInputDialog(
                    this, "CREFITO:", encontrado.getCrefito()
            );

            if (crefito == null) return;

            String especialidade = JOptionPane.showInputDialog(
                    this, "Especialidade:", encontrado.getEspecialidade()
            );

            if (especialidade == null) return;

            boolean sucesso = controller.atualizar(
                    id, nome, crefito, especialidade
            );

            JOptionPane.showMessageDialog(
                    this,
                    sucesso ? "Fisioterapeuta atualizado!"
                            : "Nao foi possivel atualizar."
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Digite um ID valido."
            );
        }
    }

    private void excluir() {

        String textoId = JOptionPane.showInputDialog(
                this, "Digite o ID do fisioterapeuta:"
        );

        if (textoId == null) {
            return;
        }

        try {
            int id = Integer.parseInt(textoId.trim());

            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja excluir o fisioterapeuta ID " + id + "?",
                    "Confirmar Exclusao",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao != JOptionPane.YES_OPTION) {
                return;
            }

            boolean sucesso = controller.excluir(id);

            JOptionPane.showMessageDialog(
                    this,
                    sucesso ? "Fisioterapeuta excluido!"
                            : "Nao foi possivel excluir."
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Digite um ID valido."
            );
        }
    }
}
