package net.grota.piratesk;

import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Timespan;
import net.grota.piratesk.bukkit.*;
import net.grota.piratesk.worldedit.EffPasteSchematic;
import net.grota.piratesk.worldedit.EffSaveSchematic;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import ch.njol.skript.Skript;

public class PirateSK extends JavaPlugin {

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
//      if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
//      }

    }

    public static PirateSK getInstance() {
        return instance;
    }
}
