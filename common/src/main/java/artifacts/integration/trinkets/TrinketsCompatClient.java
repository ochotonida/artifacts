package artifacts.integration.trinkets;

import artifacts.equipment.client.EquipmentRenderingManager;

public class TrinketsCompatClient {

    public static void setup() {
        EquipmentRenderingManager.register(new TrinketsRenderingHandler());
    }
}
