import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class AddEventForm extends JDialog{
    private JTextField txtEventDate;
    private JTextField txtEventTime;
    private JTextArea tareaEventDetails;
    private JButton btnBack;
    private JButton btnSaveEvent;
    private JPanel AddEventPanel;
    private JTextField txtEventTitle;
    private JComboBox cmbHours;
    private JComboBox cmbMinutes;
    private int userid;

    public AddEventForm(int userid){
        this.userid = userid;
        setTitle("Add New Event");
        setContentPane(AddEventPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        btnSaveEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEventDetails();
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



    private void saveEventDetails() {
        String date = txtEventDate.getText();
        String time = cmbHours.getSelectedIndex() + ":" +cmbMinutes.getSelectedIndex();
        String eventTitle = txtEventTitle.getText();
        String eventDescription = tareaEventDetails.getText();

        if (date.isEmpty() || eventTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please Fill in All Required Fields (Event Date and Event Title).",
                    "Try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try
        {
            // Parse the date and time strings using SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date eventDate = new Date(dateFormat.parse(date).getTime());

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Time eventTime = new Time(timeFormat.parse(time).getTime());


            String sql = "INSERT INTO futureevents (eventDate,eventTime,eventTitle,eventDescription,userId) VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1,eventDate);
            ps.setTime(2,eventTime);
            ps.setString(3,eventTitle);
            ps.setString(4,eventDescription);
            ps.setInt(5,userid);
            int rowAffected = ps.executeUpdate();

            if (rowAffected > 0)
            {
                // Clear the text fields
                txtEventDate.setText("");
                cmbHours.setSelectedIndex(0);
                cmbMinutes.setSelectedIndex(0);
                txtEventTitle.setText("");
                tareaEventDetails.setText("");
                JOptionPane.showMessageDialog(this,
                        "Event Entered Successfully",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                        "Event Not Entered",
                        "Try Again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        catch (Exception e) {
            //e.printStackTrace(); // Print the stack trace to help diagnose the issue
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
