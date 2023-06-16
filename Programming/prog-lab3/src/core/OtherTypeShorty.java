package core;

import utility.FirstInterface;
import utility.WhatShortyDoing;
import utility.TypeGame;

public class OtherTypeShorty implements FirstInterface {
    private String name;
    boolean sewornot = true;

    boolean playornot = true;
    private TypeGame game = TypeGame.Raschibalochka;
    private WhatShortyDoing Type1 = WhatShortyDoing.Playing;
    private WhatShortyDoing Type2 = WhatShortyDoing.Sewing;
    private WhatShortyDoing Type3 = WhatShortyDoing.SitOnTheShelf;
    private WhatShortyDoing Type4 = WhatShortyDoing.SitOnTheFloor;
    private WhatShortyDoing Type5 = WhatShortyDoing.TrueStory;
    public OtherTypeShorty() {
        name = "Другие коротышки";
        joinStory();

    }
    public OtherTypeShorty(String name){
        this.name = name;
        joinStory();
    }
    private void joinStory() {
        System.out.println(name + " присоединились к истории.");
    }
    public String Sit(){
        if (Type3 == WhatShortyDoing.SitOnTheShelf){
            return(" сидели на полках");
        }
        else return (" сидели на полу");
    }
    public String sew(){
        if (sewornot){
            return(" кто-то штопал свою одежонку");
        }
        return "";
    }
    private class plays{
        public String play() {
            if (playornot) {
                return (", кто-то играл с приятелями");
            }
            return "";
        }
    }
    plays playing = new plays();
    public String useplay(){
        return playing.play();
    }
    public String typeG(){
        if (game == TypeGame.Raschibalochka){
            return(" в расшибалочку");
        }
        else if (game == TypeGame.Dogonyalki){
            return(" в догонялки");
        }
        else return(" в хоккей");
    }
    public String trueStory(){
        class variants{
            public String TrueSt(){
                final boolean trueS = true;
                if (trueS){
                    return(", кто-то рассказывал грустную историю своей жизни");
                }
                return "";
            }
        }
       variants var = new variants();
        return var.TrueSt();
    }
public void WhatDoing(){
    System.out.println(name + Sit()+ ", каждый занимался своим делом:" + sew() + useplay() + typeG() + trueStory());
}
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Другие коротышки '" + name + "'";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof OtherTypeShorty) {
            return name.equals(((OtherTypeShorty) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
}
}
