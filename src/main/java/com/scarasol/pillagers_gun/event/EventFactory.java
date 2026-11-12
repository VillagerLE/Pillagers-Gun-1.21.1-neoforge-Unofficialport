package com.scarasol.pillagers_gun.event;

import com.scarasol.pillagers_gun.event.server.InaccuracyEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;

/**
 * @author Scarasol
 */
public class EventFactory {

    public static float getModifiedInaccuracy(float oldInaccuracy, Mob shooter, Entity target, Vec3 targetLastPosition) {
        InaccuracyEvent event = new InaccuracyEvent(oldInaccuracy, shooter, target, targetLastPosition);
        NeoForge.EVENT_BUS.post(event);
        return event.getNewInaccuracy();
    }
}
