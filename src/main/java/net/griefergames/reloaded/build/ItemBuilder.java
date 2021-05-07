package net.griefergames.reloaded.build;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Easily create itemStacks, without messing your hands.
 * <i>Note that if you do use this in one of your projects, leave this notice.</i>
 * <i>Please do credit me if you do use this in one of your projects.</i>
 *
 * @author NonameSL
 * @author NoJokeFNA
 */
public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    /**
     * Create a new {@link ItemBuilder} from scratch.
     *
     * @param material The {@code #material} to create the {@link ItemBuilder} with.
     */
    public ItemBuilder( final Material material ) {
        this( material, 1 );
    }

    /**
     * Create a new {@link ItemBuilder} over an existing this.itemStack.
     *
     * @param itemStack The this.itemStack to create the ItemBuilder over.
     */
    public ItemBuilder( final ItemStack itemStack ) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Create a new {@link ItemBuilder} from scratch.
     *
     * @param material The material of the item.
     * @param amount   The amount of the item.
     */
    public ItemBuilder( final Material material, final int amount ) {
        this.itemStack = new ItemStack( material, amount );
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Create a new {@link ItemBuilder} from scratch.
     *
     * @param material   The material of the item.
     * @param amount     The amount of the item.
     * @param durability The durability of the item.
     */
    public ItemBuilder( final Material material, final int amount, final byte durability ) {
        this.itemStack = new ItemStack( material, amount, durability );
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * Clone the {@link ItemBuilder} into a new one.
     *
     * @return The cloned instance.
     */
    public ItemBuilder clone() {
        try {
            final ItemBuilder cloneItemBuilder = ( ItemBuilder ) super.clone();
        } catch ( CloneNotSupportedException ex ) {
            ex.printStackTrace();
        }
        return new ItemBuilder( this.itemStack );
    }

    /**
     * Change the durability of the item.
     *
     * @param durability The durability to set it to.
     */
    public ItemBuilder setDurability( final short durability ) {
        this.itemStack.setDurability( durability );
        return this;
    }

    /**
     * Set the displayname of the item.
     *
     * @param name The name to change it to.
     */
    public ItemBuilder setDisplayName( final String name ) {
        this.itemMeta.setDisplayName( name );
        return this;
    }

    /**
     * Add an unsafe enchantment.
     *
     * @param enchantment The enchantment to add.
     * @param level       The level to put the enchant on.
     */
    public ItemBuilder addUnsafeEnchantment( final Enchantment enchantment, int level ) {
        this.itemStack.addUnsafeEnchantment( enchantment, level );
        return this;
    }

    /**
     * Remove a certain enchant from the item.
     *
     * @param enchantment The enchantment to remove
     */
    public ItemBuilder removeEnchantment( final Enchantment enchantment ) {
        this.itemStack.removeEnchantment( enchantment );
        return this;
    }

    /**
     * Set the skull owner for the item. Works on skulls only.
     *
     * @param ownerName The name of the skull's owner.
     */
    public ItemBuilder setSkullOwner( final String ownerName ) {
        try {
            final SkullMeta skullMeta = ( SkullMeta ) this.itemStack.getItemMeta();

            skullMeta.setOwner( ownerName );
            this.itemStack.setItemMeta( skullMeta );
        } catch ( ClassCastException ignored ) {
        }
        return this;
    }

    /**
     * Set the skull owner for the item. Works on skulls only.
     *
     * @param player The name of the skull's owner.
     */
    public ItemBuilder setSkullOwner( final Player player ) {
        try {
            final SkullMeta skullMeta = ( SkullMeta ) this.itemStack.getItemMeta();

            final Field profileField = skullMeta.getClass().getDeclaredField( "profile" );
            profileField.setAccessible( true );
            /*profileField.set( skullMeta, ( ( CraftPlayer ) player ).getProfile() );*/

            this.itemStack.setItemMeta( skullMeta );
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return this;
    }

    /**
     * Add an enchant to the item.
     *
     * @param enchantment The enchant to add
     * @param level       The level
     */
    public ItemBuilder addEnchant( final Enchantment enchantment, final int level ) {
        this.itemMeta.addEnchant( enchantment, level, true );
        return this;
    }

    /**
     * Add multiple enchants at once.
     *
     * @param enchantments The enchants to add.
     */
    public ItemBuilder addEnchantments( final Map<Enchantment, Integer> enchantments ) {
        this.itemStack.addEnchantments( enchantments );
        return this;
    }

    /**
     * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
     */
    public ItemBuilder setInfinityDurability() {
        this.itemStack.setDurability( Short.MAX_VALUE );
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore( final String... lore ) {
        this.itemMeta.setLore( Arrays.asList( lore ) );
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore( final List<String> lore ) {
        this.itemMeta.setLore( lore );
        return this;
    }

    /**
     * Remove a lore line.
     */
    public ItemBuilder removeLoreLine( final String line ) {
        final List<String> lore = new ArrayList<>( this.itemMeta.getLore() );
        if ( !lore.contains( line ) )
            return this;

        lore.remove( line );
        this.itemMeta.setLore( lore );
        return this;
    }

    /**
     * Remove a lore line.
     *
     * @param index The index of the lore line to remove.
     */
    public ItemBuilder removeLoreLine( final int index ) {
        final List<String> lore = new ArrayList<>( this.itemMeta.getLore() );
        if ( index < 0 || index > lore.size() )
            return this;

        lore.remove( index );
        this.itemMeta.setLore( lore );
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     */
    public ItemBuilder addLoreLine( final String line ) {
        List<String> lore = new ArrayList<>();
        if ( this.itemMeta.hasLore() )
            lore = new ArrayList<>( this.itemMeta.getLore() );

        lore.add( line );
        this.itemMeta.setLore( lore );
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param index The index of where to put it.
     * @param line  The lore line to add.
     */
    public ItemBuilder addLoreLine( final int index, final String line ) {
        final List<String> lore = new ArrayList<>( this.itemMeta.getLore() );
        lore.set( index, line );
        this.itemMeta.setLore( lore );
        return this;
    }

    /**
     * Sets the dye color on an item.
     * <b>* Notice that this doesn't check for item type, sets the literal data of the dyecolor as durability.</b>
     *
     * @param dyeColor The color to put.
     */
    @SuppressWarnings( "deprecation" )
    public ItemBuilder setDyeColor( final DyeColor dyeColor ) {
        this.itemStack.setDurability( dyeColor.getDyeData() );
        return this;
    }

    /**
     * Sets the dye color of a wool item. Works only on wool.
     *
     * @param dyeColor The DyeColor to set the wool item to.
     *
     * @see #setDyeColor( DyeColor )
     * @deprecated As of version 1.2 changed to setDyeColor.
     */
    @Deprecated
    public ItemBuilder setWoolColor( final DyeColor dyeColor ) {
        /*if ( !this.itemStack.getType().equals( Material.WOO ) ) return this;*/
        this.itemStack.setDurability( dyeColor.getDyeData() );
        return this;
    }

    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor pieces.
     *
     * @param color The color to set it to.
     */
    public ItemBuilder setLeatherArmorColor( final Color color ) {
        try {
            final LeatherArmorMeta armorMeta = ( LeatherArmorMeta ) this.itemStack.getItemMeta();

            armorMeta.setColor( color );
            this.itemStack.setItemMeta( armorMeta );
        } catch ( ClassCastException ignored ) {
        }
        return this;
    }

    /**
     * Sets the book data
     *
     * @param author Set the book author
     * @param title  Set the book title
     * @param page   Set the book page
     * @param data   Set the book data
     */
    public ItemBuilder setBook( final Player player, final String author, final String title, final int page, final String data ) {
        final BookMeta bookMeta = ( BookMeta ) this.itemStack.getItemMeta();

        bookMeta.setAuthor( author );
        bookMeta.setTitle( title );
        bookMeta.setPage( page, data );
        this.itemStack.setItemMeta( bookMeta );

        /*( ( org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer ) player ).getHandle().openBook( CraftItemStack.asNMSCopy( itemStack ) );*/
        return this;
    }

    /**
     * Setup a new page with new messages
     *
     * @param page    Set the pages
     * @param message Send the new message on the page
     */
    public ItemBuilder addBookPage( final int page, final String... message ) {
        final BookMeta bookMeta = ( BookMeta ) this.itemStack.getItemMeta();
        final List<String> pages = new ArrayList<>( bookMeta.getPages() );

        pages.set( page, Arrays.toString( message ) );
        bookMeta.setPages( pages );
        this.itemStack.setItemMeta( bookMeta );
        return this;
    }

    /**
     * Set an ItemFlag to an ItemStack
     *
     * @param itemFlags Set the ItemFlag array
     */
    public ItemBuilder addItemFlag( final ItemFlag... itemFlags ) {
        this.itemMeta.addItemFlags( itemFlags );
        return this;
    }

    /**
     * Remove an ItemFlag from an ItemStack
     *
     * @param itemFlags Remove the ItemFlag array
     */
    public ItemBuilder removeItemFlag( final ItemFlag... itemFlags ) {
        this.itemMeta.removeItemFlags( itemFlags );
        return this;
    }

    /**
     * Set the data from an ItemStack
     *
     * @param materialData Set the data
     */
    public ItemBuilder setData( final MaterialData materialData ) {
        this.itemStack.setData( materialData );
        return this;
    }

    /**
     * Set a Banner with patterns
     *
     * @param patterns Set the patterns by a List
     */
    public ItemBuilder setBanner( final List<Pattern> patterns ) {
        final BannerMeta bannerMeta = ( BannerMeta ) this.itemStack.getItemMeta();
        bannerMeta.setPatterns( patterns );

        this.itemStack.setItemMeta( bannerMeta );
        return this;
    }

    /**
     * Remove a pattern from the Banner
     *
     * @param value Set the value, you want to remove
     */
    public ItemBuilder removeBannerPattern( final int value ) {
        final BannerMeta bannerMeta = ( BannerMeta ) this.itemStack.getItemMeta();
        bannerMeta.removePattern( value );

        this.itemStack.setItemMeta( bannerMeta );
        return this;
    }

    /**
     * Set an StoredEnchantment
     *
     * @param enchantment            Set the enchantment you want
     * @param level                  Set the level you want
     * @param ignoreLevelRestriction Set the boolean to true, if you want to ignore the level restriction
     */
    public ItemBuilder addStoredEnchant( final Enchantment enchantment, final int level, final boolean ignoreLevelRestriction ) {
        final EnchantmentStorageMeta storageMeta = ( EnchantmentStorageMeta ) this.itemStack.getItemMeta();
        if ( !storageMeta.hasStoredEnchant( enchantment ) )
            storageMeta.addStoredEnchant( enchantment, level, ignoreLevelRestriction );

        this.itemStack.setItemMeta( storageMeta );
        return this;
    }

    /**
     * Remove an StoredEnchantment
     *
     * @param enchantment Set the enchantment you want to remove
     */
    public ItemBuilder removeStoredEnchant( final Enchantment enchantment ) {
        final EnchantmentStorageMeta storageMeta = ( EnchantmentStorageMeta ) this.itemStack.getItemMeta();
        if ( storageMeta.hasStoredEnchant( enchantment ) )
            storageMeta.removeStoredEnchant( enchantment );

        this.itemStack.setItemMeta( storageMeta );
        return this;
    }

    /**
     * Set the map scaling value
     *
     * @param value Set the boolean
     */
    public ItemBuilder setMapScaling( final boolean value ) {
        final MapMeta mapMeta = ( MapMeta ) this.itemStack.getItemMeta();
        if ( !mapMeta.isScaling() )
            mapMeta.setScaling( value );

        this.itemStack.setItemMeta( mapMeta );
        return this;
    }

    /**
     * Set the main effect on your item
     *
     * @param potionEffectType Set the PotionEffectType you want
     */
    public ItemBuilder setMainEffect( final PotionEffectType potionEffectType ) {
        final PotionMeta potionMeta = ( PotionMeta ) this.itemStack.getItemMeta();
        potionMeta.setMainEffect( potionEffectType );

        this.itemStack.setItemMeta( potionMeta );
        return this;
    }

    /**
     * Add a custom potion effect on your item
     *
     * @param potionEffect Set the PotionEffect you want
     * @param overwrite    Set the boolean
     */
    public ItemBuilder addCustomEffect( final PotionEffect potionEffect, final boolean overwrite ) {
        final PotionMeta potionMeta = ( PotionMeta ) this.itemStack.getItemMeta();
        potionMeta.addCustomEffect( potionEffect, overwrite );

        this.itemStack.setItemMeta( potionMeta );
        return this;
    }

    /**
     * Remove an potion effect
     *
     * @param potionEffectType Set the PotionEffectType you want to remove
     */
    public ItemBuilder removeCustomEffect( PotionEffectType potionEffectType ) {
        final PotionMeta potionMeta = ( PotionMeta ) this.itemStack.getItemMeta();

        potionMeta.removeCustomEffect( potionEffectType );
        this.itemStack.setItemMeta( potionMeta );
        return this;
    }

    /**
     * Set the item in glow without enchantments
     *
     * @return Return the class
     */
    public ItemBuilder glowItem() {
        this.itemStack.addEnchantment( Enchantment.ARROW_DAMAGE, 1 );
        this.itemMeta.addItemFlags( ItemFlag.HIDE_ENCHANTS );

        this.itemStack.setItemMeta( itemMeta );
        return this;
    }

    public ItemBuilder unbreakable() {
        return this;
    }

    /**
     * Retrieves the this.itemStack from the ItemBuilder.
     *
     * @return The this.itemStack created/modified by the ItemBuilder instance.
     */
    public ItemStack toItemStack() {
        this.itemStack.setItemMeta( this.itemMeta );
        return this.itemStack;
    }
}