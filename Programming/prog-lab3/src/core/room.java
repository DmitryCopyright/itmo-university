package core;
import utility.AbstractRoom;
import utility.RoomType;

public class room extends AbstractRoom{
    private String name;
    private RoomType Type = RoomType.WITHTWOWIN;

    public String TypeR(){
        if(Type == RoomType.WITHOUTWIN){
            return("Помещение без окон");
        }
        if(Type == RoomType.WITHTWOWIN){
            return("Помещение с двумя окнами");
        }
        else return("Помещение с одним окном");

    }
    public room() {
        super(true);
        name = "Помещение";
        joinStory();
    }
    public room(String name){
        super(true);
        this.name = name;
        joinStory();
    }
    public room(String name, boolean window){
        super(window);
        this.name = name;
        joinStory();
    }
    private void joinStory() {
     System.out.println(TypeR()+"'" + name + "' присоединилось к истории.");
    }
    public void descriptionroom(){
        System.out.println(TypeR()+"'"+name +"'освещалось лампочкой");
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public RoomType getType() {
        return Type;
    }
    @Override
    public String toString() {
        if (window()) return "Помещение без окон '" + name + "'";
    return TypeR()+"'" +name + "'";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof room) {
            return name.equals(((room) obj).getName()) && window() == ((room)obj).window();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if(window()) return name.hashCode()+name.length();
        return name.hashCode();
    }
}
