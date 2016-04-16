package net.grota.piratesk.worldedit;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.util.regex.Matcher;

public class EffSaveSchematic extends Effect {
    private Expression<Location> loc1;
    private Expression<Location> loc2;
    private Expression<String> name;

    @Override
    protected void execute(Event e) {
        String name = this.name.getSingle(e);
        Location loc1 = this.loc1.getSingle(e);
        Location loc2 = this.loc2.getSingle(e);

        name = name.endsWith(".schematic") ? name : name + ".schematic";
        File file;
        if (name.startsWith("/")) {
            file = new File(name.replaceFirst("/","").replaceAll("/", Matcher.quoteReplacement(File.separator)));
        } else {
            file = new File(("plugins/WorldEdit/" + WorldEdit.getInstance().getConfiguration().saveDir + "/" + name).replaceAll("/", Matcher.quoteReplacement(File.separator)));
        }

        Vector v1 = BukkitUtil.toVector(loc1);
        Vector v2 = BukkitUtil.toVector(loc2);

        CuboidRegion rg = new CuboidRegion(v1, v2);

        EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(loc1.getWorld()), -1);
        CuboidClipboard cc = new CuboidClipboard(rg.getMaximumPoint().subtract(rg.getMinimumPoint()).add(new Vector(1, 1, 1)), rg.getMinimumPoint());
        cc.copy(es);
        
        try {
            SchematicFormat.MCEDIT.save(cc, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String toString(@Nullable Event event, boolean b) {
        return null;
    }

    public boolean init(Expression<?>[] e, int i, Kleenean k, SkriptParser.ParseResult p) {
        loc1 = (Expression<Location>) e[0];
        loc2 = (Expression<Location>) e[1];
        name = (Expression<String>) e[2];
        return true;
    }
}
