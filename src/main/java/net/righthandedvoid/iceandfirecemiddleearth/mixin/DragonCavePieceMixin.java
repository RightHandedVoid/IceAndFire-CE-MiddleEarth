package net.righthandedvoid.iceandfirecemiddleearth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/*
@Mixin(targets = "com.iafenvoy.iceandfire.world.structure.DragonCaveStructure$DragonCavePiece")
public abstract class DragonCavePieceMixin {

    @ModifyArg(
            method = "postProcess",
            at = @At(
                    value = "INVOKE",
                    target = ("Lcom/iafenvoy/iceandfire/world/structure/DragonCaveStructure$DragonCavePiece;" +
                            "createDragon(Lnet/minecraft/world/level/WorldGenLevel;" +
                            "Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;I)" +
                            "Lcom/iafenvoy/iceandfire/entity/DragonBaseEntity;")
            ),
            index = 3
    )
    private int iceandfirecemiddleearth$upgradeDenDragonAge(int dragonAge, @Local(argsOnly = true) RandomSource random)
    {
        if (YourStageUtil.stageForAge(dragonAge) < 5) return dragonAge; // only mature dens grow ancient
        int cap    = YourConfig.wildDenStageCap();      // e.g. 10
        float chance = YourConfig.denUpgradeChance();   // e.g. 0.15F
        int stage = 5;
        while (stage < cap && random.nextFloat() < chance) stage++;
        if (stage == 5) return dragonAge;               // no upgrade → keep original
        return YourStageUtil.midpointAgeForStage(stage); // inverse of your stageForAge
    }
}
*/