package panda.corn.init;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import panda.corn.SimpleCorn;

@EventBusSubscriber(modid = SimpleCorn.MODID)
public class InsertVillagePieces {
	
    @SubscribeEvent
    public static void LoadWorldEvent(WorldEvent.Load event) {
//        ImmutableList<RuleEntry> rules = ObfuscationReflectionHelper.getPrivateValue(RuleStructureProcessor.class, (RuleStructureProcessor)WorldGenRegistries.STRUCTURE_PROCESSOR_LIST.getOrDefault(new ResourceLocation("farm_plains")).getList().get(0), "rules");
//        System.out.println(rules.get(rules.size()-1).getOutputState().getBlock().getRegistryName());
//        System.out.println(rules.size());
//        List<RuleEntry> list = new ArrayList<RuleEntry>();
//        for (int i = 0; i < rules.size(); i++) {
//            list.add(rules.get(i));
//        }
//        list.add(new RuleEntry(
//                new RandomBlockMatchRuleTest(Blocks.WHEAT, 1F),
//                AlwaysTrueRuleTest.INSTANCE,
//                BlockInit.TOMATO_PLANT.get().getDefaultState()
//        ));
//        rules = ImmutableList.copyOf(list);
//
//        System.out.println(rules.get(rules.size()-1).getOutputState().getBlock().getRegistryName());
//        System.out.println(rules.size());
//        ObfuscationReflectionHelper.setPrivateValue(RuleStructureProcessor.class, (RuleStructureProcessor)WorldGenRegistries.STRUCTURE_PROCESSOR_LIST.getOrDefault(new ResourceLocation("farm_plains")).getList().get(0), rules, "rules");

    }
}
