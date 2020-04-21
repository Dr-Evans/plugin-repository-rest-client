package org.jetbrains.intellij.pluginRepository.internal.instances

import org.jetbrains.intellij.pluginRepository.PluginManager
import org.jetbrains.intellij.pluginRepository.internal.api.PluginRepositoryService
import org.jetbrains.intellij.pluginRepository.internal.utils.CompatibleUpdateRequest
import org.jetbrains.intellij.pluginRepository.internal.utils.executeAndParseBody
import org.jetbrains.intellij.pluginRepository.internal.utils.convertCategory
import org.jetbrains.intellij.pluginRepository.model.*

internal class PluginManagerInstance(private val service: PluginRepositoryService) : PluginManager {

  override fun listPlugins(ideBuild: String, channel: String?, pluginId: PluginXmlId?): List<PluginXmlBean> {
    val response = executeAndParseBody(service.listPlugins(ideBuild, channel, pluginId), nullFor404 = true)
    return response?.categories?.flatMap { convertCategory(it) } ?: emptyList()
  }

  override fun getCompatiblePluginsXmlIds(build: String, max: Int, offset: Int, query: String): List<String> =
    executeAndParseBody(service.searchPluginsXmlIds(build, max, offset, query)) ?: emptyList()

  override fun searchCompatibleUpdates(xmlIds: List<PluginXmlId>, build: String, channel: String): List<UpdateBean> =
    executeAndParseBody(service.searchLastCompatibleUpdate(CompatibleUpdateRequest(xmlIds, build, channel))) ?: emptyList()

  override fun getPluginByXmlId(xmlId: PluginXmlId, family: ProductFamily): PluginBean? =
    executeAndParseBody(service.getPluginByXmlId(family.id, xmlId), nullFor404 = true)

  override fun getPlugin(id: PluginId): PluginBean? =
    executeAndParseBody(service.getPluginById(id), nullFor404 = true)

  override fun getPluginVersions(id: PluginId): List<UpdateBean> {
    val pluginBean = executeAndParseBody(service.getPluginById(id), nullFor404 = true) ?: return emptyList()
    return executeAndParseBody(service.getPluginVersions(id), nullFor404 = true).orEmpty().map {
      UpdateBean(it.id, pluginBean.id, pluginBean.xmlId, it.version)
    }
  }

  override fun getPluginDevelopers(id: PluginId): List<PluginUserBean> =
    executeAndParseBody(service.getPluginDevelopers(id), nullFor404 = true) ?: emptyList()

  override fun getPluginChannels(id: PluginId): List<String> =
    executeAndParseBody(service.getPluginChannels(id), nullFor404 = true) ?: emptyList()

  override fun getPluginCompatibleProducts(id: PluginId): List<ProductEnum> =
    executeAndParseBody(service.getPluginCompatibleProducts(id), nullFor404 = true) ?: emptyList()

  override fun getPluginXmlIdByDependency(dependency: String, includeOptional: Boolean) =
    executeAndParseBody(service.getPluginXmlIdByDependency(dependency, includeOptional), nullFor404 = true) ?: emptyList()

}