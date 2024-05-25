/**
 * Define the task label used in child's task page and parent's home page
 * @author Yunbo Jia
 * @version 3.0 2024-5-21
 */
package com.virtualbank.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a label for a task, extending {@link JLabel}.
 * This class contains information about the task such as its ID, name, description,
 * reward, associated child, start date, and due date. It also includes a delete button.
 *
 * @see JLabel
 */
public class TaskLabel extends JLabel {

    private String taskId;
    private JButton deleteButton;

    /**
     * Gets the task ID.
     *
     * @return the task ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Gets the delete button.
     *
     * @return the delete button
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * Constructs a new TaskLabel with the specified details.
     *
     * @param taskId the task ID
     * @param taskName the name of the task
     * @param description the description of the task
     * @param reward the reward for completing the task
     * @param childName the name of the child associated with the task
     * @param startDate the start date of the task
     * @param dueDate the due date of the task
     */
    public TaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
        this.taskId = taskId;
        Font font = new Font("Arial", Font.PLAIN, 16);
        this.setLayout(null);
        this.setBorder(null);
        this.setPreferredSize(new Dimension(523, 138));

        JLabel nameLabel = new JLabel(taskName);
        nameLabel.setBounds(100, -20, 220, 109);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        this.add(nameLabel);

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setBounds(100, 50, 280, 59);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        this.add(descriptionArea);

        JLabel prizeLabel = new JLabel("Prize: $" + String.format("%.2f", reward));
        prizeLabel.setVerticalAlignment(SwingConstants.TOP);
        prizeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        prizeLabel.setBounds(400, 10, 220, 109);
        prizeLabel.setFont(font);
        this.add(prizeLabel);

        JLabel startLabel = new JLabel("Start: " + startDate);
        startLabel.setVerticalAlignment(SwingConstants.TOP);
        startLabel.setHorizontalAlignment(SwingConstants.LEFT);
        startLabel.setBounds(400, 30, 220, 109);
        startLabel.setFont(font);
        this.add(startLabel);

        JLabel dueLabel = new JLabel("Due: " + dueDate);
        dueLabel.setVerticalAlignment(SwingConstants.TOP);
        dueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dueLabel.setBounds(400, 50, 220, 109);
        dueLabel.setFont(font);
        this.add(dueLabel);

        JLabel childLabel = new JLabel("Child: " + childName);
        childLabel.setVerticalAlignment(SwingConstants.TOP);
        childLabel.setHorizontalAlignment(SwingConstants.LEFT);
        childLabel.setBounds(400, 70, 220, 109);
        childLabel.setFont(font);
        this.add(childLabel);

        deleteButton = new JButton();
        deleteButton.setBounds(390, 90, 120, 40);
        ImageIcon deleteButtonImage = new ImageIcon(getClass().getResource("/images/DeleteButtonImage.png"));
        deleteButton.setIcon(deleteButtonImage);
        deleteButton.setBorder(null);
        deleteButton .setContentAreaFilled(false);
        deleteButton.setBorderPainted(false);
        this.add(deleteButton);
    }

    /**
     * Represents a terminated task label, a subclass of TaskLabel
     *
     * @see com.virtualbank.ui.TaskLabel
     */
    public static class TerminatedTaskLabel extends TaskLabel {
        /**
         * Constructs a new TerminatedTaskLabel with the specified details.
         *
         * @param taskId the task ID
         * @param taskName the name of the task
         * @param description the description of the task
         * @param reward the reward for completing the task
         * @param childName the name of the child associated with the task
         * @param startDate the start date of the task
         * @param dueDate the due date of the task
         */
        public TerminatedTaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskId, taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon(getClass().getResource("/images/TerminatedTask.png"));
            this.setIcon(taskIcon);
        }
    }

    /**
     * Represents a finished task label, a subclass of TaskLabel.
     *
     * @see com.virtualbank.ui.TaskLabel
     */
    public static class FinishedTaskLabel extends TaskLabel {
        /**
         * Constructs a new FinishedTaskLabel with the specified details.
         *
         * @param taskId the task ID
         * @param taskName the name of the task
         * @param description the description of the task
         * @param reward the reward for completing the task
         * @param childName the name of the child associated with the task
         * @param startDate the start date of the task
         * @param dueDate the due date of the task
         */
        public FinishedTaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskId, taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon(getClass().getResource("/images/FinishedTask.png"));
            this.setIcon(taskIcon);
        }
    }

    /**
     * Represents an ongoing task label, a subclass of TaskLabel.
     *
     * @see com.virtualbank.ui.TaskLabel
     */
    public static class OngoingTaskLabel extends TaskLabel {
        /**
         * Constructs a new OngoingTaskLabel with the specified details.
         *
         * @param taskId the task ID
         * @param taskName the name of the task
         * @param description the description of the task
         * @param reward the reward for completing the task
         * @param childName the name of the child associated with the task
         * @param startDate the start date of the task
         * @param dueDate the due date of the task
         */
        public OngoingTaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskId, taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon(getClass().getResource("/images/OngoingTask.png"));
            this.setIcon(taskIcon);
        }
    }

    /**
     * Represents a not accepted task label, a subclass of TaskLabel.
     *
     * @see com.virtualbank.ui.TaskLabel
     */
    public static class NotAcceptedTaskLabel extends TaskLabel {
        /**
         * Constructs a new NotAcceptedTaskLabel with the specified details.
         *
         * @param taskId the task ID
         * @param taskName the name of the task
         * @param description the description of the task
         * @param reward the reward for completing the task
         * @param childName the name of the child associated with the task
         * @param startDate the start date of the task
         * @param dueDate the due date of the task
         */
        public NotAcceptedTaskLabel(String taskId, String taskName, String description, Double reward, String childName, String startDate, String dueDate) {
            super(taskId, taskName, description, reward, childName, startDate, dueDate);
            ImageIcon taskIcon = new ImageIcon(getClass().getResource("/images/NotAcceptedTask.png"));
            this.setIcon(taskIcon);
        }
    }
}
