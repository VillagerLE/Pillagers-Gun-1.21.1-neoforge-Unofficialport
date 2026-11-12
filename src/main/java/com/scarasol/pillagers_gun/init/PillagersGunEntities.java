package com.scarasol.pillagers_gun.init;

import com.scarasol.pillagers_gun.PillagersGunMod;
import com.scarasol.pillagers_gun.entity.projectile.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PillagersGunEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, PillagersGunMod.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<ShotgunAmmoEntity>> SHOTGUN_AMMO = register("shotgun_ammo", EntityType.Builder.<ShotgunAmmoEntity>of(ShotgunAmmoEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.25f, 0.25f));
    public static final DeferredHolder<EntityType<?>, EntityType<PistolAmmoEntity>> PISTOL_AMMO = register("pistol_ammo", EntityType.Builder.<PistolAmmoEntity>of(PistolAmmoEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.25f, 0.25f));
    public static final DeferredHolder<EntityType<?>, EntityType<AssaultRifleAmmoEntity>> ASSAULT_RIFLE_AMMO = register("assault_rifle_ammo", EntityType.Builder.<AssaultRifleAmmoEntity>of(AssaultRifleAmmoEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.25f, 0.25f));
    public static final DeferredHolder<EntityType<?>, EntityType<SnipersRifleAmmoEntity>> SNIPERS_RIFLE_AMMO = register("snipers_rifle_ammo", EntityType.Builder.<SnipersRifleAmmoEntity>of(SnipersRifleAmmoEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.25f, 0.25f));
    public static final DeferredHolder<EntityType<?>, EntityType<RocketEntity>> ROCKET = register("rocket", EntityType.Builder.<RocketEntity>of(RocketEntity::new, MobCategory.MISC).clientTrackingRange(64).updateInterval(1).sized(0.5f, 0.5f));

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryname, () -> entityTypeBuilder.build(registryname));
    }
}
