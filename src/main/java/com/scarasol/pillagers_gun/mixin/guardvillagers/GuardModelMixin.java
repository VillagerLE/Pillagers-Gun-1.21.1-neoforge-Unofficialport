package com.scarasol.pillagers_gun.mixin.guardvillagers;

import com.scarasol.pillagers_gun.item.gun.GunItem;
import com.scarasol.pillagers_gun.util.GunUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tallestegg.guardvillagers.client.models.GuardModel;
import tallestegg.guardvillagers.common.entities.Guard;

@Mixin(GuardModel.class)
public abstract class GuardModelMixin extends HumanoidModel<Guard> {

    public GuardModelMixin(ModelPart root) {
        super(root);
    }

    @ModifyVariable(method = "setupAnim(Ltallestegg/guardvillagers/common/entities/Guard;FFFFF)V", at = @At("STORE"), ordinal = 0)
    private boolean pillagersGun$holdingShootable(boolean original, Guard entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        return original || GunUtil.shouldUseGunPose(entity.getItemInHand(InteractionHand.MAIN_HAND));
    }

    @Inject(method = "setupAnim(Ltallestegg/guardvillagers/common/entities/Guard;FFFFF)V", at = @At("HEAD"))
    private void pillagersGun$gunPose(Guard entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
        if (isTaczStack(itemStack)) {
            // TACZ has its own HumanoidModel support and animates the arms itself.
            return;
        }
        if (GunUtil.shouldUseGunPose(itemStack) && !entity.swinging) {
            if (!(itemStack.getItem() instanceof GunItem) || GunItem.isCharged(itemStack)) {
                this.leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
                this.rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            } else if (entity.isUsingItem()) {
                this.leftArmPose = HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                this.rightArmPose = HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }
        } else {
            this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
            this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        }
    }

    private static boolean isTaczStack(ItemStack itemStack) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        return itemId != null && "tacz".equals(itemId.getNamespace());
    }
}
