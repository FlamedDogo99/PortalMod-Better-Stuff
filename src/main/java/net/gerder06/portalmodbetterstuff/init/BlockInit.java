package net.gerder06.portalmodbetterstuff.init;

import net.gerder06.portalmodbetterstuff.block.ApertureGlassBlock;
import net.gerder06.portalmodbetterstuff.block.ApertureGlassPaneBlock;
import net.gerder06.portalmodbetterstuff.block.PortalGunPedestalBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers all blocks added by this mod.
 * <p>
 * Shared block-state properties (used by the Aperture glass family):
 * - {@code type} - enum GlassType (NORMAL or BOTTOM), controls the visual appearance
 * when two Aperture Glass blocks are stacked.
 * - {@code paneled} - boolean, toggled when a crouching player right-clicks the glass;
 * changes the glass between a normal pane look and a "paneled" look.
 */
public class BlockInit {

    /**
     * Forge deferred register - holds all blocks until registration fires.
     */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "portalmodbetterstuff");

    // -------------------------------------------------------------------------
    // Shared state properties (referenced by ApertureGlassBlock and its pane subclass)
    // -------------------------------------------------------------------------

    /**
     * Enum property that tracks whether a glass block is the bottom of a two-tall stack.
     */
    public static final EnumProperty<GlassType> type = EnumProperty.create("type", GlassType.class);

    /**
     * Boolean property that lets players toggle a "paneled" look on Aperture glass.
     */
    public static final BooleanProperty paneled = BooleanProperty.create("paneled");

    // -------------------------------------------------------------------------
    // Block registrations
    // -------------------------------------------------------------------------

    /**
     * Simple flat floor tile - copies properties from vanilla Stone.
     */
    public static final RegistryObject<Block> DESK_FLOOR = BLOCKS.register("desk_floor", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(2.0f).sound(SoundType.WOOD)));

    /**
     * Aperture Science Glass - a transparent glass block with a two-state visual (NORMAL / BOTTOM).
     * Right-click while crouching to toggle the "paneled" appearance.
     */
    public static final RegistryObject<Block> APERTURE_GLASS = BLOCKS.register("aperture_glass", () -> new ApertureGlassBlock(AbstractBlock.Properties.copy(Blocks.GLASS).noOcclusion()));

    /**
     * Aperture Science Glass Pane - a thin, directional variant of the Aperture Glass block.
     */
    public static final RegistryObject<Block> APERTURE_GLASS_PANE = BLOCKS.register("aperture_glass_pane", () -> new ApertureGlassPaneBlock(AbstractBlock.Properties.copy(Blocks.GLASS).noOcclusion()));

    // - Exit Signs -

    /**
     * A flat wall-mounted exit sign (no direction indicator).
     */
    public static final RegistryObject<Block> EXIT_SIGN = BLOCKS.register("exit_sign", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).noOcclusion()));

    /**
     * An exit sign with a direction arrow.
     */
    public static final RegistryObject<Block> EXIT_SIGN_DIRECTION = BLOCKS.register("exit_sign_direction", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).noOcclusion()));

    // - Catwalk family (all share: copied from Stone, strength 5.0, metal sound, noOcclusion) -

    /**
     * Standard straight catwalk - one open side.
     */
    public static final RegistryObject<Block> CATWALK = BLOCKS.register("catwalk", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(5.0f).sound(SoundType.METAL).noOcclusion()));

    /**
     * Catwalk with two open sides (T-shape).
     */
    public static final RegistryObject<Block> CATWALK_MORE_SIDE = BLOCKS.register("catwalk_more_side", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(5.0f).sound(SoundType.METAL).noOcclusion()));

    /**
     * Catwalk with a 90-degree corner turn.
     */
    public static final RegistryObject<Block> CATWALK_TURN = BLOCKS.register("catwalk_turn", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(5.0f).sound(SoundType.METAL).noOcclusion()));

    /**
     * Catwalk with all four sides open (no barriers).
     */
    public static final RegistryObject<Block> CATWALK_ALL_WAY = BLOCKS.register("catwalk_all_way", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(5.0f).sound(SoundType.METAL).noOcclusion()));

    /**
     * Catwalk shaped like a staircase.
     */
    public static final RegistryObject<Block> CATWALK_STAIRS = BLOCKS.register("catwalk_stairs", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(5.0f).sound(SoundType.METAL).noOcclusion()));

    /**
     * Catwalk with only one railing/barrier.
     */
    public static final RegistryObject<Block> CATWALK_ONE_BARRIER = BLOCKS.register("catwalk_one_barrier", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(5.0f).sound(SoundType.METAL).noOcclusion()));

    // - Aperture office / computer decor -


    /**
     * A computer monitor block. Emits light level 13.
     */
    public static final RegistryObject<Block> COMPUTER_SCREEN = BLOCKS.register("computer_screen", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(3.0f).sound(SoundType.METAL).noOcclusion().lightLevel(state -> 13)));

    /**
     * The tower/box unit of the computer.
     */
    public static final RegistryObject<Block> COMPUTER_BOX = BLOCKS.register("computer_box", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(4.0f).sound(SoundType.METAL).noOcclusion()));

    /**
     * A Portal Gun display pedestal.
     * Has a TileEntity ({@link net.gerder06.portalmodbetterstuff.tileentity.PedestalTileEntity})
     * that stores the item placed on it, and a custom renderer that renders that item floating above.
     * Pressing the "X" key while looking at the pedestal swaps the item in/out of the player's hand.
     */
    public static final RegistryObject<Block> PORTAL_GUN_PEDESTAL = BLOCKS.register("portal_gun_pedestal", () -> new PortalGunPedestalBlock(AbstractBlock.Properties.copy(Blocks.STONE).noOcclusion()));

    /**
     * Decorative security camera block.
     */
    public static final RegistryObject<Block> SECURITY_CAMERA = BLOCKS.register("security_camera", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(3.0f).sound(SoundType.METAL).noOcclusion()));


    /**
     * A large decorative Relaxation Vault pod block. Emits light level 7, strength 5.
     */
    public static final RegistryObject<Block> RELAXATION_VAULT = BLOCKS.register("relaxation_vault", () -> new Block(AbstractBlock.Properties.copy(Blocks.STONE).strength(5.0f).sound(SoundType.METAL).noOcclusion().lightLevel(state -> 7)));

    /**
     * Decorative office chair block. Uses Material.WOOD, strength 2, WOOD sound.
     */
    public static final RegistryObject<Block> OFFICE_CHAIR = BLOCKS.register("office_chair", () -> new Block(AbstractBlock.Properties.of(Material.WOOD).strength(2.0f).sound(SoundType.METAL).noOcclusion()));

    // -------------------------------------------------------------------------
    // Inner enum - Aperture glass visual variant
    // -------------------------------------------------------------------------

    /**
     * Controls the tile texture variant of Aperture Science Glass.
     * NORMAL = standard appearance (top / standalone block)
     * BOTTOM = shows a bottom-edge texture when another Aperture Glass block is placed above
     */
    public enum GlassType implements net.minecraft.util.IStringSerializable {
        NORMAL, BOTTOM;

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
    }
}
