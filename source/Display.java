import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Display extends JPanel {
    EntityHandler entityHandler = new EntityHandler();
    int category = 3;
    BufferedImage map;
    final int HEIGHT = 350;
    final int WIDTH = 450;
    final int offset = 10;
    final int BTNWIDTH = 100;
    final int BTNHEIGHT = 50;

    //constructor for display
    public Display() {

        setLayout(null);

        JButton categoryOneButton = new JButton("Category 1");
        categoryOneButton.setBounds(320, 25, 100, 50);
        categoryOneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                category = 1;
                repaint();
            }
        });
        
        JButton categoryTwoButton = new JButton("Category 2");
        categoryTwoButton.setBounds(320, 85, BTNWIDTH, BTNHEIGHT);
        categoryTwoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                category = 2;
                repaint();
            }
        });
        
        JButton showAllButton = new JButton("Show all");
        showAllButton.setBounds(320, 145, BTNWIDTH, BTNHEIGHT);
        showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                category = 3;
                repaint();
            }
        });

        add(categoryTwoButton);
        add(categoryOneButton);
        add(showAllButton);

        //load map file
        try {
            map = ImageIO.read(new File("C:\\Users\\jonat\\Documents\\saab\\kodprov\\map.gif"));
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    /*
     * Updates the display with command from server
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
        g.drawImage(map, offset,offset, this);
        for (Entity entity : entityHandler.getMapValues()) {
            //category 3 is all categories
            if(category == entity.category || this.category == 3)
                entity.drawEntity(g);
        }
    }
}
