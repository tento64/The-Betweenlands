package thebetweenlands.world.biomes.decorators;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.world.WorldProviderBetweenlands;
import thebetweenlands.world.biomes.base.ChunkDataAccess;
import thebetweenlands.world.biomes.decorators.base.BiomeDecoratorBaseBetweenlands;
import thebetweenlands.world.biomes.decorators.data.SurfaceType;

public class BiomeDecoratorMarsh extends BiomeDecoratorBaseBetweenlands {
	@Override
	public void postChunkGen(int pass) {
		for (int i = 0; i < 10; i++) {
			int px = this.x + offsetXZ();
			int py = 80 + rand.nextInt(14);
			int pz = this.z + offsetXZ();
			if (checkSurface(SurfaceType.PEAT, px, py, pz)) {
				world.setBlock(px, py, pz, Blocks.fire, 0, 3);
			}
		}

		DecorationHelper helper = new DecorationHelper(this.rand, this.world, this.x, this.world.getHeightValue(this.x, this.z), this.z, false);

		helper.generateGiantWeedwoodTree(1);
		
		helper.generateWeedwoodTree(1);
		helper.generateSwampGrass(20);
		helper.generateSundew(5);
		helper.generateNettles(2);
		helper.generateWeepingBlue(1);
		helper.generateWisp(4);
		helper.generateArrowArum(2);
		helper.generatePickerelWeed(2);
		helper.generateMarshHibiscus(2);
		helper.generateMarshMallow(2);
		helper.generateButtonBush(2);
		helper.generatePhragmites(400);
		helper.generateSoftRush(130);
		helper.generateBroomsedge(40);
		helper.generateBottleBrushGrass(5);
	}
}
