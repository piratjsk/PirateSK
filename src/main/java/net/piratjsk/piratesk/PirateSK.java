package net.piratjsk.piratesk;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.hooks.regions.events.RegionBorderEvent;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Timespan;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.piratjsk.piratesk.bukkit.*;
import net.piratjsk.piratesk.worldedit.EffPasteSchematic;
import net.piratjsk.piratesk.worldedit.EffSaveSchematic;
import net.piratjsk.piratesk.worldguard.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import ch.njol.skript.Skript;

public final class PirateSK extends JavaPlugin {

    private static PirateSK instance;

    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("Skript") == null || !Skript.isAcceptRegistrations()) {
            getLogger().info("Unable to find Skript or Skript isn't accepting registrations, disabling PirateSK...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;
        Skript.registerAddon(this);

        // Bukkit elements
        Skript.registerEffect(EffSaveWorlds.class, "save %worlds%");
        Skript.registerExpression(ExprExplodedBlocks.class, Block.class, ExpressionType.SIMPLE, "exploded[(-| )]blocks");
        Skript.registerExpression(ExprEntityAI.class, Boolean.class, ExpressionType.PROPERTY, "[the] ai of %livingentities%", "%livingentities%'[s] ai");
        Skript.registerExpression(ExprInvulnerabilityTime.class, Timespan.class, ExpressionType.PROPERTY, "[the] (invulnerability [time]|no damage [time]) of %livingentity%", "%livingentity%'s (invulnerability [time]|no damage [time])");
        Skript.registerEffect(EffTame.class, "tame %entities% (to|for) %player%", "untame %entities%");
        Skript.registerCondition(CondIsTamed.class, "%entity% is tamed", "%entity% (is not|isn't) tamed");
        Skript.registerExpression(ExprTameOwner.class, Player.class, ExpressionType.PROPERTY, "%entities%'s (tamer|[pet] owner)", "[the] (tamer|[pet] owner) of %entities%");

        // WorldEdit elements
        if (getServer().getPluginManager().getPlugin("WorldEdit") != null) {
            Skript.registerEffect(EffPasteSchematic.class,
                    "paste schem[atic] %string% at %location% [(ignor(e|ing)|without|[with] no) air]",
                    "paste schem[atic] %string% at %location% with air");
            Skript.registerEffect(EffSaveSchematic.class, "save blocks between %location% and %location% to [schem[atic]] [file] %string%");
        }

        // WoldGuard elements
        if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            Skript.registerCondition(CondIsFromPlugin.class, "%region% is from plugin %string%", "%region% (is not|isn't) from plugin %string%");
            Classes.registerClass(new ClassInfo<>(WorldGuardRegion.class, "wgregion")
                    .after("string", "world", "offlineplayer", "player")
                    .user("(wg|worldguard)( |-)regions?")
                    .parser(new Parser<WorldGuardRegion>() {
                        @Override
                        public boolean canParse(ParseContext context) {
                            return false;
                        }

                        @Override
                        public WorldGuardRegion parse(String s, final ParseContext context) {
                            System.out.println(s);
                            s = s.replace("WorldGuardRegion(", "");
                            s = s.replace(")", "");
                            final String[] sp = s.split(",");
                            final World world = Bukkit.getWorld(sp[1]);
                            final ProtectedRegion region = WGBukkit.getRegionManager(world).getRegion(sp[0]);
                            return new WorldGuardRegion(region, world);
                        }

                        @Override
                        public String toString(final WorldGuardRegion region, final int i) {
                            return region.toString();
                        }

                        @Override
                        public String toVariableNameString(final WorldGuardRegion region) {
                            return region.toString();
                        }

                        @Override
                        public String getVariableNamePattern() {
                            return "WorldGuardRegion\\(.+?,.+?\\)";
                        }
                    })
            );
            Skript.registerExpression(ExprWgRegion.class, WorldGuardRegion.class, ExpressionType.SIMPLE, "(wg|worldguard)( |-)region");
            Skript.registerExpression(ExprWgRegionId.class, String.class, ExpressionType.PROPERTY, "[the] (name|id) of %wgregion%", "%wgregion%'[s] (name|id)");
            Skript.registerExpression(ExprWgRegionWorld.class, World.class, ExpressionType.PROPERTY, "[the] (world) of %wgregion%", "%wgregion%'[s] (world)");
        }
    }

    public static PirateSK getInstance() {
        return instance;
    }
}
