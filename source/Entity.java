public class Entity {
    int x;
    int y;
    int type;
    String color;
    boolean visable;

    public Entity(int x_position, int y_position, int entity_type, boolean visable) {
        this.x = x_position;
        this.y = y_position;
        this.type = entity_type;
        this.color = "red";
        this.visable = visable;
    }

    public void update_position(int x_position, int y_position) {
        x = x_position;
        y = y_position;
    }
    
    public void set_visable(boolean visable) {
        this.visable = visable;
    }
    

}
