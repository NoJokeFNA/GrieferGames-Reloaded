package net.griefergames.reloaded.build;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Easily create itemStacks, without messing your hands.
 * <i>Note that if you do use this in one of your projects, leave this notice.</i>
 * <i>Please do credit me if you do use this in one of your projects.</i>
 *
 * @author NonameSL
 * @author NoJokeFNA
 */
public class ItemBuilder {

    private @NotNull
    final ItemStack itemStack;
    private @NotNull final ItemMeta itemMeta;

    /**
     * Create a new {@link ItemBuilder} from scratch.
     *
     * @param material The {@code #material} to create the {@link ItemBuilder} with.
     */
    public ItemBuilder(@NotNull final Material material) {
        this(material, 1);
    }

    /**
     * Create a new {@link ItemBuilder} over an existing this.itemStack.
     *
     * @param itemStack The this.itemStack to create the ItemBuilder over.
     */
    public ItemBuilder(@NotNull final ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = Objects.requireNonNull(itemStack.getItemMeta());
    }

    /**
     * Create a new {@link ItemBuilder} from scratch.
     *
     * @param material The material of the item.
     * @param amount   The amount of the item.
     */
    public ItemBuilder(@NotNull final Material material, final int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = Objects.requireNonNull(itemStack.getItemMeta());
    }

