import java.awt.*;

public class Entity {
    int x;
    int y;
    int type;
    int category;
    boolean visable;
    Color color;

    public Entity(int x_position, int y_position, int entity_type, boolean visable) {
        this.x = x_position;
        this.y = y_position;
        this.type = entity_type;
        if(entity_type == 1) {
            this.color = Color.GREEN;
            this.category = 1;
        }
        else if(entity_type == 2) {
            color = Color.RED;
            this.category = 1;
        }
        else if(entity_type == 3) {
            color = Color.BLUE;
            this.category = 2;
        }
        this.visable = visable;
    }

    public void update_position(int x_position, int y_position) {
        x = x_position;
        y = y_position;
    }
    
    public void set_visable(boolean visable) {
        this.visable = visable;
    }

    public void drawEntity(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 30, 30);
    }
    // @Override
    // public void paint(Graphics g) {
    //     g.setColor(color);
    //     g.fillOval(x, y, 20, 20);
    // }

}
