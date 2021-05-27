package org.odk.collect.android.feature.projects

import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.odk.collect.android.support.CollectTestRule
import org.odk.collect.android.support.TestRuleChain
import org.odk.collect.android.support.pages.GeneralSettingsPage
import org.odk.collect.android.support.pages.MainMenuPage

class UpdateProjectTest {

    val rule = CollectTestRule()

    @get:Rule
    var chain: RuleChain = TestRuleChain
        .chain()
        .around(rule)

    @Test
    fun updateProjectTest() {
        rule.startAtMainMenu()
            .assertProjectIcon("D", "#3e9fcc")
            .openProjectSettings()
            .assertCurrentProject("Demo project", "https://demo.getodk.org")
            .clickAdminSettings()
            .setProjectName("Project X")
            .setProjectIcon("X")
            .setProjectColor("#cccccc")
            .pressBack(MainMenuPage())
            .openProjectSettings()
            .clickGeneralSettings()
            .clickServerSettings()
            .clickServerUsername()
            .inputText("Anna")
            .clickOKOnDialog()
            .pressBack(GeneralSettingsPage())
            .pressBack(MainMenuPage())

            .assertProjectIcon("X", "#cccccc")
            .openProjectSettings()
            .assertCurrentProject("Project X", "Anna / https://demo.getodk.org")
    }
}
