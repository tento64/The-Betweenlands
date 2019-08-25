package thebetweenlands.common.registries;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.properties.EntityProperty;
import net.minecraft.world.storage.loot.properties.EntityPropertyManager;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.config.BetweenlandsConfig;
import thebetweenlands.common.lib.ModInfo;
import thebetweenlands.common.loot.EntityPropertyEventActive;
import thebetweenlands.common.loot.EntityPropertyFrogType;
import thebetweenlands.common.loot.EntityPropertyHasItem;
import thebetweenlands.common.loot.EntityPropertyIsBossPeatMummy;
import thebetweenlands.common.loot.EntityPropertyLootModifier;
import thebetweenlands.common.loot.EntityPropertyPeatMummyShimmerstone;
import thebetweenlands.common.loot.EntityPropertyPyradCharging;
import thebetweenlands.common.loot.LootConditionEventActive;
import thebetweenlands.common.loot.LootConditionFromSharedPool;
import thebetweenlands.common.loot.LootConditionKilledLootModifier;
import thebetweenlands.common.loot.LootConditionOr;
import thebetweenlands.common.loot.LootConditionSharedPool;
import thebetweenlands.common.loot.LootFunctionSetMetaFromArray;
import thebetweenlands.util.FakeClientWorld;

public class LootTableRegistry {

    //LOOT
    public static final ResourceLocation COMMON_POT_LOOT = register("loot/common_pot_loot");
    public static final ResourceLocation DUNGEON_CHEST_LOOT = register("loot/dungeon_chest_loot");
    public static final ResourceLocation COMMON_CHEST_LOOT = register("loot/common_chest_loot");
    public static final ResourceLocation DUNGEON_POT_LOOT = register("loot/dungeon_pot_loot");
    public static final ResourceLocation MUSIC_DISC = register("loot/music_disc");
    public static final ResourceLocation ANIMATOR_SCROLL = register("animator/scroll");
    public static final ResourceLocation PRESENT = register("loot/present_loot");
    public static final ResourceLocation PUFFSHROOM = register("loot/puffshroom");
    
    public static final ResourceLocation SHARED_LOOT_POOL_TEST = register("loot/shared_loot_pool_test");
    
    //MOBS
    public static final ResourceLocation ANGLER = register("entities/angler");
    public static final ResourceLocation BLIND_CAVE_FISH = register("entities/blind_cave_fish");
    public static final ResourceLocation BLOOD_SNAIL = register("entities/blood_snail");
    public static final ResourceLocation CHIROMAW = register("entities/chiromaw");
    public static final ResourceLocation DARK_DRUID = register("entities/dark_druid");
    public static final ResourceLocation DRAGONFLY = register("entities/dragonfly");
    public static final ResourceLocation FIREFLY = register("entities/firefly");
    public static final ResourceLocation FROG = register("entities/frog");
    public static final ResourceLocation GAS_CLOUD = register("entities/gas_cloud");
    public static final ResourceLocation GECKO = register("entities/gecko");
    public static final ResourceLocation LEECH = register("entities/leech");
    public static final ResourceLocation LURKER = register("entities/lurker");
    public static final ResourceLocation MIRE_SNAIL_EGG = register("entities/mire_snail_egg");
    public static final ResourceLocation MIRE_SNAIL = register("entities/mire_snail");
    public static final ResourceLocation PEAT_MUMMY = register("entities/peat_mummy");
    public static final ResourceLocation PYRAD = register("entities/pyrad");
    public static final ResourceLocation SILT_CRAB = register("entities/silt_crab");
    public static final ResourceLocation SLUDGE = register("entities/sludge");
    public static final ResourceLocation SPORELING = register("entities/sporeling");
    public static final ResourceLocation SWAMP_HAG = register("entities/swamp_hag");
    public static final ResourceLocation TAR_BEAST = register("entities/tar_beast");
    public static final ResourceLocation TARMINION = register("entities/tarminion");
    public static final ResourceLocation TERMITE = register("entities/termite");
    public static final ResourceLocation TOAD = register("entities/toad");
    public static final ResourceLocation WIGHT = register("entities/wight");
    public static final ResourceLocation SPIRIT_TREE_FACE_SMALL = register("entities/spirit_tree_face_small");
    public static final ResourceLocation SPIRIT_TREE_FACE_LARGE = register("entities/spirit_tree_face_large");
    public static final ResourceLocation BOULDER_SPRITE = register("entities/boulder_sprite");
    public static final ResourceLocation ROOT_SPRITE = register("entities/root_sprite");
    public static final ResourceLocation WALL_LAMPREY = register("entities/wall_lamprey");
    public static final ResourceLocation WALL_LIVING_ROOT = register("entities/wall_living_root");
    public static final ResourceLocation MOVING_SPAWNER_HOLE = register("entities/moving_spawner_hole");
    public static final ResourceLocation TINY_SLUDGE_WORM = register("entities/tiny_sludge_worm");
    public static final ResourceLocation SMALL_SLUDGE_WORM = register("entities/small_sludge_worm");
    public static final ResourceLocation LARGE_SLUDGE_WORM = register("entities/large_sludge_worm");
    public static final ResourceLocation SHAMBLER = register("entities/shambler");
    public static final ResourceLocation ASH_SPRITE = register("entities/ash_sprite");
    public static final ResourceLocation BARRISHEE = register("entities/barrishee");
    public static final ResourceLocation CRYPT_CRAWLER = register("entities/crypt_crawler");
    public static final ResourceLocation EMBERLING = register("entities/emberling");
    //BOSSES
    public static final ResourceLocation FORTRESS_BOSS = register("entities/fortress_boss");
    public static final ResourceLocation DREADFUL_PEAT_MUMMY = register("entities/dreadful_peat_mummy");
    
