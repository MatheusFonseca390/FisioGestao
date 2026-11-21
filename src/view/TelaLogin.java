
package view;

import controller.UsuarioController;
import java.awt.*;
import javax.swing.*;

public class TelaLogin extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    private final UsuarioController controller =
            new UsuarioController();

    public TelaLogin() {

        setTitle("FisioGestao - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("FisioGestao"));
        add(new JLabel("Acesso ao sistema"));

        add(new JLabel("Usuario:"));
        txtLogin = new JTextField();
        add(txtLogin);

        add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        add(new JLabel(""));
        btnEntrar = new JButton("Entrar");
        add(btnEntrar);

        btnEntrar.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {

        String login = txtLogin.getText();
        char[] senhaDigitada = txtSenha.getPassword();

        try {
            String senha = new String(senhaDigitada);

            if (controller.autenticar(login, senha)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login realizado com sucesso!"
                );

                new TelaPrincipal().setVisible(true);
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario ou senha incorretos!"
                );
            }

        } catch (IllegalStateException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nao foi possivel acessar o banco de dados."
            );

        } finally {
            java.util.Arrays.fill(senhaDigitada, '\0');
            txtSenha.setText("");
        }
    }
}
