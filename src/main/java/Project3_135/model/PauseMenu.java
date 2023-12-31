package Project3_135.model;
//6513135 Purin Pongpanich
//6513161 Jarupat Chodsitanan
//6513163 Chalisa Buathong
import Project3_135.Utilities;
import Project3_135.components.GamePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PauseMenu extends JPanel {

    private GamePage pf;
    private CardLayout cardLayout;
    private JLabel countdownLabel;
    private JLabel scoreLabel;
    private JLabel pauseLabel;
    private JPanel cardPanel;
    private JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 20));
    private JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 150, 0));
    private JButton mainMenuButton;
    private JButton backButton;
    private JButton settingButton;
    private float opacity;
    private float originalOpacity;


    public PauseMenu(float opacity, JPanel cardPanel, CardLayout cardLayout, GamePage pf) {
        this.pf = pf;
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.originalOpacity = opacity;
        this.opacity = opacity;
        setPreferredSize(new Dimension(Utilities.FRAMEWIDTH, Utilities.FRAMEHEIGHT));
        setOpaque(false);
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setVisible(true);

        createScoreCounter();
        createCountdownLabel();
        createPauseLabel();

        // Load the default image icons
        ImageIcon mainMenuIcon = new MyImageIcon(Utilities.MENU_BUTTON_IMAGE_PATH).resize(95);
        ImageIcon backIcon = new MyImageIcon(Utilities.BACK_BUTTON_IMAGE_PATH).resize(95);
        ImageIcon pauseIcon = new MyImageIcon(Utilities.STONE_SETTING_BUTTON_IMAGE_PATH).resize(70);

        // Load the hover image icons
        ImageIcon mainMenuIconHover = new MyImageIcon(Utilities.MENU_HOVER_BUTTON_PATH).resize(95);
        ImageIcon backIconHover = new MyImageIcon(Utilities.BACK_HOVER_BUTTON_PATH).resize(95);
        ImageIcon pauseIconHover = new MyImageIcon(Utilities.STONE_SETTING_BUTTON_HOVER_IMAGE_PATH).resize(70);

        // Create buttons with default icons
        mainMenuButton = createButton(mainMenuIcon);
        backButton = createButton(backIcon);
        settingButton = createButton(pauseIcon);

        // Add components listener to the buttons
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (pf.checkGameEnd()) {
                    return;
                }
                pf.requestFocus();
                pf.pauseGame();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getComponent() instanceof JButton) {
                    ((JButton) e.getComponent()).setIcon(pauseIconHover);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getComponent() instanceof JButton) {
                    ((JButton) e.getComponent()).setIcon(pauseIcon);
                }
            }
        });

        mainMenuButton.addMouseListener(new MyMouseListener("mainPage", mainMenuIcon, mainMenuIconHover, cardPanel, cardLayout) {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current progress will be lost.<br>Are you sure you want to proceed?</html>", "Confirm");

                if (confirmationDialog.isConfirmed()) {
                    pf.stopGame();
                    super.mouseClicked(e);
                }
                pf.requestFocus();
            }
        });

        backButton.addMouseListener(new MyMouseListener(null, backIcon, backIconHover, cardPanel, cardLayout) {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                pf.resumeGame();
            }
        });

        // Add components to the top panel
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 50, 60));
        rightPanel.setPreferredSize(new Dimension(1150, 110));
        rightPanel.setOpaque(false);
        rightPanel.add(scoreLabel);
        rightPanel.add(countdownLabel);
        topPanel.setOpaque(false);
        topPanel.add(settingButton);
        topPanel.add(rightPanel);

        // Add components to the center panel
        centerPanel.setOpaque(false);
        centerPanel.add(pauseLabel);
        centerPanel.add(mainMenuButton);
        centerPanel.add(backButton);

        // Add the Panel to the frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void setScore(int score){
        scoreLabel.setText(String.valueOf(score));
    }

    public void setCountdown(int countdown){
        countdownLabel.setText(String.valueOf(countdown));
    }

    private void createPauseLabel(){
        pauseLabel = new JLabel("Pause", SwingConstants.CENTER);
        pauseLabel.setForeground(Color.WHITE);
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 200));
    }

    private void createScoreCounter(){
        scoreLabel = new JLabel(String.valueOf(0));
        scoreLabel.setPreferredSize(new Dimension(120, 50));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Monaco", Font.BOLD, 50));
    }

    private void createCountdownLabel() {
        countdownLabel = new JLabel(String.valueOf(Utilities.GAMETIME));
        countdownLabel.setPreferredSize(new Dimension(100, 50));
        countdownLabel.setForeground(Color.WHITE);
        countdownLabel.setFont(new Font("Monaco", Font.BOLD, 50));
    }

    public void setVisibility(boolean visible) {
        centerPanel.setVisible(visible);
        if(visible){
            opacity = originalOpacity;
        }else{
            opacity = 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Use AlphaComposite to set the opacity
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaComposite);

        // Draw the panel content
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }

    private JButton createButton(ImageIcon icon) {
        JButton button = new JButton(icon);
        setButtonStyle(button);
        return button;
    }

    private void setButtonStyle(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(50, 0, 0, 0));
    }
}
