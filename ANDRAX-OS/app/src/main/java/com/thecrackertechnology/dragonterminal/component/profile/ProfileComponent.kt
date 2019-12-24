package com.thecrackertechnology.dragonterminal.component.profile

import io.neolang.visitor.ConfigVisitor
import com.thecrackertechnology.dragonterminal.frontend.component.helper.ConfigFileBasedComponent
import com.thecrackertechnology.dragonterminal.frontend.config.NeoTermPath
import com.thecrackertechnology.dragonterminal.frontend.logging.NLog
import java.io.File

/**
 * @author kiva
 */
class ProfileComponent : ConfigFileBasedComponent<NeoProfile>(NeoTermPath.PROFILE_PATH) {
    override val checkComponentFileWhenObtained
        get() = true

    private val profileRegistry = mutableMapOf<String, Class<out NeoProfile>>()
    private val profileList = mutableMapOf<String, MutableList<NeoProfile>>()

    override fun onCheckComponentFiles() = reloadProfiles()

    override fun onCreateComponentObject(configVisitor: ConfigVisitor): NeoProfile {
        val rootContext = configVisitor.getRootContext()

        val profileClass = rootContext.children
                .mapNotNull {
                    profileRegistry[it.contextName]
                }
                .singleOrNull()

        if (profileClass != null) {
            NLog.e("ProfileComponent", "Loaded profile: " + profileClass.name)
            return profileClass.newInstance()
        }

        throw IllegalArgumentException("No proper profile registry found")
    }

    fun getProfiles(metaName: String): List<NeoProfile> = profileList[metaName] ?: listOf()

    fun reloadProfiles() {
        profileList.clear()
        File(baseDir)
                .listFiles(NEOLANG_FILTER)
                .mapNotNull {
                    this.loadConfigure(it)
                }
                .forEach {
                    val list = profileList[it.profileMetaName]
                    if (list != null) {
                        list.add(it)
                    } else {
                        val newList = mutableListOf(it)
                        profileList.put(it.profileMetaName, newList)
                    }
                }
    }

    fun registerProfile(metaName: String, prototype: Class<out NeoProfile>) {
        profileRegistry[metaName] = prototype
        reloadProfiles()
    }

    fun unregisterProfile(metaName: String) {
        profileRegistry.remove(metaName)
    }
}