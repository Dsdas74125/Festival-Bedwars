package de.festivaldev.bedwars.helpers;

import org.bukkit.inventory.ItemStack;

/**
 * Created by Fabian on 31.03.2016.
 */
public class PickupValueCalculator {

	public static int getValue(ItemStack is) {
		switch (is.getType()) {
			case NETHER_BRICK:
				return is.getAmount() * 3;
			case CLAY_BRICK:
				return is.getAmount() * 10;
			case IRON_INGOT:
				return is.getAmount() * 20;
			default:
				return 0;
		}
	}

}
