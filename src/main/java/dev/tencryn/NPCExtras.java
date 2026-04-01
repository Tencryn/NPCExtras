package dev.tencryn;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.npc.NPCPlugin;
import dev.tencryn.actions.BuilderActionTargetBeacon;
import dev.tencryn.filters.BuilderEntityFilterFlag;

import javax.annotation.Nonnull;

public class NPCExtras extends JavaPlugin {
    private static NPCExtras instance;

    public NPCExtras(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    public static NPCExtras get() {
        return instance;
    }

    @Override
    protected void setup() {
        NPCPlugin npcPlugin = NPCPlugin.get();
        npcPlugin.registerCoreComponentType("TargetBeacon", BuilderActionTargetBeacon::new);
        npcPlugin.registerCoreComponentType("Flag", BuilderEntityFilterFlag::new);
    }
}
