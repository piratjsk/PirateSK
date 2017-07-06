package net.piratjsk.piratesk.worldguard;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

public final class ExprWgRegionId extends SimplePropertyExpression<WorldGuardRegion, String> {
    @Override
    protected String getPropertyName() {
        return "wg region id";
    }

    @Override
    public String convert(final WorldGuardRegion region) {
        return region.getProtectedRegion().getId();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
