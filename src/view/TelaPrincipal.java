
package view;

import java.awt.*;
import javax.swing.*;
import util.ConfiguracaoSistema;

public class TelaPrincipal extends JFrame {

    private JButton btnPacientes;
    private JButton btnFisioterapeutas;
    private JButton btnAgendamentos;
    private JButton btnSair;

    public TelaPrincipal() {

        setTitle(ConfiguracaoSistema.getInstancia()
                .getNomeSistema() + " - Principal");

        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(5, 1, 10, 12));
        painel.setBackground(new Color(230, 240, 250));
        painel.setBorder(BorderFactory.createEmptyBorder(
                25, 60, 25, 60));

        JLabel titulo = new JLabel(
                "FisioGestao", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(30, 70, 120));

        btnPacientes = new JButton("Pacientes");
        btnFisioterapeutas = new JButton("Fisioterapeutas");
        btnAgendamentos = new JButton("Agendamentos");
        btnSair = new JButton("Sair");

        JButton[] botoes = {
            btnPacientes,
            btnFisioterapeutas,
            btnAgendamentos
        };

        for (JButton botao : botoes) {
            botao.setBackground(new Color(70, 130, 180));
            botao.setForeground(Color.WHITE);
            botao.setFont(new Font("Arial", Font.BOLD, 16));
            botao.setFocusPainted(false);
            botao.setOpaque(true);
        }

        btnSair.setBackground(new Color(200, 70, 70));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFont(new Font("Arial", Font.BOLD, 16));
        btnSair.setFocusPainted(false);
        btnSair.setOpaque(true);

        painel.add(titulo);
        painel.add(btnPacientes);
        painel.add(btnFisioterapeutas);
        painel.add(btnAgendamentos);
        painel.add(btnSair);

        add(painel);

        btnPacientes.addActionListener(e -> {
            new TelaCadastroPaciente().setVisible(true);
        });

        btnFisioterapeutas.addActionListener(e -> {
            new TelaCadastroFisioterapeuta().setVisible(true);
        });

        btnAgendamentos.addActionListener(e -> {
            new TelaAgendamento().setVisible(true);
        });

        btnSair.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }
}
