import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeForm extends JDialog{
    private JButton btnHomeRegister;
    private JButton btnHomeLogin;
    private JPanel HomePanel;

    public HomeForm(){
        setTitle("Welcome!!");
        setContentPane(HomePanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        btnHomeRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistrationForm registrationForm = new RegistrationForm();
                registrationForm.show();
            }
        });
        btnHomeLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.show();
            }
        });
        setVisible(true);
    }

}
