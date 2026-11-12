package com.scarasol.pillagers_gun.mixin;

import com.scarasol.pillagers_gun.api.IMob;
import com.scarasol.pillagers_gun.entity.goal.GunAttackGoal;
import com.scarasol.pillagers_gun.event.EventHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

/**
 * @author Administrator
 */
@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements Targeting, IMob {

    @Shadow
    @Final
    protected GoalSelector goalSelector;

    @Unique
    private Vec3 pillagersGun$targetLastPositon;

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
    private void pillagersGun$addGunAttackGoal(EntityType<? extends LivingEntity> entityType, Level level, CallbackInfo ci) {
        if (level == null || level.isClientSide) {
            return;
        }
        Mob mob = (Mob) (Object) this;
        if (mob.getType().is(EventHandler.PILLAGER_GUNNER)) {
            this.goalSelector.addGoal(2, new GunAttackGoal<>(mob, 1.0D, 40.0F));
        }
    }

    @Unique
    public Vec3 getPillagersGun$targetLastPositon() {
        return Objects.requireNonNullElseGet(pillagersGun$targetLastPositon, this::getEyePosition);
    }

    @Unique
    public void setPillagersGun$targetLastPositon(Vec3 pillagersGun$targetLastPositon) {
        this.pillagersGun$targetLastPositon = pillagersGun$targetLastPositon;
    }
}
