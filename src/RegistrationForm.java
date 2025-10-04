import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistrationForm extends JDialog{
    private JTextField txtName;
    private JTextField txtUserNameAdd;
    private JPasswordField pwdPassword;
    private JPasswordField pwdConfirmed;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registrationPanel;
    private JLabel lblName;
    private JLabel lblUserName;
    private JLabel lblPassword;
    private JLabel lblConfirmed;
    private JButton backButton;

    public RegistrationForm(){
        setTitle("Add a new user");
        setContentPane(registrationPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HomeForm homeForm = new HomeForm();
                homeForm.show();
            }
        });
        setVisible(true);
    }

    private void registerUser() {
        String name = txtName.getText();
        String userName = txtUserNameAdd.getText();
        String password = String.valueOf(pwdPassword.getPassword());
        String confirmedPassword = String.valueOf(pwdConfirmed.getPassword());

        if(name.isEmpty() || userName.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()){
            JOptionPane.showMessageDialog(this,
                            "Please Enter All Fields!",
                            "Try Again",
                            JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (!password.equals(confirmedPassword))
        {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password Does Not Match!",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else
        {
            DB_Connection db1 = new DB_Connection();
            Connection connection = db1.getConnection();

            try
            {
                String sql = "INSERT INTO user (name, userName, password) VALUES (?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,name);
                ps.setString(2,userName);
                ps.setString(3,password);
                int rowAffected = ps.executeUpdate();

                if (rowAffected > 0)
                {
                    txtName.setText("");
                    txtUserNameAdd.setText("");
                    pwdPassword.setText("");
                    pwdConfirmed.setText("");
                    JOptionPane.showMessageDialog(this,
                            "User Registered Successfully",
                            "Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                else
                {
                    JOptionPane.showMessageDialog(this,
                            "User Not Registered!",
                            "Try Again",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this,
                        "Something went wrong!!! Try Again" + e.getMessage(),
                        "Try Again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            finally
            {
                db1.closeConnection();
            }
        }
    }
}
