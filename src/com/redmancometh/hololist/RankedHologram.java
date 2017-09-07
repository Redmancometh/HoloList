package com.redmancometh.hololist;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.redmancometh.hololist.hooking.Hook;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class RankedHologram<T>
{
    private Location loc;
    private int length;
    private String name;
    private Hook<T> dataHook;
    //Final so it will be excluded from @AllArgsConstructor
    private final Map<UUID, Hologram> viewers;

    public void update()
    {
        dataHook.updateCache().thenAccept((rankList) ->
        {
            
        });
    }

    public void addViewer(Player p)
    {
        Hologram h = HologramsAPI.createHologram(HoloList.instance(), loc);
        VisibilityManager visiblility = h.getVisibilityManager();
        visiblility.setVisibleByDefault(false);
        visiblility.showTo(p);
        viewers.put(p.getUniqueId(), h);
    }

    public void removeViewer(UUID uuid)
    {
        Hologram h = viewers.get(uuid);
        if (h == null) throw new IllegalStateException("removeViewer was called on an");
        h.delete();
    }

    public boolean isViewing(UUID uuid)
    {
        return viewers.containsKey(uuid);
    }

    /**
     * Update the board using the cache. Will never fetch from db.
     */
    public abstract void updateBoard();

}
