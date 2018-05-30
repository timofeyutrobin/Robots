package gui;

import java.awt.BorderLayout;

import javax.swing.*;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;

    public GameWindow() 
    {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);

        JPanel subPanel = new JPanel();

        JButton setTargetButton = new JButton("Задать цель");
        setTargetButton.addActionListener(e -> m_visualizer.setMode(GameVisualizer.Mode.SET_TARGET_MODE));
        setTargetButton.setVisible(true);
        subPanel.add(setTargetButton);

        JButton newRobotButton = new JButton("Новый Робот");
        newRobotButton.addActionListener(e -> m_visualizer.setMode(GameVisualizer.Mode.ADD_ROBOT_MODE));
        newRobotButton.setVisible(true);
        subPanel.add(newRobotButton);

        JButton newObstacleButton = new JButton("Новый прямоугольник");
        newObstacleButton.addActionListener(e -> m_visualizer.setMode(GameVisualizer.Mode.ADD_OBSTACLE_MODE));
        newObstacleButton.setVisible(true);
        subPanel.add(newObstacleButton);

        panel.add(subPanel, BorderLayout.NORTH);
        getContentPane().add(panel);
        pack();
    }
}
