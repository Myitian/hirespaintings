package com.myitian.network.packet.s2c.play;

import com.myitian.entity.decoration.hirespainting.HiResPaintingEntity;
import com.myitian.entity.decoration.hirespainting.HiResPaintingMotive;
import com.myitian.hirespaintings.HiResPaintingsMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.UUID;

public class HiResPaintingSpawnS2CPacket implements Packet<ClientPlayPacketListener> {
    private final int id;
    private final UUID uuid;
    private final BlockPos pos;
    private final Direction facing;
    private final int motiveId;

    public HiResPaintingSpawnS2CPacket(HiResPaintingEntity entity) {
        this.id = entity.getId();
        this.uuid = entity.getUuid();
        this.pos = entity.getDecorationBlockPos();
        this.facing = entity.getHorizontalFacing();
        this.motiveId = HiResPaintingsMain.HIRESPAINTING_MOTIVE.getRawId(entity.motive);
    }

    public HiResPaintingSpawnS2CPacket(PacketByteBuf buf) {
        this.id = buf.readVarInt();
        this.uuid = buf.readUuid();
        this.motiveId = buf.readVarInt();
        this.pos = buf.readBlockPos();
        this.facing = Direction.fromHorizontal(buf.readUnsignedByte());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(this.id);
        buf.writeUuid(this.uuid);
        buf.writeVarInt(this.motiveId);
        buf.writeBlockPos(this.pos);
        buf.writeByte(this.facing.getHorizontal());
    }

    @Override
    public void apply(ClientPlayPacketListener listener) {
        MinecraftClient mc = MinecraftClient.getInstance();
        NetworkThreadUtils.forceMainThread(this, listener, mc);
        HiResPaintingEntity paintingEntity = new HiResPaintingEntity(mc.world, this.getPos(), this.getFacing(), this.getMotive());
        paintingEntity.setId(this.getId());
        paintingEntity.setUuid(this.getPaintingUuid());
        mc.world.addEntity(this.getId(), paintingEntity);
    }

    public int getId() {
        return this.id;
    }

    public UUID getPaintingUuid() {
        return this.uuid;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public Direction getFacing() {
        return this.facing;
    }

    public HiResPaintingMotive getMotive() {
        return HiResPaintingsMain.HIRESPAINTING_MOTIVE.get(this.motiveId);
    }
}
