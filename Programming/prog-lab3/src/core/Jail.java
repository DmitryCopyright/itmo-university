package core;

import utility.FirstInterface;
import utility.TypeBuild;

public class Jail implements FirstInterface{
    private TypeBuild type = TypeBuild.PoliceDep;
    private String name;
    public static class Oven implements FirstInterface{
        private String name;
        public Oven() {
            name = "Чугунная печь";
            joinStory();

        }

        public Oven(String name) {
            this.name = name;
            joinStory();
        }

        private void joinStory() {
            System.out.println(name + " присоединилась к истории.");
        }

        public void location(){
            System.out.println(name + " стояла посреди " + room() + "и " +variki());
        }
        public String variki(){
            boolean var1 = true;
            boolean var2 = false;
            if (var1){
                return(" и трубы ее протягивались через " + room() + "у");
            }
            if (var2){
                return(" и она не работала");
            }
            else return("");
        }
        public String room(){
            return("каталажк");
        }

        @Override
        public String getName() {
            return name;
        }
        @Override
        public String toString() {
            return "Чугунная печь '" + name + "'";
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof core.Jail.Oven) {
                return name.equals(((core.Jail.Oven) obj).getName());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
    public Jail() {
        name = "Такелажное отделение";
        joinStory();

    }
    public Jail(String name){
        this.name = name;
        joinStory();
    }
    private void joinStory() {
        System.out.println(name + " присоединилось к истории.");
    }


    public void Tak(){
        System.out.println(name+ sleng() + " - комната в " + TypeFlat() + "напоминавшая корабельную кладовую,");
    }
    public void TakHave(){
        System.out.println("где были " + snasti() + "обычно именуемые" + nameT());
    }
    public void Diff(){
        System.out.println("разница была в том ,что " + Polki() + WhatDiff());
    }
    public String Polki(){
        return("на полках здесь лежали ");
    }
    public String WhatDiff(){
        boolean var1 = true;
        boolean var2 = false;
        if(var1) {
            return ("не снасти, а коротышки");
        }
        if(var2){
            return("ее не было");
        }
        return("");
    }
    public String snasti(){
        return(" различные корабельные снасти, ");
    }
    public String sleng(){
        return(" или, как его окрестили арестованные, каталажка");
    }
    public String nameT(){
        boolean name1 = false;
        boolean name2 = true;
        if (name1){
            return(" такелажными снастями");
        }
        if(name2){
            return(" такелажем");
        }
        else return("");
    }
    public String TypeFlat(){
        if (type == TypeBuild.BigHouse){
            return("большом доме, ");
        }
        else if (type == TypeBuild.Hotel){
            return("отеле, ");
        }
        else return("полицейском управлении, ");
    }



    @Override
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Такелажное отделение '" + name + "'";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Jail) {
            return name.equals(((Jail) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
