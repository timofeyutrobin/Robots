package gui;

import logic.GameMap;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    static final String GAME_MAP_FILE = "robots/data/game.dat";

    public enum MouseMode {
        SET_TARGET_MODE,
        ADD_ROBOT_MODE,
        ADD_OBSTACLE_MODE,
        REMOVE_OBSTACLE_MODE
    }

    private final Timer m_timer = initTimer();
    private GameMap gameMap = new GameMap();
    private MouseMode mouseMode = MouseMode.SET_TARGET_MODE;
    
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
            public void mouseReleased(MouseEvent e)
            {
                switch (mouseMode) {
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
                    case REMOVE_OBSTACLE_MODE: {
                        gameMap.removeObstacle(e.getPoint());
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

    void setMouseMode(MouseMode mouseMode) {
        this.mouseMode = mouseMode;
    }

    void save() {
        File file = new File(GAME_MAP_FILE);
        try(FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameMap);
            oos.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    void load() {
        try (FileInputStream fis = new FileInputStream(GAME_MAP_FILE); ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameMap = (GameMap) ois.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
