package de.festival.Bedwars.Helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

@SuppressWarnings("deprecation")
public final class BedPart {
    private final int x, y, z;
    private final String worldName;
    private final byte data;
    private final Type type;

    public BedPart(Block block) {
        if (block.getType() != Material.BED_BLOCK)
            throw new IllegalArgumentException("Block is not a bed");
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.worldName = block.getWorld().getName();
        this.data = block.getData();
        this.type = Type.fromDataValue(data);
    }

    public Block locateOtherPart() {
        return getWorld().getBlockAt(x + (data == 3 || data == 9 ? 1 : data == 1 || data == 11 ? -1 : 0), y, z + (data == 0 || data == 10 ? 1 : data == 2 || data == 8 ? -1 : 0));
    }

    public Block locateHeadPart() {
        return type == Type.HEAD ? getBlock() : locateOtherPart();
    }

    public Block locateFootPart() {
        return type == Type.FOOT ? getBlock() : locateOtherPart();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public World getWorld() throws IllegalStateException {
        World w = Bukkit.getWorld(worldName);
        if (w == null)
            throw new IllegalStateException("World '" + worldName + "' is not loaded");
        return w;
    }

    public Block getBlock() {
        return getWorld().getBlockAt(x, y, z);
    }

    public byte getData() {
        return this.data;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        HEAD((byte) 8, (byte) 9, (byte) 10, (byte) 11),
        FOOT((byte) 0, (byte) 1, (byte) 2, (byte) 3);

        private static final Map<Byte, Type> DATA_VALUE_MAP = new HashMap<Byte, Type>();
        private final List<Byte> dataValues;

        static {
            for (Type t : values())
                for (byte b : t.dataValues)
                    DATA_VALUE_MAP.put(b, t);
        }

        private Type(Byte... dataValues) {
            this.dataValues = Arrays.asList(dataValues);
        }

        public List<Byte> getDataValues() {
            return this.dataValues;
        }

        public static Type fromDataValue(byte data) {
            return DATA_VALUE_MAP.get(data);
        }
    }
}