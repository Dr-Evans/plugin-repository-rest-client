package org.jetbrains.intellij.pluginRepository.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginBean(
  val id: PluginId,
  val name: String,
  val xmlId: StringPluginId,
  val description: String?,
  val preview: String?,
  val docText: String?,
  val email: String?,
  val family: ProductFamily,
  val copyright: String?,
  val downloads: Int,
  val purchaseInfo: PluginPurchaseInfoBean? = null,
  val vendor: PluginVendorBean?,
  val urls: PluginURLsBean,
  val tags: List<PluginTagBean>?,
  val themes: Set<IntellijThemeBean>?,
  val icon: String?,
  val programmingLanguage: String? = null,
  val language: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class IntellijThemeBean(val name: String, val dark: Boolean)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginPurchaseInfoBean(val productCode: String, val buyUrl: String?, val purchaseTerms: String?)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginTagBean(
  val id: Int,
  val name: String,
  val link: String,
  val families: List<ProductFamily>?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginURLsBean(
  val url: String?,
  val forumUrl: String?,
  val licenseUrl: String?,
  val bugtrackerUrl: String?,
  val docUrl: String?,
  val sourceCodeUrl: String?
)
