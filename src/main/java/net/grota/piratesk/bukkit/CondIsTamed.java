package net.grota.piratesk.bukkit;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Event;

public class CondIsTamed extends Condition{

    Expression<LivingEntity> entity;
    boolean isnot;

    public boolean check(final Event e) {
        Entity entity = this.entity.getSingle(e);
        if (entity instanceof Tameable)
            return isnot != ((Tameable) entity).isTamed();
        return false;
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
