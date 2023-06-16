package moves;

import ru.ifmo.se.pokemon.*;

public class ThunderWave extends SpecialMove {
    public ThunderWave() {
        super(Type.ELECTRIC, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {

        Effect.paralyze(p);
    }

    @Override
    protected String describe() {
        return "использует ThunderWave";
    }
}