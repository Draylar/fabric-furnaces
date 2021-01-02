package draylar.fabricfurnaces.entity;

import draylar.fabricfurnaces.FabricFurnaces;
import draylar.fabricfurnaces.block.BaseFurnaceBlock;
import draylar.fabricfurnaces.config.FurnaceData;
import draylar.fabricfurnaces.registry.FFEntities;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
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
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseFurnaceEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider, Tickable {

    private float speedModifier;
    private float fuelModifier;
    private float duplicationChance; // 0 -> 100% chance

    private static final int[] TOP_SLOTS = new int[] {0};
    private static final int[] BOTTOM_SLOTS = new int[] {2, 1};
    private static final int[] SIDE_SLOTS = new int[] {1};

    public DefaultedList<ItemStack> inventory;

    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;

    private final PropertyDelegate propertyDelegate;
    private final Map<Identifier, Integer> recipesUsed;
    private final RecipeType<? extends SmeltingRecipe> recipeType;

    public BaseFurnaceEntity(float speedMultiplier, float fuelMultiplier, float duplicateChanceOutOf100) {
        this(FFEntities.FABRIC_FURNACE, RecipeType.SMELTING);

        this.speedModifier = speedMultiplier;
        this.fuelModifier = fuelMultiplier;
        this.duplicationChance = duplicateChanceOutOf100;
    }

    private BaseFurnaceEntity(BlockEntityType<?> type, RecipeType<? extends SmeltingRecipe> recipeType_1) {
        super(type);

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

    @Override
    public Text getContainerName() {
        return new TranslatableText("container.furnace", new Object[0]);
    }

    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);

        inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);
        burnTime = tag.getShort("BurnTime");
        cookTime = tag.getShort("CookTime");
        cookTimeTotal = tag.getShort("CookTimeTotal");
        fuelTime = this.getFuelTime(this.inventory.get(1));
        int usedRecipes = tag.getShort("RecipesUsedSize");

        // Get registry ID from state
        Identifier registryID = Registry.BLOCK.getId(state.getBlock());
        Optional<FurnaceData> data = FabricFurnaces.CONFIG.furnaceData.stream().filter(fdata -> fdata.getID().equals(registryID)).findFirst();

        if(data.isPresent()) {
            speedModifier = data.get().getSpeedModifier();
            fuelModifier = data.get().getFuelModifier();
            duplicationChance = data.get().getDuplicationChance();
        }

        // Was not able to retrieve data based on registry ID (this should not happen), fall back to whatever was saved to the tag.
        else {
            speedModifier = 1;
            fuelModifier = 1;
            duplicationChance = 1;
        }

        for (int i = 0; i < usedRecipes; ++i) {
            Identifier recipeID = new Identifier(tag.getString("RecipeLocation" + i));
            int amount = tag.getInt("RecipeAmount" + i);
            recipesUsed.put(recipeID, amount);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        tag.putShort("BurnTime", (short) this.burnTime);
        tag.putShort("CookTime", (short) this.cookTime);
        tag.putShort("CookTimeTotal", (short) this.cookTimeTotal);

        tag.putFloat("speedModifier", speedModifier);
        tag.putFloat("fuelModifier", fuelModifier);
        tag.putFloat("dupeChance", duplicationChance);

        Inventories.toTag(tag, this.inventory);
        tag.putShort("RecipesUsedSize", (short) this.recipesUsed.size());
        int int_1 = 0;

        for (Iterator var3 = this.recipesUsed.entrySet().iterator(); var3.hasNext(); ++int_1) {
            Map.Entry<Identifier, Integer> map$Entry_1 = (Map.Entry) var3.next();
            tag.putString("RecipeLocation" + int_1, ((Identifier) map$Entry_1.getKey()).toString());
            tag.putInt("RecipeAmount" + int_1, (Integer) map$Entry_1.getValue());
        }

        return tag;
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
        if (!this.inventory.get(0).isEmpty() && recipe_1 != null) {
            ItemStack itemStack_1 = recipe_1.getOutput();
            if (itemStack_1.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack_2 = this.inventory.get(2);
                if (itemStack_2.isEmpty()) {
                    return true;
                } else if (!itemStack_2.isItemEqualIgnoreDamage(itemStack_1)) {
                    return false;
                } else if (itemStack_2.getCount() < this.getMaxCountPerStack() && itemStack_2.getCount() < itemStack_2.getMaxCount()) {
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

            int resultCount = world.random.nextInt(100) < duplicationChance ? 2 : 1;

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


            if (inputStack.getItem() == Blocks.WET_SPONGE.asItem() && !this.inventory.get(1).isEmpty() && this.inventory.get(1).getItem() == Items.BUCKET) {
                this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            inputStack.decrement(1);
        }
    }


    protected int getFuelTime(ItemStack stack) {
        if (stack.isEmpty()) return 0;

        else {
            Item item_1 = stack.getItem();
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

    @Override
    public int[] getAvailableSlots(Direction direction) {
        if (direction == Direction.DOWN) {
            return BOTTOM_SLOTS;
        } else {
            return direction == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    @Override
    public boolean canInsert(int index, ItemStack stack, Direction direction) {
        return this.isValid(index, stack);
    }

    @Override
    public boolean canExtract(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            Item item = stack.getItem();
            return item == Items.WATER_BUCKET || item == Items.BUCKET;
        }

        return true;
    }


    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        Iterator invIterator = this.inventory.iterator();

        ItemStack stack;
        do {
            if (!invIterator.hasNext()) {
                return true;
            }

            stack = (ItemStack) invIterator.next();
        } while (stack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getStack(int index) {
        return this.inventory.get(index);
    }


    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }


    @Override
    public ItemStack removeStack(int index) {
        return Inventories.removeStack(this.inventory, index);
    }


    @Override
    public void setStack(int index, ItemStack stack) {
        ItemStack itemStack_2 = this.inventory.get(index);
        boolean boolean_1 = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack_2) && ItemStack.areTagsEqual(stack, itemStack_2);
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }

        if (index == 0 && !boolean_1) {
            this.cookTimeTotal = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public boolean isValid(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemStack_2 = this.inventory.get(1);
            return canUseAsFuel(stack) || stack.getItem() == Items.BUCKET && itemStack_2.getItem() != Items.BUCKET;
        }
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public void setLastRecipe(Recipe<?> recipe) {
        if (recipe != null) {
            this.recipesUsed.compute(recipe.getId(), (identifier_1, integer_1) -> 1 + (integer_1 == null ? 0 : integer_1));
        }
    }

    @Override
    public Recipe<?> getLastRecipe() {
        return null;
    }

    @Override
    public void unlockLastRecipe(PlayerEntity player) {

    }

    public void dropExperience(PlayerEntity player) {
        if (!this.world.getGameRules().getBoolean(GameRules.DO_LIMITED_CRAFTING)) {
            List<Recipe<?>> recipes = Lists.newArrayList();

            for (Map.Entry<Identifier, Integer> entrySet : this.recipesUsed.entrySet()) {
                player.world.getRecipeManager().get(entrySet.getKey()).ifPresent((Recipe) -> {
                    recipes.add(Recipe);
                    dropExperience(player, entrySet.getValue(), ((SmeltingRecipe) Recipe).getExperience());
                });
            }

            player.unlockRecipes(recipes);
        }

        this.recipesUsed.clear();
    }

    private static void dropExperience(PlayerEntity player, int int_1, float float_1) {
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
            player.world.spawnEntity(new ExperienceOrbEntity(player.world, player.getX(), player.getY() + 0.5D, player.getZ() + 0.5D, int_2));
        }
    }

    @Override
    public void provideRecipeInputs(RecipeFinder recipeFinder) {
        for (ItemStack stack : this.inventory) {
            recipeFinder.addItem(stack);
        }
    }
}