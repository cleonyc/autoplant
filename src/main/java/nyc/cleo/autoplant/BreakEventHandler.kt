package nyc.cleo.autoplant

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.scheduler.BukkitRunnable

class BreakEventHandler : Listener {
    // don't interfere with worldguard like plugins
    @EventHandler(priority = EventPriority.LOWEST)
    fun onBreakEvent(e: BlockDropItemEvent) {
        if (e.isCancelled) {
            return
        }
        try {
            val block = e.blockState.blockData as Ageable
            if (block.age != block.maximumAge) {
                return
            }
        } catch (ignored: Exception) {
            return
        }
        when (e.blockState.type) {
            Material.CARROTS -> {
                e.items.removeFirst()
            }
            Material.POTATOES -> {
                e.items.removeFirst()
            }
            Material.WHEAT -> {
                e.items.forEach{i -> if(i.itemStack.type == Material.WHEAT_SEEDS)  {i.itemStack.amount -= 1} }
            }
            Material.BEETROOTS -> {
                e.items.forEach{i -> if(i.itemStack.type == Material.BEETROOT_SEEDS)  {i.itemStack.amount -= 1} }
            }
            Material.NETHER_WART -> {
                e.items.forEach{i -> if(i.itemStack.type == Material.NETHER_WART)  {i.itemStack.amount -= 1} }
            }
            else -> {
                return
            }
        }
        val bukkitRunnable = object: BukkitRunnable() {
            override fun run() {
                e.block.type = e.blockState.type
            }
        }
        bukkitRunnable.runTaskLater(Autoplant.plugin!!, 5)




    }
}