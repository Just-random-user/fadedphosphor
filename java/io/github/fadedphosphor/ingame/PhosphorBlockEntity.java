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
    private static final int UPDATE_FREQ_TICKS = 1;

    private int charge = 0;
    private static final int PIECES_IN_ONE_CHARGE = 8;

    public PhosphorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.PHOSPHOR_LANTERN_BLOCK_ENTITY.get(), pos, state);
    }

    private int calculateMaxCharge(int lightLevel) { return lightLevel * PIECES_IN_ONE_CHARGE; }  // TODO: check performance
    public int calculateLightingLevel() { return charge / PIECES_IN_ONE_CHARGE; }
    private void changeLightingLevel()
    {
        int targetLightingLevel = calculateLightingLevel();
        assert this.level != null;
        this.level.setBlock(this.getBlockPos(), this.level.getBlockState(this.getBlockPos())
                .setValue(PhosphorBlock.LEVEL, Math.min(Math.max(targetLightingLevel - 1, 0), 15)), 2);
    }

    public int getCharge() { return charge; }
    public int getMaxCharge()
    {
        if(this.level != null)
            return calculateMaxCharge(this.level.getRawBrightness(this.getBlockPos(), this.level.getSkyDarken()));
        else return -1;
    }
    public void changeCharge(int lightLevel)
    {
        int currMaxCharge = calculateMaxCharge(lightLevel);
        if (charge < currMaxCharge)
            charge++;
        else if (charge > currMaxCharge)
            charge--;
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
    public void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state,
                     @NotNull PhosphorBlockEntity phosphorBlockEntity) {
        if (this.level == null || this.level.isClientSide()) return;
        if (ticks++ == UPDATE_FREQ_TICKS)
        {
            ticks = 0;
            changeCharge(level.getRawBrightness(pos, level.getSkyDarken()));
            changeLightingLevel();
            saveAdditional(this.getUpdateTag());
        }
    }
}
