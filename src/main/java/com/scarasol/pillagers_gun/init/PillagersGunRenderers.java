package com.scarasol.pillagers_gun.init;

import com.scarasol.pillagers_gun.client.renderer.BulletRenderer;
import com.scarasol.pillagers_gun.client.renderer.RocketRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class PillagersGunRenderers {

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PillagersGunEntities.SHOTGUN_AMMO.get(), BulletRenderer::new);
        event.registerEntityRenderer(PillagersGunEntities.PISTOL_AMMO.get(), BulletRenderer::new);
        event.registerEntityRenderer(PillagersGunEntities.ASSAULT_RIFLE_AMMO.get(), BulletRenderer::new);
        event.registerEntityRenderer(PillagersGunEntities.SNIPERS_RIFLE_AMMO.get(), BulletRenderer::new);
        event.registerEntityRenderer(PillagersGunEntities.ROCKET.get(), RocketRenderer::new);
    }
}
