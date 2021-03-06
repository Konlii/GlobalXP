package bl4ckscor3.mod.globalxp.tileentity;

import bl4ckscor3.mod.globalxp.GlobalXP;
import bl4ckscor3.mod.globalxp.network.packets.CPacketRequestXPBlockUpdate;
import bl4ckscor3.mod.globalxp.network.packets.SPacketUpdateXPBlock;
import bl4ckscor3.mod.globalxp.util.XPUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileEntityXPBlock extends TileEntity
{
	private int storedXP = 0;
	private float storedLevels = 0.0F;

	/**
	 * Adds XP to this tile entity and updates all clients within a 64 block range with that change
	 * @param amount The amount of XP to add
	 */
	public void addXP(int amount)
	{
		storedXP += amount;
		storedLevels = XPUtils.calculateStoredLevels(storedXP);
		markDirty();
		GlobalXP.network.sendToAllAround(new SPacketUpdateXPBlock(this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
	}

	/**
	 * Removes XP from the storage and returns amount removed.
	 * Updates all clients within a 64 block range with that change
	 * @param amount The amount of XP to remove
	 * @return The amount of XP that has been removed
	 */
	public int removeXP(int amount)
	{
		int amountRemoved = Math.min(amount, storedXP);

		if(amountRemoved <= 0)
			return 0;

		storedXP -= amountRemoved;
		storedLevels = XPUtils.calculateStoredLevels(storedXP);
		markDirty();
		GlobalXP.network.sendToAllAround(new SPacketUpdateXPBlock(this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
		return amountRemoved;
	}

	/**
	 * Sets how much XP is stored in this tile entity
	 * @param xp The amount of XP
	 */
	public void setStoredXP(int xp)
	{
		storedXP = xp;
		storedLevels = XPUtils.calculateStoredLevels(storedXP);
	}

	/**
	 * Gets how many XP are stored in this tile entity
	 * @return The total amount of XP stored in this tile entity
	 */
	public int getStoredXP()
	{
		return storedXP;
	}

	/**
	 * Gets how many levels are stored.
	 * This value is only used for display purposes and does not reflect partial levels
	 * @return The amount of levels stored
	 */
	public float getStoredLevels()
	{
		return storedLevels;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		tag.setInteger("stored_xp", storedXP);
		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		setStoredXP(tag.getInteger("stored_xp"));
		super.readFromNBT(tag);
	}

	@Override
	public void onLoad()
	{
		if(world.isRemote)
			GlobalXP.network.sendToServer(new CPacketRequestXPBlockUpdate(this));
	}
}
