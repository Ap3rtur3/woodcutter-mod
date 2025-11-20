package net.woodcutter.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.woodcutter.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class ModMenuCompat implements ModMenuApi {

    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ModConfig.class, parent).get();
    }

    @Override
    public void attachModpackBadges(Consumer<String> consumer) {
        consumer.accept("modmenu");
    }
}
