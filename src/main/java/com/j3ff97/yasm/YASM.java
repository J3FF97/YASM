package com.j3ff97.yasm;

import com.j3ff97.yasm.YASM.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.Iterator;
import java.util.List;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION)
public class YASM
{

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Booting up YASM!");
        LogHelper.info("Made by: " + Reference.AUTHOR);

        LogHelper.info("YASM: Starting PreInit");

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        LogHelper.info("YASM: Initialized ConfigurationHandler");

        ModItems.init();
        LogHelper.info("YASM: Initialized Items");

        ModBlocks.init();
        LogHelper.info("YASM: Initialized Blocks");


        LogHelper.info("YASM: PreInit Complete");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("YASM: Starting Init");

        if(ConfigurationHandler.flintAndSteel)
        {
            RecipeRemover.removeRecipes(Items.flint_and_steel);
            LogHelper.info("YASM: Removed Recipes");
        }

        ModItems.registerItemRenderer();
        ModBlocks.registerBlockRenderer();
        LogHelper.info("YASM: Initialized Rendering");

        Recipes.init();
        LogHelper.info("YASM: Initialized Crafting");

        LootHandler.init();
        LogHelper.info("YASM: Initialized Chest Loot");

        LogHelper.info("YASM: Init Complete");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("YASM: Starting PostInit");

