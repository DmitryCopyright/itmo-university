package moves;

import ru.ifmo.se.pokemon.*;

public class WorkUp extends SpecialMove {
    public WorkUp() {
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected void applySelfEffects(Pokemon p){
        super.applySelfEffects(p);
        Effect specat = new Effect().stat(Stat.SPECIAL_ATTACK, +1);
        Effect at = new Effect().stat(Stat.ATTACK, +1);
        p.addEffect(specat);
        p.addEffect(at);
    }

    @Override
    protected String describe() {
        return "использует WorkUp";
    }
}