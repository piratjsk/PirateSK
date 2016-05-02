package net.grota.piratesk.worldedit;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
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

import java.io.File;
import java.util.regex.Matcher;

public class EffSaveSchematic extends Effect {

    private Expression<Location> loc1;
    private Expression<Location> loc2;
    private Expression<String> name;

    @Override
    protected void execute(final Event e) {
        String name = this.name.getSingle(e);
        final Location loc1 = this.loc1.getSingle(e);
        final Location loc2 = this.loc2.getSingle(e);

        name = name.endsWith(".schematic") ? name : name + ".schematic";
        final File file;
        if (name.startsWith("/")) {
            file = new File(name.replaceFirst("/","").replaceAll("/", Matcher.quoteReplacement(File.separator)));
        } else {
            file = new File(("plugins/WorldEdit/" + WorldEdit.getInstance().getConfiguration().saveDir + "/" + name).replaceAll("/", Matcher.quoteReplacement(File.separator)));
        }

        final Vector v1 = BukkitUtil.toVector(loc1);
        final Vector v2 = BukkitUtil.toVector(loc2);

        final CuboidRegion rg = new CuboidRegion(v1, v2);

        final EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(loc1.getWorld()), -1);
        final CuboidClipboard cc = new CuboidClipboard(rg.getMaximumPoint().subtract(rg.getMinimumPoint()).add(new Vector(1, 1, 1)), rg.getMinimumPoint());
        cc.copy(es);
        
        try {
            SchematicFormat.MCEDIT.save(cc, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String toString(final Event e, final boolean b) {
        return "save blocks between two locations to schematic";
    }

    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final ParseResult p) {
        loc1 = (Expression<Location>) e[0];
        loc2 = (Expression<Location>) e[1];
        name = (Expression<String>) e[2];
        return true;
    }
}
