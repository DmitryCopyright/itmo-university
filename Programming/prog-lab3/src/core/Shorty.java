package core;
        import utility.FirstInterface;
        import utility.NoWords;

public class Shorty implements FirstInterface{
    private String name;
    private String nameN = "Незнайки";
    boolean see;
    boolean face;
    boolean smileornot;

    public Shorty() {
        name = "Коротышки";
        joinStory();
        see = true;
        face = true;
        smileornot = true;
    }
    public Shorty(String name){
        this.name = name;
        joinStory();
    }
    public String saw(){
        if (see){
            return(" увидели Незнайку");
        }
        return(" не увидели Незнайку");
    }
        public String gotoNezn(){
            if (see){
                return(" соскочили со своих полок и подбежали к нему");
            }
        return(" сидели молча");
        }
    public String sawFace(){
        if (face){
            return(" разглядев его измазанную физиономию");
        }
        return(" не увидев лица Незнайки");
    }
    private void joinStory() {
        System.out.println("Коротышки '" + name + "' присоединились к истории.");
    }
    public void meet(){
        System.out.println(name + saw() + gotoNezn());
    }
    public void laugth() throws NoWords {
        System.out.println("Коротышки '" + name + "'" + sawFace() + ", невольно рассмеялись");
        if (smileornot){
            System.out.println("Смех заглушал слова " + nameN);
        }
        else throw new NoWords();
    }

    public String took(){
       boolean t = true;
       boolean ice = false;
       boolean nothotnotice = true;
       if (t){
           System.out.print("вытаскивали из золы картошку ");
           if (ice == false){
               System.out.print("и начинали дуть на нее,");
           }
           if (nothotnotice){
               System.out.print(" пытаясь окончательно остудить ее");
           }
       }
      else return("очищали печь от золы");
      return "";
    }
    public String open(){
        return (" открывали");
    }

    public void MainShorty() {
        System.out.println("Иногда " + name + open() + " чугунную дверцу, ");
        System.out.println(took());
    }

    public void WithOven() {
        System.out.println(name + " сидели вокруг " + Oven() + " и " + TypeEnjoy());
    }

    public String Oven() {
        return ("печки");
    }
    public String TypeEnjoy(){
        boolean var1 = true;
        boolean var2 = false;
        if(var1){
            return("пекли в золе картошку");
        }
        else if (var2){
            return("грелись");
        }
        return("");
    }
    public String Money(){
        return("Фертинги");
    }
    public String Joke(){
        boolean goodornot = true;
        if (goodornot){
            return("посчитали остроумной");
        }
        return("посчитали глупой");
    }
    public void GoodJoke(){
        System.out.println("Все знали, что такое " + Money() + " и шутку Незнайки " + Joke());
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Коротышки '" + name + "'";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Shorty) {
            return name.equals(((Shorty) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
