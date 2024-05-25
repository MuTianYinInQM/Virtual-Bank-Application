/**
 * The window for a parent user to create a new task.
 * @author Yunbo Jia
 * @version 3.0 2024-5-21
 */
package com.virtualbank.ui;

import com.virtualbank.controller.ParentHomeController;
import com.virtualbank.service.TaskService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.Border;

/**
 * Represents the window for creating a new task in the JoyBank application.
 * This class extends {@link JFrame} and includes fields for entering task details such as
 * task name, content, reward amount, child name, start date, and due date. It also includes
 * a confirmation button.
 *
 * @see JFrame
 */
public class Window3_CreateNewTask extends JFrame {
    private JTextField taskName_textField;
    private JTextField content_textField;
    private JTextField money_textField;
    private JTextField childName_textField;
    private JTextField start_textField;
    private JTextField due_textField;
    private JButton confirmButton;

    /**
     * Gets the text field for entering the task name.
     *
     * @return the text field for entering the task name
     */
    public JTextField getTaskName_textField() {
        return taskName_textField;
    }

    /**
     * Gets the text field for entering the task content.
     *
     * @return the text field for entering the task content
     */
    public JTextField getContent_textField() {
        return content_textField;
    }

    /**
     * Gets the text field for entering the reward amount.
     *
     * @return the text field for entering the reward amount
     */
    public JTextField getMoney_textField() {
        return money_textField;
    }

    /**
     * Gets the text field for entering the child's name.
     *
     * @return the text field for entering the child's name
     */
    public JTextField getChildName_textField() {
        return childName_textField;
    }

    /**
     * Gets the text field for entering the start date of the task.
     *
     * @return the text field for entering the start date of the task
     */
    public JTextField getStart_textField() {
        return start_textField;
    }

    /**
     * Gets the text field for entering the due date of the task.
     *
     * @return the text field for entering the due date of the task
     */
    public JTextField getDue_textField() {
        return due_textField;
    }

    /**
     * Gets the confirmation button.
     *
     * @return the confirmation button
     */
    public JButton getConfirmButton() {
        return confirmButton;
    }

    /**
     * Constructs a new window for creating a task.
     */
    public Window3_CreateNewTask() {
        final int window_width = 800;
        final int window_height = 701;
        setBounds(0, 0, window_width, window_height);
        setResizable(false);
        setLayout(null);
        setTitle("JoyBank - Creating a New Task");
        ImageIcon loginBackgroundImageIcon = new ImageIcon("src/main/resources/images/CreateTaskBackground.png");
        JLabel backgroundLabel = new JLabel(loginBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, window_width, window_height);
        getContentPane().add(backgroundLabel);

        Border border = BorderFactory.createLineBorder(new Color(0x3d6de1), 2);

        taskName_textField = new JTextField();
        taskName_textField.setBounds(330, 108, 200, 45);
        Font font = new Font(taskName_textField.getFont().getName(), Font.PLAIN, 20);
        taskName_textField.setFont(font);
        taskName_textField.setBorder(border);
        backgroundLabel.add(taskName_textField);

        content_textField = new JTextField();
        content_textField.setBounds(330, 168, 330, 45);
        content_textField.setFont(font);
        content_textField.setBorder(border);
        backgroundLabel.add(content_textField);

        money_textField = new JTextField();
        money_textField.setBounds(330, 235, 200, 45);
        money_textField.setFont(font);
        money_textField.setBorder(border);
        backgroundLabel.add(money_textField);

        childName_textField = new JTextField();
        childName_textField.setBounds(330, 295, 200, 45);
        childName_textField.setFont(font);
        childName_textField.setBorder(border);
        backgroundLabel.add(childName_textField);

        start_textField = new JTextField();
        start_textField.setBounds(330, 352, 200, 45);
        start_textField.setFont(font);
        start_textField.setBorder(border);
        backgroundLabel.add(start_textField);

        due_textField = new JTextField();
        due_textField.setBounds(330, 412, 200, 45);
        due_textField.setFont(font);
        due_textField.setBorder(border);
        backgroundLabel.add(due_textField);

        confirmButton = new JButton();
        confirmButton.setBounds(310, 600, 200, 55);
        ImageIcon confirmButtonIcon = new ImageIcon("src/main/resources/images/confirm2.png");
        confirmButton.setIcon(confirmButtonIcon);
        confirmButton.setBorder(null);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setBorderPainted(false);
        backgroundLabel.add(confirmButton);

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                Page04_ParentHome parentHomeUI = new Page04_ParentHome();
                TaskService taskService = new TaskService();
                new ParentHomeController(taskService, parentHomeUI);
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * The main method to create and display the window.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Window3_CreateNewTask window3CreateNewTask = new Window3_CreateNewTask();
    }
}
