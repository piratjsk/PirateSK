package net.piratjsk.piratesk.worldedit;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.io.File;
import java.util.regex.Matcher;

public final class EffPasteSchematic extends Effect {

    private Expression<Location> loc;
    private Expression<String> path;
    private boolean air;

    @Override
    protected void execute(final Event e) {
        String path = this.path.getSingle(e);
        final Location loc = this.loc.getSingle(e);

        @SuppressWarnings("deprecation")
        final EditSession session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitUtil.getLocalWorld(loc.getWorld()),-1);
        final Vector origin = BukkitUtil.toVector(loc);

        path = path.endsWith(".schematic") ? path : path + ".schematic";
        final File file;
        if (path.startsWith("/")) {
            file = new File(path.replaceFirst("/","").replaceAll("/", Matcher.quoteReplacement(File.separator)));
        } else {
            file = new File(("plugins/WorldEdit/" + WorldEdit.getInstance().getConfiguration().saveDir + "/" + path).replaceAll("/", Matcher.quoteReplacement(File.separator)));
        }

        if (!file.exists()) {
            Skript.error("Schematic \"" + file.toString() + "\" does not exist.");
            return;
        }
        try {
            SchematicFormat.getFormat(file).load(file).paste(session, origin, air);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String toString(final Event e, final boolean b) {
        return "paste schematic at location";
    }

    public boolean init(final Expression<?>[] e, final int i, final Kleenean k, final ParseResult p) {
        air = i == 1;
        path = (Expression<String>) e[0];
        loc = (Expression<Location>) e[1];
        return true;
    }

}
