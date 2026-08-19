package fr.fabienhebuterne.pickspawner.nms

import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.world.level.block.Blocks
import org.bukkit.Bukkit

class Utils_26_R2 : Utils {

    override fun setMaxStackSize(maxStack: Int) {
        try {
            /**
             * Since 26.x, CraftBukkit/Spigot builds directly against Mojang's mappings, so NMS
             * fields/methods are no longer obfuscated to single letters (unlike v1_21_R6 and earlier).
             * The Item's DataComponentMap now lives on its registry Holder (Holder.Reference),
             * exposed with public accessors, so this no longer needs reflection at all.
             */
            val holder = Blocks.SPAWNER.asItem().builtInRegistryHolder()
            val updatedComponents: DataComponentMap = DataComponentMap.builder()
                .addAll(holder.components())
                .set(DataComponents.MAX_STACK_SIZE, maxStack)
                .build()
            holder.bindComponents(updatedComponents)
        } catch (e: Exception) {
            Bukkit.getLogger().warning("Can't update maxStackSize for spawner because : $e")
            e.printStackTrace()
        }
    }

}
