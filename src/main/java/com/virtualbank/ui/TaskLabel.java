import javax.swing.*;
import java.awt.*;

public class TaskLabel extends JLabel {

    private JButton deleteButton; // 每个任务的任务板的删除按钮

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public TaskLabel(Double prize, String due, String child, String id,
                     String taskName, String description) { // 任务板的父亲类
        Font font = new Font(this.getFont().getName(), Font.PLAIN, 18);
        this.setSize(523, 133);

        // 删除按钮
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(415, 90, 80, 40);
        this.add(deleteButton);

        // 奖励金额
        String prizeText = String.format("Prize:$%.2f", prize);
        JLabel prizeLabel = new JLabel(prizeText);
        prizeLabel.setBounds(400, -30, 220, 109);
        prizeLabel.setFont(font);
        this.add(prizeLabel);
        // 截止时间
        JLabel dueLabel = new JLabel("Due:" + due);
        dueLabel.setBounds(400, -5, 220, 109);
        dueLabel.setFont(font);
        this.add(dueLabel);
        // 哪个孩子的任务
        JLabel childLabel = new JLabel("Child:" + child);
        childLabel.setBounds(400, 20, 220, 109);
        childLabel.setFont(font);
        this.add(childLabel);
        // id和名称
        font = new Font(this.getFont().getName(), Font.PLAIN, 22);
        JLabel idLabel = new JLabel(id + " " + taskName);
        idLabel.setBounds(100, -20, 220, 109);
        idLabel.setFont(font);
        this.add(idLabel);
        // 任务描述，最长72字节
        font = new Font(this.getFont().getName(), Font.PLAIN, 16);
        JLabel descriptionLabel = new JLabel("<html>" + description + "</html>");
        descriptionLabel.setBounds(100, 50, 280, 109);
        descriptionLabel.setFont(font);
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP); // 设置垂直对齐方式为顶部
        descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT); // 设置水平对齐方式为左对齐
        descriptionLabel.setPreferredSize(new Dimension(230, 109)); // 设置首选大小，确保布局管理器可以正确计算多行高度
        descriptionLabel.setOpaque(false); // 设置透明度，确保背景色生效
        this.add(descriptionLabel);
    }

    public static class TerminatedTaskLabel extends TaskLabel { // 已终止的任务
        public TerminatedTaskLabel(Double prize, String due, String child, String id,
                                   String taskName, String description) {
            super(prize, due, child, id, taskName, description);
            // 图标
            ImageIcon taskIcon = new ImageIcon("images/TerminatedTask.png");
            this.setIcon(taskIcon);
        }
    }

    public static class FinishedTaskLabel extends TaskLabel { // 已完成的任务
        public FinishedTaskLabel(Double prize, String due, String child, String id,
                                 String taskName, String description) {
            super(prize, due, child, id, taskName, description);
            // 图标
            ImageIcon taskIcon = new ImageIcon("images/FinishedTask.png");
            this.setIcon(taskIcon);
        }
    }

    public static class OngoingTaskLabel extends TaskLabel { // 正在进行，也就是已接受的任务
        public OngoingTaskLabel(Double prize, String due, String child, String id,
                                String taskName, String description) {
            super(prize, due, child, id, taskName, description);
            // 图标
            ImageIcon taskIcon = new ImageIcon("images/OngoingTask.png");
            this.setIcon(taskIcon);
        }
    }

    public static class NotAcceptedTaskLabel extends TaskLabel { // 未接受的任务
        public NotAcceptedTaskLabel(Double prize, String due, String child, String id,
                                    String taskName, String description) {
            super(prize, due, child, id, taskName, description);
            // 图标
            ImageIcon taskIcon = new ImageIcon("images/NotAcceptedTask.png");
            this.setIcon(taskIcon);
        }
    }
}
