package moves;

import ru.ifmo.se.pokemon.*;

public class Confide extends SpecialMove {
    public Confide() {
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected void applyOppEffects(Pokemon pokeEnemy){
        super.applyOppEffects(pokeEnemy);
        Effect spAtkEff = new Effect().stat(Stat.SPECIAL_ATTACK, -1);
        pokeEnemy.addEffect(spAtkEff);
    }

    @Override
    protected String describe() {
        return "использует Confide";
    }
}