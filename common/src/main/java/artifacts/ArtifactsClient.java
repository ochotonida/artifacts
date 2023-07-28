package artifacts;

import artifacts.registry.ModKeyMappings;

public class ArtifactsClient {

    public static void init() {
        ModKeyMappings.register();
    }
}
