package com.titanmod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class Keys {
    /** Shift + M */
    public static final KeyMapping TRANSFORM = new KeyMapping("key.titanmod.transform",
            KeyConflictContext.IN_GAME, KeyModifier.SHIFT, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_M, "key.categories.titanmod");
}
