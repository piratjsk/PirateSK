package net.grota.piratesk.bukkit;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class ExprExplodedBlocks extends SimpleExpression<Block> {

    public Class<? extends Block> getReturnType() {
        return Block.class;
    }

    public boolean isSingle() {
        return false;
    }

    public boolean init(Expression<?>[] e, int i, Kleenean k, SkriptParser.ParseResult p) {
        if (!ScriptLoader.isCurrentEvent(EntityExplodeEvent.class)) {
            Skript.error("The expression 'exploded blocks' can only be used in explode events", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    public String toString(Event arg0, boolean arg1) {
        return "exploded blocks";
    }

    protected Block[] get(Event e) {
        if (e instanceof EntityExplodeEvent) {
            List<Block> list = ((EntityExplodeEvent) e).blockList();
            Block[] blocks = list.toArray(new Block[0]);
            return blocks;
        }
        return null;
    }
}
