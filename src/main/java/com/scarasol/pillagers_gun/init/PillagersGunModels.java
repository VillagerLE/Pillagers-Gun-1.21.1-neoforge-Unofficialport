package com.scarasol.pillagers_gun.init;

import com.scarasol.pillagers_gun.client.model.BulletModel;
import com.scarasol.pillagers_gun.client.model.RocketModel;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class PillagersGunModels {

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BulletModel.LAYER_LOCATION, BulletModel::createBodyLayer);
        event.registerLayerDefinition(RocketModel.LAYER_LOCATION, RocketModel::createBodyLayer);
    }

}
