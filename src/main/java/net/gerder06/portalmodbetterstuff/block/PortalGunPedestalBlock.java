package net.gerder06.portalmodbetterstuff.block;

import net.gerder06.portalmodbetterstuff.tileentity.PedestalTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;

import javax.annotation.Nullable;
import javax.swing.*;

/**
 * The Portal Gun Pedestal block.
 *
 * <p>A decorative display stand that holds one item. The item is stored in
 * {@link PedestalTileEntity} and rendered floating above the pedestal by
 * {@link net.gerder06.portalmodbetterstuff.client.renderer.PedestalRenderer}.
 *
 * <p>Players interact with the pedestal by pressing the "X" key while looking at it.
 * This sends a {@link net.gerder06.portalmodbetterstuff.network.PacketPedestalInteract}
 * packet to the server, which swaps the player's main-hand item with the stored item.
 *
 * <p>Block properties: copies Iron Block, no occlusion (transparent hitbox model).
 * Facing: horizontal direction (inherits HorizontalBlock.FACING).
 * Shape: a 12x12x12 box centered at x/z 2-14, full height y 0-14.
 */
public class PortalGunPedestalBlock extends Block {

    /**
     * Horizontal facing direction (N/S/E/W). Inherited from HorizontalBlock.
     */
    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    /**
     * Collision / selection shape: a slightly inset box (2,0,2 -> 14,14,14).
     */
    protected static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 14, 14);

    public PortalGunPedestalBlock(AbstractBlock.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    /**
     * Pedestal emits no light (returns 0).
     */

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 0;
    }

    /**
     * Right-clicking the pedestal does nothing special server-side here;
     * the swap logic is triggered by the "X" key via the packet system.
     * Returns SUCCESS on the client (to prevent further processing) or PASS on the server.
     */
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        // world.isClientSide -> true on client, false on server
        return ActionResultType.sidedSuccess(world.isClientSide);
    }

    /**
     * Faces the pedestal toward the player when placed (opposite of player look direction).
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    /**
     * Supports structure rotation.
     */
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Supports structure mirroring.
     */
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /**
     * This block always has a tile entity.
     */
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    /**
     * Creates the tile entity that stores the displayed item.
     */
    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PedestalTileEntity();
    }
}