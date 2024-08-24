package com.constanzee.mixin;

import com.constanzee.SaveRod;
import com.constanzee.SaveRodInterface;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin  {
	@WrapOperation(
			method = "drop",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;dropInventory()V")
	)
	private void modify(LivingEntity instance, Operation<Void> original) {
		if (instance instanceof PlayerEntity player) {
			int index = player.getInventory().indexOf(new ItemStack(SaveRod.saveRodItem));

			if (index != -1) {
				player.getInventory().removeStack(index, 1);

				if (!player.getWorld().isClient()) {
					SaveRodInterface saveRodInterface = (SaveRodInterface) player;
					saveRodInterface.save_rod$setIsSaveRodActive(true);
				}

				player.playSoundToPlayer(
						SoundEvents.ENTITY_WITHER_SPAWN,
						SoundCategory.MASTER,
						1.0f,
						1.0f
				);

				return;
			}
		}

		original.call(instance);
	}

}