package core;
import utility.FirstInterface;

public class Door implements FirstInterface{
    private String name;

    public Door() {
        name = "Дверь";
        joinStory();
    }
    public Door(String name){
        this.name = name;
        joinStory();
    }
    private void joinStory() {
        System.out.println("Дверь '" + name + "' присоединилась к истории.");
    }
    public void slammed(){
        System.out.println("Дверь'"+name +"'за ним захлопнулась,");
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Дверь '" + name + "'";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Door) {
            return name.equals(((Door) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
