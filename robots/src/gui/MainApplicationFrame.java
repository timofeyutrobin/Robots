package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import log.Logger;
import net.Client;
import net.Server;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final GameWindow gameWindow = createGameWindow();

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        setTitle("Robots");


        JButton exitButton = new JButton("Выход");
        exitButton.setLocation(getWidth() - 50, 25);
        exitButton.setSize(100,50);
        exitButton.addActionListener(e -> processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        exitButton.setVisible(true);
        desktopPane.add(exitButton);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitWithDialog(e);
            }
        });
        pack();
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void exitWithDialog(WindowEvent event) {
        Object[] options = { "Да", "Нет" };
        int chosenOption = JOptionPane.showOptionDialog(event.getWindow(), "Закрыть окно?",
                "Подтверждение", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options,
                options[1]);
        if (chosenOption == 0) {
            event.getWindow().setVisible(false);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected GameWindow createGameWindow() {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setLocation(230, 10);
        return gameWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void startServer() {
        Server server = new Server(gameWindow);
        String message = "IP: " + server.getIpAddress();
        JOptionPane.showMessageDialog(this, message,
                "Сервер ожидает подключение", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startClient() {
        String ip = JOptionPane.showInputDialog(this,
                "Введите IP-адрес компьютера, к которому хотите подключиться.\n(localhost - собственный компьютер)");
        if (ip == null) return;
        new Client(gameWindow, ip);
    }
    
    private JMenuBar generateMenuBar()
    {
        MenuBar menuBar = new MenuBar();

        JMenu lookAndFeelMenu = menuBar.createMenu("Режим отображения", KeyEvent.VK_V, "Управление режимом отображения приложения");
        JMenuItem systemLookItem = menuBar.addItem(lookAndFeelMenu, "Системная схема", KeyEvent.VK_S);
        JMenuItem crossplatformLookItem = menuBar.addItem(lookAndFeelMenu, "Универсальная схема", KeyEvent.VK_U);

        JMenu testMenu = menuBar.createMenu("Тесты", KeyEvent.VK_T, "Тестовые команды");
        JMenuItem logMessageItem = menuBar.addItem(testMenu, "Сообщение в лог", KeyEvent.VK_M);

        JMenu otherMenu = menuBar.createMenu("Другое", KeyEvent.VK_O, null);
        JMenuItem exitItem = menuBar.addItem(otherMenu, "Выход", KeyEvent.VK_Q);
        JMenuItem saveItem = menuBar.addItem(otherMenu, "Сохранить");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        JMenuItem loadItem = menuBar.addItem(otherMenu, "Загрузить");
        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));

        JMenu netMenu = menuBar.createMenu("Сеть", KeyEvent.VK_N, "Сетевые функции");
        JMenuItem getConnectionItem = menuBar.addItem(netMenu, "Принять подключение");
        JMenuItem connectItem = menuBar.addItem(netMenu, "Подключиться");

        systemLookItem.addActionListener(e -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        crossplatformLookItem.addActionListener(e -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        logMessageItem.addActionListener(e -> Logger.debug("Тук-тук"));
        exitItem.addActionListener(e -> processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        saveItem.addActionListener(e -> {
            gameWindow.saveMap();
            JOptionPane.showMessageDialog(this, "Карта сохранена");
        });
        loadItem.addActionListener(e -> {
            gameWindow.loadMap();
            JOptionPane.showMessageDialog(this, "Карта загружена из файла");
        });
        getConnectionItem.addActionListener(e -> startServer());
        connectItem.addActionListener(e -> startClient());

        menuBar.addMenus(lookAndFeelMenu, testMenu, otherMenu, netMenu);
        return menuBar;
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
