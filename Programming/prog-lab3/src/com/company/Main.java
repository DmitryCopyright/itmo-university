package com.company;

import core.*;
import utility.NoWords;

public class Main {
    public static void main(String[] args) throws NoWords {
         boolean anon = false;
        Door adoor = new Door();
        ElectricLamp aelectriclamp = new ElectricLamp();
        Naznayka neznayka = new Naznayka();
        room aroom = new room();

        Shorty ashorty = new Shorty();
        Shorty main = new Shorty(){
            public void anonim(){
            if(anon == true){
                System.out.println("Анонимный класс");
            }
            }
        };
        OtherTypeShorty shorty = new OtherTypeShorty();
        Jail ja = new Jail();
        Jail.Oven ov = new Jail.Oven();
        ja.Tak();
        ja.TakHave();
        ja.Diff();
        ov.location();
        ov.variki();
        ashorty.WithOven();
        main.MainShorty();
        shorty.WhatDoing();
        aroom.descriptionroom();
        aelectriclamp.light();
        neznayka.room();
        adoor.slammed();
        neznayka.eyes();
        neznayka.unlucky();
        ashorty.meet();
        neznayka.scary();
        neznayka.smile();
        ashorty.laugth();
        ashorty.GoodJoke();
    }
}
