package gui;

import logic.GameMap;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    public enum Mode {
        SET_TARGET_MODE,
        ADD_ROBOT_MODE,
        ADD_OBSTACLE_MODE
    }

    private final Timer m_timer = initTimer();
    private GameMap gameMap = new GameMap();
    private Mode mode = Mode.SET_TARGET_MODE;
    
    private static Timer initTimer() 
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public GameVisualizer()
    {
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                switch (mode) {
                    case SET_TARGET_MODE : {
                        gameMap.setTargetPosition(e.getPoint());
                        break;
                    }
                    case ADD_ROBOT_MODE : {
                        gameMap.addRobot(e.getPoint());
                        break;
                    }
                    case ADD_OBSTACLE_MODE : {
                        gameMap.addObstacle(e.getPoint());
                        break;
                    }
                }
            }
        });
        setDoubleBuffered(true);
    }
    
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }
    
    protected void onModelUpdateEvent()
    {
        gameMap.update();
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        gameMap.draw(g2d);
    }

    void setMode(Mode mode) {
        this.mode = mode;
    }
}
