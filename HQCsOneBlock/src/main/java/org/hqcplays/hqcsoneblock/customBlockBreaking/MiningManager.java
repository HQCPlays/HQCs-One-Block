package org.hqcplays.hqcsoneblock.customBlockBreaking;

// import com.comphenix.protocol.PacketType;
// import com.comphenix.protocol.ProtocolLibrary;
// import com.comphenix.protocol.ProtocolManager;
// import com.comphenix.protocol.events.PacketContainer;
// import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.hqcplays.hqcsoneblock.numberSheets.MiningSpeedSheet;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MiningManager implements Listener {
    private final HashMap<UUID, Long> nextPhase = new HashMap<>();
    private final HashMap<Location, Integer> blockStages = new HashMap<>();
    //private final ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public boolean updatePhaseCooldown(Player player, Block block) {
        List<UUID> toRemove = new ArrayList<>();
        long currentTime = System.currentTimeMillis() / 50;
        nextPhase.forEach((uuid, phase) -> {
            if (phase <= currentTime) {
                toRemove.add(uuid);
            }
        });
        toRemove.forEach(nextPhase::remove);

        if (nextPhase.containsKey(player.getUniqueId())) return false;
        nextPhase(player, block);
        return true;
    }

    public void nextPhase(Player player, Block block) {
        long currentTime = System.currentTimeMillis() / 50;
        int ticksToMine = MiningSpeedSheet.ticksToMine(player, block.getType());
        long phaseTime = currentTime + (ticksToMine / 10);
        nextPhase.put(player.getUniqueId(), phaseTime);
    }

    public boolean updateAndNextPhase(Player player, Block block) {
        if (updatePhaseCooldown(player, block)) {
            nextPhase(player, block);
            return true;
        }
        return false;
    }

    // public void sendBlockDamage(Player player, Location location, float progress) {
    //     int locationId = location.getBlockX() + location.getBlockY() + location.getBlockZ();
    //     PacketContainer packet = manager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
    //     packet.getIntegers().write(0, locationId);
    //     packet.getBlockPositionModifier().write(0, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    //     packet.getIntegers().write(1, (int) (progress * 10)); // Progress should be between 0 and 10
    //     try {
    //         manager.sendServerPacket(player, packet);
    //     } catch (InvocationTargetException e) {
    //         e.printStackTrace();
    //     }
    // }

    public int getBlockStage(Location loc) {
        return blockStages.getOrDefault(loc, 0);
    }

    public void setBlockStage(Location loc, int stage) {
        blockStages.put(loc, stage);
    }

    public void removeBlockStage(Location loc) {
        blockStages.remove(loc);
    }

    // Resets blocks
    public void resetPlayerBlockStages(Player player) {
        for (Location loc : new ArrayList<>(blockStages.keySet())) {
            //sendBlockDamage(player, loc, -1);
            setBlockStage(loc, -1);
            removeBlockStage(loc);
        }
    }
}
