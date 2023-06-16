package core;
import utility.FirstInterface;

public class ElectricLamp implements FirstInterface{
    private String name;
    private boolean lite;

    public ElectricLamp() {
        name = "Электрическая лампа";
        joinStory();
        lite = false;
    }
    public ElectricLamp(String name){
        this.name = name;
        joinStory();
    }

    public String SmallLite(){
      if (lite){
          return "была новой и освещала путь";
      }
      return("была тусклая и ничего не освещала");
    }

    private void joinStory() {
        System.out.println("Электрическая лампа '" + name + "' присоединилась к истории.");
    }
    public void light(){
            System.out.println("Электрическая лампа'" + name + "'" + SmallLite());

    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Электрическая лампа '" + name + "'";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof ElectricLamp) {
            return name.equals(((ElectricLamp) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
