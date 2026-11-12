package com.scarasol.pillagers_gun.entity.projectile;

import com.scarasol.pillagers_gun.config.CommonConfig;
import com.scarasol.pillagers_gun.init.PillagersGunDamageTypes;
import com.scarasol.pillagers_gun.init.PillagersGunEntities;
import com.scarasol.pillagers_gun.init.PillagersGunSounds;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;


public class PistolAmmoEntity extends Ammo{

    public PistolAmmoEntity(EntityType<? extends PistolAmmoEntity> entityType, Level level) {
        super(entityType, level);
    }


    public PistolAmmoEntity(Level level, LivingEntity livingEntity) {
        super(PillagersGunEntities.PISTOL_AMMO.get(), livingEntity, level);
    }

    public PistolAmmoEntity(Level level, double d, double d2, double d3) {
        super(PillagersGunEntities.PISTOL_AMMO.get(), d, d2, d3, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        this.playSound(PillagersGunSounds.bullet_hit_body.get(), 1.0f, 1.0f);
        Entity entity = entityHitResult.getEntity();
        if (entity.invulnerableTime >= 10 && !CommonConfig.BYPASS_INVULNERABLE.get()){
            this.discard();
            return;
        }
        Entity owner = this.getOwner();


        DamageSource ammo1 = PillagersGunDamageTypes.damageSource(this.level(), PillagersGunDamageTypes.AMMO, this, owner);
        entity.invulnerableTime = 0;
        entity.hurt(ammo1, CommonConfig.PISTOL_POWER.get());
        super.onHitEntity(entityHitResult);
        this.discard();
    }
}
