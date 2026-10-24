package net.righthandedvoid.iceandfirecemiddleearth.mixin;

import com.iafenvoy.iceandfire.render.texture.DragonTextureProvider;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import static net.righthandedvoid.iceandfirecemiddleearth.Config.BASE_DRAGON_TIERS;

@Mixin(DragonTextureProvider.class)
public class DragonTextureProviderMixin {
    @ModifyExpressionValue(
            method = "getTextureByEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/iafenvoy/iceandfire/entity/DragonBaseEntity;getDragonStage()I"
            )
    )
    private int iceandfirecemiddleearth$clampEntityTextures(int original) {
        return Math.min(original, BASE_DRAGON_TIERS);
    }
}
