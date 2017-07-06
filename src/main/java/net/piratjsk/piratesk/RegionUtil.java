package net.piratjsk.piratesk;

import ch.njol.skript.hooks.regions.WorldGuardHook;
import ch.njol.skript.hooks.regions.classes.Region;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import java.lang.reflect.Field;

public final class RegionUtil {

    public static ProtectedRegion getProtectedRegion(final Region region) {
        final WorldGuardHook.WorldGuardRegion rg = (WorldGuardHook.WorldGuardRegion) region;
        try {
            final Field field = rg.getClass().getDeclaredField("region");
            field.setAccessible(true);
            return (ProtectedRegion) field.get(rg);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return null;
        }
    }

}
