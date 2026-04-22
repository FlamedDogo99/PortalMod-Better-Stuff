package net.gerder06.portalmodbetterstuff.init;

import net.gerder06.portalmodbetterstuff.tileentity.PedestalTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Registers all TileEntityTypes for this mod.
 * Currently only the Portal Gun Pedestal has a tile entity.
 */
public class TileEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "portalmodbetterstuff");

    /**
     * TileEntityType for the Portal Gun Pedestal.
     * Paired with: {@link BlockInit#PORTAL_GUN_PEDESTAL}.
     * Handles: item storage (serialized to NBT as "StoredItem").
     */
    public static final RegistryObject<TileEntityType<PedestalTileEntity>> PEDESTAL = TILE_ENTITIES.register("pedestal", () -> TileEntityType.Builder.of(PedestalTileEntity::new, (net.minecraft.block.Block) BlockInit.PORTAL_GUN_PEDESTAL.get()).build(null));
}