package net.grota.piratesk;

import ch.njol.skript.lang.ExpressionType;
import net.grota.piratesk.bukkit.EffSaveWorlds;
import net.grota.piratesk.bukkit.ExprEntityAI;
import net.grota.piratesk.bukkit.ExprExplodedBlocks;
import net.grota.piratesk.worldedit.EffPasteSchematic;
import net.grota.piratesk.worldedit.EffSaveSchematic;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import ch.njol.skript.Skript;
import org.mcstats.Metrics;

import java.io.IOException;

public class PirateSK extends JavaPlugin {

    private static PirateSK instance;

    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("Skript") == null || !Skript.isAcceptRegistrations()) {
            getLogger().info("Unable to find Skript or Skript isn't accepting registrations, disabling PirateSK...");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            instance = this;
            Skript.registerAddon(this);

            try {
                Metrics metrics = new Metrics(this);
                metrics.start();
            } catch (IOException e) {
                // Failed to submit the stats :-(
            }

            // Bukkit elements
            Skript.registerEffect(EffSaveWorlds.class, "save %worlds%");
            Skript.registerExpression(ExprExplodedBlocks.class, Block.class, ExpressionType.SIMPLE, "exploded[(-| )]blocks");
            Skript.registerExpression(ExprEntityAI.class, Boolean.class, ExpressionType.PROPERTY, "[the] ai of %livingentities%", "%livingentities%'[s] ai");

            // WorldEdit elements
            if (getServer().getPluginManager().getPlugin("WorldEdit") != null) {
                Skript.registerEffect(EffPasteSchematic.class,
                        "paste schem[atic] %string% at %location% [(ignor(e|ing)|without|[with] no) air]",
                        "paste schem[atic] %string% at %location% with air");
                Skript.registerEffect(EffSaveSchematic.class, "save blocks between %location% and %location% to [schem[atic]] [file] %string%");
            }

            // WoldGuard elements
//          if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
//          }

        }
    }

    public static PirateSK getInstance() {
        return instance;
    }
}
