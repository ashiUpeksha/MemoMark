import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewEventForm extends JDialog{
    private JTextField txtEnteredDate;
    private JButton btnSearch;
    private JList lstSelectedDetails;
    private JTextField txtSelectedTitle;
    private JButton btnShowEvents;
    private JTextField txtTime;
    private JButton btnBack;
    private JButton btnDeleteEvents;
    private JButton btnEdit;
    private JPanel ViewEventsPanel;
    private JLabel lblSelectedEvents;
    private int userid;

    public ViewEventForm(int userid){
        this.userid = userid;
        setTitle("View and Edit Events");
        setContentPane(ViewEventsPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchedData();
            }
        });

        btnShowEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSelectedEvents();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoEdits();
            }
        });

        btnDeleteEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventDelete();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FutureEventForm futureEventForm = new FutureEventForm(userid);
                futureEventForm.show();
            }
        });
        setVisible(true);
    }


    private void searchedData() {
        String searchedDate = txtEnteredDate.getText();
        if (searchedDate.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot Search Without Date!",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try {
            String sql = "SELECT * FROM futureevents WHERE eventDate=? AND userId=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, searchedDate);
            ps.setInt(2, userid);
            ResultSet rs = ps.executeQuery();

            DefaultListModel<String> listModel = new DefaultListModel<>(); // Create a list model

            while (rs.next()) {
                String time = rs.getString("eventTime");
                String title = rs.getString("eventTitle");
                String detailsSearched = time + "     " +title;
                listModel.addElement(detailsSearched); // Add data to the list model

            }
            lstSelectedDetails.setModel(listModel); // Set the list model to your JList

            if (listModel.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No entries found for the selected date.",
                        "No Entries",
                        JOptionPane.INFORMATION_MESSAGE);
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


    private void showSelectedEvents() {

        String enteredDate = txtEnteredDate.getText();
        String enteredTitle = txtSelectedTitle.getText();
        if (enteredDate.isEmpty() || enteredTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot Search Without Date and Title!",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try {
            String sql = "SELECT * FROM futureevents WHERE eventDate=? AND userId=? AND eventTitle=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, enteredDate);
            ps.setInt(2, userid);
            ps.setString(3,enteredTitle);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String time = rs.getString("eventTime");
                String description = rs.getString("eventDescription");
                txtTime.setText(time);
                lblSelectedEvents.setText(description);
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


    private void gotoEdits() {
        String enteredDate = txtEnteredDate.getText();
        String enteredTitle = txtSelectedTitle.getText();
        String enteredTime = txtTime.getText();
        String enteredDescription = lblSelectedEvents.getText();
        if (enteredDate.isEmpty() || enteredTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please Enter Required Date and Title to Edit!",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        dispose();
        SaveChangesEventsForm saveChangesEventsForm = new SaveChangesEventsForm(enteredDate, enteredTime, enteredTitle, enteredDescription, userid);
    }


    private void eventDelete(){
        String searchedTitle = txtSelectedTitle.getText();
        String enteredDate = txtEnteredDate.getText();
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
            String sql = "DELETE FROM futureevents WHERE eventDate=? AND userId=? AND eventTitle=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,enteredDate);
            ps.setInt(2,userid);
            ps.setString(3,searchedTitle);
            int rowAffected = ps.executeUpdate();

            if (rowAffected > 0)
            {
                txtEnteredDate.setText("");
                txtSelectedTitle.setText("");
                txtTime.setText("");
                lblSelectedEvents.setText("");
                JOptionPane.showMessageDialog(this,
                        "Event Deleted Successfully",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                        "Event not Deleted",
                        "Try Again",
                        JOptionPane.ERROR_MESSAGE);
                return;
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
}
