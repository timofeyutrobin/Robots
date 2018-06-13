package gui;

import java.awt.*;
import java.io.*;

import javax.swing.*;

public class GameWindow extends JInternalFrame implements Serializable
{
    private final GameVisualizer m_visualizer;

    public GameWindow()
    {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(600,600));
        panel.add(m_visualizer, BorderLayout.CENTER);

        JPanel subPanel = new JPanel();
        subPanel.setBackground(new Color(0x8B09FF));

        JButton setTargetButton = new JButton("Задать цель");
        setTargetButton.addActionListener(e -> m_visualizer.setMouseMode(GameVisualizer.MouseMode.SET_TARGET_MODE));
        setTargetButton.setVisible(true);
        subPanel.add(setTargetButton);

        JButton newRobotButton = new JButton("Новый Робот");
        newRobotButton.addActionListener(e -> m_visualizer.setMouseMode(GameVisualizer.MouseMode.ADD_ROBOT_MODE));
        newRobotButton.setVisible(true);
        subPanel.add(newRobotButton);

        JButton newObstacleButton = new JButton("Новое препятствие");
        newObstacleButton.addActionListener(e -> m_visualizer.setMouseMode(GameVisualizer.MouseMode.ADD_OBSTACLE_MODE));
        newObstacleButton.setVisible(true);
        subPanel.add(newObstacleButton);

        JButton removeObstacleButton = new JButton("Стереть препятствие");
        removeObstacleButton.addActionListener(e -> m_visualizer.setMouseMode(GameVisualizer.MouseMode.REMOVE_OBSTACLE_MODE));
        removeObstacleButton.setVisible(true);
        subPanel.add(removeObstacleButton);

        panel.add(subPanel, BorderLayout.NORTH);
        getContentPane().add(panel);
        pack();
    }

    void save() {
        m_visualizer.save();
    }

    void loadMap() {
        m_visualizer.load();
    }
}
