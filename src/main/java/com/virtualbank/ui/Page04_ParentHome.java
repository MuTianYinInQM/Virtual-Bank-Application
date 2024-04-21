import javax.swing.*;
import java.awt.*;

public class Page04_ParentHome {

    private JButton exitButton = new JButton("Exit");
    private JButton createTaskButton = new JButton();

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getCreateTaskButton() {
        return createTaskButton;
    }

    public Page04_ParentHome() {
        // 页面 window
        JFrame window = new JFrame(); // 页面窗口
        final int window_width = 1260;
        final int window_height = 780;
        window.setBounds(0, 0, window_width, window_height);
        window.setResizable(false);
        window.setLayout(null);
        window.setTitle("JoyBank - Parent Home Page");
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0xf8f6ea));
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        window.add(backgroundPanel);

        // 返回上一级，LoginPage的按钮Exit
        exitButton.setBounds(20, 20, 100, 50);
        Font font = new Font(exitButton.getFont().getName(), Font.PLAIN, 20);
        exitButton.setFont(font);
        backgroundPanel.add(exitButton);

        // 创建新任务的按钮
        createTaskButton.setBounds(369, 102, 516, 64);
        ImageIcon createAccountButtonIcon = new ImageIcon("images/CreateTaskButton.png");
        createTaskButton.setIcon(createAccountButtonIcon);
        backgroundPanel.add(createTaskButton);

        // 创建带滚动条的区域
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(new Color(0xf8f6ea));
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setBounds(369, 190, 550, 400); // 调整位置和大小
        scrollPane.setBackground(new Color(0xf8f6ea));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        backgroundPanel.add(scrollPane);

        // terminated task 1
        String description1 = "This is a description of the task, " +
                "I don't know the max length of it, so"; // 任务panel 描述最长72字节
        TaskLabel.TerminatedTaskLabel terminatedTaskLabel1 = new TaskLabel.TerminatedTaskLabel(10.00,
                "04-24", "Tom", "001", "TaskName", description1);
        scrollPanel.add(terminatedTaskLabel1);
        scrollPanel.add(Box.createVerticalStrut(10));

        // Terminated task 2
        TaskLabel.TerminatedTaskLabel terminatedTaskLabel2 = new TaskLabel.TerminatedTaskLabel(5.00,
                "03-02", "Lisa", "002", "TaskName2", "description");
        scrollPanel.add(terminatedTaskLabel2);
        scrollPanel.add(Box.createVerticalStrut(10));

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        // Ongoing task
        Parent_OngoingTaskLabel parentOngoingTaskLabel = new Parent_OngoingTaskLabel(6.50,
                "03-02", "Lisa", "003", "TaskName3", "description");
        scrollPanel.add(parentOngoingTaskLabel);
        scrollPanel.add(Box.createVerticalStrut(10));

        // Not accepted task
        Parent_NotAcceptedTaskLabel notAcceptedTaskLabel = new Parent_NotAcceptedTaskLabel(6.50,
                "03-02", "Lisa", "004", "TaskName4", "description");
        scrollPanel.add(notAcceptedTaskLabel);
        scrollPanel.add(Box.createVerticalStrut(10));

        // Finished task
        TaskLabel.FinishedTaskLabel finishedTaskLabel = new TaskLabel.FinishedTaskLabel(6.50,
                "03-02", "Tom", "005", "TaskName5", "description");
        scrollPanel.add(finishedTaskLabel);
        scrollPanel.add(Box.createVerticalStrut(10));

    }

    public class Parent_NotAcceptedTaskLabel extends TaskLabel.NotAcceptedTaskLabel {

        private JButton terminateButton;

        public JButton getTerminateButton() {
            return terminateButton;
        }

        public Parent_NotAcceptedTaskLabel(Double prize, String due, String child, String id,
                                           String taskName, String description) {
            super(prize, due, child, id, taskName, description);
            terminateButton = new JButton("Terminate");
            terminateButton.setBounds(315, 90, 80, 40);
            this.add(terminateButton);
        }
    }

    public class Parent_OngoingTaskLabel extends TaskLabel.OngoingTaskLabel {

        private JButton terminateButton;

        public JButton getTerminateButton() {
            return terminateButton;
        }

        public Parent_OngoingTaskLabel(Double prize, String due, String child, String id,
                                       String taskName, String description) {
            super(prize, due, child, id, taskName, description);
            terminateButton = new JButton("Terminate");
            terminateButton.setBounds(315, 90, 80, 40);
            this.add(terminateButton);
        }
    }

    public static void main(String[] args) {
        Page04_ParentHome page04ParentHome = new Page04_ParentHome();
    }
}
