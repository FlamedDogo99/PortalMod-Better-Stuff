package net.gerder06.portalmodbetterstuff.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * Sets up the mod's network channel and registers all packet types.
 *
 * <p>Uses Forge's {@link SimpleChannel} (a version-checked, indexed channel).
 * Channel resource location: {@code portalmodbetterstuff:main}.
 * Protocol version: {@code "1"} (a simple string; client and server must match).
 *
 * <p>Registered messages:
 * <ul>
 * <li>ID 0 - {@link PacketPedestalInteract} (client -> server)</li>
 * </ul>
 */
public class PortalModPacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    /**
     * The global SimpleChannel instance; used everywhere packets are sent.
     */
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("portalmodbetterstuff", "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,   // client version acceptor
        PROTOCOL_VERSION::equals    // server version acceptor
    );

    /**
     * Called once during mod construction to register all packet types.
     */
    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, PacketPedestalInteract.class, PacketPedestalInteract::encode, PacketPedestalInteract::decode, PacketPedestalInteract::handle);
    }
}