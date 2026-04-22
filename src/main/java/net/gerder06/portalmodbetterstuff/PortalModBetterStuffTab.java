package net.gerder06.portalmodbetterstuff;

import net.gerder06.portalmodbetterstuff.init.BlockInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * The creative-mode item tab for all Portal Mod Better Stuff items.
 * Shows up in the creative inventory under the name "PortalMod+".
 * Uses the Computer Screen block as its display icon.
 */
public class PortalModBetterStuffTab extends ItemGroup {

    /**
     * Singleton instance created at class load time.
     */
    public static final PortalModBetterStuffTab INSTANCE = new PortalModBetterStuffTab();

    public PortalModBetterStuffTab() {
        super("portalmodbetterstuff");  // Translation key -> "itemGroup.portalmodbetterstuff" = "PortalMod+"
    }

    /**
     * Returns the ItemStack shown as the tab icon in the creative inventory.
     */
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(BlockInit.COMPUTER_SCREEN.get());
    }
}
