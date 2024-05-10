package com.virtualbank.ui;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.Task;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Page04_ParentHome extends JFrame {

    private JButton exitButton = new JButton();
    private JButton createTaskButton = new JButton();
    private JPanel backgroundPanel; // 声明 backgroundPanel 字段
    private JScrollPane scrollPane; 

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getCreateTaskButton() {
        return createTaskButton;
    }

    public Page04_ParentHome() {
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Parent Home Page");
//        ImageIcon background = new ImageIcon("images/ParentHomePage.png");
//        JLabel backgroundLabel = new JLabel(background);
//        backgroundLabel.setBounds(0, 0, window_width, window_height);
        backgroundPanel = new JPanel(); // 初始化 backgroundPanel
//        backgroundPanel.add(backgroundLabel);
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        add(backgroundPanel);

        // 返回上一级，LoginPage的按钮Exit
        exitButton.setBounds(20, 20, 134, 50);
        Font font = new Font(exitButton.getFont().getName(), Font.PLAIN, 20);
        exitButton.setFont(font);
        ImageIcon exitButtonIcon = new ImageIcon("images/ExitButtonImage.png");
        exitButton.setIcon(exitButtonIcon);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
//        exitButton.setBorderPainted(false);
        backgroundPanel.add(exitButton);

        // 创建新任务的按钮
        createTaskButton.setBounds(30, 135, 516, 98);
        ImageIcon createAccountButtonIcon = new ImageIcon("images/CreateTaskButton.png");
        createTaskButton.setIcon(createAccountButtonIcon);
        createTaskButton.setBorder(null);
        backgroundPanel.add(createTaskButton);

        // 创建带滚动条的区域
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(new Color(0xfcfcf7));
        scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setBounds(660, 190, 550, 540); // 调整位置和大小
        scrollPane.setBackground(new Color(0xf8f6ea));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        backgroundPanel.add(scrollPane);

        displayTasksFromJsonFile("src/main/resources/tasks.json"); // 从 JSON 文件中读取并显示任务数据

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void displayTasksFromJsonFile(String jsonFilePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Task> tasks = objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<Task>>() {});

            Container scrollPanel = null;
            for (Component component : backgroundPanel.getComponents()) {
                if (component instanceof JScrollPane) {
                    scrollPanel = (Container) ((JScrollPane) component).getViewport().getView();
                    break;
                }
            }

            if (scrollPanel == null) {
                System.err.println("No JScrollPane found in the content pane.");
                return;
            }

            // 清空原有任务
            scrollPanel.removeAll();

            for (Task task : tasks) {
                if ("terminated".equals(task.getStatus())) {
                    TaskLabel.TerminatedTaskLabel terminatedTaskLabel = new TaskLabel.TerminatedTaskLabel(task.getId(), task.getTaskName(), task.getDescription(), task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate());
                    scrollPanel.add(terminatedTaskLabel);
                } else if ("ongoing".equals(task.getStatus())) {
                    Parent_OngoingTaskLabel ongoingTaskLabel = new Parent_OngoingTaskLabel(task.getId(), task.getTaskName(), task.getDescription(),task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate());
                    scrollPanel.add(ongoingTaskLabel);
                } else if ("not_accepted".equals(task.getStatus())) {
                    Parent_NotAcceptedTaskLabel notAcceptedTaskLabel = new Parent_NotAcceptedTaskLabel(task.getId(), task.getTaskName(), task.getDescription(),task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate());
                    scrollPanel.add(notAcceptedTaskLabel);
                } else if ("finished".equals(task.getStatus())) {
                    TaskLabel.FinishedTaskLabel finishedTaskLabel = new Parent_FinishedTaskLabel(task.getId(), task.getTaskName(), task.getDescription(),task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate());
                    scrollPanel.add(finishedTaskLabel);
                }
                scrollPanel.add(Box.createVerticalStrut(10));
            }

            scrollPanel.revalidate();
            scrollPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon background = new ImageIcon("images/ParentHomePage.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        backgroundPanel.add(backgroundLabel);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public class Parent_FinishedTaskLabel extends TaskLabel.FinishedTaskLabel {

        private JButton confirmButton;

        public JButton getConfirmButton() { return confirmButton; }

        public Parent_FinishedTaskLabel(String id, String taskName, String description, double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            confirmButton = new JButton();
            confirmButton.setBounds(290, 90, 100, 40);
            ImageIcon confirmButtonImage = new ImageIcon("images/confirm3.png");
            confirmButton.setIcon(confirmButtonImage);
            confirmButton.setBorder(null);
            confirmButton.setContentAreaFilled(false);
            confirmButton.setBorderPainted(false);
            this.add(confirmButton);

        }
    }

    public class Parent_NotAcceptedTaskLabel extends TaskLabel.NotAcceptedTaskLabel {

        private JButton terminateButton;

        public JButton getTerminateButton() {
            return terminateButton;
        }

        public Parent_NotAcceptedTaskLabel(String id, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            terminateButton = new JButton();
            terminateButton.setBounds(290, 90, 100, 40);
            ImageIcon terminateButtonImage = new ImageIcon("images/TerminateButtonImage.png");
            terminateButton.setIcon(terminateButtonImage);
            terminateButton.setBorder(null);
            terminateButton.setContentAreaFilled(false); // 不填充内容区
            terminateButton.setBorderPainted(false); // 不绘制边框
            this.add(terminateButton);
        }
    }

    public class Parent_OngoingTaskLabel extends TaskLabel.OngoingTaskLabel {

        private JButton terminateButton;

        public JButton getTerminateButton() {
            return terminateButton;
        }

        public Parent_OngoingTaskLabel(String id, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            terminateButton = new JButton();
            terminateButton.setBounds(290, 90, 100, 40);
            ImageIcon terminateButtonImage = new ImageIcon("images/TerminateButtonImage.png");
            terminateButton.setIcon(terminateButtonImage);
            terminateButton.setBorder(null);
            terminateButton.setContentAreaFilled(false); // 不填充内容区
            terminateButton.setBorderPainted(false);
            this.add(terminateButton);
        }
    }

    public static void main(String[] args) {
        Page04_ParentHome page04ParentHome = new Page04_ParentHome();
    }
}
