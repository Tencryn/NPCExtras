package dev.tencryn.actions;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleOrValidator;
import com.hypixel.hytale.server.npc.asset.builder.validators.StringNotEmptyValidator;
import com.hypixel.hytale.server.npc.asset.builder.validators.StringNullOrNotEmptyValidator;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;

import javax.annotation.Nonnull;
import java.util.Set;

public class BuilderActionTargetBeacon extends BuilderActionBase {
    protected final StringHolder message = new StringHolder();
    protected String sendTargetSlot;
    protected double expirationTime;

    @Nonnull
    @Override
    public Action build(BuilderSupport builderSupport) {
        return new ActionTargetBeacon(this, builderSupport);
    }

    @Nonnull
    @Override
    public String getShortDescription() {
        return "Send beacon message to target";
    }

    @Nonnull
    @Override
    public String getLongDescription() {
        return this.getShortDescription();
    }

    public void registerTags(@Nonnull Set<String> tags) {
        super.registerTags(tags);
        tags.add("message");
    }

    @Nonnull
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Experimental;
    }

    @Nonnull
    public BuilderActionTargetBeacon readConfig(@Nonnull JsonElement data) {
        this.requireString(data, "Message", this.message, StringNotEmptyValidator.get(), BuilderDescriptorState.Stable, "Message to send to target", null);
        this.getString(data, "SendTargetSlot", (b) -> this.sendTargetSlot = b, null, StringNullOrNotEmptyValidator.get(), BuilderDescriptorState.Stable, "The marked target slot to send. If omitted, sends own position", null);
        this.getDouble(data, "ExpirationTime", (d) -> this.expirationTime = d, 1.0F, DoubleOrValidator.greaterEqual0OrMinus1(), BuilderDescriptorState.Stable, "The number of seconds that the message should last. -1 represents infinite time.", "The number of seconds that the message should last and be acknowledged by the receiving NPC. -1 represents infinite time.");
        return this;
    }

    public String getMessage(@Nonnull BuilderSupport support) {
        return this.message.get(support.getExecutionContext());
    }

    public int getTargetSlot(@Nonnull BuilderSupport builderSupport) {
        return builderSupport.getTargetSlot("LockedTarget");
    }

    public int getSendTargetSlot(@Nonnull BuilderSupport support) {
        return this.sendTargetSlot == null ? Integer.MIN_VALUE : support.getTargetSlot(this.sendTargetSlot);
    }

    public double getExpirationTime() {
        return this.expirationTime;
    }
}
