import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display extends JPanel {
    EntityHandler entityHandler = new EntityHandler();
    int category = 3;
    final int HEIGHT = 500;
    final int WIDTH = 800;

    public Display() {

        setLayout(null);

        JButton categoryOneButton = new JButton("Category 1");
        categoryOneButton.setBounds(550, 25, 225, 75);
        categoryOneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                category = 1;
                repaint();
            }
        });
        
        JButton categoryTwoButton = new JButton("Category 2");
        categoryTwoButton.setBounds(550, 110, 225, 75);
        categoryTwoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                category = 2;
                repaint();
            }
        });
        
        JButton showAllButton = new JButton("Show all");
        showAllButton.setBounds(550, 195, 225, 75);
        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                category = 3;
                repaint();
            }
        });

        add(categoryTwoButton);
        add(categoryOneButton);
        add(showAllButton);
    }

    /*
     * Updates the display with command
     */
    public void update(String command) {
        try {
            entityHandler.server_command_update(command);
            repaint();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        
    }
    /**
     * Draws all Enities of the correct category on the display
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Entity entity : entityHandler.getMapValues()) {
            //category 3 is all categories
            if(category == entity.category || this.category == 3)
                entity.drawEntity(g);
        }

        //draws border between Map and buttons
        g.setColor(Color.BLACK);
        g.drawLine(540, 0, 540, HEIGHT);
    }
}
