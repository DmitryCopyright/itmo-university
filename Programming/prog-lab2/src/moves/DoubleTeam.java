package moves;
import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove{
    public DoubleTeam(){
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected void applySelfEffects(Pokemon pokeSelf){
        super.applySelfEffects(pokeSelf);
        Effect evsEff = new Effect().stat(Stat.EVASION, 1);
        pokeSelf.addEffect(evsEff);
    }

    @Override
    protected java.lang.String describe() {
        return "использует Double Team";
    }
}