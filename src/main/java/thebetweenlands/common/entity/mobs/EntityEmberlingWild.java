package thebetweenlands.common.entity.mobs;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thebetweenlands.api.entity.IEntityBL;
import thebetweenlands.common.registries.LootTableRegistry;
import thebetweenlands.common.registries.SoundRegistry;

//TODO Loot tables
public class EntityEmberlingWild extends EntityMob implements IEntityMultiPart, IEntityBL {

	public MultiPartEntityPart[] tailPart;
	private static final DataParameter<Boolean> IS_FLAME_ATTACKING = EntityDataManager.<Boolean>createKey(EntityEmberlingWild.class, DataSerializers.BOOLEAN);
	public float animationTicks, prevAnimationTicks;
	
    private EntityMoveHelper moveHelperWater;
    private EntityMoveHelper moveHelperLand;

    private PathNavigateGround pathNavigatorGround;
    private PathNavigateSwimmer pathNavigatorWater;

	public EntityEmberlingWild(World world) {
		super(world);
		setSize(1F, 1F);
		stepHeight = 1F;
		isImmuneToFire = true;
		tailPart = new MultiPartEntityPart[] { new MultiPartEntityPart(this, "tail", 0.5F, 0.5F) };
		
		setPathPriority(PathNodeType.WATER, 100);

        moveHelperWater = new EntityEmberlingWild.EmberlingMoveHelper(this);
        moveHelperLand = new EntityMoveHelper(this);

        pathNavigatorGround = new PathNavigateGround(this, world);
        pathNavigatorGround.setCanSwim(true);
        
        pathNavigatorWater = new PathNavigateSwimmer(this, world);

        updateMovementAndPathfinding();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(IS_FLAME_ATTACKING, false);
	}

	public void setIsFlameAttacking(boolean flame_on) {
		dataManager.set(IS_FLAME_ATTACKING, flame_on);
	}

	public boolean getIsFlameAttacking() {
		return dataManager.get(IS_FLAME_ATTACKING);
	}

