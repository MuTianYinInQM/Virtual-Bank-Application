package com.virtualbank.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.virtualbank.model.Task;  // 确保有一个Task类
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Page05_ChildTask extends JFrame{

    private JButton exitButton = new JButton();
    private JPanel backgroundPanel; // 声明 backgroundPanel 字段
    private JScrollPane scrollPane; // 定义成员变量
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
        backgroundPanel = new JPanel(); // 初始化 backgroundPanel
        backgroundPanel.setBackground(new Color(0xf8f6ea));
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        add(backgroundPanel);


        // 返回上一级，ChildHome Page的按钮Exit
        exitButton.setBounds(20, 20, 134, 50);
        Font font = new Font(exitButton.getFont().getName(), Font.PLAIN, 20);
        exitButton.setFont(font);
        ImageIcon exitButtonIcon = new ImageIcon("images/ExitButtonImage.png");
        exitButton.setIcon(exitButtonIcon);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false); // 不填充内容区
        exitButton.setBorderPainted(false);
        backgroundPanel.add(exitButton);

        // 创建带滚动条的区域
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(new Color(0xfcfcf7));

        scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setBounds(670, 200, 550, 520); // 调整位置和大小
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
                switch (task.getStatus()) {
                    case "terminated":
                        scrollPanel.add(new TaskLabel.TerminatedTaskLabel(task.getId(), task.getTaskName(), task.getDescription(), task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate()));
                        break;
                    case "ongoing":
                        scrollPanel.add(new Child_OngoingTaskLabel(task.getId(), task.getTaskName(), task.getDescription(),task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate()));
                        break;
                    case "not_accepted":
                        scrollPanel.add(new Child_NotAcceptedTaskLabel(task.getId(), task.getTaskName(), task.getDescription(),task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate()));
                        break;
                    case "finished":
                        scrollPanel.add(new TaskLabel.FinishedTaskLabel(task.getId(), task.getTaskName(), task.getDescription(),task.getReward(), task.getChildName(), task.getStartDate(), task.getEndDate()));
                        break;
                }
                scrollPanel.add(Box.createVerticalStrut(10));
            }

            scrollPanel.revalidate();
            scrollPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon background = new ImageIcon("images/ChildTaskPage.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        backgroundPanel.add(backgroundLabel);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public class Child_NotAcceptedTaskLabel extends TaskLabel.NotAcceptedTaskLabel {
        private JButton giveUpButton;
        private JButton acceptButton;

        public JButton getGiveUpButton() {
            return giveUpButton;
        }

        public JButton getAcceptButton() {
            return acceptButton;
        }

        public Child_NotAcceptedTaskLabel(String id, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            giveUpButton = new JButton();
            giveUpButton.setBounds(290, 90, 110, 40);
            ImageIcon giveupButtonImage = new ImageIcon("images/GiveUpButtonImage.png");
            giveUpButton.setIcon(giveupButtonImage);
            giveUpButton.setBorder(null);
            giveUpButton.setContentAreaFilled(false); // 不填充内容区
            giveUpButton.setBorderPainted(false);
            this.add(giveUpButton);

            acceptButton = new JButton();
            acceptButton.setBounds(185, 90, 110, 40);
            ImageIcon acceptButtonImage = new ImageIcon("images/AcceptButtonImage.png");
            acceptButton.setIcon(acceptButtonImage);
            acceptButton.setOpaque(false);
            acceptButton.setBorder(null);
            acceptButton.setContentAreaFilled(false); // 不填充内容区
            acceptButton.setBorderPainted(false);
            this.add(acceptButton);
        }
    }

    public class Child_OngoingTaskLabel extends TaskLabel.OngoingTaskLabel {
        private JButton giveUPButton;
        private JButton submitButton;

        public JButton getGiveUPButton() {
            return giveUPButton;
        }

        public JButton getSubmitButton() {
            return submitButton;
        }

        public Child_OngoingTaskLabel(String id, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            giveUPButton = new JButton();
            giveUPButton.setBounds(290, 90, 110, 40);
            ImageIcon giveupButtonImage = new ImageIcon("images/GiveUpButtonImage.png");
            giveUPButton.setIcon(giveupButtonImage);
            giveUPButton.setBorder(null);
            giveUPButton.setContentAreaFilled(false); // 不填充内容区
            giveUPButton.setBorderPainted(false);
            this.add(giveUPButton);

            submitButton = new JButton();
            submitButton.setBounds(185, 90, 110, 40);
            ImageIcon submitButtonImage = new ImageIcon("images/SubmitButtonImage.png");
            submitButton.setIcon(submitButtonImage);
            submitButton.setBorder(null);
            submitButton.setContentAreaFilled(false); // 不填充内容区
            submitButton.setBorderPainted(false);
            this.add(submitButton);
        }
    }

    public static void main(String[] args) {
        Page05_ChildTask page05ChildTask = new Page05_ChildTask();
    }
}
