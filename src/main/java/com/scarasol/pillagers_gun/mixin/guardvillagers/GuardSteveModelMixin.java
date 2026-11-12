package com.scarasol.pillagers_gun.mixin.guardvillagers;

import com.scarasol.pillagers_gun.util.GunUtil;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import tallestegg.guardvillagers.client.models.GuardSteveModel;
import tallestegg.guardvillagers.common.entities.Guard;

@Mixin(GuardSteveModel.class)
public class GuardSteveModelMixin {

    @ModifyVariable(method = "setupAnim(Ltallestegg/guardvillagers/common/entities/Guard;FFFFF)V", at = @At("STORE"), ordinal = 0)
    private boolean pillagersGun$holdingShootable(boolean original, Guard entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        return original || GunUtil.shouldUseGunPose(entity.getItemInHand(InteractionHand.MAIN_HAND));
    }
}
