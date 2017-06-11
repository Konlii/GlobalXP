package bl4ckscor3.mod.globalxp.blocks;

import bl4ckscor3.mod.globalxp.tileentity.TileEntityXPBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

public class XPBlock extends Block
{
	public XPBlock(Material materialIn)
	{
		super(materialIn);
		
		setCreativeTab(CreativeTabs.MISC);
		setHardness(12.5F);
		setResistance(2000.0F);
		setSoundType(SoundType.METAL);
		setUnlocalizedName("xp_block");
		setRegistryName("xp_block");
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	/**
	 * Gets the stack that is being displayed in the WAILA toolip
	 */
	public ItemStack getWailaDisplayStack()
	{
		return new ItemStack(this);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityXPBlock();
	}
}
