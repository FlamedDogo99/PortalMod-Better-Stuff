package net.gerder06.portalmodbetterstuff.network;

import net.gerder06.portalmodbetterstuff.tileentity.PedestalTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Packet sent from the client to the server when the player presses "X" while
 * looking at a Portal Gun Pedestal.
 *
 * <h3>Server-side swap logic (handle method):</h3>
 * <ol>
 * <li>Verify the sending player is non-null.</li>
 * <li>Verify the target block position is loaded.</li>
 * <li>Verify a {@link PedestalTileEntity} exists at that position.</li>
 * <li>Get {@code itemInHand} = player's main-hand item.</li>
 * <li>Get {@code itemInPedestal} = item currently stored on the pedestal.</li>
 * <li><strong>Place item onto pedestal:</strong> if pedestal is empty AND player
 * is holding something -> copy the held item into the pedestal, clear the
 * player's main hand.</li>
 * <li><strong>Take item from pedestal:</strong> if pedestal has an item AND player
 * hand is empty -> give the pedestal's item copy to the player, clear
 * the pedestal.</li>
 * </ol>
 */
public class PacketPedestalInteract {

    /**
     * World position of the pedestal block the player interacted with.
     */
    private final BlockPos pos;

    public PacketPedestalInteract(BlockPos pos) {
        this.pos = pos;
    }

    // --- Codec ---

    /**
     * Serialises the packet by writing the BlockPos as a single long.
     */
    public static void encode(PacketPedestalInteract msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.pos);
    }

    /**
     * Deserialises the packet by reading a BlockPos.
     */
    public static PacketPedestalInteract decode(PacketBuffer buf) {
        return new PacketPedestalInteract(buf.readBlockPos());
    }

    // --- Handler ---

    /**
     * Schedules the swap work on the main server thread and marks the packet handled.
     */
    public static void handle(PacketPedestalInteract msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> handleOnMainThread(ctx, msg));
        ctx.get().setPacketHandled(true);
    }

    private static void handleOnMainThread(Supplier<NetworkEvent.Context> ctx, PacketPedestalInteract msg) {
        ServerPlayerEntity player = ctx.get().getSender();  // getSender
        if(player == null) {
            return;
        }

        ServerWorld world = player.getLevel();
        if(!world.isLoaded(msg.pos)) {
            return;
        }

        TileEntity te = world.getBlockEntity(msg.pos);
        if(!(te instanceof PedestalTileEntity)) {
            return;
        }

        PedestalTileEntity pedestal = (PedestalTileEntity) te;
        ItemStack itemInHand = player.getItemInHand(Hand.MAIN_HAND);
        ItemStack itemInPedestal = pedestal.getStoredItem();

        // Case 1 - pedestal is empty and player holds an item -> place item on pedestal
        if(itemInPedestal.isEmpty() && !itemInHand.isEmpty()) {
            pedestal.setStoredItem(itemInHand);
            player.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            return;
        }

        // Case 2 - pedestal has an item and player hand is empty -> take item from pedestal
        if(!itemInPedestal.isEmpty() && itemInHand.isEmpty()) {
            player.setItemInHand(Hand.MAIN_HAND, itemInPedestal.copy());
            pedestal.setStoredItem(ItemStack.EMPTY);
        }
    }
}