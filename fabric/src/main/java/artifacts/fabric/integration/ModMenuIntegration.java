package artifacts.fabric.integration;

import artifacts.config.screen.ArtifactsConfigScreen;
import artifacts.integration.ModCompat;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (ModCompat.CLOTH_CONFIG.isLoaded()) {
            return parent -> new ArtifactsConfigScreen(parent).build();
        }
        return ModMenuApi.super.getModConfigScreenFactory();
    }
}
