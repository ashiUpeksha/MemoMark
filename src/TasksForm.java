import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TasksForm extends JDialog{
    private JPanel TasksPanel;
    private JButton btnDiary;
    private JButton btnFutureEvents;
    private int userid;

    public TasksForm(int userid)
    {
        this.userid = userid;
        setTitle("Tasks");
        setContentPane(TasksPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        btnDiary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                DiaryForm diaryForm = new DiaryForm(userid);
                diaryForm.show();
            }
        });

        btnFutureEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FutureEventForm futureEventForm = new FutureEventForm(userid);
                futureEventForm.show();
            }
        });
        setVisible(true);
    }
}
