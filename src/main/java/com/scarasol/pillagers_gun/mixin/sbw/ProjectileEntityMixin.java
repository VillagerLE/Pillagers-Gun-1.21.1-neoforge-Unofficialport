package com.scarasol.pillagers_gun.mixin.sbw;

import com.atsuishio.superbwarfare.entity.projectile.IAdvancedHitDetection;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.scarasol.pillagers_gun.entity.projectile.Ammo;
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
@Mixin(IAdvancedHitDetection.DefaultImpls.class)
public abstract class ProjectileEntityMixin {
    @WrapOperation(method = "findEntitiesOnPath(Lcom/atsuishio/superbwarfare/entity/projectile/IAdvancedHitDetection;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Ljava/util/List;", remap = false, at = @At(value = "INVOKE", remap = true, target = "Lnet/minecraft/world/level/Level;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"))
    private static List<Entity> pillagersGun$skipFriendlyFireProjectileTargets(Level level, Entity except, AABB area, Predicate<Entity> predicate, Operation<List<Entity>> original, IAdvancedHitDetection instance, Vec3 startVec, Vec3 endVec) {
        List<Entity> entities = original.call(level, except, area, predicate);
        if (instance instanceof Projectile projectile) {
            Entity shooter = projectile.getOwner();
            if (shooter != null) {
                entities.removeIf(entity -> Ammo.checkFriendlyFire(entity, shooter));
            }
        }
        return entities;
    }
}
