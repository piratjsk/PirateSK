package net.piratjsk.piratesk.worldguard;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.World;

public final class WorldGuardRegion {

    private final ProtectedRegion region;
    private final World world;

    public WorldGuardRegion(final ProtectedRegion region, final World world) {
        this.region = region;
        this.world = world;
    }

    public ProtectedRegion getProtectedRegion() {
        return region;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public String toString() {
        return "WorldGuardRegion("+this.getProtectedRegion().getId()+","+this.getWorld().getName()+")";
    }
}
