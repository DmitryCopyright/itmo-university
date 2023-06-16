package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Starly extends Pokemon {
    public Starly(String name, int level) {
        super(name, level);
        setStats(40, 55, 30, 30, 30, 60);
        setType(Type.NORMAL, Type.FLYING);
        setMove(new Rest(), new Facade());
    }
}