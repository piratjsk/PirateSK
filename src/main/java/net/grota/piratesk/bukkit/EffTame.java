package net.grota.piratesk.bukkit;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Event;

public class EffTame extends Effect {

    // "tame %livingentities% (to|for) %player%", "untame %livingentities%"

    Expression<Entity> entity;
    Expression<Player> player;
    boolean tame;

    protected void execute(final Event e) {
        Entity[] entities = this.entity.getAll(e);
        Player player = this.player.getSingle(e);
        for (Entity entity : entities) {
            if (entity instanceof Tameable) {
                if (tame) {
                    ((Tameable) entity).setTamed(true);
                    ((Tameable) entity).setOwner(player);
                }else {
                    ((Tameable) entity).setTamed(false);
                    ((Tameable) entity).setOwner(null);
                }
            }
        }

    }

    public String toString(final Event e, final boolean b) {
        return "(un)tame entity";
    }

    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final ParseResult p) {
        entity = (Expression<Entity>) e[0];
        player = (Expression<Player>) e[1];
        tame = i == 0;
        return true;
    }
}
