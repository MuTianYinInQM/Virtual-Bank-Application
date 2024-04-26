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

    private JButton exitButton = new JButton("Exit");
    private JButton createTaskButton = new JButton();
    private JPanel backgroundPanel; // 声明 backgroundPanel 字段

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
        backgroundPanel = new JPanel(); // 初始化 backgroundPanel
        backgroundPanel.setBackground(new Color(0xf8f6ea));
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        add(backgroundPanel);

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
                    TaskLabel.TerminatedTaskLabel terminatedTaskLabel = new TaskLabel.TerminatedTaskLabel(task.getTaskName(), task.getDescription(), task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate());
                    scrollPanel.add(terminatedTaskLabel);
                } else if ("ongoing".equals(task.getStatus())) {
                    Parent_OngoingTaskLabel ongoingTaskLabel = new Parent_OngoingTaskLabel(task.getTaskName(), task.getDescription(),task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate());
                    scrollPanel.add(ongoingTaskLabel);
                } else if ("not_accepted".equals(task.getStatus())) {
                    Parent_NotAcceptedTaskLabel notAcceptedTaskLabel = new Parent_NotAcceptedTaskLabel(task.getTaskName(), task.getDescription(),task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate());
                    scrollPanel.add(notAcceptedTaskLabel);
                } else if ("finished".equals(task.getStatus())) {
                    TaskLabel.FinishedTaskLabel finishedTaskLabel = new TaskLabel.FinishedTaskLabel(task.getTaskName(), task.getDescription(),task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate());
                    scrollPanel.add(finishedTaskLabel);
                }
                scrollPanel.add(Box.createVerticalStrut(10));
            }

            scrollPanel.revalidate();
            scrollPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Parent_NotAcceptedTaskLabel extends TaskLabel.NotAcceptedTaskLabel {

        private JButton terminateButton;

        public JButton getTerminateButton() {
            return terminateButton;
        }

        public Parent_NotAcceptedTaskLabel(String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskName, description, reward, childName, startDate, dueDate);
            terminateButton = new JButton("Terminate");
            terminateButton.setBounds(300, 90, 100, 40);
            this.add(terminateButton);
        }
    }

    public class Parent_OngoingTaskLabel extends TaskLabel.OngoingTaskLabel {

        private JButton terminateButton;

        public JButton getTerminateButton() {
            return terminateButton;
        }

        public Parent_OngoingTaskLabel(String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskName, description, reward, childName, startDate, dueDate);
            terminateButton = new JButton("Terminate");
            terminateButton.setBounds(300, 90, 100, 40);
            this.add(terminateButton);
        }
    }

    public static void main(String[] args) {
        Page04_ParentHome page04ParentHome = new Page04_ParentHome();
    }
}