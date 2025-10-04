import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewAllEventsForm extends JDialog{
    private JTable tblData;
    private JButton btnBack;
    private JPanel EnteredEventPanel;
    private int userid;

    public ViewAllEventsForm(int userid) {
        this.userid = userid;
        setTitle("Planned events");
        setContentPane(EnteredEventPanel);
        setMinimumSize(new Dimension(700, 350));
        setModal(true);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FutureEventForm futureEventForm = new FutureEventForm(userid);
                futureEventForm.show();
            }
        });
        viewEnteredEvents();
        setVisible(true);
    }

    private void viewEnteredEvents(){
        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try {
            String sql = "SELECT * FROM futureevents WHERE userId=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel defaultTableModel = new DefaultTableModel();
            defaultTableModel.addColumn("Date");
            defaultTableModel.addColumn("Time");
            defaultTableModel.addColumn("Title");
            defaultTableModel.addColumn("Description");

            tblData.setModel(defaultTableModel);

            while (rs.next()) { // Use a while loop to iterate through the result set
                Date date = rs.getDate("eventDate");
                String time = rs.getString("eventTime");
                String title = rs.getString("eventTitle");
                String description = rs.getString("eventDescription");

                defaultTableModel.addRow(new Object[]{date, time, title, description});
            }
        } catch (Exception e) {
            // Handle exceptions properly
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Something went wrong!!! Try Again",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            db1.closeConnection();
        }
    }
}
