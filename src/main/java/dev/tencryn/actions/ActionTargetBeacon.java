package dev.tencryn.actions;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.components.messaging.BeaconSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ActionTargetBeacon extends ActionBase {
    protected static final ComponentType<EntityStore, BeaconSupport> BEACON_SUPPORT_COMPONENT_TYPE = BeaconSupport.getComponentType();
    protected final String message;
    protected final double expirationTime;
    protected final int targetSlot;
    protected final int sendTargetSlot;

    public ActionTargetBeacon(@Nonnull BuilderActionTargetBeacon builderActionTargetBeacon, @Nonnull BuilderSupport builderSupport) {
        super(builderActionTargetBeacon);
        this.message = builderActionTargetBeacon.getMessage(builderSupport);
        this.expirationTime = builderActionTargetBeacon.getExpirationTime();
        this.targetSlot = builderActionTargetBeacon.getTargetSlot(builderSupport);
        this.sendTargetSlot = builderActionTargetBeacon.getSendTargetSlot(builderSupport);
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);

        Ref<EntityStore> target = role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot);
        if (target == null) return false;

        Ref<EntityStore> sendTargetRef = this.sendTargetSlot >= 0 ? role.getMarkedEntitySupport().getMarkedEntityRef(this.sendTargetSlot) : ref;
        this.sendNPCMessage(target, sendTargetRef, store);

        return true;
    }

    protected void sendNPCMessage(@Nonnull Ref<EntityStore> ref, @Nullable Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
        BeaconSupport beaconSupport = componentAccessor.getComponent(ref, BEACON_SUPPORT_COMPONENT_TYPE);
        if (beaconSupport != null) {
            beaconSupport.postMessage(this.message, targetRef, this.expirationTime);
        }
    }
}
