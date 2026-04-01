package dev.tencryn.filters;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.LivingEntity;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.role.Role;

import javax.annotation.Nonnull;

public class EntityFilterFlag extends EntityFilterBase {
    public static final int COST = 100;

    protected final int flag;
    protected final boolean value;

    public EntityFilterFlag(@Nonnull BuilderEntityFilterFlag builder, @Nonnull BuilderSupport support) {
        this.flag = builder.getFlagSlot(support);
        this.value = builder.getValue(support);
    }

    @Override
    public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
        NPCEntity targetEntity = store.getComponent(targetRef, NPCEntity.getComponentType());
        if (targetEntity == null) return false;

        Role targetRole = targetEntity.getRole();
        return targetRole != null && targetRole.isFlagSet(flag) == this.value;
    }

    @Override
    public int cost() {
        return COST;
    }
}
