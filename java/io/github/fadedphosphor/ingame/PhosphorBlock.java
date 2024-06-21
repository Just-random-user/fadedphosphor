package io.github.fadedphosphor.ingame;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class PhosphorBlock extends LanternBlock implements EntityBlock {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    public PhosphorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(0)));
    }

    @Override
    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        level.getBlockState(pos).setValue(LEVEL, newState.getValue(PhosphorBlock.LEVEL)); //setValue(LEVEL, newState);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos position, @NotNull BlockState state) {
        return BlockEntityInit.PHOSPHOR_LANTERN_BLOCK_ENTITY.get().create(position, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
        super.createBlockStateDefinition(builder);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : (level0, pos0, state0, blockEntity) -> ((PhosphorBlockEntity)blockEntity).tick(level0, pos0, state0, (PhosphorBlockEntity)blockEntity);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand,
                                          @NotNull BlockHitResult result) {
        if(!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof PhosphorBlockEntity && hand == InteractionHand.MAIN_HAND) {
                player.sendSystemMessage(Component.literal("-> LIGHT LEVEL : %d".formatted(level.getRawBrightness(pos, level.getSkyDarken()))));
                player.sendSystemMessage(Component.literal("   CHARGE      : %d".formatted(((PhosphorBlockEntity) be).getCharge())));
                player.sendSystemMessage(Component.literal("   MAX CHARGE  : %d".formatted(((PhosphorBlockEntity) be).getMaxCharge())));
                player.sendSystemMessage(Component.literal("   TLL         : %d".formatted(((PhosphorBlockEntity) be).calculateLightingLevel())));
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.use(state, level, pos, player, hand, result);
    }
}
