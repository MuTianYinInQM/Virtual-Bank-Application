/**
 * The home page for a parent user to check task lists for his children
 * as well as create new tasks
 * @author Yunbo Jia, Liyang Qian
 * @version 4.0 2024-5-24
 */
package com.virtualbank.ui;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualbank.model.Task;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The Page04_ParentHome class represents the parent home page in the JoyBank application.
 * This page allows parents to view, create, and manage tasks.
 *
 * @see JFrame
 */
public class Page04_ParentHome extends JFrame {

    private JButton exitButton = new JButton();
    private JButton createTaskButton = new JButton();
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
     * Gets the create task button.
     *
     * @return the create task button
     */
    public JButton getCreateTaskButton() {
        return createTaskButton;
    }

    /**
     * Constructs a new Page04_ParentHome instance.
     * Sets up the main UI components and displays tasks from a JSON file.
     */
    public Page04_ParentHome() {
        final int window_width = 1260;
        final int window_height = 780;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Parent Home Page");
        backgroundPanel = new JPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, window_width, window_height);
        add(backgroundPanel);

        exitButton.setBounds(20, 20, 134, 50);
        Font font = new Font(exitButton.getFont().getName(), Font.PLAIN, 20);
        exitButton.setFont(font);
        ImageIcon exitButtonIcon = new ImageIcon(getClass().getResource("src/main/resources/images/ExitButtonImage.png"));
        exitButton.setIcon(exitButtonIcon);
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        backgroundPanel.add(exitButton);

        createTaskButton.setBounds(30, 135, 516, 98);
        ImageIcon createAccountButtonIcon = new ImageIcon(getClass().getResource("src/main/resources/images/CreateTaskButton.png"));
        createTaskButton.setIcon(createAccountButtonIcon);
        createTaskButton.setBorder(null);
        backgroundPanel.add(createTaskButton);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(new Color(0xfcfcf7));
        scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setBounds(660, 190, 550, 540);
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
        ImageIcon background = new ImageIcon(getClass().getResource("src/main/resources/images/ParentHomePage.png"));
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
     * The Parent_FinishedTaskLabel class represents a label for finished tasks with a confirm button.
     */
    public class Parent_FinishedTaskLabel extends TaskLabel.FinishedTaskLabel {

        private JButton confirmButton;

        /**
         * Gets the confirm button.
         *
         * @return the confirm button
         */
        public JButton getConfirmButton() { return confirmButton; }

        /**
         * Constructs a new Parent_FinishedTaskLabel instance.
         *
         * @param id          the task ID
         * @param taskName    the task name
         * @param description the task description
         * @param reward      the task reward
         * @param childName   the child's name
         * @param startDate   the task start date
         * @param dueDate     the task due date
         */
        public Parent_FinishedTaskLabel(String id, String taskName, String description, double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            confirmButton = new JButton();
            confirmButton.setBounds(290, 90, 100, 40);
            ImageIcon confirmButtonImage = new ImageIcon(getClass().getResource("src/main/resources/images/confirm3.png"));
            confirmButton.setIcon(confirmButtonImage);
            confirmButton.setBorder(null);
            confirmButton.setContentAreaFilled(false);
            confirmButton.setBorderPainted(false);
            this.add(confirmButton);

        }
    }

    /**
     * The Parent_NotAcceptedTaskLabel class represents a label for not accepted tasks with a terminate button.
     */
    public class Parent_NotAcceptedTaskLabel extends TaskLabel.NotAcceptedTaskLabel {

        private JButton terminateButton;

        /**
         * Gets the terminate button.
         *
         * @return the terminate button
         */
        public JButton getTerminateButton() {
            return terminateButton;
        }

        /**
         * Constructs a new Parent_NotAcceptedTaskLabel instance.
         *
         * @param id          the task ID
         * @param taskName    the task name
         * @param description the task description
         * @param reward      the task reward
         * @param childName   the child's name
         * @param startDate   the task start date
         * @param dueDate     the task due date
         */
        public Parent_NotAcceptedTaskLabel(String id, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            terminateButton = new JButton();
            terminateButton.setBounds(290, 90, 100, 40);
            ImageIcon terminateButtonImage = new ImageIcon(getClass().getResource("src/main/resources/images/TerminateButtonImage.png"));
            terminateButton.setIcon(terminateButtonImage);
            terminateButton.setBorder(null);
            terminateButton.setContentAreaFilled(false);
            terminateButton.setBorderPainted(false);
            this.add(terminateButton);
        }
    }

    /**
     * The Parent_OngoingTaskLabel class represents a label for ongoing tasks with a terminate button.
     */
    public class Parent_OngoingTaskLabel extends TaskLabel.OngoingTaskLabel {

        private JButton terminateButton;

        /**
         * Gets the terminate button.
         *
         * @return the terminate button
         */
        public JButton getTerminateButton() {
            return terminateButton;
        }

        /**
         * Constructs a new Parent_OngoingTaskLabel instance.
         *
         * @param id          the task ID
         * @param taskName    the task name
         * @param description the task description
         * @param reward      the task reward
         * @param childName   the child's name
         * @param startDate   the task start date
         * @param dueDate     the task due date
         */
        public Parent_OngoingTaskLabel(String id, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(id, taskName, description, reward, childName, startDate, dueDate);
            terminateButton = new JButton();
            terminateButton.setBounds(290, 90, 100, 40);
            ImageIcon terminateButtonImage = new ImageIcon(getClass().getResource("src/main/resources/images/TerminateButtonImage.png"));
            terminateButton.setIcon(terminateButtonImage);
            terminateButton.setBorder(null);
            terminateButton.setContentAreaFilled(false);
            terminateButton.setBorderPainted(false);
            this.add(terminateButton);
        }
    }

    /**
     * The main method to run the Page04_ParentHome application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Page04_ParentHome page04ParentHome = new Page04_ParentHome();
    }
}
