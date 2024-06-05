package io.github.fadedphosphor.ingame;

import io.github.fadedphosphor.FadedPhosphor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PhosphorBlockEntity extends BlockEntity implements BlockEntityTicker<PhosphorBlockEntity> {
    private int ticks = 0;
    private static final int updateFreqTicks = 100;

    private int charge = 0;
    private static final int maxCharge = 32;

    public int getCharge() { return charge; }
    public void changeCharge(int value)
    {
        int newCharge = charge + value;
        if (newCharge > maxCharge)
            charge = maxCharge;
        else charge = Math.max(newCharge, 0);
    }

    public PhosphorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.PHOSPHOR_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        CompoundTag fadedphosphorData = tag.getCompound(FadedPhosphor.MODID);
        this.charge = fadedphosphorData.getInt("Charge");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag fadedphosphorData = new CompoundTag();
        fadedphosphorData.putInt("Charge", this.charge);
        tag.put(FadedPhosphor.MODID, fadedphosphorData);
    }

    @Override
    public void tick(@NotNull Level p_155253_, @NotNull BlockPos p_155254_, @NotNull BlockState p_155255_,
                     @NotNull PhosphorBlockEntity p_155256_) {
        if (this.level == null || this.level.isClientSide()) return;
        if (ticks++ == updateFreqTicks)
        {
            ticks = 0;
            changeCharge(1);
            saveAdditional(this.getUpdateTag());
        }
    }
}
