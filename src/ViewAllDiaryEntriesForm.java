import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewAllDiaryEntriesForm extends JDialog {
    private JTable tblData;
    private JButton btnBack;
    private JPanel EnteredDiaryEntriesPanel;
    private int userid;

    public ViewAllDiaryEntriesForm(int userid) {
        this.userid = userid;
        setTitle("Entered Diary Entries");
        setContentPane(EnteredDiaryEntriesPanel);
        setMinimumSize(new Dimension(700, 350));
        setModal(true);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                DiaryForm diaryForm = new DiaryForm(userid);
                diaryForm.show();
            }
        });
        viewEnteredEntries();
        setVisible(true);
    }

    private void viewEnteredEntries(){
        DB_Connection db1 = new DB_Connection();
        Connection connection = db1.getConnection();

        try {
            String sql = "SELECT * FROM diaryentry WHERE userId=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel defaultTableModel = new DefaultTableModel();
            defaultTableModel.addColumn("Date");
            defaultTableModel.addColumn("Title");

            tblData.setModel(defaultTableModel);

            while (rs.next()) { // Use a while loop to iterate through the result set
                Date date = rs.getDate("diaryEntryDate");
                String title = rs.getString("diaryEntryTitle");

                defaultTableModel.addRow(new Object[]{date, title});
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