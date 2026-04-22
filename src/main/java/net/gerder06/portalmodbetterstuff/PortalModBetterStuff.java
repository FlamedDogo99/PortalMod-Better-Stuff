package net.gerder06.portalmodbetterstuff;

import net.gerder06.portalmodbetterstuff.client.renderer.PedestalRenderer;
import net.gerder06.portalmodbetterstuff.init.BlockInit;
import net.gerder06.portalmodbetterstuff.init.ItemInit;
import net.gerder06.portalmodbetterstuff.init.TileEntityInit;
import net.gerder06.portalmodbetterstuff.network.PortalModPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * "Portal Mod Better Stuff" - a Forge 1.16.5 addon for the base PortalMod.
 * <p>
 * Adds Aperture Science-themed decorative blocks (catwalks, glass, furniture,
 * computers, signs, a security camera, a relaxation vault, and a Portal Gun Pedestal).
 * <p>
 * Authors: LeVraiRayan & MG
 * Mod ID : portalmodbetterstuff
 * License: MIT
 */
@Mod("portalmodbetterstuff")
public class PortalModBetterStuff {

    public static final String MOD_ID = "portalmodbetterstuff";

    public PortalModBetterStuff() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the network packet channel
        PortalModPacketHandler.register();

        // Register all blocks, items, and tile entities with the mod event bus
        BlockInit.BLOCKS.register(bus);
        ItemInit.ITEMS.register(bus);
        TileEntityInit.TILE_ENTITIES.register(bus);

        // Register the client-side setup listener
        bus.addListener(this::clientSetup);

        // Register this class to receive Forge bus events (e.g. player events)
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * FMLClientSetupEvent handler - runs only on the client side.
     * Sets the render layers for blocks that require transparency or cutout rendering.
     */
    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Cutout rendering (hides fully-transparent pixels) for catwalk family
            RenderType cutout = RenderType.cutout();
            setRenderLayer(BlockInit.CATWALK, cutout);
            setRenderLayer(BlockInit.RELAXATION_VAULT, cutout);
            setRenderLayer(BlockInit.COMPUTER_BOX, cutout);
            setRenderLayer(BlockInit.SECURITY_CAMERA, cutout);
            setRenderLayer(BlockInit.COMPUTER_SCREEN, cutout);
            setRenderLayer(BlockInit.OFFICE_CHAIR, cutout);
            setRenderLayer(BlockInit.CATWALK_MORE_SIDE, cutout);
            setRenderLayer(BlockInit.CATWALK_TURN, cutout);
            setRenderLayer(BlockInit.CATWALK_STAIRS, cutout);
            setRenderLayer(BlockInit.CATWALK_ALL_WAY, cutout);
            setRenderLayer(BlockInit.CATWALK_ONE_BARRIER, cutout);

            // Translucent rendering for the Aperture glass blocks
            RenderType translucent = RenderType.translucent();
            setRenderLayer(BlockInit.APERTURE_GLASS, translucent);
            setRenderLayer(BlockInit.APERTURE_GLASS_PANE, translucent);

            // Bind the custom tile-entity renderer to the Pedestal tile entity type
            ClientRegistry.bindTileEntityRenderer(
                TileEntityInit.PEDESTAL.get(),
                PedestalRenderer::new
            );
        });
    }

    /**
     * Helper to reduce boilerplate when assigning a render layer to a block registry object.
     */
    private static void setRenderLayer(net.minecraftforge.fml.RegistryObject<Block> blockObj, RenderType layer) {
        RenderTypeLookup.setRenderLayer((Block) blockObj.get(), layer);
    }
}
