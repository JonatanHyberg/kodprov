import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Display extends JPanel {
    
    public HashMap<Long, Entity> entitiesMap = new HashMap<>(); 
    EntityHandler entityHandler = new EntityHandler();
    int category = 3;

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

    public void update(String command) {
        entityHandler.server_command_update(command);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Entity entity : entityHandler.getMapValues()) {
            if(category == entity.category || this.category == 3)
                entity.drawEntity(g);
        }
    }
}
