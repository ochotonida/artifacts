package artifacts.common;

import net.minecraft.item.Item;

public interface IProxy {

    void preInit();

    void init();

    void postInit();

    void registerItemRenderer(Item item, int meta, String name);
}
