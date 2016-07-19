package panda.corn;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;;

public class ComponentCornField extends StructureVillagePieces.Village{

	private Block crop;

	public ComponentCornField() {}

	public ComponentCornField(StructureVillagePieces.Start p_i45569_1_, int p_i45569_2_, Random p_i45569_3_, StructureBoundingBox p_i45569_4_, EnumFacing p_i45569_5_)
	{
		super(p_i45569_1_, p_i45569_2_);
		this.coordBaseMode = p_i45569_5_;
		this.boundingBox = p_i45569_4_;
		this.crop = Corn.corn;
	}

	@Override
	public boolean addComponentParts(World worldIn, Random rand, StructureBoundingBox box)
	{
		if (this.field_143015_k < 0)
		{
			this.field_143015_k = this.getAverageGroundLevel(worldIn, box);

			if (this.field_143015_k < 0)
			{
				return true;
			}

			this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
		}

		this.func_175804_a(worldIn, box, 0, 1, 0, 6, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
		this.func_175804_a(worldIn, box, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
		this.func_175804_a(worldIn, box, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
		this.func_175804_a(worldIn, box, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
		this.func_175804_a(worldIn, box, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
		this.func_175804_a(worldIn, box, 1, 0, 0, 5, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
		this.func_175804_a(worldIn, box, 1, 0, 8, 5, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
		this.func_175804_a(worldIn, box, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
		int i;

		for (i = 1; i <= 7; ++i)
		{
			this.func_175811_a(worldIn, this.crop.getStateFromMeta(MathHelper.getRandomIntegerInRange(rand, 0, 3)), 1, 1, i, box);
			this.func_175811_a(worldIn, this.crop.getStateFromMeta(MathHelper.getRandomIntegerInRange(rand, 0, 3)), 2, 1, i, box);
			this.func_175811_a(worldIn, this.crop.getStateFromMeta(MathHelper.getRandomIntegerInRange(rand, 0, 3)), 4, 1, i, box);
			this.func_175811_a(worldIn, this.crop.getStateFromMeta(MathHelper.getRandomIntegerInRange(rand, 0, 3)), 5, 1, i, box);
		}

		for (i = 0; i < 9; ++i)
		{
			for (int j = 0; j < 7; ++j)
			{
				this.clearCurrentPositionBlocksUpwards(worldIn, j, 4, i, box);
				this.func_175808_b(worldIn, Blocks.dirt.getDefaultState(), j, -1, i, box);
			}
		}

		return true;
	}

	public static ComponentCornField buildComponent(StructureVillagePieces.Start p_175852_0_, List p_175852_1_, Random p_175852_2_, int x, int y, int z, EnumFacing facing, int p_175852_7_)
	{
		StructureBoundingBox structureboundingbox = StructureBoundingBox.func_175897_a(x, y, z, 0, 0, 0, 7, 4, 9, facing);
		return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175852_1_, structureboundingbox) == null ? new ComponentCornField(p_175852_0_, p_175852_7_, p_175852_2_, structureboundingbox, facing) : null;
	}

}


