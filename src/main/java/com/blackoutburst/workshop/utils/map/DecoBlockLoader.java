package com.blackoutburst.workshop.utils.map;

import com.blackoutburst.workshop.core.WSPlayer;
import com.blackoutburst.workshop.core.blocks.DecoBlock;
import com.blackoutburst.workshop.utils.files.DecoFileUtils;
import com.blackoutburst.workshop.utils.files.MapFileUtils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DecoBlockLoader extends BukkitRunnable {

    protected WSPlayer wsplayer;

    public DecoBlockLoader(WSPlayer wsplayer) {
        this.wsplayer = wsplayer;
    }

    @Override
    public void run() {
        String mapName = wsplayer.getPlayArea().getType();
        Location anchor = wsplayer.getPlayArea().getAnchor();
        World world = wsplayer.getPlayer().getWorld();
        File decoFile = MapFileUtils.getMapFile(mapName, 'D');
        YamlConfiguration deco = YamlConfiguration.loadConfiguration(decoFile);
        Location[] normalKeys = MapFileUtils.getDecoNormalKeys(deco, world);
        Location[] neededKeys = MapFileUtils.getDecoNeededKeys(deco, world);
        BlockData[][] blocks = DecoFileUtils.readFile(deco, normalKeys, false);
        BlockData[][] neededBlocks = DecoFileUtils.readFile(deco, neededKeys, true);
        List<DecoBlock> decoBlocks = wsplayer.getDecoBlocks();

        for (int i = 0; i < normalKeys.length; i++) {
            BlockData[] normalLocBlocks = blocks[i];
            int neededIndex = Arrays.asList(neededKeys).indexOf(normalKeys[i]);
            int totalBlocks = (neededIndex == -1) ? normalLocBlocks.length :
                    normalLocBlocks.length + neededBlocks[neededIndex].length;
            BlockData[] locBlocks = new BlockData[totalBlocks];
            System.arraycopy(normalLocBlocks, 0, locBlocks, 0, normalLocBlocks.length);
            if (neededIndex != -1) {
                BlockData[] neededLocBlocks = neededBlocks[neededIndex];
                System.arraycopy(neededLocBlocks, 0, locBlocks, normalLocBlocks.length, neededLocBlocks.length);
            }
            Location location = normalKeys[i].add(anchor);

            DecoBlock decoBlock = new DecoBlock(locBlocks, location, world, normalLocBlocks.length);
            decoBlocks.add(decoBlock);
        }
        wsplayer.getPlayArea().setLoading(false);
    }
}
