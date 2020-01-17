package org.jetbrains.intellij.pluginRepository

import org.jetbrains.intellij.pluginRepository.model.json.CompatibleUpdateBean
import org.jetbrains.intellij.pluginRepository.model.json.PluginBean
import org.jetbrains.intellij.pluginRepository.model.json.PluginUpdateBean
import org.jetbrains.intellij.pluginRepository.model.json.PluginUserBean
import org.jetbrains.intellij.pluginRepository.model.repository.IntellijUpdateMetadata
import org.jetbrains.intellij.pluginRepository.model.repository.ProductEnum
import org.jetbrains.intellij.pluginRepository.model.repository.ProductFamily
import org.jetbrains.intellij.pluginRepository.model.xml.PluginXmlBean
import java.io.File

interface PluginRepository {
  val pluginManager: PluginManager
  val pluginUpdateManager: PluginUpdateManager
  val downloader: PluginDownloader
  val uploader: PluginUploader
}

interface PluginManager {
  fun getPluginByXmlId(xmlId: String, family: ProductFamily = ProductFamily.INTELLIJ): PluginBean?
  fun getPlugin(id: Int): PluginBean?
  fun getPluginDevelopers(id: Int): List<PluginUserBean>
  fun getPluginChannels(id: Int): List<String>
  fun getPluginCompatibleProducts(id: Int): List<ProductEnum>
  fun getPluginXmlIdByDependency(dependency: String, includeOptional: Boolean = true): List<String>
  fun listPlugins(ideBuild: String, channel: String? = null, pluginId: String? = null): List<PluginXmlBean>
  fun getCompatiblePluginsXmlIds(build: String, max: Int, offset: Int): List<String>
  fun getCompatibleUpdate(xmlId: String, build: String, max: Int): List<CompatibleUpdateBean>
}

interface PluginUpdateManager {
  fun getUpdatesByVersionAndFamily(xmlId: String, version: String, family: ProductFamily = ProductFamily.INTELLIJ): List<PluginUpdateBean>
  fun getUpdateById(id: Int): PluginUpdateBean?
  fun getIntellijUpdateMetadata(pluginId: Int, updateId: Int): IntellijUpdateMetadata?
}

interface PluginDownloader {
  fun download(pluginXmlId: String, version: String, channel: String? = null, targetPath: File): File?
  fun downloadCompatiblePlugin(pluginXmlId: String, ideBuild: String, channel: String? = null, targetPath: File): File?
}

interface PluginUploader {
  fun uploadPlugin(id: Int, file: File, channel: String? = null, notes: String? = null)
  fun uploadPlugin(xmlId: String, file: File, channel: String? = null, notes: String? = null)
  fun uploadNewPlugin(
    file: File,
    family: ProductFamily = ProductFamily.INTELLIJ,
    categoryId: Int,
    licenseUrl: String
  ): PluginBean
}