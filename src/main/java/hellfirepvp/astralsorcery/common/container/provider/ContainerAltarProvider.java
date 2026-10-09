/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2026<p>
 * <p>
 * All rights reserved.<p>
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery<p>
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.container.provider;

import hellfirepvp.astralsorcery.common.container.*;
import hellfirepvp.astralsorcery.common.container.base.ContainerProviderCustom;
import hellfirepvp.astralsorcery.common.tile.TileAltar;
import hellfirepvp.astralsorcery.common.util.MiscUtil;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on GitHub.
 * Class: ContainerAltarProvider
 * Created by HellFirePvP
 * Date: 07.09.2026 / 10:00
 */
public class ContainerAltarProvider<T extends ContainerAltar> extends ContainerProviderCustom<T, ContainerAltarProvider.Data> {

    private final BlockPos pos;
    private final TileAltar.AltarType altarType;

    public static final ExtendedScreenHandlerType<ContainerAltarIllumination, Data> ILLUMINATION_TYPE = new ExtendedScreenHandlerType<>(
            (syncId, inventory, data) -> createServer(syncId, inventory, inventory.player.level(), data),
            Data.STREAM_CODEC);
    public static final ExtendedScreenHandlerType<ContainerAltarLuminance, Data> LUMINANCE_TYPE = new ExtendedScreenHandlerType<>(
            (syncId, inventory, data) -> createServer(syncId, inventory, inventory.player.level(), data),
            Data.STREAM_CODEC);
    public static final ExtendedScreenHandlerType<ContainerAltarRadiance, Data> RADIANCE_TYPE = new ExtendedScreenHandlerType<>(
            (syncId, inventory, data) -> createServer(syncId, inventory, inventory.player.level(), data),
            Data.STREAM_CODEC);

    protected ContainerAltarProvider(BlockPos pos, TileAltar.AltarType altarType) {
        super(MiscUtil.cast(altarType.getContainerType().getMenuType()));
        this.pos = pos;
        this.altarType = altarType;
    }

    public static ContainerAltarProvider<?> openAltar(TileAltar altar) {
        return new ContainerAltarProvider<>(altar.getBlockPos(), altar.getTileData().getAltarType());
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public TileAltar.AltarType getAltarType() {
        return this.altarType;
    }

    @Override
    public Data getScreenOpeningData(ServerPlayer player) {
        return new Data(this.getPos(), this.getAltarType());
    }

    @Nullable
    @Override
    public T createMenu(int containerId, Inventory playerInventory, Player player) {
        return createServer(containerId, playerInventory, player.level(), new Data(this.pos, this.altarType));
    }

    @Nullable
    public static <T extends ContainerAltar> T createServer(int containerId, Inventory playerInventory, Level level, Data data) {
        return MiscUtil.getTileAt(level, data.pos, TileAltar.class, false).map(tile -> {
            if (data.altarType != null && tile.getTileData().getAltarType() != data.altarType) return null;
            return (T) tile.getTileData().getAltarType().getContainerType().provideServerContainer(containerId, playerInventory, tile);
        }).orElse(null);
    }

    public static class Data {
        private final BlockPos pos;
        private final TileAltar.AltarType altarType;

        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC, Data::pos,
                TileAltar.AltarType.STREAM_CODEC, Data::altarType,
                Data::new
        );

        public Data(BlockPos pos, TileAltar.AltarType altarType) {
            this.pos = pos;
            this.altarType = altarType;
        }

        public BlockPos pos() {
            return this.pos;
        }

        public TileAltar.AltarType altarType() {
            return this.altarType;
        }
    }

    public static class Resonance extends ContainerAltarProvider<ContainerAltarResonance> {

        private final boolean isExpanded;

        public static ExtendedScreenHandlerType<ContainerAltarResonance, Data> TYPE = new ExtendedScreenHandlerType<>(
                (syncId, inventory, data) -> createServer(syncId, inventory, inventory.player.level(), data),
                Data.STREAM_CODEC);

        protected Resonance(BlockPos pos, TileAltar.AltarType altarType, boolean isExpanded) {
            super(pos, altarType);
            this.isExpanded = isExpanded;
        }

        @Override
        public Data getScreenOpeningData(ServerPlayer player) {
            return new Data(this.getPos(), this.getAltarType(), isExpanded);
        }

        public static class Data extends ContainerAltarProvider.Data {

            private final boolean isExpanded;

            public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                    BlockPos.STREAM_CODEC, Data::pos,
                    TileAltar.AltarType.STREAM_CODEC, Data::altarType,
                    ByteBufCodecs.BOOL, Data::isExpanded,
                    Data::new
            );

            public Data(BlockPos pos, TileAltar.AltarType altarType, boolean isExpanded) {
                super(pos, altarType);
                this.isExpanded = isExpanded;
            }

            public boolean isExpanded() {
                return this.isExpanded;
            }
        }
    }
}
