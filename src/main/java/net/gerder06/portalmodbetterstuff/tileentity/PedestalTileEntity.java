package net.gerder06.portalmodbetterstuff.tileentity;

import net.gerder06.portalmodbetterstuff.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

/**
 * Tile entity for the Portal Gun Pedestal block.
 *
 * <p>Stores exactly one {@link ItemStack} ("storedItem"). The item is saved to NBT
 * under the key {@code "StoredItem"} and synced to the client via
 * {@link SUpdateTileEntityPacket} whenever it changes.
 *
 * <p>Item swapping is initiated by the player pressing the "X" key while looking
 * at the pedestal (see {@link net.gerder06.portalmodbetterstuff.client.PedestalKeyHandler}).
 * The actual server-side swap logic is in
 * {@link net.gerder06.portalmodbetterstuff.network.PacketPedestalInteract}.
 */
public class PedestalTileEntity extends TileEntity {

    /**
     * The item currently displayed / stored on the pedestal. Defaults to EMPTY.
     */
    private ItemStack storedItem = ItemStack.EMPTY;

    public PedestalTileEntity() {
        super(TileEntityInit.PEDESTAL.get());
    }

    // --- Getters / setters ---

    public ItemStack getStoredItem() {
        return storedItem;
    }

    /**
     * Updates the stored item, marks the tile entity dirty (so it will be saved),
     * and notifies the world so clients receive the updated state.
     */
    public void setStoredItem(ItemStack stack) {
        this.storedItem = stack.copy();
        setChanged();
        if(level != null) {
            BlockState state = level.getBlockState(worldPosition);
            // Flag 3 = update neighbors + send to client
            level.sendBlockUpdated(worldPosition, state, state, 3);
        }
    }

    // --- NBT serialization ---

    /**
     * Reads storedItem from NBT on load.
     */
    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        if(nbt.contains("StoredItem")) {
            storedItem = ItemStack.of(nbt.getCompound("StoredItem"));
        }
    }

    /**
     * Writes storedItem to NBT for saving.
     */
    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        nbt.put("StoredItem", storedItem.save(new CompoundNBT()));
        return nbt;
    }

    // --- Client sync ---

    /**
     * Returns the NBT data to send to clients when the tile entity is updated.
     */
    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    /**
     * Creates the packet sent to clients to keep them in sync.
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, 0, getUpdateTag());
    }

    /**
     * Handles the sync packet on the client side by reading the incoming NBT.
     */
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        load(getBlockState(), pkt.getTag());
    }
}