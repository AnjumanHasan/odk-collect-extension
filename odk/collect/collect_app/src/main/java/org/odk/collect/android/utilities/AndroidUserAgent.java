package org.odk.collect.android.utilities;

import org.odk.collect.android.BuildConfig;
import org.odk.collect.settings.SettingsProvider;
import org.odk.collect.settings.keys.ExtensionKeys;
import org.odk.collect.settings.keys.ProjectKeys;
import org.odk.collect.shared.settings.Settings;
import org.odk.collect.utilities.UserAgentProvider;

public final class AndroidUserAgent implements UserAgentProvider {


    @Override
    public String getUserAgent(SettingsProvider settingsProvider) {
        Settings settings = settingsProvider.getUnprotectedSettings();
        return String.format("%s/%s %s",
                settings.getString(ExtensionKeys.APP_PROVIDER),
                settings.getString(ExtensionKeys.APP_VERSION),
                System.getProperty("http.agent"));
    }
}
