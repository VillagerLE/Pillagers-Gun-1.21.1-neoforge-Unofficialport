package com.scarasol.pillagers_gun.entity.projectile;

import com.scarasol.pillagers_gun.config.CommonConfig;
import com.scarasol.pillagers_gun.init.PillagersGunDamageTypes;
import com.scarasol.pillagers_gun.init.PillagersGunEntities;
import com.scarasol.pillagers_gun.init.PillagersGunSounds;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.item.ShieldItem;
import java.util.Random;


public class ShotgunAmmoEntity extends Ammo{

    public ShotgunAmmoEntity(EntityType<? extends ShotgunAmmoEntity> entityType, Level level) {
        super(entityType, level);
    }


    public ShotgunAmmoEntity(Level level, LivingEntity livingEntity) {
        super(PillagersGunEntities.SHOTGUN_AMMO.get(), livingEntity, level);
    }

    public ShotgunAmmoEntity(Level level, double d, double d2, double d3) {
        super(PillagersGunEntities.SHOTGUN_AMMO.get(), d, d2, d3, level);
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        this.playSound(PillagersGunSounds.bullet_hit_body.get(), 1.0f, 1.0f);
        Entity entity = entityHitResult.getEntity();
        if (entity.invulnerableTime >= 10 && !CommonConfig.BYPASS_INVULNERABLE.get()){
            this.discard();
            return;
        }
        Entity owner = this.getOwner();

        DamageSource ammo1 = PillagersGunDamageTypes.damageSource(this.level(), PillagersGunDamageTypes.AMMO, this, owner);
        entity.invulnerableTime = 0;
        entity.hurt(ammo1, CommonConfig.SHOTGUN_POWER.get());
        if(new Random().nextDouble() < 0.1){
            if(entity instanceof Player player){
                if(player.isDamageSourceBlocked(ammo1) && player.getUseItem().getItem() instanceof ShieldItem){
                    player.disableShield();
                }
            }else if(entity instanceof LivingEntity livingEntity){
                if(livingEntity.isDamageSourceBlocked(ammo1) && livingEntity.getUseItem().getItem() instanceof ShieldItem){
                    livingEntity.stopUsingItem();
                }
            }
        }
        super.onHitEntity(entityHitResult);
        this.discard();
    }
}

