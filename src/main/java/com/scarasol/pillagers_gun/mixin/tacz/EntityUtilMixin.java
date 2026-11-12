package com.scarasol.pillagers_gun.mixin.tacz;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.scarasol.pillagers_gun.entity.projectile.Ammo;
import com.tacz.guns.util.EntityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Scarasol
 */
@Mixin(EntityUtil.class)
public abstract class EntityUtilMixin {

    @WrapOperation(method = "findEntityOnPath", remap = false, at = @At(value = "INVOKE", remap = true, target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"))
    private static List<Entity> pillagersGun$checkFriendlyFireEntities(Level level, Entity except, AABB area, Predicate<Entity> predicate, Operation<List<Entity>> original, Projectile bulletEntity, Vec3 startVec, Vec3 endVec) {
        List<Entity> entities = original.call(level, except, area, predicate);
        Entity owner = bulletEntity.getOwner();
        if (owner != null) {
            entities.removeIf(entity -> Ammo.checkFriendlyFire(entity, owner));
        }
        return entities;
    }
}
