package net.gerder06.portalmodbetterstuff.block;

import net.gerder06.portalmodbetterstuff.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Aperture Science Glass - a semi-transparent glass block with two extra block states:
 *
 * <ul>
 * <li>{@code type} (NORMAL / BOTTOM) - automatically set when another Aperture Glass is placed
 * directly above this block; shows a bottom-border texture variant.</li>
 * <li>{@code paneled} (true / false) - toggled by a <em>sneaking</em> player right-clicking
 * with their main hand. Plays a wood-door sound on toggle.</li>
 * </ul>
 */
public class ApertureGlassBlock extends GlassBlock {

    public ApertureGlassBlock(AbstractBlock.Properties properties) {
        super(properties);
        // Set the default block state: not paneled, type = NORMAL
        registerDefaultState(stateDefinition.any().setValue(BlockInit.paneled, false).setValue(BlockInit.type, BlockInit.GlassType.NORMAL));
    }

    /**
     * Adds a tooltip hinting that the block connects visually when stacked.
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.portalmodbetterstuff.aperture_glass.connect").withStyle(TextFormatting.GRAY));
    }

    /**
     * Registers {@code paneled} and {@code type} as valid block-state properties.
     */
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockInit.paneled, BlockInit.type);
    }

    /**
     * Places the block with paneled=false and type=NORMAL as the initial state.
     */
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(BlockInit.paneled, false).setValue(BlockInit.type, BlockInit.GlassType.NORMAL);
    }

    /**
     * Right-click handler: a <em>sneaking</em> player using their main hand toggles
     * the {@code paneled} state and plays a click sound.
     *
     * @return SUCCESS (consumed) when toggled, PASS otherwise.
     */
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(player.isCrouching() && hand == Hand.MAIN_HAND) {
            boolean nextPaneled = !state.getValue(BlockInit.paneled);
            world.setBlock(pos, state.setValue(BlockInit.paneled, nextPaneled), 3);

            world.playSound(null, pos, SoundEvents.WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 1.0f, 1.2f);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    /**
     * When the block above changes and becomes another Aperture Glass block, this block
     * switches to the BOTTOM visual variant; otherwise it resets to NORMAL.
     */
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP) {
            // Check whether the block above is also an ApertureGlassBlock
            if(facingState.getBlock() == this) {
                return state.setValue(BlockInit.type, BlockInit.GlassType.BOTTOM);
            } else {
                return state.setValue(BlockInit.type, BlockInit.GlassType.NORMAL);
            }
        }
        // For all other directions, delegate to the vanilla GlassBlock logic
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }
}