package net.grota.piratesk.bukkit;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

public class EffSaveWorlds extends Effect {
    private Expression<World> worlds;

    @Override
    protected void execute(Event e) {
        World[] worlds = this.worlds.getArray(e);
        for (int i = 0; i < worlds.length; i++) {
            worlds[i].save();
        }
    }

    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    public boolean init(Expression<?>[] e, int i, Kleenean k, SkriptParser.ParseResult p) {
        worlds = (Expression<World>) e[0];
        return true;
    }
}
