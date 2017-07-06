package net.piratjsk.piratesk.worldguard;

import ch.njol.skript.hooks.regions.classes.Region;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public final class CondIsFromPlugin extends Condition {

    private Expression<Region> region;
    private Expression<String> plugin;
    private boolean isnot;

    @Override
    public boolean check(final Event event) {
        final Region rg = this.region.getSingle(event);
        final String pl = this.plugin.getSingle(event);
        if (rg.getPlugin().getName().equalsIgnoreCase(pl))
            return !isnot;
        return isnot;
    }

    @Override
    public String toString(final Event event, final boolean b) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(final Expression<?>[] e, final int matchedPattern, final Kleenean k, final SkriptParser.ParseResult p) {
        region = (Expression<Region>) e[0];
        plugin = (Expression<String>) e[1];
        isnot = matchedPattern == 1;
        return true;
    }
}
