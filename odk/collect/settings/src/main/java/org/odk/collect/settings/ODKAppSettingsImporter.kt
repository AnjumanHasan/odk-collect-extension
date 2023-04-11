package org.odk.collect.settings

import android.util.Log
import org.odk.collect.projects.Project
import org.odk.collect.projects.ProjectsRepository
import org.odk.collect.settings.importing.ProjectDetailsCreatorImpl
import org.odk.collect.settings.importing.SettingsChangeHandler
import org.odk.collect.settings.importing.SettingsImporter
import org.odk.collect.settings.validation.JsonSchemaSettingsValidator
import java.security.cert.Extension
import kotlin.math.log

class ODKAppSettingsImporter(
    projectsRepository: ProjectsRepository,
    settingsProvider: SettingsProvider,
    generalDefaults: Map<String, Any>,
    adminDefaults: Map<String, Any>,
    extensionDefaults: Map<String, Any>,
    projectColors: List<String>,
    settingsChangedHandler: SettingsChangeHandler
) {

    private val settingsImporter = SettingsImporter(
        settingsProvider,
        ODKAppSettingsMigrator(settingsProvider.getMetaSettings()),
        JsonSchemaSettingsValidator { javaClass.getResourceAsStream("/client-settings.schema.json")!! },
        generalDefaults,
        adminDefaults,
        extensionDefaults,
        settingsChangedHandler,
        projectsRepository,
        ProjectDetailsCreatorImpl(projectColors, generalDefaults)
    )

    fun fromJSON(json: String, project: Project.Saved): Boolean {
        return try {
            settingsImporter.fromJSON(json, project)
        } catch (e: Throwable) {
            Log.e("ODKAppSettingsImporter", "fromJSON: " + e.printStackTrace(), e)
            false
        }
    }
}
