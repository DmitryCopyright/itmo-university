package core;
        import utility.FirstInterface;

public class Naznayka implements FirstInterface{
    private String name;
    boolean tryunderstand;
    boolean dirty;
    boolean run;
    boolean scaryornot;
    public Naznayka() {
        name = "Незнайка";
        joinStory();
        tryunderstand = false;
        dirty = true;
        run = false;

    }
    public Naznayka(String name){
        this.name = name;
        joinStory();
    }
    public String cleaneyes(){
        return(" протирать глаза руками");
    }
    public String tryuseeyes(){
        return(" разглядеть в полутьме");
    }
    public String understand(){
        if (tryunderstand){
            return(" понимал");
        }
        return(" не понимал");
    }
    public String dirtyHands(){
        if (dirty){
            return(" которой были испачканы руки");
        }
        return(" которой искачался раньше");
    }
    public String snuggleUp(){

        return(" прижавшись спиной к двери");
    }
    public String runordef(){
    if(run) {
        return (" приготовился бежать");
    }
    return(" приготовился защищаться");
    }
    public String scaryor(){
        if(scaryornot) {
            return (" испугался");
        }
        else return(" бояться не надо");
    }

    private void joinStory() {
        System.out.println("Незнайка '" + name + "' присоединился к истории.");
    }
    public void room(){
        System.out.println("Незнайка'"+name+"'только попал в котажку и");
    }
    public void eyes(){
        System.out.println("Незнайка'"+name+"' принялся " + cleaneyes()+ ", пытаясь хоть что-нибудь" + tryuseeyes());
    }
    public void unlucky(){
        System.out.println("Незнайка'"+name+"'"+ understand()+", что он размазал по лицу черную краску,"+dirtyHands());
    }
    public void scary(){
        scaryornot = true;
        System.out.println("Незнайка'"+name+scaryor()+"'и" + snuggleUp() + runordef());
    }
    public void smile(){
        scaryornot = false;
        System.out.println("Незнайка'"+name+"'понял, что "+scaryor()+" ,и его лицо тоже расплылось в улыбке.");

    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Незнайка '" + name + "'";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Naznayka) {
            return name.equals(((Naznayka) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
