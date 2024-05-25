/**
 * The home page for a child user to check and operate on his tasks
 * @author Yunbo Jia, Liyang Qian
 * @version 3.0 2024-5-21
 */
package com.virtualbank.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.virtualbank.model.Task;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The Page05_ChildTask class represents the child task page in the JoyBank application.
 * This page allows children to view and manage their tasks.
 */
public class Page05_ChildTask extends JFrame{

    private JButton exitButton = new JButton();
    private JPanel backgroundPanel;
    private JScrollPane scrollPane;

    /**
     * Gets the exit button.
     *
     * @return the exit button
     */
    public JButton getExitButton() {
        return exitButton;
    }

    /**
     * Constructs a new Page05_ChildTask instance.
     * Sets up the main UI components and displays tasks from a JSON file.
     */
    public Page05_ChildTask() {
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Child Task Page");

        ImageIcon loginBackgroundImageIcon = new ImageIcon(getClass().getResource("/images/TaskBoardBackground.png"));
        backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(0xf8f6ea));
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        add(backgroundPanel);

        exitButton.setBounds(20, 20, 134, 50);
        Font font = new Font(exitButton.getFont().getName(), Font.PLAIN, 20);
        exitButton.setFont(font);
        ImageIcon exitButtonIcon = new ImageIcon(getClass().getResource("/images/ExitButtonImage.png"));
        exitButton.setIcon(exitButtonIcon);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        backgroundPanel.add(exitButton);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(new Color(0xfcfcf7));

        scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setBounds(670, 200, 550, 520);
        scrollPane.setBackground(new Color(0xf8f6ea));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        backgroundPanel.add(scrollPane);

        displayTasksFromJsonFile("src/main/resources/tasks.json");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Displays tasks from a JSON file.
     * Reads tasks from the specified JSON file and displays them in the scroll panel.
     *
     * @param jsonFilePath the path to the JSON file containing task data
     */
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
        ImageIcon background = new ImageIcon(getClass().getResource("/images/ChildTaskPage.png"));
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1260, 780);
        backgroundPanel.add(backgroundLabel);
    }

    /**
     * Gets the scroll pane containing the tasks.
     *
     * @return the scroll pane
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * The Child_NotAcceptedTaskLabel class represents a label for not accepted tasks with accept and give up buttons.
     */
    public class Child_NotAcceptedTaskLabel extends TaskLabel.NotAcceptedTaskLabel {
        private JButton giveUpButton;
        private JButton acceptButton;

        /**
         * Gets the give up button.
         *
         * @return the give up button
         */
        public JButton getGiveUpButton() {
            return giveUpButton;
        }

        /**
         * Gets the accept button.
         *
         * @return the accept button
         */
        public JButton getAcceptButton() {
            return acceptButton;
        }

        /**
         * Constructs a new Child_NotAcceptedTaskLabel instance.
         *
         * @param id          the task ID
         * @param taskName    the task name
         * @param description the task description
         * @param reward      the task reward
         * @param childName   the child's name
         * @param startDate   the task start date
         * @param dueDate     the task due date
         */
        public Child_NotAcceptedTaskLabel(String id, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            giveUpButton = new JButton();
            giveUpButton.setBounds(290, 90, 110, 40);
            ImageIcon giveupButtonImage = new ImageIcon(getClass().getResource("/images/GiveUpButtonImage.png"));
            giveUpButton.setIcon(giveupButtonImage);
            giveUpButton.setBorder(null);
            giveUpButton.setContentAreaFilled(false);
            giveUpButton.setBorderPainted(false);
            this.add(giveUpButton);

            acceptButton = new JButton();
            acceptButton.setBounds(185, 90, 110, 40);
            ImageIcon acceptButtonImage = new ImageIcon(getClass().getResource("/images/AcceptButtonImage.png"));
            acceptButton.setIcon(acceptButtonImage);
            acceptButton.setOpaque(false);
            acceptButton.setBorder(null);
            acceptButton.setContentAreaFilled(false);
            acceptButton.setBorderPainted(false);
            this.add(acceptButton);
        }
    }

    /**
     * The Child_OngoingTaskLabel class represents a label for ongoing tasks with give up and submit buttons.
     */
    public class Child_OngoingTaskLabel extends TaskLabel.OngoingTaskLabel {
        private JButton giveUPButton;
        private JButton submitButton;

        /**
         * Gets the give up button.
         *
         * @return the give up button
         */
        public JButton getGiveUPButton() {
            return giveUPButton;
        }

        /**
         * Gets the submit button.
         *
         * @return the submit button
         */
        public JButton getSubmitButton() {
            return submitButton;
        }

        /**
         * Constructs a new Child_OngoingTaskLabel instance.
         *
         * @param id          the task ID
         * @param taskName    the task name
         * @param description the task description
         * @param reward      the task reward
         * @param childName   the child's name
         * @param startDate   the task start date
         * @param dueDate     the task due date
         */
        public Child_OngoingTaskLabel(String id, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            giveUPButton = new JButton();
            giveUPButton.setBounds(290, 90, 110, 40);
            ImageIcon giveupButtonImage = new ImageIcon(getClass().getResource("/images/GiveUpButtonImage.png"));
            giveUPButton.setIcon(giveupButtonImage);
            giveUPButton.setBorder(null);
            giveUPButton.setContentAreaFilled(false);
            giveUPButton.setBorderPainted(false);
            this.add(giveUPButton);

            submitButton = new JButton();
            submitButton.setBounds(185, 90, 110, 40);
            ImageIcon submitButtonImage = new ImageIcon(getClass().getResource("/images/SubmitButtonImage.png"));
            submitButton.setIcon(submitButtonImage);
            submitButton.setBorder(null);
            submitButton.setContentAreaFilled(false);
            submitButton.setBorderPainted(false);
            this.add(submitButton);
        }
    }

    /**
     * The main method to run the Page05_ChildTask application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Page05_ChildTask page05ChildTask = new Page05_ChildTask();
    }
}
