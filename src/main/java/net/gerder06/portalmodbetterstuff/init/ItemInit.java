package net.gerder06.portalmodbetterstuff.init;

import net.gerder06.portalmodbetterstuff.PortalModBetterStuffTab;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers all items added by this mod.
 * <p>
 * Most items are simple {@link BlockItem}s - they let a block be held and placed
 * from the inventory. A helper method {@link #registerBlockItem} handles the
 * common case. A few items (catwalk variants, Aperture glass) needed to be
 * registered directly when BlockItem required an explicit item properties builder.
 * <p>
 * All items are placed in the {@link PortalModBetterStuffTab} creative tab
 * ("PortalMod+").
 */
public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "portalmodbetterstuff");

    // --- Catwalk items ---

    /**
     * BlockItem for the straight catwalk.
     */
    public static final RegistryObject<Item> CATWALK = registerBlockItem("catwalk", BlockInit.CATWALK);

    /**
     * BlockItem for the turning catwalk.
     */
    public static final RegistryObject<Item> CATWALK_TURN_ITEM = registerBlockItem("catwalk_turn", BlockInit.CATWALK_TURN);

    /**
     * BlockItem for the 2-way (T-shaped) catwalk.
     */
    public static final RegistryObject<Item> CATWALK_MORE_SIDE_ITEM = registerBlockItem("catwalk_more_side", BlockInit.CATWALK_MORE_SIDE);

    /**
     * BlockItem for the catwalk stairs (explicit lambda because it uses a custom supplier).
     */
    public static final RegistryObject<Item> CATWALK_STAIRS_ITEM = ITEMS.register("catwalk_stairs", () -> new BlockItem((net.minecraft.block.Block) BlockInit.CATWALK_STAIRS.get(), properties()));

    /**
     * BlockItem for the all-way (no-barrier) catwalk.
     */
    public static final RegistryObject<Item> CATWALK_ALL_WAY_ITEM = ITEMS.register("catwalk_all_way", () -> new BlockItem((net.minecraft.block.Block) BlockInit.CATWALK_ALL_WAY.get(), properties()));

    /**
     * BlockItem for the one-barrier catwalk.
     */
    public static final RegistryObject<Item> CATWALK_ONE_BARRIER_ITEM = ITEMS.register("catwalk_one_barrier", () -> new BlockItem((net.minecraft.block.Block) BlockInit.CATWALK_ONE_BARRIER.get(), properties()));

    // --- Office / decor items ---

    /**
     * BlockItem for the desk floor tile.
     */
    public static final RegistryObject<Item> DESK_FLOOR_ITEM = registerBlockItem("desk_floor", BlockInit.DESK_FLOOR);

    /**
     * BlockItem for the computer monitor.
     */
    public static final RegistryObject<Item> COMPUTER_SCREEN_ITEM = registerBlockItem("computer_screen", BlockInit.COMPUTER_SCREEN);

    /**
     * BlockItem for the computer tower.
     */
    public static final RegistryObject<Item> COMPUTER_BOX_ITEM = registerBlockItem("computer_box", BlockInit.COMPUTER_BOX);

    /**
     * BlockItem for the security camera.
     */
    public static final RegistryObject<Item> SECURITY_CAMERA = registerBlockItem("security_camera", BlockInit.SECURITY_CAMERA);

    /**
     * BlockItem for the office chair.
     */
    public static final RegistryObject<Item> OFFICE_CHAIR_ITEM = registerBlockItem("office_chair", BlockInit.OFFICE_CHAIR);

    // --- Aperture glass items (explicit lambdas with fresh properties builders) ---

    /**
     * BlockItem for Aperture Science Glass.
     */
    public static final RegistryObject<Item> APERTURE_GLASS_ITEM = ITEMS.register("aperture_glass", () -> new BlockItem((net.minecraft.block.Block) BlockInit.APERTURE_GLASS.get(), new Item.Properties().tab(PortalModBetterStuffTab.INSTANCE)));

    /**
     * BlockItem for Aperture Science Glass Pane.
     */
    public static final RegistryObject<Item> APERTURE_GLASS_PANE_ITEM = ITEMS.register("aperture_glass_pane", () -> new BlockItem((net.minecraft.block.Block) BlockInit.APERTURE_GLASS_PANE.get(), new Item.Properties().tab(PortalModBetterStuffTab.INSTANCE)));

    // --- Portal / Aperture themed items ---

    /**
     * BlockItem for the Relaxation Vault decorative block.
     */
    public static final RegistryObject<Item> RELAXATION_VAULT_ITEM = registerBlockItem("relaxation_vault", BlockInit.RELAXATION_VAULT);

    /**
     * BlockItem for the Portal Gun Pedestal (with explicit properties builder).
     */
    public static final RegistryObject<Item> PORTAL_GUN_PEDESTAL_ITEM = ITEMS.register("portal_gun_pedestal", () -> new BlockItem((net.minecraft.block.Block) BlockInit.PORTAL_GUN_PEDESTAL.get(), new Item.Properties().tab(PortalModBetterStuffTab.INSTANCE)));

    /**
     * BlockItem for the exit sign.
     */
    public static final RegistryObject<Item> EXIT_SIGN_ITEM = ITEMS.register("exit_sign", () -> new BlockItem((net.minecraft.block.Block) BlockInit.EXIT_SIGN.get(), new Item.Properties().tab(PortalModBetterStuffTab.INSTANCE)));

    /**
     * BlockItem for the directional exit sign.
     */
    public static final RegistryObject<Item> EXIT_SIGN_DIRECTION_ITEM = ITEMS.register("exit_sign_direction", () -> new BlockItem((net.minecraft.block.Block) BlockInit.EXIT_SIGN_DIRECTION.get(), new Item.Properties().tab(PortalModBetterStuffTab.INSTANCE)));

    // --------------------------------------------
    // Helpers
    // --------------------------------------------

    /**
     * Convenience factory: registers a BlockItem whose item properties use the mod's creative tab.
     *
     * @param name  Registry name (must match the block's registry name).
     * @param block The block RegistryObject to wrap.
     */
    public static RegistryObject<Item> registerBlockItem(String name, RegistryObject<net.minecraft.block.Block> block) {
        return ITEMS.register(name, () -> new BlockItem((net.minecraft.block.Block) block.get(), properties()));
    }

    /**
     * Returns a fresh {@link Item.Properties} builder pre-assigned to the mod's creative tab.
     */
    public static Item.Properties properties() {
        return new Item.Properties().tab(PortalModBetterStuffTab.INSTANCE);
    }
}
