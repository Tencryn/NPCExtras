package dev.tencryn.filters;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.Builder;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.holder.BooleanHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.StringNotEmptyValidator;
import com.hypixel.hytale.server.npc.corecomponents.IEntityFilter;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderEntityFilterBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BuilderEntityFilterFlag extends BuilderEntityFilterBase {
    protected final StringHolder name = new StringHolder();
    protected final BooleanHolder value = new BooleanHolder();

    @Nullable
    @Override
    public String getShortDescription() {
        return "Match flag of the entity";
    }

    @Nullable
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    @Nullable
    @Override
    public IEntityFilter build(BuilderSupport builderSupport) {
        return new EntityFilterFlag(this, builderSupport);
    }

    @Nullable
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Experimental;
    }

    public Builder<IEntityFilter> readConfig(@Nonnull JsonElement data) {
        this.requireString(data, "Name", this.name, StringNotEmptyValidator.get(), BuilderDescriptorState.Stable, "The name of the flag", null);
        this.getBoolean(data, "Set", this.value, true, BuilderDescriptorState.Stable, "Whether the flag should be set or not", null);
        return this;
    }

    public int getFlagSlot(@Nonnull BuilderSupport support) {
        String flag = this.name.get(support.getExecutionContext());
        return support.getFlagSlot(flag);
    }

    public boolean getValue(@Nonnull BuilderSupport support) {
        return this.value.get(support.getExecutionContext());
    }
}
