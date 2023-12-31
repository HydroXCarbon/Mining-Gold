package Project3_135;
//6513135 Purin Pongpanich
//6513161 Jarupat Chodsitanan
//6513163 Chalisa Buathong

import Project3_135.components.CreditPage;
import Project3_135.components.MainPage;
import Project3_135.components.SettingPage;
import Project3_135.model.MySoundEffect;

import javax.swing.*;
import java.awt.*;


class MainApplication extends JFrame {
    private final int framewidth = Utilities.FRAMEWIDTH;
    private final int frameheight = Utilities.FRAMEHEIGHT;

    // components
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private MySoundEffect themeSound;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApplication().Main());
    }

    public void Main() {
        // Set up the frame
        setTitle("Mining Gold");
        setSize(framewidth, frameheight);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel and layout manager for the card panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Run theme sound
        themeSound = new MySoundEffect(Utilities.THEME_SOUND_PATH);
        themeSound.playLoop();
        themeSound.setVolume(0.5f);

        // Create different pages (JPanel) for your application
        JPanel settingPage = new SettingPage(cardPanel, cardLayout, themeSound);
        settingPage.setName("settingPage");

        JPanel creditPage = new CreditPage(cardPanel, cardLayout);
        creditPage.setName("creditPage");

        JPanel mainPage = new MainPage(cardPanel, cardLayout);
        mainPage.setName("mainPage");

        // Add pages to cardPanel
        cardPanel.add(mainPage, "mainPage");
        cardPanel.add(settingPage, "settingPage");
        cardPanel.add(creditPage, "creditPage");

        // Add cardPanel to the frame
        setContentPane(cardPanel);
        setVisible(true);
    }
}
