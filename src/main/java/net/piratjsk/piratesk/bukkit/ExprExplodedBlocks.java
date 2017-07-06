package net.piratjsk.piratesk.bukkit;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public final class ExprExplodedBlocks extends SimpleExpression<Block> {

    public Class<? extends Block> getReturnType() {
        return Block.class;
    }

    public boolean isSingle() {
        return false;
    }

    public boolean init(final Expression<?>[] e, final int i, Kleenean k, final ParseResult p) {
        if (!ScriptLoader.isCurrentEvent(EntityExplodeEvent.class)) {
            Skript.error("The expression 'exploded blocks' can only be used in explode events", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    public String toString(final Event e, final boolean b) {
        return "exploded blocks";
    }

    protected Block[] get(final Event e) {
        if (e instanceof EntityExplodeEvent) {
            final List<Block> list = ((EntityExplodeEvent) e).blockList();
            return list.toArray(new Block[0]);
        }
        return null;
    }
}
