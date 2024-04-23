package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;

public class Page05_ChildTask extends JFrame{

    private JButton exitButton = new JButton("Exit");

    public JButton getExitButton() {
        return exitButton;
    }

    public Page05_ChildTask() {
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Child Task Page");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("images/TaskBoardBackground.png"); // 添加背景图
        JLabel backgroundPanel = new JLabel(loginBackgroundImageIcon);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        getContentPane().add(backgroundPanel);

        // 返回上一级，ChildHome Page的按钮Exit
        exitButton.setBounds(20, 20, 100, 50);
        Font font = new Font(exitButton.getFont().getName(), Font.PLAIN, 20);
        exitButton.setFont(font);
        backgroundPanel.add(exitButton);

        // 创建带滚动条的区域
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(new Color(0xf8f6ea));
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setBounds(369, 200, 550, 400); // 调整位置和大小
        scrollPane.setBackground(new Color(0xf8f6ea));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        backgroundPanel.add(scrollPane);

        // terminated task 1
        String description1 = "This is a description of the task, " +
                "I don't know the max length of it, so"; // 任务panel 描述最长72字节
        TaskLabel.TerminatedTaskLabel terminatedTaskLabel1 = new TaskLabel.TerminatedTaskLabel(10.00,
                "04-24", "Lisa", "001", "TaskName", description1);
        scrollPanel.add(terminatedTaskLabel1);
        scrollPanel.add(Box.createVerticalStrut(10));

        // Terminated task 2
        TaskLabel.TerminatedTaskLabel terminatedTaskLabel2 = new TaskLabel.TerminatedTaskLabel(5.00,
                "03-02", "Lisa", "002", "TaskName2", "description");
        scrollPanel.add(terminatedTaskLabel2);
        scrollPanel.add(Box.createVerticalStrut(10));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Ongoing task
        Child_OngoingTaskLabel parentOngoingTaskLabel = new Child_OngoingTaskLabel(6.50,
                "03-02", "Lisa", "003", "TaskName3", "description");
        scrollPanel.add(parentOngoingTaskLabel);
        scrollPanel.add(Box.createVerticalStrut(10));

        // Not accepted task
        Child_NotAcceptedTaskLabel notAcceptedTaskLabel = new Child_NotAcceptedTaskLabel(6.50,
                "03-02", "Lisa", "004", "TaskName4", "description");
        scrollPanel.add(notAcceptedTaskLabel);
        scrollPanel.add(Box.createVerticalStrut(10));

        // Finished task
        TaskLabel.FinishedTaskLabel finishedTaskLabel = new TaskLabel.FinishedTaskLabel(6.50,
                "03-02", "Lisa", "005", "TaskName5", "description");
        scrollPanel.add(finishedTaskLabel);
        scrollPanel.add(Box.createVerticalStrut(10));

    }

    public class Child_NotAcceptedTaskLabel extends TaskLabel.NotAcceptedTaskLabel {

        // 孩子的未接受任务的任务板
        private JButton giveUpButton; // 放弃按钮
        private JButton acceptButton; // 接受按钮

        public JButton getGiveUpButton() {

            return giveUpButton;
        }

        public JButton getAcceptButton() {
            return acceptButton;
        }

        public Child_NotAcceptedTaskLabel(Double prize, String due, String child, String id,
                                          String taskName, String description) {
            super(prize, due, child, id, taskName, description);
            giveUpButton = new JButton("Give Up");
            giveUpButton.setBounds(315, 90, 80, 40);
            this.add(giveUpButton);
            acceptButton = new JButton("Accept");
            acceptButton.setBounds(215, 90, 80, 40);
            this.add(acceptButton);
        }
    }

    public class Child_OngoingTaskLabel extends TaskLabel.OngoingTaskLabel {

        //孩子已接受的正在进行的任务板
        private JButton giveUPButton; // 放弃按钮
        private JButton submitButton; // 提交按钮

        public JButton getGiveUPButton() {

            return giveUPButton;
        }

        public JButton getSubmitButton() {
            return submitButton;
        }

        public Child_OngoingTaskLabel(Double prize, String due, String child, String id,
                                      String taskName, String description) {
            super(prize, due, child, id, taskName, description);
            giveUPButton = new JButton("Give Up");
            giveUPButton.setBounds(315, 90, 80, 40);
            this.add(giveUPButton);
            submitButton = new JButton("Submit");
            submitButton.setBounds(215, 90, 80, 40);
            this.add(submitButton);
        }
    }

    public static void main(String[] args) {
        Page05_ChildTask page05ChildTask = new Page05_ChildTask();
    }
}
