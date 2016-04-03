package thebetweenlands.common.registries;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thebetweenlands.client.tab.BLCreativeTabs;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.item.BLMaterial;
import thebetweenlands.common.item.armor.ItemBLArmor;
import thebetweenlands.common.item.food.*;
import thebetweenlands.common.item.herblore.ItemGenericCrushed;
import thebetweenlands.common.item.herblore.ItemGenericPlantDrop;
import thebetweenlands.common.item.misc.ItemGeneric;
import thebetweenlands.common.item.misc.ItemSwampTalisman;
import thebetweenlands.common.item.tools.ItemBLPickaxe;
import thebetweenlands.common.item.tools.ItemBLShovel;
import thebetweenlands.common.item.tools.ItemBLSword;
import thebetweenlands.common.lib.ModInfo;
import thebetweenlands.util.TranslationHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {
    //generic
    public static final Item itemsGeneric = new ItemGeneric().setCreativeTab(BLCreativeTabs.items);
    public static final Item itemsGenericCrushed = new ItemGenericCrushed().setCreativeTab(BLCreativeTabs.herbLore);
    public static final Item itemsGenericPlantDrop = new ItemGenericPlantDrop().setCreativeTab(BLCreativeTabs.herbLore);
    public static final Item swampTalisman = new ItemSwampTalisman().setCreativeTab(BLCreativeTabs.items);
    //food
    public static final Item sapBall = new ItemSapBall().setUnlocalizedName(ModInfo.NAME_PREFIX + ".sapBall");
    public static final ItemRottenFood rottenFood = (ItemRottenFood) new ItemRottenFood().setAlwaysEdible().setUnlocalizedName(ModInfo.NAME_PREFIX + ".rottenFood");
    //public static final Item middleFruitSeeds = new ItemBLGenericSeed(0, 0F, BLBlockRegistry.middleFruitBush, BLBlockRegistry.farmedDirt).setUnlocalizedName(ModInfo.NAME_PREFIX + ".middleFruitSeeds");
    //public static final Item spores = new ItemBLGenericSeed(0, 0F, BLBlockRegistry.fungusCrop, BLBlockRegistry.farmedDirt).setUnlocalizedName(ModInfo.NAME_PREFIX + ".spores");
    //public static final Item aspectrusCropSeed = new ItemAspectrusCropSeed(0, 0F).setUnlocalizedName(ModInfo.NAME_PREFIX + ".aspectrusSeeds");
    public static final Item anglerMeatRaw = new ItemBLFood(4, 0.4F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".anglerMeatRaw");
    public static final Item anglerMeatCooked = new ItemBLFood(8, 0.8F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".anglerMeatCooked");
    public static final Item frogLegsRaw = new ItemBLFood(3, 0.4F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".frogLegsRaw");
    public static final Item frogLegsCooked = new ItemBLFood(6, 0.8F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".frogLegsCooked");
    public static final Item snailFleshRaw = new ItemBLFood(3, 0.4F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".snailFleshRaw");
    public static final Item snailFleshCooked = new ItemBLFood(6, 0.9F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".snailFleshCooked");
    public static final Item reedDonut = new ItemBLFood(6, 0.6F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".reedDonut");
    public static final Item jamDonut = new ItemBLFood(10, 0.6F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".jamDonut");
    public static final Item gertsDonut = new ItemGertsDonut().setUnlocalizedName(ModInfo.NAME_PREFIX + ".gertsDonut");
    public static final Item krakenTentacle = new ItemBLFood(8, 0.9F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".krakenTentacle");
    public static final Item krakenCalamari = new ItemBLFood(14, 1.2F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".krakenCalamari");
    public static final Item middleFruit = new ItemBLFood(6, 0.6F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".middleFruit");
    public static final Item mincePie = new ItemBLFood(4, 0.85F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".mincePie");
    public static final Item weepingBluePetal = new ItemWeepingBluePetal().setUnlocalizedName(ModInfo.NAME_PREFIX + ".weepingBluePetal");
    public static final Item wightsHeart = new ItemWightHeart().setUnlocalizedName(ModInfo.NAME_PREFIX + ".wightHeart");
    public static final Item yellowDottedFungus = new ItemBLFood(8, 0.6F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".yellowDottedFungus");
    public static final Item siltCrabClaw = new ItemBLFood(2, 0.6F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".siltCrabClaw");
    public static final Item crabStick = new ItemBLFood(6, 0.9F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".crabStick");
    public static final Item nettleSoup = new ItemNettleSoup().setUnlocalizedName(ModInfo.NAME_PREFIX + ".nettleSoup");
    public static final Item sludgeJello = new ItemBLFood(4, 0.9F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".sludgeJello");
    public static final Item middleFruitJello = new ItemBLFood(10, 1.0F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".middleFruitJello");
    public static final Item sapJello = new ItemSapJello().setUnlocalizedName(ModInfo.NAME_PREFIX + ".sapJello");
    public static final Item marshmallow = new ItemMarshmallow().setUnlocalizedName(ModInfo.NAME_PREFIX + ".marshmallow");
    public static final Item marshmallowPink = new ItemMarshmallowPink().setUnlocalizedName(ModInfo.NAME_PREFIX + ".marshmallowPink");
    public static final Item flatheadMushroomItem = new ItemFlatheadMushroom().setUnlocalizedName(ModInfo.NAME_PREFIX + ".flatheadMushroomItem");
    public static final Item blackHatMushroomItem = new ItemBlackHatMushroom().setUnlocalizedName(ModInfo.NAME_PREFIX + ".blackHatMushroomItem");
    public static final Item bulbCappedMushroomItem = new ItemBulbCappedMushroom().setUnlocalizedName(ModInfo.NAME_PREFIX + ".bulbCappedMushroomItem");
    public static final Item friedSwampKelp = new ItemBLFood(5, 0.6F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".friedSwampKelp");
    public static final Item forbiddenFig = new ItemForbiddenFig().setUnlocalizedName(ModInfo.NAME_PREFIX + ".forbiddenFig");
    public static final Item candyBlue = new ItemBLFood(3, 1.0F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".candyBlue");
    public static final Item candyRed = new ItemBLFood(3, 1.0F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".candyRed");
    public static final Item candyYellow = new ItemBLFood(3, 1.0F, false).setUnlocalizedName(ModInfo.NAME_PREFIX + ".candyYellow");
    public static final Item chiromawWing = new ItemChiromawWing();
    //armor
    public static final Item syrmoriteHelmet = new ItemBLArmor(BLMaterial.armorSyrmorite, 0, EntityEquipmentSlot.HEAD, "syrmorite").setUnlocalizedName(ModInfo.NAME_PREFIX + "syrmoriteHelmet").setCreativeTab(BLCreativeTabs.gears);
    public static final Item syrmoriteChestplate = new ItemBLArmor(BLMaterial.armorSyrmorite, 1, EntityEquipmentSlot.CHEST, "syrmorite").setUnlocalizedName(ModInfo.NAME_PREFIX + "syrmoriteChestplate").setCreativeTab(BLCreativeTabs.gears);
    public static final Item syrmoriteLeggings = new ItemBLArmor(BLMaterial.armorSyrmorite, 2, EntityEquipmentSlot.LEGS, "syrmorite").setUnlocalizedName(ModInfo.NAME_PREFIX + "syrmoriteLeggings").setCreativeTab(BLCreativeTabs.gears);
    public static final Item syrmoriteBoots = new ItemBLArmor(BLMaterial.armorSyrmorite, 3, EntityEquipmentSlot.FEET, "syrmorite").setUnlocalizedName(ModInfo.NAME_PREFIX + "syrmoriteBoots").setCreativeTab(BLCreativeTabs.gears);
    public static final Item lurkerSkinHelmet = new ItemBLArmor(BLMaterial.armorLurkerSkin, 0, EntityEquipmentSlot.HEAD, "lurkerSkin").setUnlocalizedName(ModInfo.NAME_PREFIX + "lurkerSkinHelmet").setCreativeTab(BLCreativeTabs.gears);
    public static final Item lurkerSkinChestplate = new ItemBLArmor(BLMaterial.armorLurkerSkin, 1, EntityEquipmentSlot.CHEST, "lurkerSkin").setUnlocalizedName(ModInfo.NAME_PREFIX + "lurkerSkinChestplate").setCreativeTab(BLCreativeTabs.gears);
    public static final Item lurkerSkinLeggings = new ItemBLArmor(BLMaterial.armorLurkerSkin, 2, EntityEquipmentSlot.LEGS, "lurkerSkin").setUnlocalizedName(ModInfo.NAME_PREFIX + "lurkerSkinLeggings").setCreativeTab(BLCreativeTabs.gears);
    public static final Item lurkerSkinBoots = new ItemBLArmor(BLMaterial.armorLurkerSkin, 3, EntityEquipmentSlot.FEET, "lurkerSkin").setUnlocalizedName(ModInfo.NAME_PREFIX + "lurkerSkinBoots").setCreativeTab(BLCreativeTabs.gears);
    public static final Item boneHelmet = new ItemBLArmor(BLMaterial.armorBone, 0, EntityEquipmentSlot.HEAD, "bone").setUnlocalizedName(ModInfo.NAME_PREFIX + "boneHelmet").setCreativeTab(BLCreativeTabs.gears);
    public static final Item boneChestplate = new ItemBLArmor(BLMaterial.armorBone, 1, EntityEquipmentSlot.CHEST, "bone").setUnlocalizedName(ModInfo.NAME_PREFIX + "boneChestplate").setCreativeTab(BLCreativeTabs.gears);
    public static final Item boneLeggings = new ItemBLArmor(BLMaterial.armorBone, 2, EntityEquipmentSlot.LEGS, "bone").setUnlocalizedName(ModInfo.NAME_PREFIX + "boneLeggings").setCreativeTab(BLCreativeTabs.gears);
    public static final Item boneBoots = new ItemBLArmor(BLMaterial.armorBone, 3, EntityEquipmentSlot.FEET, "bone").setUnlocalizedName(ModInfo.NAME_PREFIX + "boneBoots").setCreativeTab(BLCreativeTabs.gears);
    public static final Item valoniteHelmet = new ItemBLArmor(BLMaterial.armorValonite, 0, EntityEquipmentSlot.HEAD, "valonite").setUnlocalizedName(ModInfo.NAME_PREFIX + "valoniteHelmet").setCreativeTab(BLCreativeTabs.gears);
    public static final Item valoniteChestplate = new ItemBLArmor(BLMaterial.armorValonite, 1, EntityEquipmentSlot.CHEST, "valonite").setUnlocalizedName(ModInfo.NAME_PREFIX + "valoniteChestplate").setCreativeTab(BLCreativeTabs.gears);
    public static final Item valoniteLeggings = new ItemBLArmor(BLMaterial.armorValonite, 2, EntityEquipmentSlot.LEGS, "valonite").setUnlocalizedName(ModInfo.NAME_PREFIX + "valoniteLeggings").setCreativeTab(BLCreativeTabs.gears);
    public static final Item valoniteBoots = new ItemBLArmor(BLMaterial.armorValonite, 3, EntityEquipmentSlot.FEET, "valonite").setUnlocalizedName(ModInfo.NAME_PREFIX + "valoniteBoots").setCreativeTab(BLCreativeTabs.gears);
    //tools
    public static final Item weedwoodSword = new ItemBLSword(BLMaterial.toolWeedWood).setUnlocalizedName(ModInfo.NAME_PREFIX + "weedwoodSword").setCreativeTab(BLCreativeTabs.gears);
    public static final Item weedwoodShovel = new ItemBLShovel(BLMaterial.toolWeedWood).setUnlocalizedName(ModInfo.NAME_PREFIX + "weedwoodShovel").setCreativeTab(BLCreativeTabs.gears);
    //public static final Item weedwoodAxe = new ItemBLAxe(BLMaterial.toolWeedWood).setUnlocalizedName(ModInfo.NAME_PREFIX + "weedwood_axe").setCreativeTab(BLCreativeTabs.gears);
    public static final Item weedwoodPickaxe = new ItemBLPickaxe(BLMaterial.toolWeedWood).setUnlocalizedName(ModInfo.NAME_PREFIX + "weedwoodPickaxe").setCreativeTab(BLCreativeTabs.gears);
    public static final Item betweenstoneSword = new ItemBLSword(BLMaterial.toolBetweenstone).setUnlocalizedName(ModInfo.NAME_PREFIX + "betweenstoneSword").setCreativeTab(BLCreativeTabs.gears);
    public static final Item betweenstoneShovel = new ItemBLShovel(BLMaterial.toolBetweenstone).setUnlocalizedName(ModInfo.NAME_PREFIX + "betweenstoneShovel").setCreativeTab(BLCreativeTabs.gears);
    //public static final Item betweenstoneAxe = new ItemBLAxe(BLMaterial.toolBetweenstone).setUnlocalizedName(ModInfo.NAME_PREFIX + "betweenstone_axe").setCreativeTab(BLCreativeTabs.gears);
    public static final Item betweenstonePickaxe = new ItemBLPickaxe(BLMaterial.toolBetweenstone).setUnlocalizedName(ModInfo.NAME_PREFIX + "betweenstonePickaxe").setCreativeTab(BLCreativeTabs.gears);
    public static final Item octineSword = new ItemBLSword(BLMaterial.toolOctine).setUnlocalizedName(ModInfo.NAME_PREFIX + "octineSword").setCreativeTab(BLCreativeTabs.gears);
    public static final Item octineShovel = new ItemBLShovel(BLMaterial.toolOctine).setUnlocalizedName(ModInfo.NAME_PREFIX + "octineShovel").setCreativeTab(BLCreativeTabs.gears);
    //public static final Item octineAxe = new ItemBLAxe(BLMaterial.toolOctine).setUnlocalizedName(ModInfo.NAME_PREFIX + "octine_axe").setCreativeTab(BLCreativeTabs.gears);
    public static final Item octinePickaxe = new ItemBLPickaxe(BLMaterial.toolOctine).setUnlocalizedName(ModInfo.NAME_PREFIX + "octinePickaxe").setCreativeTab(BLCreativeTabs.gears);
    public static final Item valoniteSword = new ItemBLSword(BLMaterial.toolValonite).setUnlocalizedName(ModInfo.NAME_PREFIX + "valoniteSword").setCreativeTab(BLCreativeTabs.gears);
    public static final Item valoniteShovel = new ItemBLShovel(BLMaterial.toolValonite).setUnlocalizedName(ModInfo.NAME_PREFIX + "valoniteShovel").setCreativeTab(BLCreativeTabs.gears);
    //public static final Item valoniteAxe = new ItemBLAxe(BLMaterial.toolValonite).setUnlocalizedName(ModInfo.NAME_PREFIX + "valonite_axe").setCreativeTab(BLCreativeTabs.gears);
    public static final Item valonitePickaxe = new ItemBLPickaxe(BLMaterial.toolValonite).setUnlocalizedName(ModInfo.NAME_PREFIX + "valonitePickaxe").setCreativeTab(BLCreativeTabs.gears);

    public final List<Item> items = new ArrayList<Item>();

    public void preInit() {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (field.getType().isAssignableFrom(Item.class)) {
                    Item item = (Item) field.get(this);
                    registerItem(item);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void init() {
        for (Item item : this.items) {
            TheBetweenlands.proxy.registerDefaultItemRenderer(item);
        }
    }


    private void registerItem(Item item) {
        String name = item.getUnlocalizedName();
        String itemName = name.substring(name.lastIndexOf(".") + 1, name.length());
        GameRegistry.registerItem(item, itemName);
        items.add(item);
        TranslationHelper.canTranslate(name + ".name");
    }

}