    /**
     * Clone the {@link ItemBuilder} into a new one.
     *
     * @return The cloned instance.
     */
    public ItemBuilder clone() {
        try {
            @NotNull final ItemBuilder cloneItemBuilder = (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return new ItemBuilder(this.itemStack);
    }

    /**
     * Set the displayname of the item.
     *
     * @param name The name to change it to.
     */
    public ItemBuilder setDisplayName(@NotNull final String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    /**
     * Add an unsafe enchantment.
     *
     * @param enchantment The enchantment to add.
     * @param level       The level to put the enchant on.
     */
    public ItemBuilder addUnsafeEnchantment(@NotNull final Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Remove a certain enchant from the item.
     *
     * @param enchantment The enchantment to remove
     */
    public ItemBuilder removeEnchantment(@NotNull final Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Set the skull owner for the item. Works on skulls only.
     *
     * @param owner The {@link OfflinePlayer} of the skull's owner.
     */
    public ItemBuilder setSkullOwner(@NotNull final OfflinePlayer owner) {
        @NotNull final SkullMeta skullMeta = (SkullMeta) Objects.requireNonNull(this.itemStack.getItemMeta());
        skullMeta.setOwningPlayer(owner);

        this.itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * Add an enchant to the item.
     *
     * @param enchantment The enchant to add
     * @param level       The level
     */
    public ItemBuilder addEnchant(@NotNull final Enchantment enchantment, final int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Add multiple enchants at once.
     *
     * @param enchantments The enchants to add.
     */
    public ItemBuilder addEnchantments(@NotNull final Map<Enchantment, Integer> enchantments) {
        this.itemStack.addEnchantments(enchantments);
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(@NotNull final String... lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(@NotNull final List<String> lore) {
        this.itemMeta.setLore(lore);
        return this;
    }

    /**
     * Remove a lore line.
     */
    public ItemBuilder removeLoreLine(@NotNull final String line) {
        @NotNull final List<String> lore = new ArrayList<>(Objects.requireNonNull(this.itemMeta.getLore()));
        if (!lore.contains(line))
            return this;

        lore.remove(line);

        this.itemMeta.setLore(lore);
        return this;
    }

    /**
     * Remove a lore line.
     *
     * @param index The index of the lore line to remove.
     */
    public ItemBuilder removeLoreLine(final int index) {
        @NotNull final List<String> lore = new ArrayList<>(Objects.requireNonNull(this.itemMeta.getLore()));
        if (index < 0 || index > lore.size())
            return this;

        lore.remove(index);

        this.itemMeta.setLore(lore);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     */
    public ItemBuilder addLoreLine(@NotNull final String line) {
        List<String> lore = new ArrayList<>();
        if (this.itemMeta.hasLore())
            lore = new ArrayList<>(Objects.requireNonNull(this.itemMeta.getLore()));

        lore.add(line);

        this.itemMeta.setLore(lore);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param index The index of where to put it.
     * @param line  The lore line to add.
     */
    public ItemBuilder addLoreLine(final int index, @NotNull final String line) {
        @NotNull final List<String> lore = new ArrayList<>(Objects.requireNonNull(this.itemMeta.getLore()));
        lore.set(index, line);

        this.itemMeta.setLore(lore);
        return this;
    }

    /**
     * Sets the dye color on an item.
     * <b>* Notice that this doesn't check for item type, sets the literal data of the dyecolor as durability.</b>
     *
     * @param dyeColor The color to put.
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder setDyeColor(@NotNull final DyeColor dyeColor) {
        this.itemStack.setDurability(dyeColor.getDyeData());
        return this;
    }

    /**
     * Sets the dye color of a wool item. Works only on wool.
     *
     * @param dyeColor The DyeColor to set the wool item to.
     * @see #setDyeColor(DyeColor)
     * @deprecated As of version 1.2 changed to setDyeColor.
     */
    @Deprecated
    public ItemBuilder setWoolColor(@NotNull final DyeColor dyeColor) {
        /*if ( !this.itemStack.getType().equals( Material.WOO ) ) return this;*/
        this.itemStack.setDurability(dyeColor.getDyeData());
        return this;
    }

    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor pieces.
     *
     * @param color The color to set it to.
     */
    public ItemBuilder setLeatherArmorColor(@NotNull final Color color) {
        @NotNull final LeatherArmorMeta armorMeta = (LeatherArmorMeta) Objects.requireNonNull(this.itemStack.getItemMeta());
        armorMeta.setColor(color);

        this.itemStack.setItemMeta(armorMeta);
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
    public ItemBuilder setBook(@NotNull final Player player, @NotNull final String author, @NotNull final String title, final int page, @NotNull final String data) {
        @NotNull final BookMeta bookMeta = (BookMeta) Objects.requireNonNull(this.itemStack.getItemMeta());
        bookMeta.setAuthor(author);
        bookMeta.setTitle(title);
        bookMeta.setPage(page, data);

        this.itemStack.setItemMeta(bookMeta);
        return this;
    }

    /**
     * Setup a new page with new messages
     *
     * @param page    Set the pages
     * @param message Send the new message on the page
     */
    public ItemBuilder addBookPage(final int page, @NotNull final String... message) {
        @NotNull final BookMeta bookMeta = (BookMeta) Objects.requireNonNull(this.itemStack.getItemMeta());

        @NotNull final List<String> pages = new ArrayList<>(bookMeta.getPages());
        pages.set(page, Arrays.toString(message));

        bookMeta.setPages(pages);
        this.itemStack.setItemMeta(bookMeta);
        return this;
    }

    /**
     * Set an ItemFlag to an ItemStack
     *
     * @param itemFlags Set the ItemFlag array
     */
    public ItemBuilder addItemFlag(@NotNull final ItemFlag... itemFlags) {
        this.itemMeta.addItemFlags(itemFlags);
        return this;
    }

    /**
     * Remove an ItemFlag from an ItemStack
     *
     * @param itemFlags Remove the ItemFlag array
     */
    public ItemBuilder removeItemFlag(@NotNull final ItemFlag... itemFlags) {
        this.itemMeta.removeItemFlags(itemFlags);
        return this;
    }

    /**
     * Set a Banner with patterns
     *
     * @param patterns Set the patterns by a List
     */
    public ItemBuilder setBanner(@NotNull final List<Pattern> patterns) {
        @NotNull final BannerMeta bannerMeta = (BannerMeta) Objects.requireNonNull(this.itemStack.getItemMeta());
        bannerMeta.setPatterns(patterns);

        this.itemStack.setItemMeta(bannerMeta);
        return this;
    }

    /**
     * Remove a pattern from the Banner
     *
     * @param value Set the value, you want to remove
     */
    public ItemBuilder removeBannerPattern(final int value) {
        @NotNull final BannerMeta bannerMeta = (BannerMeta) Objects.requireNonNull(this.itemStack.getItemMeta());
        bannerMeta.removePattern(value);

        this.itemStack.setItemMeta(bannerMeta);
        return this;
    }

    /**
     * Set an StoredEnchantment
     *
     * @param enchantment            Set the enchantment you want
     * @param level                  Set the level you want
     * @param ignoreLevelRestriction Set the boolean to true, if you want to ignore the level restriction
     */
    public ItemBuilder addStoredEnchant(@NotNull final Enchantment enchantment, final int level, final boolean ignoreLevelRestriction) {
        @NotNull final EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) Objects.requireNonNull(this.itemStack.getItemMeta());
        if (!storageMeta.hasStoredEnchant(enchantment))
            storageMeta.addStoredEnchant(enchantment, level, ignoreLevelRestriction);

        this.itemStack.setItemMeta(storageMeta);
        return this;
    }

    /**
     * Remove an StoredEnchantment
     *
     * @param enchantment Set the enchantment you want to remove
     */
    public ItemBuilder removeStoredEnchant(@NotNull final Enchantment enchantment) {
        @NotNull final EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) Objects.requireNonNull(this.itemStack.getItemMeta());
        if (storageMeta.hasStoredEnchant(enchantment))
            storageMeta.removeStoredEnchant(enchantment);

        this.itemStack.setItemMeta(storageMeta);
        return this;
    }

    /**
     * Set the map scaling value
     *
     * @param value Set the boolean
     */
    public ItemBuilder setMapScaling(final boolean value) {
        @NotNull final MapMeta mapMeta = (MapMeta) Objects.requireNonNull(this.itemStack.getItemMeta());
        if (!mapMeta.isScaling())
            mapMeta.setScaling(value);

        this.itemStack.setItemMeta(mapMeta);
        return this;
    }

    /**
     * Add a custom potion effect on your item
     *
     * @param potionEffect Set the PotionEffect you want
     * @param overwrite    Set the boolean
     */
    public ItemBuilder addCustomEffect(@NotNull final PotionEffect potionEffect, final boolean overwrite) {
        @NotNull final PotionMeta potionMeta = (PotionMeta) Objects.requireNonNull(this.itemStack.getItemMeta());
        potionMeta.addCustomEffect(potionEffect, overwrite);

        this.itemStack.setItemMeta(potionMeta);
        return this;
    }

    /**
     * Remove an potion effect
     *
     * @param potionEffectType Set the PotionEffectType you want to remove
     */
    public ItemBuilder removeCustomEffect(PotionEffectType potionEffectType) {
        @NotNull final PotionMeta potionMeta = (PotionMeta) Objects.requireNonNull(this.itemStack.getItemMeta());

        potionMeta.removeCustomEffect(potionEffectType);
        this.itemStack.setItemMeta(potionMeta);
        return this;
    }

    /**
     * Set the item in glow without enchantments
     *
     * @return Return the class
     */
    public ItemBuilder glowItem() {
        this.itemStack.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        this.itemStack.setItemMeta(itemMeta);
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
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}