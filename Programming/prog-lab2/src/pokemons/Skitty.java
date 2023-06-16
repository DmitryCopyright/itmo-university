package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Skitty extends Pokemon {
    public Skitty(String name, int level) {
        super(name, level);
        setStats(50, 45, 45, 35, 35, 50);
        setType(Type.NORMAL);
        setMove(new DoubleTeam(), new Confide(), new Facade());
    }
}