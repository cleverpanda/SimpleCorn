package panda.corn.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityParticlePopcornFX extends Particle
{
	private int fireworkAge;
	protected NBTTagList fireworkExplosions;
	
    public EntityParticlePopcornFX(World parWorld,double parX, double parY, double parZ,double parMotionX, double parMotionY, double parMotionZ) 
    {
        super(parWorld, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);
        setParticleTextureIndex(192);
        particleScale = 2.0F;
        setRBGColorF(1,1,1);
        this.setAlphaF(1.0F);
        this.setMaxAge(600);
    }
    
    @Override
	public void onUpdate()
    {
    	
    	this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

        this.motionY -= 0.04D * .08F;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98D;
        this.motionY *= 0.98D;
        this.motionZ *= 0.98D;

        if (this.onGround)
        {
            this.motionX *= 0.70D;
            this.motionZ *= 0.70D;
        }
        
        if (this.fireworkAge == 0 && this.fireworkExplosions != null)
        {
        	
        	
        	boolean flag = this.isFarFromCamera();
            boolean flag1 = false;

            if (this.fireworkExplosions.tagCount() >= 3)
            {
                flag1 = true;
            }
            else
            {
                for (int i = 0; i < this.fireworkExplosions.tagCount(); ++i)
                {
                    NBTTagCompound nbttagcompound = this.fireworkExplosions.getCompoundTagAt(i);

                    if (nbttagcompound.getByte("Type") == 1)
                    {
                        flag1 = true;
                        break;
                    }
                }
            }

            SoundEvent soundevent1;

            if (flag1)
            {
                soundevent1 = flag ? SoundEvents.ENTITY_FIREWORK_LARGE_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_LARGE_BLAST;
            }
            else
            {
                soundevent1 = flag ? SoundEvents.ENTITY_FIREWORK_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_BLAST;
            }

            this.world.playSound(this.posX, this.posY, this.posZ, soundevent1, SoundCategory.AMBIENT, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
        }

        if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount())
        {
            int k = this.fireworkAge / 2;
            NBTTagCompound nbttagcompound1 = this.fireworkExplosions.getCompoundTagAt(k);
            int l = nbttagcompound1.getByte("Type");
            if (l == 1)
            {
                this.createBall(0.5D, 4);
            }
            else if (l == 2)
            {
                this.createShaped(0.5D, new double[][] {{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3796D, -0.1265D}, {0.6122D, -0.8041D}, {0.0D, -0.3592D}}, false);
            }
            else if (l == 3)
            {
                this.createShaped(0.5D, new double[][] {{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, true);
            }
            else if (l == 4)
            {
                this.createBurst();
            }
            else
            {
                this.createBall(0.25D, 2);
            }
        }

        ++this.fireworkAge;

        if (this.fireworkAge > this.particleMaxAge)
        {
            this.setExpired();
        }
    }
    
    @Override
	public int getBrightnessForRender(float p_189214_1_) {
		return super.getBrightnessForRender(p_189214_1_);
	}



	private boolean isFarFromCamera()
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        return minecraft == null || minecraft.getRenderViewEntity() == null || minecraft.getRenderViewEntity().getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0D;
    }
    
    private void createParticle(double p_92034_1_, double p_92034_3_, double p_92034_5_, double p_92034_7_, double p_92034_9_, double p_92034_11_)
    {
        EntityParticlePopcornFX particle = new EntityParticlePopcornFX(this.world, p_92034_1_, p_92034_3_, p_92034_5_, p_92034_7_, p_92034_9_, p_92034_11_);

        Minecraft.getMinecraft().effectRenderer.addEffect(particle); 

    }
    
    /**
     * Creates a small ball or large ball type explosion effect.
     */
    private void createBall(double speed, int size)
    {
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;

        for (int i = -size; i <= size; ++i)
        {
            for (int j = -size; j <= size; ++j)
            {
                for (int k = -size; k <= size; ++k)
                {
                    double d3 = j + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
                    double d4 = i + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
                    double d5 = k + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
                    double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5) / speed + this.rand.nextGaussian() * 0.05D;
                    this.createParticle(d0, d1, d2, d3 / d6, d4 / d6, d5 / d6);

                    if (i != -size && i != size && j != -size && j != size)
                    {
                        k += size * 2 - 1;
                    }
                }
            }
        }
    }

    /**
     * Creates a creeper-shaped or star-shaped explosion.
     */
    private void createShaped(double speed, double[][] shape, boolean p_92038_8_)
    {
        double d0 = shape[0][0];
        double d1 = shape[0][1];
        this.createParticle(this.posX, this.posY, this.posZ, d0 * speed, d1 * speed, 0.0D);
        float f = this.rand.nextFloat() * (float)Math.PI;
        double d2 = p_92038_8_ ? 0.034D : 0.34D;

        for (int i = 0; i < 3; ++i)
        {
            double d3 = f + i * (float)Math.PI * d2;
            double d4 = d0;
            double d5 = d1;

            for (int j = 1; j < shape.length; ++j)
            {
                double d6 = shape[j][0];
                double d7 = shape[j][1];

                for (double d8 = 0.25D; d8 <= 1.0D; d8 += 0.25D)
                {
                    double d9 = (d4 + (d6 - d4) * d8) * speed;
                    double d10 = (d5 + (d7 - d5) * d8) * speed;
                    double d11 = d9 * Math.sin(d3);
                    d9 = d9 * Math.cos(d3);

                    for (double d12 = -1.0D; d12 <= 1.0D; d12 += 2.0D)
                    {
                        this.createParticle(this.posX, this.posY, this.posZ, d9 * d12, d10, d11 * d12);
                    }
                }

                d4 = d6;
                d5 = d7;
            }
        }
    }

    /**
     * Creates a burst type explosion effect.
     */
    private void createBurst()
    {
        double d0 = this.rand.nextGaussian() * 0.05D;
        double d1 = this.rand.nextGaussian() * 0.05D;

        for (int i = 0; i < 70; ++i)
        {
            double d2 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D + d0;
            double d3 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D + d1;
            double d4 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
            this.createParticle(this.posX, this.posY, this.posZ, d2, d4, d3);
        }
    }




	public static Particle generatePopcornParticles(World parWorld,double parX, double parY, double parZ,double parMotionX, double parMotionY, double parMotionZ,NBTTagCompound nbt,int age)
    {
        
        Particle particle = new EntityParticlePopcornFX(parWorld, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);

        ((EntityParticlePopcornFX)particle).fireworkExplosions = nbt.getTagList("Explosions", 10);
        ((EntityParticlePopcornFX)particle).fireworkAge = 0;
        Minecraft.getMinecraft().effectRenderer.addEffect(particle); 
        return particle;
    }


}