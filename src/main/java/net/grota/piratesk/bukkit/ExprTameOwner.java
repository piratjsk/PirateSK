package net.grota.piratesk.bukkit;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Event;

public class ExprTameOwner extends SimplePropertyExpression<Entity, Player> {

    protected String getPropertyName() {
        return "owner";
    }

    public Player convert(final Entity entity) {
        if (entity instanceof Tameable)
            return ((Tameable) entity).getOwner() instanceof Player ? (Player)((Tameable) entity).getOwner() : null;
        return null;
    }

    public Class<? extends Player> getReturnType() {
        return Player.class;
    }

    public Class<?>[] acceptChange(final ChangeMode mode) {
        if (mode == ChangeMode.RESET || mode == ChangeMode.SET)
            return new Class[] {Player.class};
        return null;
    }

    public void change(final Event e, final Object[] delta, final ChangeMode mode) {
        final Player p = delta[0] == null ? null : (Player) delta[0];
        if (mode == ChangeMode.SET || mode == ChangeMode.RESET)
            for (final Entity en : getExpr().getAll(e))
                if (en instanceof Tameable) ((Tameable) en).setOwner(p);
    }
}
