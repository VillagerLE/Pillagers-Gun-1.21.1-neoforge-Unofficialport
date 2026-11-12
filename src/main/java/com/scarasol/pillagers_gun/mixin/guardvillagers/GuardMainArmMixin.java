package com.scarasol.pillagers_gun.mixin.guardvillagers;

import com.scarasol.pillagers_gun.util.GunUtil;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tallestegg.guardvillagers.common.entities.Guard;

@Mixin(Mob.class)
public abstract class GuardMainArmMixin {

    @Inject(method = "getMainArm", at = @At("HEAD"), cancellable = true)
    private void pillagersGun$forceRightArm(CallbackInfoReturnable<HumanoidArm> cir) {
        if ((Object) this instanceof Guard guard
                && guard.isLeftHanded()
                && GunUtil.shouldUseGunPose(guard.getMainHandItem())) {
            cir.setReturnValue(HumanoidArm.RIGHT);
        }
    }
}
