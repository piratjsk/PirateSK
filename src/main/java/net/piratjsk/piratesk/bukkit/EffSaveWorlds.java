package net.piratjsk.piratesk.bukkit;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.World;
import org.bukkit.event.Event;

public final class EffSaveWorlds extends Effect {

    private Expression<World> worlds;

    @Override
    protected void execute(Event e) {
        final World[] worlds = this.worlds.getArray(e);
        for (World world : worlds) {
            world.save();
        }
    }

    public String toString(final Event e, final boolean b) {
        return "save world(s)";
    }

    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final ParseResult p) {
        worlds = (Expression<World>) e[0];
        return true;
    }
}