	@Override
	protected void initEntityAI() {
		//tasks.addTask(3, new EntityEmberlingWild.EntityAIFireballColumn(this));
		tasks.addTask(2, new EntityEmberlingWild.AIEmberlingAttack(this));
		tasks.addTask(3, new EntityAIWander(this, 0.6D));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(5, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityZombie.class, 0, false, true, null));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundRegistry.EMBERLING_LIVING;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundRegistry.EMBERLING_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundRegistry.EMBERLING_DEATH;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return LootTableRegistry.EMBERLING;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class entity) {
		return EntityEmberlingWild.class != entity;
	}

	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere();
	}

    @Override
    public boolean isNotColliding() {
        return getEntityWorld().getCollisionBoxes(this, getEntityBoundingBox()).isEmpty() && getEntityWorld().checkNoEntityCollision(getEntityBoundingBox(), this);
    }

    @Override
    public boolean isInWater() {
        return getEntityWorld().handleMaterialAcceleration(getEntityBoundingBox(), Material.WATER, this);
    }

	@Override
	public int getMaxSpawnedInChunk() {
		return 3;
	}

    @SideOnly(Side.CLIENT)
    public float smoothedAngle(float partialTicks) {
        return prevAnimationTicks + (animationTicks - prevAnimationTicks) * partialTicks;
    }

	@Override
    public void onUpdate() {
		if (getEntityWorld().getTotalWorldTime()%5 == 0)
			if (getEntityWorld().isRemote)
				flameParticles(getEntityWorld(), tailPart[0].posX, tailPart[0].posY + 0.25, tailPart[0].posZ, rand);
		
		updateMovementAndPathfinding();
    	super.onUpdate();
    }

	protected void updateMovementAndPathfinding() {
		if (isInWater())
			moveHelper = moveHelperWater;
		else
			moveHelper = moveHelperLand;

		if (isInWater() && !world .isAirBlock(new BlockPos(posX, getEntityBoundingBox().maxY + 0.25D, posZ)))
			navigator = pathNavigatorWater;
		else
			navigator = pathNavigatorGround;
		
    	//renderYawOffset = rotationYaw;
		double a = Math.toRadians(renderYawOffset);
		double offSetX = -Math.sin(a) * 1.5D;
		double offSetZ = Math.cos(a) * 1.5D;
		tailPart[0].setLocationAndAngles(posX - offSetX, posY, posZ - offSetZ, 0.0F, 0.0F);
	}

    @Override
    public void travel(float strafe,float up, float forward) {
        if (isServerWorld()) {
            if (isInWater()) {
                moveRelative(strafe, up, forward, 0.1F);
                move(MoverType.SELF, motionX, motionY, motionZ);
                motionX *= 0.8999999761581421D;
                motionY *= 0.8999999761581421D;
                motionZ *= 0.8999999761581421D;

                if (getAttackTarget() == null) {
                    motionY += 0.003D;
                }
            } else {
                super.travel(strafe, up, forward);
            }
        } else {
            super.travel(strafe, up, forward);
        }
    }

	@SideOnly(Side.CLIENT)
	public void flameParticles(World world, double x, double y, double z, Random rand) {
		for (int count = 0; count < 3; ++count) {
			double velX = 0.0D;
			double velY = 0.0D;
			double velZ = 0.0D;
			int motionX = rand.nextBoolean() ? 1 : - 1;
			int motionZ = rand.nextBoolean() ? 1 : - 1;
			velY = rand.nextFloat() * 0.05D;
			velZ = rand.nextFloat() * 0.025D * motionZ;
			velX = rand.nextFloat() * 0.025D * motionX;
			world.spawnParticle(EnumParticleTypes.FLAME,  x, y, z, velX, velY, velZ);
		}
	}

	@Override
	public World getWorld() {
		return getEntityWorld();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		if (isEntityInvulnerable(source) || source.equals(DamageSource.IN_WALL) || source.equals(DamageSource.DROWN))
			return false;
		return super.attackEntityFrom(source, damage);
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage) {
		return false;
	}

	@Override
    public Entity[] getParts(){
        return tailPart;
    }

	static class AIEmberlingAttack extends EntityAIAttackMelee {

		public AIEmberlingAttack(EntityEmberlingWild emberling) {
			super(emberling, 0.6D, false);
		}

		@Override
		protected double getAttackReachSqr(EntityLivingBase attackTarget) {
			return (double) (4.0F + attackTarget.width);
		}
	}
