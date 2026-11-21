package com.titanmod.item;

import com.titanmod.registry.ModEffects;
import com.titanmod.registry.ModTabs;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class GiantSerumItem extends Item {
    public static final int FOREVER = 1_000_000_000;

    public GiantSerumItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).tab(ModTabs.TAB));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) { return UseAnim.DRINK; }

    @Override
    public int getUseDuration(ItemStack stack) { return 32; }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.hasEffect(ModEffects.GIANT_POWER.get())) {
            player.displayClientMessage(new TranslatableComponent("msg.titanmod.already_has"), true);
            return InteractionResultHolder.fail(stack);
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!level.isClientSide) {
                player.addEffect(new MobEffectInstance(ModEffects.GIANT_POWER.get(), FOREVER, 0, false, true, true));
                player.getPersistentData().putBoolean("titanmod_power", true);
                player.displayClientMessage(new TranslatableComponent("msg.titanmod.serum_used"), true);
                level.playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.2f, 0.6f);
                if (level instanceof ServerLevel sl) {
                    sl.sendParticles(ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 1, player.getZ(), 30, 0.5, 0.8, 0.5, 0.1);
                }
            }
            if (!player.isCreative()) {
                stack.shrink(1);
                if (stack.isEmpty()) return new ItemStack(Items.GLASS_BOTTLE);
                player.addItem(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return stack;
    }
}
