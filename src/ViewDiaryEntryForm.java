import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewDiaryEntryForm extends JDialog{
    private JTextField txtSelectedDate;
    private JButton btnSearch;
    private JList lstSelectedTitle;
    private JTextField txtSelectedTitle;
    private JButton btnShow;
    private JButton btnBack;
    private JButton btnDelete;
    private JLabel lblSearchedData;
    private JButton btnEdits;
    private JPanel ViewDiaryPanel;
    private int userid;

    public ViewDiaryEntryForm(int userid){
        this.userid = userid;
        setTitle("View and Edit Diary Entry");
        setContentPane(ViewDiaryPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchData();
            }
        });

        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDiaryEntry();
            }
        });

        btnEdits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoEdits();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEntry();
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


    private void searchData() {
        String enteredDate = txtSelectedDate.getText();
        if (enteredDate.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot Search Without Date!",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try
        {
            String sql = "SELECT * FROM diaryentry WHERE diaryEntryDate=? AND userId=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,enteredDate);
            ps.setInt(2,userid);
            ResultSet rs = ps.executeQuery();

            DefaultListModel<String> listModel = new DefaultListModel<>(); // Create a list model

            while (rs.next()){
                String title = rs.getString("diaryEntryTitle");
                listModel.addElement(title); // Add each title to the list model
            }
            lstSelectedTitle.setModel(listModel); // Set the list model to your JList

            if (listModel.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No Entries Found for Selected Date",
                        "No Entries",
                        JOptionPane.INFORMATION_MESSAGE);
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


    private void showDiaryEntry() {
        String enteredDate = txtSelectedDate.getText();
        String searchedTitle = txtSelectedTitle.getText();
        if (enteredDate.isEmpty() || searchedTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot Search Without Date and Title!",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try {
            String sql = "SELECT * FROM diaryentry WHERE diaryEntryDate=? AND userId=? AND diaryEntryTitle=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, enteredDate);
            ps.setInt(2, userid);
            ps.setString(3,searchedTitle);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String description = rs.getString("diaryEntryText");
                lblSearchedData.setText(description);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this,
                    "Something went wrong!!! Try Again",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        finally
        {
            db1.closeConnection();
        }
    }


    private void gotoEdits() {
        String enteredDate = txtSelectedDate.getText();
        String searchedTitle = txtSelectedTitle.getText();
        String searchedDescription = lblSearchedData.getText();
        if (enteredDate.isEmpty() || searchedTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please Enter Required Date and Title to Edit!",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

            dispose();
            SaveChangesDiaryForm saveChangesDiaryForm = new SaveChangesDiaryForm(userid, enteredDate, searchedTitle, searchedDescription);
            saveChangesDiaryForm.show();
    }


    private void deleteEntry() {
        String enteredDate = txtSelectedDate.getText();
        String searchedTitle = txtSelectedTitle.getText();
        if (enteredDate.isEmpty() || searchedTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please Enter Required Date and Title to Delete!",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try
        {
            String sql = "DELETE FROM diaryentry WHERE diaryEntryDate=? AND userId=? AND diaryEntryTitle=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,enteredDate);
            ps.setInt(2,userid);
            ps.setString(3,searchedTitle);
            int rowAffected = ps.executeUpdate();

            if (rowAffected > 0)
            {
                txtSelectedDate.setText("");
                txtSelectedTitle.setText("");
                lblSearchedData.setText("");
               // lstSelectedTitle.setModel();
                JOptionPane.showMessageDialog(this,
                        "Diary Entry Deleted Successfully",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                        "Diary Entry not Deleted",
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