        LogHelper.info("YASM: PostInit Complete");
        LogHelper.info("YASM: Ready to rock!");
    }


    public static class ModItems
    {
        public static ItemYASM ingotSteel;
        public static ItemYASM nuggetSteel;
        public static ItemYASM itemIronCompound;
        public static ItemYASM itemSteelMesh;

        public static AxeYASM     axeSteel;
        public static HoeYASM     hoeSteel;
        public static PickAxeYASM pickaxeSteel;
        public static ShearsYASM  shearsSteel;
        public static ShovelYASM  shovelSteel;
        public static SwordYASM   swordSteel;

        public static ArmorSteel steelBoots;
        public static ArmorSteel steelLeggings;
        public static ArmorSteel steelChestplate;
        public static ArmorSteel steelHelmet;


        public static void init()
        {

            ingotSteel = new ItemYASM(Names.Items.ingotSteelName);
            nuggetSteel = new ItemYASM(Names.Items.nuggetSteelName);
            itemIronCompound = new ItemYASM(Names.Items.itemIronCompoundName);
            itemSteelMesh = new ItemYASM(Names.Items.itemSteelMeshName);

            axeSteel = new AxeYASM(MaterialHandler.SteelTool, Names.Tools.axeSteelName);
            hoeSteel = new HoeYASM(MaterialHandler.SteelTool, Names.Tools.hoeSteelName);
            pickaxeSteel = new PickAxeYASM(MaterialHandler.SteelTool, Names.Tools.pickaxeSteelName);
            shovelSteel = new ShovelYASM(MaterialHandler.SteelTool, Names.Tools.shovelSteelName);
            swordSteel = new SwordYASM(MaterialHandler.SteelTool, Names.Tools.swordSteelName);
            shearsSteel = new ShearsYASM(2048, Names.Tools.shearsSteelName);

            steelBoots = new ArmorSteel(Names.Armor.bootsSteelName, MaterialHandler.SteelArmor, "steel", 3);
            steelLeggings = new ArmorSteel(Names.Armor.legsSteelName, MaterialHandler.SteelArmor, "steel", 2);
            steelChestplate = new ArmorSteel(Names.Armor.chestplateSteelName, MaterialHandler.SteelArmor, "steel", 1);
            steelHelmet = new ArmorSteel(Names.Armor.helmetSteelName, MaterialHandler.SteelArmor, "steel", 0);

            registerItems();
            registerOreDict();
        }

        public static void registerItems()
        {
            GameRegistry.registerItem(ingotSteel, Names.Items.ingotSteelName);
            GameRegistry.registerItem(nuggetSteel, Names.Items.nuggetSteelName);
            GameRegistry.registerItem(itemIronCompound, Names.Items.itemIronCompoundName);
            GameRegistry.registerItem(itemSteelMesh, Names.Items.itemSteelMeshName);

            GameRegistry.registerItem(axeSteel, Names.Tools.axeSteelName);
            GameRegistry.registerItem(hoeSteel, Names.Tools.hoeSteelName);
            GameRegistry.registerItem(pickaxeSteel, Names.Tools.pickaxeSteelName);
            GameRegistry.registerItem(shearsSteel, Names.Tools.shearsSteelName);
            GameRegistry.registerItem(shovelSteel, Names.Tools.shovelSteelName);
            GameRegistry.registerItem(swordSteel, Names.Tools.swordSteelName);

            GameRegistry.registerItem(steelBoots, Names.Armor.bootsSteelName);
            GameRegistry.registerItem(steelLeggings, Names.Armor.legsSteelName);
            GameRegistry.registerItem(steelChestplate, Names.Armor.chestplateSteelName);
            GameRegistry.registerItem(steelHelmet, Names.Armor.helmetSteelName);
        }

        public static void registerOreDict()
        {
            OreDictionary.registerOre("ingotSteel", ModItems.ingotSteel);
            OreDictionary.registerOre("nuggetSteel", ModItems.nuggetSteel);
        }

        public static void registerItemRenderer()
        {
            registerModel(ingotSteel, Names.Items.ingotSteelName);
            registerModel(nuggetSteel, Names.Items.nuggetSteelName);
            registerModel(itemIronCompound, Names.Items.itemIronCompoundName);
            registerModel(itemSteelMesh, Names.Items.itemSteelMeshName);

            registerModel(axeSteel, Names.Tools.axeSteelName);
            registerModel(hoeSteel, Names.Tools.hoeSteelName);
            registerModel(pickaxeSteel, Names.Tools.pickaxeSteelName);
            registerModel(shearsSteel, Names.Tools.shearsSteelName);
            registerModel(shovelSteel, Names.Tools.shovelSteelName);
            registerModel(swordSteel, Names.Tools.swordSteelName);
            registerModel(steelBoots, Names.Armor.bootsSteelName);
            registerModel(steelLeggings, Names.Armor.legsSteelName);
            registerModel(steelChestplate, Names.Armor.chestplateSteelName);
            registerModel(steelHelmet, Names.Armor.helmetSteelName);
        }

        public static void registerModel(Item item, String itemName)
        {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.ID + ":" + itemName, "inventory"));
        }
    }

    public static class ModBlocks
    {
        public static BlockYASM     blockSteel;
        public static BlockYASMPane barsSteel;

        public static void init()
        {
            blockSteel = new BlockYASM(Names.Blocks.blockSteelName, Material.iron, 5F, 30F, BlockYASM.soundTypeMetal, "pickaxe", 2);
            barsSteel = new BlockYASMPane(Names.Blocks.barSteelName, Material.iron, true, 5F, 30F, BlockYASMPane.soundTypeMetal, "pickaxe", 2);

            registerBlocks();
            registerOreDict();

        }

        public static void registerBlocks()
        {
            GameRegistry.registerBlock(blockSteel, Names.Blocks.blockSteelName);
            GameRegistry.registerBlock(barsSteel, Names.Blocks.barSteelName);
        }

        public static void registerOreDict()
        {
            OreDictionary.registerOre("blockSteel", ModBlocks.blockSteel);
        }

        public static void registerBlockRenderer()
        {
            registerModel(blockSteel, Names.Blocks.blockSteelName);
            registerModel(barsSteel, Names.Blocks.barSteelName);
        }

        public static void registerModel(Block block, String blockName)
        {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.ID + ":" + blockName, "inventory"));
        }
    }

    public static class ItemYASM extends Item
    {
        public ItemYASM(String name)
        {
            super();
            this.setUnlocalizedName(name);
            this.setCreativeTab(CreativeTab.tabYASMItems);
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        @Override
        public String getUnlocalizedName(ItemStack stack)
        {
            return String.format("item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class ArmorSteel extends ItemArmor
    {
        public String textureName;

        public ArmorSteel(String name, ArmorMaterial material, String textureName, int type)
        {
            super(material, 0, type);
            this.setUnlocalizedName(name);
            this.setCreativeTab(CreativeTab.tabYASMItems);
            this.textureName = textureName;
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
        {
            return Reference.ID + ":textures/models/armor/" + this.textureName + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("Item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        @Override
        public String getUnlocalizedName(ItemStack itemstack)
        {
            return String.format("item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }


        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class SwordYASM extends ItemSword
    {
        public SwordYASM(ToolMaterial material, String name)
        {
            super(material);
            this.setUnlocalizedName(name);
            this.setCreativeTab(CreativeTab.tabYASMItems);
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("Item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        @Override
        public String getUnlocalizedName(ItemStack itemstack)
        {
            return String.format("item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class ShovelYASM extends ItemSpade
    {
        public ShovelYASM(ToolMaterial material, String name)
        {
            super(material);
            this.setUnlocalizedName(name);
            this.setCreativeTab(CreativeTab.tabYASMItems);
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("Item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        @Override
        public String getUnlocalizedName(ItemStack itemstack)
        {
            return String.format("item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class PickAxeYASM extends ItemPickaxe
    {
        public PickAxeYASM(ToolMaterial material, String name)
        {
            super(material);
            this.setUnlocalizedName(name);
            this.setCreativeTab(CreativeTab.tabYASMItems);
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("Item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        @Override
        public String getUnlocalizedName(ItemStack itemstack)
        {
            return String.format("item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class AxeYASM extends ItemAxe
    {
        public AxeYASM(ToolMaterial material, String name)
        {
            super(material);
            this.setUnlocalizedName(name);
            this.setCreativeTab(CreativeTab.tabYASMItems);
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("Item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        @Override
        public String getUnlocalizedName(ItemStack itemstack)
        {
            return String.format("item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class HoeYASM extends ItemHoe
    {
        public HoeYASM(ToolMaterial material, String name)
        {
            super(material);
            this.setUnlocalizedName(name);
            this.setCreativeTab(CreativeTab.tabYASMItems);
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("Item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        @Override
        public String getUnlocalizedName(ItemStack itemstack)
        {
            return String.format("item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class ShearsYASM extends ItemShears
    {
        public ShearsYASM(int maxDamage, String name)
        {
            super();
            this.setMaxDamage(maxDamage);
            this.setUnlocalizedName(name);
            this.setCreativeTab(CreativeTab.tabYASMItems);
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("Item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        @Override
        public String getUnlocalizedName(ItemStack itemstack)
        {
            return String.format("item.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }


        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class BlockYASM extends Block
    {
        public BlockYASM(String name, Material material, float hardness, float resistance, SoundType stepSound, String toolClass, int harvestLevel)
        {
            super(material);
            this.setUnlocalizedName(name);
            this.setHarvestLevel(toolClass, harvestLevel);
            this.setCreativeTab(CreativeTab.tabYASMBlocks);
            this.setHardness(hardness);
            this.setResistance(resistance);
            this.setStepSound(stepSound);
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("tile.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class BlockYASMPane extends BlockPane
    {
        public BlockYASMPane(String name, Material material, boolean canDrop, float hardness, float resistance, SoundType stepSound, String toolClass, int harvestLevel)
        {
            super(material, canDrop);
            this.setUnlocalizedName(name);
            this.setCreativeTab(CreativeTab.tabYASMBlocks);
            this.setHarvestLevel(toolClass, harvestLevel);
            this.setStepSound(stepSound);
            this.setHardness(hardness);
            this.setResistance(resistance);
        }

        @Override
        public String getUnlocalizedName()
        {
            return String.format("tile.%s%s", Reference.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        }

        protected String getUnwrappedUnlocalizedName(String unlocalizedName)
        {
            return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
        }
    }

    public static class ConfigurationHandler
    {
        public static Configuration configuration;
        public static boolean flintAndSteel = true;

        public static void init(File configFile)
        {
            if(configuration == null)
            {
                configuration = new Configuration(configFile);
                loadConfiguration();
            }
        }

        public static void loadConfiguration()
        {
            String desc;

            desc = "Whether or not to use Steel for flint and steel.";
            flintAndSteel = getBool("flintAndSteel", flintAndSteel, desc);

            if(configuration.hasChanged())
            {
                configuration.save();
            }
        }

        public static boolean getBool(String propName, boolean default_, String desc)
        {
            Property property = configuration.get(Configuration.CATEGORY_GENERAL, propName, default_);
            property.comment = desc;
            return property.getBoolean(default_);
        }
    }

    public static class CreativeTab
    {
        public static final CreativeTabs tabYASMItems = new CreativeTabs("tabYASMItems")
        {
            @Override
            public Item getTabIconItem()
            {
                return ModItems.ingotSteel;
            }
        };

        public static final CreativeTabs tabYASMBlocks = new CreativeTabs("tabYASMBlocks")
        {
            @Override
            public Item getTabIconItem()
            {
                return Item.getItemFromBlock(ModBlocks.blockSteel);
            }
        };
    }

    public static class LogHelper
    {
        public static void log(Level logLevel, Object object)
        {
            FMLLog.log(Reference.NAME, logLevel, String.valueOf(object));
        }

        public static void all(Object object)
        {
            log(Level.ALL, object);
        }

        public static void error(Object object)
        {
            log(Level.ERROR, object);
        }

        public static void info(Object object)
        {
            log(Level.INFO, object);
        }

    }

    public static class RecipeRemover
    {
        public static void removeRecipes(Item itemtoRemove)
        {
            List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
            Iterator<IRecipe> remover = recipes.iterator();

            while(remover.hasNext())
            {
                ItemStack stack = remover.next().getRecipeOutput();

                if(stack != null && stack.getItem() == itemtoRemove)
                {
                    remover.remove();
                }
            }
        }
    }

    public static class LootHandler
    {
        public static void init()
        {
            ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(ModItems.itemIronCompound), 1, 3, 20));
        }
    }

    public static class MaterialHandler
    {

        public static Item.ToolMaterial SteelTool = EnumHelper.addToolMaterial("SteelTool", 2, 750, 7.0F, 3.0F, 5);

        public static ItemArmor.ArmorMaterial SteelArmor = EnumHelper.addArmorMaterial("SteelArmor", "yasm:SteelArmor", 25, new int[]{3, 6, 6, 2}, 5);
    }

    public static class Recipes
    {
        public static void init()
        {
            initCrafting();
            initShapeless();
            initSmelting();
        }

        public static void initCrafting()
        {
            GameRegistry.addRecipe(new ItemStack(Items.chainmail_boots), "X X", "X X", 'X', ModItems.itemSteelMesh);

            GameRegistry.addRecipe(new ItemStack(Items.chainmail_chestplate), "X X", "XXX", "XXX", 'X', ModItems.itemSteelMesh);

            GameRegistry.addRecipe(new ItemStack(Items.chainmail_helmet), "XXX", "X X", 'X', ModItems.itemSteelMesh);

            GameRegistry.addRecipe(new ItemStack(Items.chainmail_leggings), "XXX", "X X", "X X", 'X', ModItems.itemSteelMesh);

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockSteel, 1), "XXX", "XXX", "XXX", 'X', "ingotSteel"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.ingotSteel), "XXX", "XXX", "XXX", 'X', "nuggetSteel"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.axeSteel), "XX", "XY", " Y", 'X', "ingotSteel", 'Y', "stickWood"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hoeSteel), "XX", " Y", " Y", 'X', "ingotSteel", 'Y', "stickWood"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.pickaxeSteel), "XXX", " Y ", " Y ", 'X', "ingotSteel", 'Y', "stickWood"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.shovelSteel), "X", "Y", "Y", 'X', "ingotSteel", 'Y', "stickWood"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.swordSteel), "X", "X", "Y", 'X', "ingotSteel", 'Y', "stickWood"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.shearsSteel), " X ", "X  ", 'X', "ingotSteel"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.steelBoots), "X X", "X X", 'X', "ingotSteel"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.steelChestplate), "X X", "XXX", "XXX", 'X', "ingotSteel"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.steelHelmet), "XXX", "X X", 'X', "ingotSteel"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.steelLeggings), "XXX", "X X", "X X", 'X', "ingotSteel"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemIronCompound), "XXX", "XYX", "XXX", 'X', Items.coal, 'Y', "ingotIron"));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemSteelMesh, 4), " Y ", "YXY", " Y ", 'X', "ingotSteel", 'Y', ModBlocks.barsSteel));
        }

        public static void initShapeless()
        {
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.nuggetSteel, 9), "ingotSteel"));
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingotSteel, 9), "blockSteel"));

            if(ConfigurationHandler.flintAndSteel)
            {
                GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.flint_and_steel), "ingotSteel", new ItemStack(Items.flint)));
            }
        }

        public static void initSmelting()
        {
            GameRegistry.addSmelting(ModItems.itemIronCompound, new ItemStack(ModItems.ingotSteel), 0.8F);
        }
    }

    public static final class Reference
    {
        public static final String ID      = "YASM";
        public static final String NAME    = "YASM";
        public static final String VERSION = "1.8-1.0";
        public static final String AUTHOR  = "J3FF97";
    }

    public static final class Names
    {
        public static final class Items
        {
            public static final String itemIronCompoundName = "itemIronCompound";
            public static final String ingotSteelName       = "ingotSteel";
            public static final String itemSteelMeshName    = "itemSteelMesh";
            public static final String nuggetSteelName      = "nuggetSteel";
        }

        public static final class Blocks
        {
            public static final String blockSteelName = "blockSteel";
            public static final String barSteelName   = "barSteel";
        }

        public static final class Armor
        {
            public static final String helmetSteelName     = "helmetSteel";
            public static final String chestplateSteelName = "chestplateSteel";
            public static final String legsSteelName       = "legsSteel";
            public static final String bootsSteelName      = "bootsSteel";
        }

        public static final class Tools
        {
            public static final String swordSteelName   = "swordSteel";
            public static final String pickaxeSteelName = "pickaxeSteel";
            public static final String axeSteelName     = "axeSteel";
            public static final String shovelSteelName  = "shovelSteel";
            public static final String hoeSteelName     = "hoeSteel";
            public static final String shearsSteelName  = "shearsSteel";
        }
    }
}
