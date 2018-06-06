package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();

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

        GameWindow gameWindow = new GameWindow();
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
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }
    
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

        menuBar.addMenus(lookAndFeelMenu, testMenu, otherMenu);
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
