import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JDialog {
    private JTextField txtUserName;
    private JPasswordField txtUSerPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPanel LoginPanel;
    private JButton btnBack;

    public LoginForm(){
        setTitle("Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HomeForm homeForm = new HomeForm();
                homeForm.show();
            }
        });
        setVisible(true);
    }

    private void loginUser() {
        String username = txtUserName.getText();
        String password = String.valueOf(txtUSerPassword.getPassword());

        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try
        {
            String sql = "SELECT userName,password FROM user WHERE userName=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String sql2 = "SELECT userId FROM user WHERE userName=?";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setString(1,username);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()){
                    int userid = rs2.getInt("userId");
                    dispose();
                    TasksForm tasksForm = new TasksForm(userid);
                    tasksForm.show();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                        "Invalid User Name or Password!",
                        "Try Again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
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