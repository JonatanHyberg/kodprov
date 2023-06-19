import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityHandler {
    public HashMap<Long, Entity> entitiesMap = new HashMap<>();  
    Pattern pattern = Pattern.compile("\\d+");
    Pattern formatCheck = Pattern.compile("ID=\\d+;X=\\d+;Y=\\d+;TYPE=\\d+");
    
    public EntityHandler() {

    }

    public Collection<Entity>  getMapValues() {
        return entitiesMap.values();
    }

    //Input Parser
    public void server_command_update(String command) throws Exception {
        //checks correct input format
        if(!formatCheck.matcher(command).matches())
            throw new Exception("Wrong input format");

        Matcher matcher = pattern.matcher(command);
                matcher.find();
                long id = Long.parseLong(matcher.group());
                matcher.find();
                int x = Integer.parseInt(matcher.group());
                matcher.find();
                int y = Integer.parseInt(matcher.group());
                matcher.find();
                int type = Integer.parseInt(matcher.group());
                
                
                if(entitiesMap.containsKey(id)) {
                    entitiesMap.get(id).update_position(x, y);
                }
                else {
                    entitiesMap.put(id, new Entity(x, y, type));
                }
    }
}
