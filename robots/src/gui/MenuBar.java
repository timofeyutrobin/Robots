package gui;

import javax.swing.*;

class MenuBar extends JMenuBar {

    void addMenus(JMenu... menus) {
        for (JMenu menu : menus) {
            this.add(menu);
        }
    }

    JMenu createMenu(String title, int mnemonicKeyCode, String description) {
        JMenu menu = new JMenu(title);
        menu.setMnemonic(mnemonicKeyCode);
        menu.getAccessibleContext().setAccessibleDescription(description);
        return menu;
    }

    JMenuItem addItem(JMenu menu, String itemName, int itemMnemonic) {
        JMenuItem item = new JMenuItem(itemName, itemMnemonic);
        menu.add(item);
        return item;
    }

    JMenuItem addItem(JMenu menu, String itemName) {
        JMenuItem item = new JMenuItem(itemName);
        menu.add(item);
        return item;
    }
}
