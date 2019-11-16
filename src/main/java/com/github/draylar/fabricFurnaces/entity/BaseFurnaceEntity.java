package com.github.draylar.fabricFurnaces.entity;

import com.github.draylar.fabricFurnaces.block.BaseFurnaceBlock;
import com.github.draylar.fabricFurnaces.registry.Entities;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.FurnaceContainer;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BaseFurnaceEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider, Tickable {

    private float speedModifier;
    private float fuelModifier;
    private float dupeChance100;

    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};

    public DefaultedList<ItemStack> inventory;

    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;

    private final PropertyDelegate propertyDelegate;
    private final Map<Identifier, Integer> recipesUsed;
    private final RecipeType<? extends SmeltingRecipe> recipeType;

    public BaseFurnaceEntity(float speedMultiplier, float fuelMultiplier, float duplicateChanceOutOf100) {
        this(Entities.FABRIC_FURNACE, RecipeType.SMELTING);
        this.speedModifier = speedMultiplier;
        this.fuelModifier = fuelMultiplier;
        this.dupeChance100 = duplicateChanceOutOf100;
    }

    protected Text getContainerName() {
        return new TranslatableText("container.furnace", new Object[0]);
    }

    protected Container createContainer(int int_1, PlayerInventory playerInventory_1) {
        return new FurnaceContainer(int_1, playerInventory_1, this, this.propertyDelegate);
    }

    private BaseFurnaceEntity(BlockEntityType<?> blockEntityType_1, RecipeType<? extends SmeltingRecipe> recipeType_1) {
        super(blockEntityType_1);

        this.inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

        this.propertyDelegate = new PropertyDelegate() {
            public int get(int int_1) {
                switch (int_1) {
                    case 0:
                        return BaseFurnaceEntity.this.burnTime;
                    case 1:
                        return BaseFurnaceEntity.this.fuelTime;
                    case 2:
                        return BaseFurnaceEntity.this.cookTime;
                    case 3:
                        return BaseFurnaceEntity.this.cookTimeTotal;
                    default:
                        return 0;
                }
            }

            public void set(int int_1, int int_2) {
                switch (int_1) {
                    case 0:
                        BaseFurnaceEntity.this.burnTime = int_2;
                        break;
                    case 1:
                        BaseFurnaceEntity.this.fuelTime = int_2;
                        break;
                    case 2:
                        BaseFurnaceEntity.this.cookTime = int_2;
                        break;
                    case 3:
                        BaseFurnaceEntity.this.cookTimeTotal = int_2;
                }

            }

            public int size() {
                return 4;
            }
        };

        this.recipesUsed = Maps.newHashMap();
        this.recipeType = recipeType_1;
    }


    private boolean isBurning() {
        return this.burnTime > 0;
    }


    @Override
    public void fromTag(CompoundTag compoundTag_1) {
        super.fromTag(compoundTag_1);

        this.inventory = DefaultedList.ofSize(this.getInvSize(), ItemStack.EMPTY);
        Inventories.fromTag(compoundTag_1, this.inventory);
        this.burnTime = compoundTag_1.getShort("BurnTime");
        this.cookTime = compoundTag_1.getShort("CookTime");
        this.cookTimeTotal = compoundTag_1.getShort("CookTimeTotal");
        this.fuelTime = this.getFuelTime((ItemStack) this.inventory.get(1));
        int int_1 = compoundTag_1.getShort("RecipesUsedSize");

        this.speedModifier = compoundTag_1.getFloat("speedModifier");
        this.fuelModifier = compoundTag_1.getFloat("fuelModifier");
        this.dupeChance100 = compoundTag_1.getFloat("dupeChance");

        for (int int_2 = 0; int_2 < int_1; ++int_2) {
            Identifier identifier_1 = new Identifier(compoundTag_1.getString("RecipeLocation" + int_2));
            int int_3 = compoundTag_1.getInt("RecipeAmount" + int_2);
            this.recipesUsed.put(identifier_1, int_3);
        }

    }


    @Override
    public CompoundTag toTag(CompoundTag compoundTag_1) {
        super.toTag(compoundTag_1);

        compoundTag_1.putShort("BurnTime", (short) this.burnTime);
        compoundTag_1.putShort("CookTime", (short) this.cookTime);
        compoundTag_1.putShort("CookTimeTotal", (short) this.cookTimeTotal);

        compoundTag_1.putFloat("speedModifier", speedModifier);
        compoundTag_1.putFloat("fuelModifier", fuelModifier);
        compoundTag_1.putFloat("dupeChance", dupeChance100);

        Inventories.toTag(compoundTag_1, this.inventory);
        compoundTag_1.putShort("RecipesUsedSize", (short) this.recipesUsed.size());
        int int_1 = 0;

        for (Iterator var3 = this.recipesUsed.entrySet().iterator(); var3.hasNext(); ++int_1) {
            Map.Entry<Identifier, Integer> map$Entry_1 = (Map.Entry) var3.next();
            compoundTag_1.putString("RecipeLocation" + int_1, ((Identifier) map$Entry_1.getKey()).toString());
            compoundTag_1.putInt("RecipeAmount" + int_1, (Integer) map$Entry_1.getValue());
        }

        return compoundTag_1;
    }


    @Override
    public void tick() {
        boolean isBurning = this.isBurning();
        boolean boolean_2 = false;

        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isClient) {
            ItemStack itemStack_1 = this.inventory.get(1);

            // decay cook time if an inventory is empty
            if (!this.isBurning() && (itemStack_1.isEmpty() || this.inventory.get(0).isEmpty())) {
                if (!this.isBurning() && this.cookTime > 0)
                    this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            } else {
                Recipe<?> recipe_1 = this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).orElse(null);

                if (!this.isBurning() && this.canAcceptRecipeOutput(recipe_1)) {
                    this.burnTime = this.getFuelTime(itemStack_1);
                    this.fuelTime = this.burnTime;

                    if (this.isBurning()) {
                        boolean_2 = true;
                        if (!itemStack_1.isEmpty()) {
                            Item item_1 = itemStack_1.getItem();
                            itemStack_1.decrement(1);
                            if (itemStack_1.isEmpty()) {
                                Item item_2 = item_1.getRecipeRemainder();
                                this.inventory.set(1, item_2 == null ? ItemStack.EMPTY : new ItemStack(item_2));
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canAcceptRecipeOutput(recipe_1)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTime();
                        this.craftRecipe(recipe_1);
                        boolean_2 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            }

            if (isBurning != this.isBurning()) {
                boolean_2 = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(BaseFurnaceBlock.LIT, this.isBurning()), 3);
            }
        }

        if (boolean_2) {
            this.markDirty();
        }

    }


    private boolean canAcceptRecipeOutput(Recipe<?> recipe_1) {
        if (!((ItemStack) this.inventory.get(0)).isEmpty() && recipe_1 != null) {
            ItemStack itemStack_1 = recipe_1.getOutput();
            if (itemStack_1.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack_2 = (ItemStack) this.inventory.get(2);
                if (itemStack_2.isEmpty()) {
                    return true;
                } else if (!itemStack_2.isItemEqualIgnoreDamage(itemStack_1)) {
                    return false;
                } else if (itemStack_2.getCount() < this.getInvMaxStackAmount() && itemStack_2.getCount() < itemStack_2.getMaxCount()) {
                    return true;
                } else {
                    return itemStack_2.getCount() < itemStack_1.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    private void craftRecipe(Recipe<?> currentRecipe) {
        if (currentRecipe != null && this.canAcceptRecipeOutput(currentRecipe)) {
            ItemStack inputStack = this.inventory.get(0);
            ItemStack outputStack = this.inventory.get(2);
            ItemStack recipeResultStack = currentRecipe.getOutput();

            int resultCount = world.random.nextInt(100) < dupeChance100 ? 2 : 1;

            if (outputStack.isEmpty()) {
                ItemStack newResultStack = recipeResultStack.copy();
                newResultStack.setCount(resultCount);
                this.inventory.set(2, newResultStack);
            } else if (outputStack.getItem() == recipeResultStack.getItem()) {
                // TODO: WHAT HAPPENS IF FINAL COUNT IS 63 AND WE SMELT DOUBLE?
                outputStack.increment(resultCount);
            }


            if (!this.world.isClient)
                this.setLastRecipe(currentRecipe);


            if (inputStack.getItem() == Blocks.WET_SPONGE.asItem() && !((ItemStack) this.inventory.get(1)).isEmpty() && ((ItemStack) this.inventory.get(1)).getItem() == Items.BUCKET) {
                this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            inputStack.decrement(1);
        }
    }


    protected int getFuelTime(ItemStack itemStack_1) {
        if (itemStack_1.isEmpty()) return 0;

        else {
            Item item_1 = itemStack_1.getItem();
            Integer fuelTime = FuelRegistry.INSTANCE.get(item_1);
            return fuelTime == null ? 0 : (int) (fuelTime / fuelModifier);
        }
    }

    public int getCookTime() {
        int cookTime = this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).map(SmeltingRecipe::getCookTime).orElse(200);
        return (int) (cookTime / speedModifier);
    }

    private static boolean canUseAsFuel(ItemStack itemStack_1) {
        return FuelRegistry.INSTANCE.get(itemStack_1.getItem()) != null;
    }

    public int[] getInvAvailableSlots(Direction direction_1) {
        if (direction_1 == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return direction_1 == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    public boolean canInsertInvStack(int int_1, ItemStack itemStack_1, Direction direction_1) {
        return this.isValidInvStack(int_1, itemStack_1);
    }

    public boolean canExtractInvStack(int int_1, ItemStack itemStack_1, Direction direction_1) {
        if (direction_1 == Direction.DOWN && int_1 == 1) {
            Item item_1 = itemStack_1.getItem();
            return item_1 == Items.WATER_BUCKET || item_1 == Items.BUCKET;
        }

        return true;
    }


    @Override
    public int getInvSize() {
        return this.inventory.size();
    }


    @Override
    public boolean isInvEmpty() {
        Iterator var1 = this.inventory.iterator();

        ItemStack itemStack_1;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack_1 = (ItemStack) var1.next();
        } while (itemStack_1.isEmpty());

        return false;
    }


    @Override
    public ItemStack getInvStack(int int_1) {
        return this.inventory.get(int_1);
    }


    @Override
    public ItemStack takeInvStack(int int_1, int int_2) {
        return Inventories.splitStack(this.inventory, int_1, int_2);
    }


    @Override
    public ItemStack removeInvStack(int int_1) {
        return Inventories.removeStack(this.inventory, int_1);
    }


    @Override
    public void setInvStack(int int_1, ItemStack itemStack_1) {
        ItemStack itemStack_2 = this.inventory.get(int_1);
        boolean boolean_1 = !itemStack_1.isEmpty() && itemStack_1.isItemEqualIgnoreDamage(itemStack_2) && ItemStack.areTagsEqual(itemStack_1, itemStack_2);
        this.inventory.set(int_1, itemStack_1);
        if (itemStack_1.getCount() > this.getInvMaxStackAmount()) {
            itemStack_1.setCount(this.getInvMaxStackAmount());
        }

        if (int_1 == 0 && !boolean_1) {
            this.cookTimeTotal = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }

    }


    @Override
    public boolean canPlayerUseInv(PlayerEntity playerEntity_1) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return playerEntity_1.squaredDistanceTo((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }


    @Override
    public boolean isValidInvStack(int int_1, ItemStack itemStack_1) {
        if (int_1 == 2) {
            return false;
        } else if (int_1 != 1) {
            return true;
        } else {
            ItemStack itemStack_2 = this.inventory.get(1);
            return canUseAsFuel(itemStack_1) || itemStack_1.getItem() == Items.BUCKET && itemStack_2.getItem() != Items.BUCKET;
        }
    }


    @Override
    public void clear() {
        this.inventory.clear();
    }


    @Override
    public void setLastRecipe(Recipe<?> recipe_1) {
        if (recipe_1 != null) {
            this.recipesUsed.compute(recipe_1.getId(), (identifier_1, integer_1) -> 1 + (integer_1 == null ? 0 : integer_1));
        }

    }


    @Override
    public Recipe<?> getLastRecipe() {
        return null;
    }


    @Override
    public void unlockLastRecipe(PlayerEntity playerEntity_1) {
    }

    public void dropExperience(PlayerEntity playerEntity_1) {
        if (!this.world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING)) {
            List<Recipe<?>> list_1 = Lists.newArrayList();
            Iterator var3 = this.recipesUsed.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry<Identifier, Integer> map$Entry_1 = (Map.Entry) var3.next();
                playerEntity_1.world.getRecipeManager().get(map$Entry_1.getKey()).ifPresent((recipe_1) -> {
                    list_1.add(recipe_1);
                    dropExperience(playerEntity_1, map$Entry_1.getValue(), ((SmeltingRecipe) recipe_1).getExperience());
                });
            }

            playerEntity_1.unlockRecipes(list_1);
        }

        this.recipesUsed.clear();
    }

    private static void dropExperience(PlayerEntity playerEntity_1, int int_1, float float_1) {
        int int_2;
        if (float_1 == 0.0F) {
            int_1 = 0;
        } else if (float_1 < 1.0F) {
            int_2 = MathHelper.floor((float) int_1 * float_1);
            if (int_2 < MathHelper.ceil((float) int_1 * float_1) && Math.random() < (double) ((float) int_1 * float_1 - (float) int_2)) {
                ++int_2;
            }

            int_1 = int_2;
        }

        while (int_1 > 0) {
            int_2 = ExperienceOrbEntity.roundToOrbSize(int_1);
            int_1 -= int_2;
            playerEntity_1.world.spawnEntity(new ExperienceOrbEntity(playerEntity_1.world, playerEntity_1.getX(), playerEntity_1.getY() + 0.5D, playerEntity_1.getZ() + 0.5D, int_2));
        }

    }

    @Override
    public void provideRecipeInputs(RecipeFinder recipeFinder_1) {
        Iterator var2 = this.inventory.iterator();

        while (var2.hasNext()) {
            ItemStack itemStack_1 = (ItemStack) var2.next();
            recipeFinder_1.addItem(itemStack_1);
        }
    }
}