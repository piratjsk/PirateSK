package net.piratjsk.piratesk.bukkit;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

public final class ExprEntityAI extends SimplePropertyExpression<LivingEntity, Boolean> {

    public Boolean convert(final LivingEntity entity) {
        return entity.hasAI();
    }

    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.DELETE || mode == ChangeMode.RESET)
            return new Class[] {Boolean.class};
        return null;
    }

    public void change(final Event e, final Object[] delta, final ChangeMode mode) {
        if (mode == ChangeMode.DELETE) {
            for (final LivingEntity en : getExpr().getArray(e))
                en.setAI(false);
        } else if (mode == ChangeMode.RESET) {
            for (final LivingEntity en : getExpr().getArray(e))
                en.setAI(true);
        } else {
            final boolean ai = (Boolean) delta[0];
            for (final LivingEntity en : getExpr().getArray(e))
                en.setAI(ai);
        }
    }

    protected String getPropertyName() {
        return "ai";
    }

    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

}
