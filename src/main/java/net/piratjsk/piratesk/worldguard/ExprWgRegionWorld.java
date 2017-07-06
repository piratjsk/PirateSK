package net.piratjsk.piratesk.worldguard;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.World;

public class ExprWgRegionWorld extends SimplePropertyExpression<WorldGuardRegion, World> {
    @Override
    protected String getPropertyName() {
        return "wg region world";
    }

    @Override
    public World convert(final WorldGuardRegion region) {
        return region.getWorld();
    }

    @Override
    public Class<? extends World> getReturnType() {
        return World.class;
    }
}
