package com.scarasol.pillagers_gun.init;

import com.scarasol.pillagers_gun.PillagersGunMod;
import com.scarasol.pillagers_gun.item.ammo.*;
import com.scarasol.pillagers_gun.item.gun.*;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PillagersGunItems {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(PillagersGunMod.MODID);

    public static final DeferredItem<Item> SHOTGUN = REGISTRY.register("shotgun", ShotgunItem::new);
    public static final DeferredItem<Item> SHOTGUN_AMMO = REGISTRY.register("shotgun_ammo", ShotgunAmmoItem::new);

    public static final DeferredItem<Item> PISTOL = REGISTRY.register("pistol", PistolItem::new);
    public static final DeferredItem<Item> PISTOL_AMMO = REGISTRY.register("pistol_ammo", PistolAmmoItem::new);

    public static final DeferredItem<Item> ASSAULT_RIFLE = REGISTRY.register("assault_rifle", AssaultRifleItem::new);
    public static final DeferredItem<Item> ASSAULT_RIFLE_AMMO = REGISTRY.register("assault_rifle_ammo", AssaultRifleAmmoItem::new);

    public static final DeferredItem<Item> SNIPERS_RIFLE = REGISTRY.register("snipers_rifle", SnipersRifleItem::new);
    public static final DeferredItem<Item> SNIPERS_RIFLE_AMMO = REGISTRY.register("snipers_rifle_ammo", SnipersRifleAmmoItem::new);

    public static final DeferredItem<Item> BAZOOKA = REGISTRY.register("bazooka", BazookaItem::new);
    public static final DeferredItem<Item> ROCKET = REGISTRY.register("rocket", RocketItem::new);
}
