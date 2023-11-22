package com.kiva.client.mixins;

import net.minecraft.src.client.renderer.block.TextureStitched;
import net.minecraft.src.client.renderer.block.Texture;
import net.minecraft.src.client.renderer.block.icon.data.AnimationMetadataSection;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureStitched.class)
public class MixinTextureStitched{
    @Shadow protected Texture textureSheet;
    @Shadow protected List<int[]> framesTextureData;
    @Shadow private AnimationMetadataSection animationMetadata;
    @Shadow protected int originX;
    @Shadow protected int originY;
    @Shadow private int width;
    @Shadow private int height;
    @Shadow protected int frameCounter;
    @Shadow protected int tickCounter;
    
    public void updateAnimation() { /*
		 if(!framesTextureData.isEmpty()){
        ++tickCounter;
        if (tickCounter >= animationMetadata.getFrameTimeSingle(frameCounter)) {
            final int frameIndex = animationMetadata.getFrameIndex(frameCounter);
            final int frameCount = (animationMetadata.getFrameCount() == 0) ? framesTextureData.size() : animationMetadata.getFrameCount();
            frameCounter = (frameCounter + 1) % frameCount;
            tickCounter = 0;
            final int frameIndex_ = animationMetadata.getFrameIndex(frameCounter);
            if (frameIndex != frameIndex_ && frameIndex_ >= 0 && frameIndex_ < framesTextureData.size()) {
                textureSheet.uploadTexture(framesTextureData.get(frameIndex_), width, height, originX, originY);
            }
        }/*
        else if (animationMetadata.isInterpolate()) {
            this.updateAnimationInterpolated();
        }*/
    /*}*/
	}
}
