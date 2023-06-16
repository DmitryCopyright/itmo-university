package moves;

import ru.ifmo.se.pokemon.*;

public class HealPulse extends SpecialMove {
    public HealPulse() {
        super(Type.PSYCHIC, 0, 0);
    }

    @Override
    protected void applyOppEffects(Pokemon pokEnemy) {
        super.applyOppEffects(pokEnemy);
        double currHP = pokEnemy.getHP();
        double maxHP = pokEnemy.getStat(Stat.HP);
        if (currHP >= maxHP*0.5){
            pokEnemy.setMod(Stat.HP, (int) maxHP);
        }
        else{
            pokEnemy.setMod(Stat.HP, (int) (currHP+maxHP*0.5));
        }
    }


    @Override
    protected String describe() {
        return "использует HealPulse";
    }
}