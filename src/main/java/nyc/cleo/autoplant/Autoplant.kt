package nyc.cleo.autoplant

import org.bukkit.plugin.java.JavaPlugin

class Autoplant : JavaPlugin() {
    override fun onEnable() {
        this.server.pluginManager.registerEvents(BreakEventHandler(), this)
        plugin = this
    }

    override fun onDisable() {}
    companion object {
        var plugin: JavaPlugin? = null
    }
}