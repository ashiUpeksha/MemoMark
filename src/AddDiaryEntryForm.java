import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class AddDiaryEntryForm extends JDialog{
    private JTextField txtDiaryTitle;
    private JLabel lblEnteredDate;
    private JTextArea tareaDiaryDescription;
    private JPanel AddDiaryPanel;
    private JButton btnSaveDiary;
    private JButton btnBack;
    private int userid;

    public AddDiaryEntryForm(int userid)
    {
        this.userid = userid;
        setTitle("Add New Diary Entry");
        setContentPane(AddDiaryPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        lblEnteredDate.setText(String.valueOf(date));

        btnSaveDiary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               saveDiaryEntry();
            }

        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                DiaryForm diaryForm = new DiaryForm(userid);
                diaryForm.show();
            }
        });
        setVisible(true);
    }

    private void saveDiaryEntry() {

        Date date=new Date();
        java.sql.Date sqlDate=new java.sql.Date(date.getTime());

        String title = txtDiaryTitle.getText();
        String description = tareaDiaryDescription.getText();

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
            String sql = "INSERT INTO diaryentry (diaryEntryDate,diaryEntryTitle,diaryEntryText,userId) VALUES (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1,sqlDate);
            ps.setString(2,title);
            ps.setString(3,description);
            ps.setInt(4,userid);
            int rowAffected = ps.executeUpdate();

            if (rowAffected > 0)
            {
                // Clear the text fields
                txtDiaryTitle.setText("");
                tareaDiaryDescription.setText("");

                JOptionPane.showMessageDialog(this,
                        "Diary Entry Saved Successfully",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                        "Diary Entry not Saved",
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
