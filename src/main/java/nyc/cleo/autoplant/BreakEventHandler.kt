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
        println("c")
        try {
            val block = e.blockState.blockData as Ageable
            if (block.age != block.maximumAge) {
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        println("b")
        when (e.blockState.type) {
            Material.CARROTS -> {
                e.items.removeLast()
            }
            Material.POTATOES -> {
                e.items.removeLast()
            }
            Material.WHEAT -> {
                println("a")
                e.items.remove(e.items.find { i -> i.itemStack.type == Material.WHEAT_SEEDS })
            }
            Material.BEETROOTS -> {
                e.items.removeLast()
            }
            Material.NETHER_WART -> {
                e.items.removeLast()
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