/*
	static class EntityAIFireballColumn extends EntityAIBase {
		EntityEmberlingWild emberling;
		EntityLivingBase target;
		int missileCount;
		int shootCount;

		public EntityAIFireballColumn(EntityEmberlingWild emberling) {
			this.emberling = emberling;
			setMutexBits(5);
		}

		@Override
		public boolean shouldExecute() {
			target = emberling.getAttackTarget();

			if (target == null)
				return false;
			else {
				double distance = emberling.getDistanceSq(target);
				if (distance >= 36.0D && distance <= 144.0D) {
					if (!emberling.onGround)
						return false;
					else
						return emberling.getRNG().nextInt(8) == 0;
				} else
					return false;
			}
		}

		@Override
		public boolean shouldContinueExecuting() {
			return shootCount !=-1 && missileCount !=-1 && emberling.recentlyHit <= 40;
		}

		@Override
		public void startExecuting() {
			missileCount = 0;
			shootCount = 0;
			emberling.getEntityWorld().playSound(null, emberling.getPosition(), SoundRegistry.EMBERLING_FLAMES, SoundCategory.HOSTILE, 1F, 1F);
		}

		@Override
		public void updateTask() {
			if(!emberling.getIsCastingSpell())
				emberling.setIsCastingSpell(true);
			emberling.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
			float f = (float) MathHelper.atan2(target.posZ - emberling.posZ, target.posX - emberling.posX);
			int distance = MathHelper.floor(emberling.getDistance(target));
			missileCount++;
			if (missileCount %5 == 0) {
				shootCount++;
				double d2 = 1D * (double) (shootCount);
				EntityFlameJet flame_jet = new EntityFlameJet(emberling.getEntityWorld(), emberling);
				flame_jet.setPosition(emberling.posX + (double) MathHelper.cos(f) * d2, emberling.posY, emberling.posZ + (double) MathHelper.sin(f) * d2);
				emberling.getEntityWorld().spawnEntity(flame_jet);
				emberling.getEntityWorld().playSound(null, emberling.getPosition(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.HOSTILE, 0.5F, 1F + (emberling.getEntityWorld().rand.nextFloat() - emberling.getEntityWorld().rand.nextFloat()) * 0.8F);
			}
			if (shootCount >= distance || shootCount >= 12) {
				shootCount = -1;
				missileCount = -1;
				if(emberling.getIsCastingSpell())
					emberling.setIsCastingSpell(false);
			}
		}
	}
	  public boolean isPushedByWater() {
	        return false;
	    }
*/

	static class EmberlingMoveHelper extends EntityMoveHelper {
		private final EntityEmberlingWild emberling;

		public EmberlingMoveHelper(EntityEmberlingWild emberling) {
			super(emberling);
			this.emberling = emberling;
		}

		@Override
		public void onUpdateMoveHelper() {
			if (action == EntityMoveHelper.Action.MOVE_TO && !emberling.getNavigator().noPath()) {
				double d0 = posX - emberling.posX;
				double d1 = posY - emberling.posY;
				double d2 = posZ - emberling.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				d3 = (double) MathHelper.sqrt(d3);
				d1 = d1 / d3;
				float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
				emberling.rotationYaw = limitAngle(emberling.rotationYaw, f, 90.0F);
				emberling.renderYawOffset = emberling.rotationYaw;
				float f1 = (float) (speed * emberling.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				emberling.setAIMoveSpeed(emberling.getAIMoveSpeed() + (f1 - emberling.getAIMoveSpeed()) * 0.125F);
				double d4 = Math.sin((double) (emberling.ticksExisted + emberling.getEntityId()) * 0.5D) * 0.05D;
				double d5 = Math.cos((double) (emberling.rotationYaw * 0.017453292F));
				double d6 = Math.sin((double) (emberling.rotationYaw * 0.017453292F));
				emberling.motionX += d4 * d5;
				emberling.motionZ += d4 * d6;
				d4 = Math.sin((double) (emberling.ticksExisted + emberling.getEntityId()) * 0.75D) * 0.05D;
				emberling.motionY += d4 * (d6 + d5) * 0.25D;
				if (Math.abs(emberling.motionY) < 0.35) {
					emberling.motionY += (double) emberling.getAIMoveSpeed() * d1 * 0.1D * (2 + (d1 > 0 ? 0.4 : 0) + (emberling.collidedHorizontally ? 20 : 0));
				}
				EntityLookHelper entitylookhelper = emberling.getLookHelper();
				double d7 = emberling.posX + d0 / d3 * 2.0D;
				double d8 = (double) emberling.getEyeHeight() + emberling.posY + d1 / d3;
				double d9 = emberling.posZ + d2 / d3 * 2.0D;
				double d10 = entitylookhelper.getLookPosX();
				double d11 = entitylookhelper.getLookPosY();
				double d12 = entitylookhelper.getLookPosZ();

				if (!entitylookhelper.getIsLooking()) {
					d10 = d7;
					d11 = d8;
					d12 = d9;
				}

				emberling.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
			} else {
				emberling.setAIMoveSpeed(0.0F);
			}
		}
	}
	    
	    @Override
	    public float getBlockPathWeight(BlockPos pos) {
	        return 0.5F;
	    }

}