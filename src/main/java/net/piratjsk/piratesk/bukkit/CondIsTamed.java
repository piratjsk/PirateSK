package net.piratjsk.piratesk.bukkit;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Event;

public final class CondIsTamed extends Condition{

    private Expression<LivingEntity> entity;
    private boolean isnot;

    public boolean check(final Event e) {
        final Entity entity = this.entity.getSingle(e);
        return entity instanceof Tameable && isnot != ((Tameable) entity).isTamed();
    }

    public String toString(final Event e, final boolean b) {
        return "is tamed";
    }

    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final ParseResult p) {
        entity = (Expression<LivingEntity>) e[0];
        isnot = i == 1;
        return true;
    }
}
