package com.scarasol.pillagers_gun.event;


import com.scarasol.pillagers_gun.compat.sbw.SbwCompat;
import com.scarasol.pillagers_gun.compat.tacz.TaczCompat;
import com.scarasol.pillagers_gun.config.CommonConfig;
import com.scarasol.pillagers_gun.entity.goal.combat.FireTeamCoordinator;
import com.scarasol.pillagers_gun.event.server.InaccuracyEvent;
import com.scarasol.pillagers_gun.init.PillagersGunItems;
import com.scarasol.pillagers_gun.util.GunnerEquipmentService;
import com.scarasol.pillagers_gun.util.GunUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class EventHandler {

    public static final ResourceLocation ATTRIBUTE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("pillagers_gun", "sniper_follow_range");

    public static final TagKey<EntityType<?>> BORN_WITH_GUN = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("forge:born_with_gun"));
    public static final TagKey<EntityType<?>> PILLAGER_GUNNER = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("forge:pillager_gunner"));

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (event.getEntity() instanceof Mob mob && mob.getType().is(BORN_WITH_GUN)) {
            GunnerEquipmentService.scheduleBornWithGun(mob);
        }
    }

    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (event.getEntity() instanceof Mob mob) {
            FireTeamCoordinator.INSTANCE.remove(mob);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getEntity() instanceof Mob mob) {
            FireTeamCoordinator.INSTANCE.remove(mob);
        }
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Mob mob
                && mob.getType().is(BORN_WITH_GUN)
                && GunnerEquipmentService.hasScheduledBornWithGun(mob)) {
            GunnerEquipmentService.tickBornWithGun(mob);
        }
    }

    @SubscribeEvent
    public static void changeEquip(LivingEquipmentChangeEvent event) {
        LivingEntity mob = event.getEntity();
        if (mob.getType().is(PILLAGER_GUNNER) && event.getSlot() == EquipmentSlot.MAINHAND) {
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(event.getTo().getItem());
            if (event.getTo().is(PillagersGunItems.SNIPERS_RIFLE.get())) {
                GunnerEquipmentService.applyVanillaSniperFollowRange(mob, event.getTo());
            } else if (CommonConfig.TACZ_GUN_USE.get() && itemId != null && "tacz".equals(itemId.getNamespace())) {
                TaczCompat.changeEquipmentTo(event);
            } else if (CommonConfig.SBW_GUN_USE.get()
                    && itemId != null
                    && "superbwarfare".equals(itemId.getNamespace())
                    && SbwCompat.isSbwGun(event.getTo())) {
                SbwCompat.changeEquipmentTo(event);
            } else {
                GunnerEquipmentService.clearFollowRangeModifier(mob);
            }

        }
    }

//    @SubscribeEvent
//    public static void hit(ProjectileImpactEvent event) {
//        if (event.getRayTraceResult() instanceof EntityHitResult entityHitResult && event.getProjectile() instanceof Ammo ammo) {
//            Entity entity = ammo.getOwner();
//            if (entity != null) {
//                if (Ammo.checkFriendlyFire(entityHitResult.getEntity(), entity)) {
//                    event.setImpactResult(ProjectileImpactEvent.ImpactResult.SKIP_ENTITY);
//                }
//            }
//        }
//    }


    @SubscribeEvent
    public static void knockbackCancel(LivingKnockBackEvent event) {
        if (event.getEntity().getPersistentData().getBoolean("ShootByGun")) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void modifyInaccuracy(InaccuracyEvent event) {
        if (CommonConfig.DYNAMIC_INACCURACY.get() && event.getTarget() != null) {
            float newInaccuracy = GunUtil.getModifiedInaccuracy(event.getShooter(), event.getTarget(), event.getOldInaccuracy());
            event.setNewInaccuracy(newInaccuracy);
        }
    }

}