    //LOOT ENTITY PROPERTIES
    public static final ResourceLocation ENTITY_PROPERTY_FROG_TYPE = register(new EntityPropertyFrogType.Serializer());
    public static final ResourceLocation ENTITY_PROPERTY_PEAT_MUMMY_SHIMMERSTONE = register(new EntityPropertyPeatMummyShimmerstone.Serializer());
    public static final ResourceLocation ENTITY_PROPERTY_PYRAD_CHARGING = register(new EntityPropertyPyradCharging.Serializer());
    public static final ResourceLocation ENTITY_PROPERTY_HAS_ITEM = register(new EntityPropertyHasItem.Serializer());
    public static final ResourceLocation ENTITY_PROPERTY_IS_BOSS_MUMMY = register(new EntityPropertyIsBossPeatMummy.Serializer());
    public static final ResourceLocation ENTITY_PROPERTY_IS_EVENT_ACTIVE = register(new EntityPropertyEventActive.Serializer());
    public static final ResourceLocation ENTITY_PROPERTY_LOOT_MODIFIER = register(new EntityPropertyLootModifier.Serializer());
    
    //LOOT CONDITIONS
    public static final ResourceLocation LOOT_CONDITION_OR = register(new LootConditionOr.Serializer());
    public static final ResourceLocation LOOT_CONDITION_EVENT_ACTIVE = register(new LootConditionEventActive.Serializer());
    public static final ResourceLocation LOOT_CONDITION_ENTITY_LOOT_MODIFIER = register(new LootConditionKilledLootModifier.Serializer());
    public static final ResourceLocation LOOT_CONDITION_SHARED_POOL = register(new LootConditionSharedPool.Serializer());
    public static final ResourceLocation LOOT_CONDITION_FROM_SHARED_POOL = register(new LootConditionFromSharedPool.Serializer());
    
    //LOOT FUNCTIONS
    public static final ResourceLocation LOOT_FUNCTION_SET_META_FROM_ARRAY = register(new LootFunctionSetMetaFromArray.Serializer());
    
    public static void preInit() {
    	if(BetweenlandsConfig.DEBUG.debug) {
    		TheBetweenlands.logger.info("Loaded loot tables");
    	}
    }
    
    private static ResourceLocation register(String id) {
        return LootTableList.register(new ResourceLocation(ModInfo.ID, id));
    }

    private static ResourceLocation register(EntityProperty.Serializer<?> serializer) {
        EntityPropertyManager.registerProperty(serializer);
        return serializer.getName();
    }
    
    private static ResourceLocation register(LootCondition.Serializer<?> serializer) {
        LootConditionManager.registerCondition(serializer);
        return serializer.getLootTableLocation();
    }
    
    private static ResourceLocation register(LootFunction.Serializer<?> serializer) {
        LootFunctionManager.registerFunction(serializer);
        return serializer.getFunctionName();
    }

    @SuppressWarnings("unchecked")
	public static ArrayList<ItemStack> getItemsFromTable(ResourceLocation lootTable, World world, boolean getCountSpan) {
        ArrayList<ItemStack> items = new ArrayList<>();

        LootTableManager manager = getManager(world);
        LootTable table = manager.getLootTableFromLocation(lootTable);
        LootContext lootContext = new LootContext(0, null, manager, null, null, null);
        Field f = ReflectionHelper.findField(LootTable.class, "pools", "field_186466_c", "c");
        List<LootPool> pools = null;
        try {
            pools = (List<LootPool>) f.get(table);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (pools != null && pools.size() > 0) {
            for (LootPool pool : pools) {
                Field f2 = ReflectionHelper.findField(LootPool.class, "lootEntries", "field_186453_a", "a");
                List<LootEntry> entries = null;
                try {
                    entries = (List<LootEntry>) f2.get(pool);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (entries != null && entries.size() > 0) {
                    for (LootEntry entry: entries) {
                        if (entry instanceof LootEntryItem) {
                            LootFunction[] functions = ((LootEntryItem) entry).functions;
                            ArrayList<ItemStack> tmpItems = new ArrayList<>();
                            entry.addLoot(tmpItems, new Random(), lootContext);
                            if (getCountSpan && functions != null && functions.length > 0) {
                                for (LootFunction function: functions) {
                                    if (function instanceof SetCount) {
                                        RandomValueRange valueRange = ((SetCount) function).countRange;
                                        if (valueRange == null)
                                            continue;

                                        if (!tmpItems.get(0).hasTagCompound()) {
                                            tmpItems.get(0).setTagCompound(new NBTTagCompound());
                                        }

                                        NBTTagCompound compound = tmpItems.get(0).getTagCompound();
                                        compound.setFloat("LootCountMin", valueRange.getMin());
                                        compound.setFloat("LootCountMax", valueRange.getMax());
                                        break;
                                    }
                                }
                            }
                            items.addAll(tmpItems);
                        } else if (entry instanceof LootEntryTable) {
                            ResourceLocation location = ((LootEntryTable) entry).table;
                            if (location != null)
                                items.addAll(getItemsFromTable(location, world, getCountSpan));
                        }
                    }
                }
            }
        }
        return items;
    }

    private static LootTableManager manager;

    public static LootTableManager getManager(@Nullable World world) {
        if (world == null || world.getLootTableManager() == null) {
            if (manager == null) {
                ISaveHandler saveHandler = FakeClientWorld.saveHandler;
                manager = new LootTableManager(new File(new File(saveHandler.getWorldDirectory(), "data"), "loot_tables"));
            }
            return manager;
        }
        return world.getLootTableManager();
    }
}
