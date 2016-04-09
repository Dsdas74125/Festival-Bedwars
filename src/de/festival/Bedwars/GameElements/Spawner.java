package de.festival.Bedwars.GameElements;

import org.bukkit.Location;
import org.bukkit.Material;
import org.json.simple.JSONObject;

import java.util.UUID;

public class Spawner {

    //public ItemStack is;
    public Material material;
    public Location location;
    public boolean enabled = false;
    public String id = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    public int rate;
    public int done = 1;
    public boolean isSpecial = false;

    public Spawner(Material material, int rate, Location loc, boolean isSpecial) {
        //this.is = is;
        this.material = material;
        this.location = loc;
        this.location.setX(this.location.getX()+0.5);
        this.location.setY(this.location.getY() + 1.5);
        this.location.setZ(this.location.getZ()+0.5);

        //System.out.println("["+loc.getX()+", "+loc.getY()+", "+loc.getZ()+"]");
        this.rate = rate;
        this.isSpecial = isSpecial;
    }

    public JSONObject exportSpawner() {
        //Double[] loc = {this.location.getX(), this.location.getY(), this.location.getZ()};
        JSONObject loc = new JSONObject();
        loc.put("x", this.location.getX() - 0.5);
        loc.put("y", this.location.getY() - 1.5);
        loc.put("z", this.location.getZ() - 0.5);

        JSONObject obj = new JSONObject();
        obj.put("material", this.material.toString());
        obj.put("location", loc);
        obj.put("rate", this.rate);
        obj.put("isSpecial", this.isSpecial);
        obj.put("world_name", this.location.getWorld().getName());

        return obj;
    }
}