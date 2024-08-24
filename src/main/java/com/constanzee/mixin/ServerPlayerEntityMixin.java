package com.constanzee.mixin;

import com.constanzee.SaveRodInterface;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements SaveRodInterface {

    @Inject(method = "copyFrom", at = @At("HEAD"))
    private void modify(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) (this);

        boolean keepInventory = player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY);
        boolean isSpectator = oldPlayer.isSpectator();

        if (!alive && !keepInventory && !isSpectator) {
            SaveRodInterface saveRodInterface = (SaveRodInterface) oldPlayer;

            if (saveRodInterface.save_rod$getIsSaveRodActive()) {
                player.getInventory().clone(oldPlayer.getInventory());
            }
        }
    }


    @Unique
    private boolean save_rod$isSaveRodActive = false;

    @Override
    public boolean save_rod$getIsSaveRodActive() {
        return save_rod$isSaveRodActive;
    }

    @Override
    public void save_rod$setIsSaveRodActive(boolean value) {
        save_rod$isSaveRodActive = value;
    }
}
