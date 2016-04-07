package net.grota.piratesk;

import net.grota.piratesk.worldedit.EffPasteSchematic;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import ch.njol.skript.Skript;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.logging.Level;

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

            if (getServer().getPluginManager().getPlugin("WorldEdit") != null) {
                Skript.registerEffect(EffPasteSchematic.class,
                        "paste schem[atic] %string% at %location% [(ignor(e|ing)|without|[with] no) air]",
                        "paste schem[atic] %string% at %location% with air");
            }

//          if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
//          }

        }
    }

    public static PirateSK getInstance() {
        return instance;
    }
}
