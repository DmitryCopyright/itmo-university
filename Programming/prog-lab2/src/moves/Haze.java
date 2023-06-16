package moves;

import ru.ifmo.se.pokemon.*;

public class Haze extends SpecialMove {
    public Haze() {
        super(Type.ICE, 0, 0);
    }


    public final void getStat(Stat stat) {
        getStat(Stat.ACCURACY);
        getStat(Stat.EVASION);

    }

    public final void setStats(Stat stats, int i ) {
        setStats(Stat.ACCURACY,0);
        setStats(Stat.EVASION, 0);

    }


    @Override
    protected String describe() {
        return "использует Haze";
    }
}