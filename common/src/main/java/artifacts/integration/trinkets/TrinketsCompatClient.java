package artifacts.integration.trinkets;

import artifacts.equipment.client.EquipmentRenderingManager;
import artifacts.integration.ModCompat;

public class TrinketsCompatClient {

    public static void setup() {
        if (!ModCompat.TCLAYER.isLoaded()) {
            EquipmentRenderingManager.register(new TrinketsRenderingHandler());
        }
    }
}
