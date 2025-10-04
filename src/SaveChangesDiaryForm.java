import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SaveChangesDiaryForm extends JDialog{
    private JTextField txtselectedTitle;
    private JTextArea tareaSelectedDescription;
    private JButton btnSave;
    private JButton btnBack;
    private JLabel lblSelectedDate;
    private JPanel SaveChangesPanel;
    private int userid;
    private String enteredDate;
    private String searchedTitle;
    private String searchedDescription;

    public SaveChangesDiaryForm(int userid, String enteredDate, String searchedTitle, String searchedDescription){
        this.userid = userid;
        this.enteredDate = enteredDate;
        this.searchedTitle = searchedTitle;
        this.searchedDescription = searchedDescription;

        setTitle("Edit Diary Entry");
        setContentPane(SaveChangesPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        lblSelectedDate.setText(enteredDate);
        txtselectedTitle.setText(searchedTitle);
        tareaSelectedDescription.setText(searchedDescription);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewDiaryEntryForm viewDiaryEntryForm = new ViewDiaryEntryForm(userid);
                viewDiaryEntryForm.show();
            }
        });
        setVisible(true);
    }

    private void saveChanges() {
        String title = txtselectedTitle.getText();
        String description= tareaSelectedDescription.getText();

        if(title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot Save Without Title!",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try
        {
            String sql = "UPDATE diaryentry set diaryEntryTitle=?, diaryEntryText=? WHERE diaryEntryDate=? AND userId=? AND diaryEntryTitle=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,title);
            ps.setString(2,description);
            ps.setString(3,enteredDate);
            ps.setInt(4,userid);
            ps.setString(5,searchedTitle);
            int rowAffected = ps.executeUpdate();

            if (rowAffected > 0)
            {
                /*Clear the text fields
                lblSelectedDate.setText("");
                txtselectedTitle.setText("");
                tareaSelectedDescription.setText("");
                 */

                JOptionPane.showMessageDialog(this,
                        "Diary Entry Updated Successfully",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                        "Diary Entry not Updated",
                        "Try Again",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this,
                    "Something went wrong Try Again",
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
