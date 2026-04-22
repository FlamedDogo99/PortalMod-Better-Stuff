package net.gerder06.portalmodbetterstuff.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.gerder06.portalmodbetterstuff.tileentity.PedestalTileEntity;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

/**
 * Renders the item stored in a {@link PedestalTileEntity} floating above the pedestal block.
 *
 * <p>Rendering steps:
 * <ol>
 * <li>Push a new matrix onto the stack.</li>
 * <li>Translate to the centre of the block face: (0.5, 1.45, 0.5).</li>
 * <li>Scale down to 70% of normal item size.</li>
 * <li>Rotate the item around the Y-axis to match the pedestal's FACING direction,
 * adding 180 degrees so the item faces the same way as the pedestal front.</li>
 * <li>Render the item using {@link ItemCameraTransforms.TransformType#FIXED}
 * (the transform used for items in item frames).</li>
 * <li>Pop the matrix.</li>
 * </ol>
 */
public class PedestalRenderer extends TileEntityRenderer<PedestalTileEntity> {

    public PedestalRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(PedestalTileEntity te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {

        ItemStack stack = te.getStoredItem();
        if(stack.isEmpty()) {
            return;
        }

        matrixStack.pushPose();

        // Centre above the pedestal (0.5 X, 1.45 Y, 0.5 Z)
        matrixStack.translate(0.5, 1.45, 0.5);

        // Scale the item to 70% normal size
        matrixStack.scale(0.7f, 0.7f, 0.7f);

        // Get the Y-axis rotation angle from the pedestal's facing direction.

        // We negate it and add 180 degrees so the item faces outward from the pedestal front.
        float angle = -(te.getBlockState().getValue(HorizontalBlock.FACING).toYRot()) + 180.0f;
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(angle));

        // Render the item as a "fixed" (item-frame) display
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);

        matrixStack.popPose();
    }
}