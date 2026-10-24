package net.righthandedvoid.iceandfirecemiddleearth.mixin;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

import static net.righthandedvoid.iceandfirecemiddleearth.Config.*;

@Mixin(DragonBaseEntity.class)
public abstract class DragonBaseEntityMixin {
    @Shadow public abstract int getAgeInDays();
    @Shadow @Final public double minimumHealth;
    @Shadow @Final public double maximumHealth;
    @Shadow @Final public double minimumDamage;
    @Shadow @Final public double maximumDamage;
    @Shadow @Final public double maximumArmor;

    // @Unique Parts

    @Unique
    private int iceandfirecemiddleearth$stageForAge(int age) {
        for (int stage = DRAGON_MIN_TIER; stage <= DRAGON_MAX_TIER; stage++) {       // ascending, explicit order
            if (age <= DRAGON_STAGES_LOOKUP_TABLE.get(stage)) return stage; // first band it fits, stop
        }
        return DRAGON_MAX_TIER;                                          // older than the largest band
    }

    // @ModifyExpressionValue Parts

    @ModifyExpressionValue(
            method = "mobInteract",
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lcom/iafenvoy/iceandfire/registry/IafItems;DRAGON_MEAL:Lnet/neoforged/neoforge/registries/DeferredItem;",
                            opcode = Opcodes.GETSTATIC
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/iafenvoy/jupiter/config/entry/IntegerEntry;getValue()Ljava/lang/Object;"
            )
    )
    private Object iceandfirecemiddleearth$raiseFeedCap(Object original) {
        return DRAGON_AGE_CAP;
    }

    @ModifyExpressionValue(
            method = "getAgeInDays",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/iafenvoy/jupiter/config/entry/IntegerEntry;getValue()Ljava/lang/Object;"
            )
    )
    private Object iceandfirecemiddleearth$raiseTamedAgeCap(Object original) {
        return DRAGON_AGE_CAP;
    }

    @ModifyExpressionValue(
            method = {"getMaxHeadYRot", "isInWater", "updateRider", "roar"},
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/iafenvoy/iceandfire/entity/DragonBaseEntity;getDragonStage()I"
            )
    )
    private int iceandfirecemiddleearth$clampBehaviorStage(int original) {
        return Math.min(original, BASE_DRAGON_TIERS);
    }

    @ModifyExpressionValue(
            method = "getRiderPosition",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;map(FFFFF)F")
    )
    private float iceandfirecemiddleearth$clampRiderLinearFactor(float original) {
        return Math.min(original, 1.0f);
    }

    // @ModifyArg Parts

    @ModifyArg(
            method = "getRenderSize",
            at = @At(value = "INVOKE",
                    target = "Lcom/iafenvoy/iceandfire/entity/util/dragon/DragonSize;getSize(I)Lcom/iafenvoy/iceandfire/entity/util/dragon/DragonSize;"),
            index = 0
    )
    private int iceandfirecemiddleearth$clampSizeStage(int stage) {
        return Math.min(stage, BASE_DRAGON_TIERS); // cap physical size at stage 5, keep the big number for everything else
    }

    // @ModifyConstant

    @ModifyConstant(method = "getAgeInDays", constant = @Constant(intValue = 128))
    private int iceandfirecemiddleearth$raiseAgeCap(int original) {
        return DRAGON_AGE_CAP;
    }

    // @Inject Parts

    @Inject(method = "getDragonStage", at = @At("HEAD"), cancellable = true)
    private void iceandfirecemiddleearth$raiseStageCap(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(this.iceandfirecemiddleearth$stageForAge(this.getAgeInDays()));
    }

    @Inject(method = "refreshDirtyAttributes", at = @At("TAIL"))
    private void iceandfirecemiddleearth$extendStats(CallbackInfo ci) {
        int tier = this.iceandfirecemiddleearth$stageForAge(this.getAgeInDays());
        if (tier <= BASE_DRAGON_TIERS) return;

        int extra = tier - BASE_DRAGON_TIERS;
        LivingEntity self = (LivingEntity) (Object) this;

        Objects.requireNonNull(self.getAttribute(Attributes.MAX_HEALTH))
                .setBaseValue(this.maximumHealth * (1.0 + extra * DRAGON_HP_PER_TIER));
        Objects.requireNonNull(self.getAttribute(Attributes.ATTACK_DAMAGE))
                .setBaseValue(this.maximumDamage * (1.0 + extra * DRAGON_DMG_PER_TIER));
        Objects.requireNonNull(self.getAttribute(Attributes.ARMOR))
                .setBaseValue(this.maximumArmor * (1.0 + extra * DRAGON_DEF_PER_TIER));
    }
}
