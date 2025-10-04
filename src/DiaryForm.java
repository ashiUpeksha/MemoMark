import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiaryForm extends JDialog{
    private JButton btnEditDiary;
    private JButton btnBackDiary;
    private JButton btnAddDiary;
    private JPanel DiaryPanel;
    private JButton btnViewAll;
    private int userid;

    public DiaryForm(int userid){
        this.userid = userid;
        setTitle("Diary");
        setContentPane(DiaryPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        btnAddDiary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AddDiaryEntryForm addDiaryEntryForm = new AddDiaryEntryForm(userid);
                addDiaryEntryForm.show();
            }
        });

        btnEditDiary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewDiaryEntryForm viewDiaryEntryForm = new ViewDiaryEntryForm(userid);
                viewDiaryEntryForm.show();
            }
        });

        btnViewAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewAllDiaryEntriesForm viewAllDiaryEntriesForm = new ViewAllDiaryEntriesForm(userid);
                viewAllDiaryEntriesForm.show();
            }
        });

        btnBackDiary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TasksForm tasksForm = new TasksForm(userid);
                tasksForm.show();
            }
        });
        setVisible(true);
    }
}
