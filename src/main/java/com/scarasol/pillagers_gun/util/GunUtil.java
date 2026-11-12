package com.scarasol.pillagers_gun.util;

import com.scarasol.pillagers_gun.PillagersGunMod;
import com.scarasol.pillagers_gun.api.IMob;
import com.scarasol.pillagers_gun.compat.sbw.SbwCompat;
import com.scarasol.pillagers_gun.compat.tacz.TaczCompat;
import com.scarasol.pillagers_gun.config.CommonConfig;
import com.scarasol.pillagers_gun.init.PillagersGunItems;
import com.scarasol.pillagers_gun.item.gun.GunItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.minecraft.core.registries.BuiltInRegistries;

/**
 * @author Scarasol
 */
public class GunUtil {

    private static final double ANG_DEAD_ZONE_DEG_PER_TICK = 0.05D;
    private static final double ANG_REF_DEG_PER_TICK = 0.70D;
    private static final double ANG_MAX_BOOST = 2D;

    private static final double SNIPER_ANG_DEAD_ZONE_DEG_PER_TICK = 0.02D;
    private static final double SNIPER_ANG_REF_DEG_PER_TICK = 0.15D;

    private static final double DIST_REF_BLOCKS = 16.0D;
    private static final double DIST_CLAMP_MIN = 0.25D;
    private static final double DIST_CLAMP_MAX = 4.00D;
    private static final double DIST_EXP = 0.50D;

    public static boolean isSniperGun(ItemStack itemStack) {
        if (itemStack.is(PillagersGunItems.SNIPERS_RIFLE.get())) {
            return true;
        }
        if (ModList.get().isLoaded("tacz") && TaczCompat.isSniperGun(itemStack)) {
            return true;
        }
        return ModList.get().isLoaded("superbwarfare") && SbwCompat.isSniperGun(itemStack);
    }

    public static boolean shouldUseGunPose(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return false;
        }
        if (itemStack.getItem() instanceof GunItem) {
            return true;
        }

        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        if (itemId == null) {
            return false;
        }
        if (ModList.get().isLoaded("tacz")
                && CommonConfig.TACZ_GUN_USE.get()
                && !TaczCompat.getGunType(itemStack).isEmpty()) {
            return true;
        }
        return ModList.get().isLoaded("superbwarfare")
                && CommonConfig.SBW_GUN_USE.get()
                && SbwCompat.isSbwGun(itemStack);
    }

    public static Vec3 getPerpendicularVelocityToEyeLine(Mob attacker, Entity target, float partialTicks) {
        Vec3 r = target.getEyePosition(partialTicks).subtract(attacker.getEyePosition(partialTicks));
        double r2 = r.lengthSqr();
        if (r2 < 1.0E-12) {
            return Vec3.ZERO;
        }

        Vec3 vRel = getTargetDeltaMovement(attacker, target).subtract(attacker.getDeltaMovement());
        double projScale = vRel.dot(r) / r2;
        return vRel.subtract(r.scale(projScale));
    }

    public static Vec3 getTargetDeltaMovement(Mob attacker, Entity target) {
        if (attacker instanceof IMob iMob) {
           return target.getEyePosition().subtract(iMob.getPillagersGun$targetLastPositon());
        }
        return target.getDeltaMovement();
    }

    public static double getAngularSpeedToEyeLineDegPerTick(Mob attacker, Entity target, float partialTicks) {
        Vec3 r = target.getEyePosition(partialTicks).subtract(attacker.getEyePosition(partialTicks));
        double r2 = r.lengthSqr();
        if (r2 < 1.0E-12) {
            return 0.0D;
        }

        Vec3 vRel = getTargetDeltaMovement(attacker, target).subtract(attacker.getDeltaMovement());
        double omegaRadPerTick = r.cross(vRel).length() / r2;
        return omegaRadPerTick * (180.0D / Math.PI);
    }

    public static float getModifiedInaccuracy(Mob attacker, Entity target, float oldInaccuracy) {
        if (oldInaccuracy <= 0.0F) {
            return oldInaccuracy;
        }

        boolean sniper = isSniperGun(attacker.getMainHandItem());

        double distMul = 1.0D;
        if (!sniper) {
            Vec3 r = target.getEyePosition(1.0F).subtract(attacker.getEyePosition(1.0F));
            double dist = r.length();
            double d = dist / DIST_REF_BLOCKS;
            d = Mth.clamp(d, DIST_CLAMP_MIN, DIST_CLAMP_MAX);
            distMul = Math.pow(d, DIST_EXP);
        }

        double omega = getAngularSpeedToEyeLineDegPerTick(attacker, target, 1.0F);

        double deadZone = sniper ? SNIPER_ANG_DEAD_ZONE_DEG_PER_TICK : ANG_DEAD_ZONE_DEG_PER_TICK;
        double refDeg = sniper ? SNIPER_ANG_REF_DEG_PER_TICK : ANG_REF_DEG_PER_TICK;

        omega = Math.max(0.0D, omega - deadZone);

        double w2 = omega * omega;
        double angMul = 1.0D + ANG_MAX_BOOST * (w2 / (w2 + refDeg * refDeg));

        double mul = distMul * angMul;
        return (float) (oldInaccuracy * mul);
    }
}
