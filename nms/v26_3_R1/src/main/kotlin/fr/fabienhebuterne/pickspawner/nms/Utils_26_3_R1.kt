package fr.fabienhebuterne.pickspawner.nms

import net.minecraft.core.Holder
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.component.ItemAttributeModifiers
import net.minecraft.world.item.component.ItemLore
import net.minecraft.world.item.enchantment.ItemEnchantments
import net.minecraft.world.level.block.Blocks
import org.bukkit.Bukkit
import java.lang.reflect.Field

class Utils_26_3_R1 : Utils {

    override fun setMaxStackSize(maxStack: Int) {
        try {
            /**
             * DataComponents.c is maxStackSize, other var is default value for blocks, see [DataComponents.ag]
              */
            val dataComponentsUpdated: DataComponentMap = DataComponentMap.builder()
                .set(DataComponents.MAX_STACK_SIZE, maxStack)
                .set(DataComponents.LORE, ItemLore.EMPTY)
                .set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
                .set(DataComponents.REPAIR_COST, 0)
                .set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY)
                .set(DataComponents.RARITY, Rarity.COMMON)
                .build()
            val holder = Blocks.SPAWNER.asItem().builtInRegistryHolder()
            holder.bindComponents(dataComponentsUpdated)
            val registryHolderField: Field = Item::class.java.getDeclaredField("builtInRegistryHolder")
            registryHolderField.isAccessible = true
            registryHolderField.set(Blocks.SPAWNER.asItem(), holder)
        } catch (e: Exception) {
            Bukkit.getLogger().warning("Can't update maxStackSize for spawner because : $e")
            e.printStackTrace()
        }
    }

}