package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Delcatty extends Pokemon {
    public Delcatty(String name, int level) {
        super(name, level);
        setStats(70, 65, 65, 55, 55, 90);
        setType(Type.NORMAL);
        setMove(new DoubleTeam(), new Confide(), new Facade(), new Flamethrower());
    }
}