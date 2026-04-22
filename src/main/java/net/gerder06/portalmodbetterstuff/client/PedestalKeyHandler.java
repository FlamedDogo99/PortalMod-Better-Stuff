package net.gerder06.portalmodbetterstuff.client;

import net.gerder06.portalmodbetterstuff.network.PacketPedestalInteract;
import net.gerder06.portalmodbetterstuff.network.PortalModPacketHandler;
import net.gerder06.portalmodbetterstuff.tileentity.PedestalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-only Forge event subscriber that watches for the "X" key (GLFW key code 88).
 *
 * <p>When the player presses X while:
 * <ul>
 * <li>in-game (not in a GUI screen),</li>
 * <li>looking at a block (crosshair ray trace hits a block),</li>
 * <li>and that block has a {@link PedestalTileEntity},</li>
 * </ul>
 * it sends a {@link PacketPedestalInteract} to the server with the targeted block position.
 *
 * <p>The server then handles the item swap logic (see
 * {@link PacketPedestalInteract#handle}).
 */
@Mod.EventBusSubscriber(modid = "portalmodbetterstuff", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PedestalKeyHandler {

    /**
     * GLFW key code for the X key (87 = W in GLFW, 88 = X).
     */
    private static final int KEY_X = 88;

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();

        // Bail if no player or a GUI screen is open
        if(mc.player == null || mc.screen != null) {
            return;
        }

        // Only act on key-down (action == GLFW_PRESS == 1)
        if(event.getKey() == KEY_X && event.getAction() == 1) {
            // Check that the crosshair is aimed at a block
            if(!(mc.hitResult instanceof BlockRayTraceResult)) {
                return;
            }

            BlockPos pos = ((BlockRayTraceResult) mc.hitResult).getBlockPos();

            // Check that the targeted block actually has a pedestal tile entity
            if(mc.level.getBlockEntity(pos) instanceof PedestalTileEntity) {
                // Send interaction packet to server
                PortalModPacketHandler.INSTANCE.sendToServer(new PacketPedestalInteract(pos));
            }
        }
    }
}