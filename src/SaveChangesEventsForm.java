import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class SaveChangesEventsForm extends JDialog{
    private JTextField txtEneredDate;
    private JTextField txtEnteredTime;
    private JTextField txtEnteredTitle;
    private JTextArea tareaEnteredDescription;
    private JButton btnSave;
    private JButton btnBack;
    private JPanel SaveChangesEventsPanel;
    private int userid;
    private String selectedDate;
    private String selectedTime;
    private String selectedTitle;
    private String selectedDescription;

    public SaveChangesEventsForm(String selectedDate, String selectedTime, String selectedTitle, String selectedDescription, int userid){
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
        this.selectedTitle = selectedTitle;
        this.selectedDescription = selectedDescription;
        this.userid = userid;

        setTitle("Edit Diary Entry");
        setContentPane(SaveChangesEventsPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        txtEneredDate.setText(selectedDate);
        txtEnteredTime.setText(selectedTime);
        txtEnteredTitle.setText(selectedTitle);
        tareaEnteredDescription.setText(selectedDescription);

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
                ViewEventForm viewEventForm = new ViewEventForm(userid);
                viewEventForm.show();
            }
        });
        setVisible(true);
    }


    private void saveChanges() {
        String date = txtEneredDate.getText();
        String time = txtEnteredTime.getText();
        String title = txtEnteredTitle.getText();
        String description = tareaEnteredDescription.getText();
        if(date.isEmpty() || title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot Save Without Title and Date!",
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


            String sql = "UPDATE futureevents set eventTitle=?, eventDescription=?, eventDate=?, eventTime=? WHERE eventDate=? AND userId=? AND eventTitle=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,title);
            ps.setString(2,description);
            ps.setDate(3,eventDate);
            ps.setTime(4,eventTime);
            ps.setString(5,selectedDate);
            ps.setInt(6,userid);
            ps.setString(7,selectedTitle);
            int rowAffected = ps.executeUpdate();

            if (rowAffected > 0)
            {
                JOptionPane.showMessageDialog(this,
                        "Event Updated Successfully",
                        "Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                        "Event not Updated",
                        "Try Again",
                        JOptionPane.ERROR_MESSAGE);
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
