package com.scarasol.pillagers_gun;

import com.mojang.logging.LogUtils;
import com.scarasol.pillagers_gun.config.CommonConfig;
import com.scarasol.pillagers_gun.event.EventHandler;
import com.scarasol.pillagers_gun.init.PillagersGunEntities;
import com.scarasol.pillagers_gun.init.PillagersGunItems;
import com.scarasol.pillagers_gun.init.PillagersGunModels;
import com.scarasol.pillagers_gun.init.PillagersGunRenderers;
import com.scarasol.pillagers_gun.init.PillagersGunSounds;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PillagersGunMod.MODID)
public class PillagersGunMod
{
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "pillagers_gun";

    public PillagersGunMod(IEventBus modEventBus, ModContainer modContainer)
    {
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        PillagersGunItems.REGISTRY.register(modEventBus);
        PillagersGunEntities.REGISTRY.register(modEventBus);
        PillagersGunSounds.REGISTRY.register(modEventBus);

        if (net.neoforged.fml.loading.FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(PillagersGunRenderers::registerEntityRenderers);
            modEventBus.addListener(PillagersGunModels::registerLayerDefinitions);
        }

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.register(EventHandler.class);
    }
}
