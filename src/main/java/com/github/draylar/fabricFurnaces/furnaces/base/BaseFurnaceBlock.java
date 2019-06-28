package com.github.draylar.fabricFurnaces.furnaces.base;

import com.github.draylar.fabricFurnaces.init.Entities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.loot.context.LootContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseFurnaceBlock extends BlockWithEntity
{
    private static final DirectionProperty FACING;
    static final BooleanProperty LIT;

    protected float speed;
    protected float fuel;
    protected float dupe;

    public BaseFurnaceBlock(Settings block$Settings_1, float speedMultiplier, float fuelMultiplier, float dupeChance100) {
        super(block$Settings_1);

        this.speed = speedMultiplier;
        this.fuel = fuelMultiplier;
        this.dupe = dupeChance100;

        this.setDefaultState(this.stateFactory.getDefaultState().with(FACING, Direction.NORTH).with(LIT, false));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView_1) {
        return new BaseFurnaceEntity(Entities.FABRIC_FURNACE, speed, fuel, dupe);
    }

    @Override
    public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
        if (!world_1.isClient) {
            this.openContainer(world_1, blockPos_1, playerEntity_1);
        }

        return true;
    }

    @Override
    public void onBreak(World world_1, BlockPos blockPos_1, BlockState blockState_1, PlayerEntity playerEntity_1)
    {
        if (world_1.getBlockEntity(blockPos_1) instanceof BaseFurnaceEntity)
        {
            ((BaseFurnaceEntity) world_1.getBlockEntity(blockPos_1)).dropExperience(playerEntity_1);
        }
        super.onBreak(world_1, blockPos_1, blockState_1, playerEntity_1);
    }

    private void openContainer(World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1) {
        BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
        if (blockEntity_1 instanceof BaseFurnaceEntity) {
            playerEntity_1.openContainer((NameableContainerProvider)blockEntity_1);
            playerEntity_1.increaseStat(Stats.INTERACT_WITH_FURNACE, 1);
        }
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState blockState_1, LootContext.Builder lootContext$Builder_1)
    {
        ArrayList<ItemStack> dropList = new ArrayList<ItemStack>();
        dropList.add(new ItemStack(this));
        return dropList;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
        if (blockState_1.get(LIT)) {
            double double_1 = (double)blockPos_1.getX() + 0.5D;
            double double_2 = (double)blockPos_1.getY();
            double double_3 = (double)blockPos_1.getZ() + 0.5D;
            if (random_1.nextDouble() < 0.1D) {
                world_1.playSound(double_1, double_2, double_3, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction_1 = blockState_1.get(FACING);
            Direction.Axis direction$Axis_1 = direction_1.getAxis();
            double double_4 = 0.52D;
            double double_5 = random_1.nextDouble() * 0.6D - 0.3D;
            double double_6 = direction$Axis_1 == Direction.Axis.X ? (double)direction_1.getOffsetX() * 0.52D : double_5;
            double double_7 = random_1.nextDouble() * 6.0D / 16.0D;
            double double_8 = direction$Axis_1 == Direction.Axis.Z ? (double)direction_1.getOffsetZ() * 0.52D : double_5;
            world_1.addParticle(ParticleTypes.SMOKE, double_1 + double_6, double_2 + double_7, double_3 + double_8, 0.0D, 0.0D, 0.0D);
            world_1.addParticle(ParticleTypes.FLAME, double_1 + double_6, double_2 + double_7, double_3 + double_8, 0.0D, 0.0D, 0.0D);
        }
    }


    @Override
    public int getLuminance(BlockState blockState_1) {
        return blockState_1.get(LIT) ? super.getLuminance(blockState_1) : 0;
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        return (BlockState)this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerLookDirection().getOpposite());
    }

    @Override
    public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1)
    {
        if (itemStack_1.hasCustomName())
        {
            BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
            if (blockEntity_1 instanceof BaseFurnaceEntity)
            {
                ((BaseFurnaceEntity) blockEntity_1).setCustomName(itemStack_1.getName());
            }
        }
    }


    @Override
    public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
        if (blockState_1.getBlock() != blockState_2.getBlock()) {
            BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
            if (blockEntity_1 instanceof BaseFurnaceEntity) {
                ItemScatterer.spawn(world_1, blockPos_1, (BaseFurnaceEntity)blockEntity_1);
                world_1.updateHorizontalAdjacent(blockPos_1, this);
            }

            super.onBlockRemoved(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
        }
    }


    @Override
    public boolean hasComparatorOutput(BlockState blockState_1) {
        return true;
    }


    @Override
    public int getComparatorOutput(BlockState blockState_1, World world_1, BlockPos blockPos_1) {
        return Container.calculateComparatorOutput(world_1.getBlockEntity(blockPos_1));
    }


    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.MODEL;
    }


    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(new Property[]{FACING, LIT});
    }

    public float getSpeedMultiplier()
    {
        return speed;
    }

    public float getFuelMultiplier()
    {
        return fuel;
    }

    public float getDupeChance()
    {
        return dupe;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        LIT = RedstoneTorchBlock.LIT;
    }
}
