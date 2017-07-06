package net.piratjsk.piratesk.worldguard;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.hooks.regions.events.RegionBorderEvent;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.piratjsk.piratesk.RegionUtil;
import org.bukkit.World;
import org.bukkit.event.Event;

public final class ExprWgRegion extends SimpleExpression<WorldGuardRegion> {
    @Override
    protected WorldGuardRegion[] get(final Event event) {
        final RegionBorderEvent evt = (RegionBorderEvent) event;
        final ProtectedRegion region = RegionUtil.getProtectedRegion(evt.getRegion());
        final World world = evt.getPlayer().getWorld();
        return new WorldGuardRegion[] {new WorldGuardRegion(region, world)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends WorldGuardRegion> getReturnType() {
        return WorldGuardRegion.class;
    }

    @Override
    public String toString(final Event event, final boolean b) {
        final RegionBorderEvent evt = (RegionBorderEvent) event;
        final ProtectedRegion region = RegionUtil.getProtectedRegion(evt.getRegion());
        final World world = evt.getPlayer().getWorld();
        return new WorldGuardRegion(region, world).toString();
    }

    @Override
    public boolean init(final Expression<?>[] expressions, final int i, final Kleenean kleenean, final SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(RegionBorderEvent.class)) {
            Skript.error("The expression 'wg region' can only be used in 'on region (enter|leave)' events.", ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }
}
