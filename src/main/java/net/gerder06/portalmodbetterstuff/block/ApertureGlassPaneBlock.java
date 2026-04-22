package net.gerder06.portalmodbetterstuff.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.gerder06.portalmodbetterstuff.init.BlockInit;

import javax.annotation.Nullable;
import java.util.List;

/**
 * A thin, directional pane variant of {@link ApertureGlassBlock}.
 * <p>
 * Extends ApertureGlassBlock so it inherits the {@code paneled}/{@code type} states
 * and right-click toggle behaviour. Additionally it has a {@code FACING} direction
 * property (from {@link BlockStateProperties#HORIZONTAL_FACING}) and four custom
 * collision shapes - one per horizontal direction.
 * <p>
 * The hitbox is a 1-pixel-wide slab spanning the full block height in the
 * direction the pane faces:
 * NORTH / default : x 0-16, y 0-16, z 0- 1
 * SOUTH : x 0-16, y 0-16, z 15-16
 * EAST : x 15-16, y 0-16, z 0-16
 * WEST : x 0- 1, y 0-16, z 0-16
 */
public class ApertureGlassPaneBlock extends ApertureGlassBlock {

    protected static final VoxelShape SHAPE_NORTH = Block.box(0, 0, 0, 16, 16, 1);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(0, 0, 15, 16, 16, 16);
    protected static final VoxelShape SHAPE_EAST = Block.box(15, 0, 0, 16, 16, 16);
    protected static final VoxelShape SHAPE_WEST = Block.box(0, 0, 0, 1, 16, 16);

    public ApertureGlassPaneBlock(AbstractBlock.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            case WEST:
                return SHAPE_WEST;
            default:
                return SHAPE_NORTH;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.portalmodbetterstuff.aperture_glass_pane.connect").withStyle(TextFormatting.GRAY));
    }

    /**
     * Adds the HORIZONTAL_FACING property on top of the inherited paneled/type properties.
     */
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    /**
     * When placing the pane, face it opposite to where the player is looking
     * (so the pane faces the player's view direction).
     */
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState base = super.getStateForPlacement(context);
        if(base == null) {
            return base;
        }
        return base.setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }
}