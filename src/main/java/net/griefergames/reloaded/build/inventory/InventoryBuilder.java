package net.griefergames.reloaded.build.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A simple <b>InventoryBuilder</b> with which you can create an inventory using various methods
 * <p></p>
 * <h1>A list of the current methods</h1>
 * <li>fill the {@code inventory} with the {@link org.bukkit.inventory.ItemStack} of your choice; you can also fill it with different colors</li>
 * <li>set an {@link org.bukkit.inventory.ItemStack} delayed; you can choose if you wanna play a {@link org.bukkit.Sound}, or not</li>
 */
public class InventoryBuilder {

    private final Inventory inventory;

    /**
     * Initialize the class
     *
     * @param size        Set the {@code size} of the inventory you've created
     *                    You can only use numbers that are divisible by nine
     *                    The maximum is fifty-four and the minimum is nine
     * @param displayName Enter the {@code displayName} of the inventory you created to be displayed above
     */
    public InventoryBuilder( int size, String displayName ) {
        if ( size == size % 9 || size > 54 )
            throw new UnsupportedOperationException( "Unsupported value: " + size );

        this.inventory = Bukkit.createInventory( null, size, displayName );
    }

    /**
     * Initialize the class
     *
     * @param player        Define the {@code player}
     * @param inventoryType Specify the {@code inventoryType} that you want to create
     */
    public InventoryBuilder( Player player, InventoryType inventoryType ) {
        this.inventory = Bukkit.createInventory( player, inventoryType );
    }

    /**
     * Fill the whole {@code inventory} with the {@code itemStack} you selected
     *
     * @param itemStack Create the {@code itemStack} you want to fill up the inventory
     */
    public InventoryBuilder fillInventory( ItemStack itemStack ) {
        for ( short i = 0; i < this.inventory.getSize(); i++ )
            this.setItem( i, itemStack );
        return this;
    }

    /**
     * Fill the whole {@code inventory} with the {@code itemStack} you selected, with random colors
     *
     * @param itemStack     Create the {@code itemStack} you want to fill up the inventory
     * @param maximalRandom Set the {@code maximalRandom} value, to be used for the {@code subId} of the current {@code itemStack}
     */
    public InventoryBuilder fillInventoryRandomColors( ItemStack itemStack, int maximalRandom ) {
        for ( short i = 0; i < this.inventory.getSize(); i++ ) {
            itemStack.setDurability( ( short ) ThreadLocalRandom.current().nextInt( maximalRandom ) );
            this.setItem( i, itemStack );
        }
        return this;
    }

    /**
     * Fill the whole {@code inventory} with the {@code itemStack} you selected, with random colors
     *
     * @param itemStack     Create the {@code itemStack} you want to fill up the inventory
     * @param minimalRandom Set the {@code minimalRandom} value, from the start value, of the {@code subId}
     * @param maximalRandom Set the {@code maximalRandom} value, to be used for the {@code subId} of the current {@code itemStack}
     */
    public InventoryBuilder fillInventoryRandomColors( ItemStack itemStack, int minimalRandom, int maximalRandom ) {
        for ( short i = 0; i < this.inventory.getSize(); i++ ) {
            itemStack.setDurability( ( short ) ThreadLocalRandom.current().nextInt( minimalRandom, maximalRandom ) );
            this.setItem( i, itemStack );
        }
        return this;
    }

    /**
     * Fill the {@code inventory} with the {@code itemStack} you selected, from {@code from} to {@code to}
     *
     * @param itemStack Create the {@code itemStack} you want to fill up the inventory
     * @param from      Set the {@code from} value, where we should start to calc
     * @param to        Set the {@code to} value, to be used for the {@code subId} of the current {@code itemStack}
     */
    public InventoryBuilder fillInventoryFromTo( ItemStack itemStack, short from, short to ) {
        for ( short i = from; i < to; i++ ) {
            this.setItem( i, itemStack );
        }
        return this;
    }

    /**
     * Set the item in the current {@code inventory}
     *
     * @param slot      Set the {@code slot} of the item you want to set
     * @param itemStack Create the {@code itemStack} you want to use
     */
    public InventoryBuilder setItem( int slot, ItemStack itemStack ) {
        this.inventory.setItem( slot, itemStack );
        return this;
    }

    /**
     * Set the item in the current {@code inventory} with a delay, which you selected
     *
     * @param slot      Set the {@code slot} of the item you want to set
     * @param itemStack Create the {@code itemStack} you want to use
     * @param plugin    Initialize the {@code plugin} with your Main-Class
     * @param delay     Enter the {@code delay} you want
     */
    public InventoryBuilder setDelayedItem( int slot, ItemStack itemStack, Plugin plugin, long delay ) {
        new BukkitRunnable() {
            @Override
            public void run() {
                setItem( slot, itemStack );
            }
        }.runTaskLater( plugin, delay );
        return this;
    }

    /**
     * Set the item in the current {@code inventory} with a delay, which you select, and play a sound
     *
     * @param player    Initialize the {@code player} object
     * @param slot      Set the {@code slot} of the item you want to set
     * @param itemStack Create the {@code itemStack} you want to use
     * @param plugin    Initialize the {@code plugin} with your Main-Class
     * @param delay     Enter the {@code delay} you want
     * @param sound     Specify the {@code sound} you want to play for the {@code player}
     */
    public InventoryBuilder setDelayedItem( Player player, int slot, ItemStack itemStack, Plugin plugin, long delay, Sound sound ) {
        new BukkitRunnable() {
            @Override
            public void run() {
                setItem( slot, itemStack );
                player.playSound( player.getLocation(), sound, 1.0F, 1.0F );
            }
        }.runTaskLater( plugin, delay );
        return this;
    }

    /**
     * Set the item in the current {@code inventory} with a delay, which you select, and play a sound with the desired volume
     *
     * @param player    Initialize the {@code player} object
     * @param slot      Set the {@code slot} of the item you want to set
     * @param itemStack Create the {@code itemStack} you want to use
     * @param plugin    Initialize the {@code plugin} with your Main-Class
     * @param delay     Enter the {@code delay} you want
     * @param sound     Specify the {@code sound} you want to play for the {@code player}
     * @param volume    Set the {@code volume} you want to have for the {@code sound}
     * @param pitch     Set the {@code pitch} you want to have for the {@code sound}
     */
    public InventoryBuilder setDelayedItem( Player player, int slot, ItemStack itemStack, Plugin plugin, long delay, Sound sound, float volume, float pitch ) {
        new BukkitRunnable() {
            @Override
            public void run() {
                setItem( slot, itemStack );
                player.playSound( player.getLocation(), sound, volume, pitch );
            }
        }.runTaskLater( plugin, delay );
        return this;
    }

    /**
     * Finally open the inventory by the {@code player} you specified
     *
     * @param player Define the {@code player} that should open the inventory
     */
    public InventoryBuilder openInventory( Player player ) {
        player.openInventory( this.inventory );
        return this;
    }
}