package com.company;
import pokemons.*;
import ru.ifmo.se.pokemon.Battle;



class Main {
    public static void main(String args[]) {
        Battle b = new Battle();

        Cobalion p1 = new Cobalion("Игрок1", 1);
        Skitty p2 = new Skitty("Игрок2", 2);
        Delcatty p3 = new Delcatty("Игрок3", 2);
        Starly p4 = new Starly("Игрок4", 1);
        Staravia p5 = new Staravia("Игрок5", 3);
        Staraptor p6 = new Staraptor("Игрок6", 1);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);

        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);

        b.go();
    }
}