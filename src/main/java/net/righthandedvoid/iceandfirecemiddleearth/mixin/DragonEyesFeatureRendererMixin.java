package net.righthandedvoid.iceandfirecemiddleearth.mixin;

import com.iafenvoy.iceandfire.render.entity.feature.DragonEyesFeatureRenderer;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static net.righthandedvoid.iceandfirecemiddleearth.Config.BASE_DRAGON_TIERS;

@Mixin(DragonEyesFeatureRenderer.class)
public class DragonEyesFeatureRendererMixin {
    @ModifyExpressionValue(
            method = "render*",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/iafenvoy/iceandfire/entity/DragonBaseEntity;getDragonStage()I"
            )
    )
    private int iceandfirecemiddleearth$clampBehaviorStage(int original) {
        return Math.min(original, BASE_DRAGON_TIERS);
    }
}
