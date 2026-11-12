package com.scarasol.pillagers_gun.init;

import com.scarasol.pillagers_gun.PillagersGunMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PillagersGunSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, PillagersGunMod.MODID);
    public static final DeferredHolder<SoundEvent, SoundEvent> bullet_hit_body = REGISTRY.register("bullet_hit_body", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "bullet_hit_body")));
    public static final DeferredHolder<SoundEvent, SoundEvent> bullet_hit_ground = REGISTRY.register("bullet_hit_ground", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "bullet_hit_ground")));
    public static final DeferredHolder<SoundEvent, SoundEvent> shotgun_fire = REGISTRY.register("shotgun_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "shotgun_fire")));
    public static final DeferredHolder<SoundEvent, SoundEvent> shotgun_reload = REGISTRY.register("shotgun_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "shotgun_reload")));
    public static final DeferredHolder<SoundEvent, SoundEvent> pistol_fire = REGISTRY.register("pistol_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "pistol_fire")));
    public static final DeferredHolder<SoundEvent, SoundEvent> assault_rifle_fire = REGISTRY.register("assault_rifle_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "assault_rifle_fire")));
    public static final DeferredHolder<SoundEvent, SoundEvent> assault_rifle_fire_three_round = REGISTRY.register("assault_rifle_fire_three_round", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "assault_rifle_fire_three_round")));
    public static final DeferredHolder<SoundEvent, SoundEvent> assault_rifle_reload = REGISTRY.register("assault_rifle_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "assault_rifle_reload")));
    public static final DeferredHolder<SoundEvent, SoundEvent> pistol_reload = REGISTRY.register("pistol_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "pistol_reload")));
    public static final DeferredHolder<SoundEvent, SoundEvent> snipers_rifle_fire = REGISTRY.register("snipers_rifle_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "snipers_rifle_fire")));
    public static final DeferredHolder<SoundEvent, SoundEvent> snipers_rifle_reload = REGISTRY.register("snipers_rifle_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "snipers_rifle_reload")));
    public static final DeferredHolder<SoundEvent, SoundEvent> bazooka_fire = REGISTRY.register("bazooka_fire", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "bazooka_fire")));
    public static final DeferredHolder<SoundEvent, SoundEvent> bazooka_reload = REGISTRY.register("bazooka_reload", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PillagersGunMod.MODID, "bazooka_reload")));


}
