import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FutureEventForm extends JDialog{
    private JButton addEventButton;
    private JButton viewAndEditEventButton;
    private JButton backButton;
    private JPanel FutureEventPanel;
    private JButton btnViewAllPlannedEvents;
    private int userid;

    public FutureEventForm(int userid){
        this.userid = userid;
        setTitle("Future Events And Plans");
        setContentPane(FutureEventPanel);
        setMinimumSize(new Dimension(700,350));
        setModal(true);

        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AddEventForm addEventForm = new AddEventForm(userid);
                addEventForm.show();
            }
        });
        viewAndEditEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewEventForm viewEventForm = new ViewEventForm(userid);
                viewEventForm.show();
            }
        });

        btnViewAllPlannedEvents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewAllEventsForm viewAllEventsForm = new ViewAllEventsForm(userid);
                viewAllEventsForm.show();
            }
        });

        backButton.addActionListener(new ActionListener() {
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
