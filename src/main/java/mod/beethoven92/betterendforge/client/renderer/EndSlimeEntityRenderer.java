package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.EndSlimeEntityModel;
import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class EndSlimeEntityRenderer extends MobRenderer<EndSlimeEntity, EndSlimeEntityModel<EndSlimeEntity>> {
	private static final ResourceLocation TEXTURE[] = new ResourceLocation[4];
	private static final RenderType GLOW[] = new RenderType[4];

	public EndSlimeEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new EndSlimeEntityModel<EndSlimeEntity>(false), 0.25F);
		this.addLayer(new OverlayFeatureRenderer<EndSlimeEntity>(this));
		this.addLayer(new EyesLayer<EndSlimeEntity, EndSlimeEntityModel<EndSlimeEntity>>(this) {
			@Override
			public RenderType renderType() {
				return GLOW[0];
			}

			@Override
			public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn,
					EndSlimeEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
					float ageInTicks, float netHeadYaw, float headPitch) {
				VertexConsumer vertexConsumer = bufferIn.getBuffer(GLOW[entitylivingbaseIn.getSlimeType()]);
				this.getParentModel().renderToBuffer(matrixStackIn, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F,
						1.0F, 1.0F, 1.0F);
				if (entitylivingbaseIn.isLake()) {
					this.getParentModel().renderFlower(matrixStackIn, vertexConsumer, 15728640,
							OverlayTexture.NO_OVERLAY);
				}
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(EndSlimeEntity entity) {
		return TEXTURE[entity.getSlimeType()];
	}

	@Override
	public void render(EndSlimeEntity slimeEntity, float f, float g, PoseStack matrixStack,
			MultiBufferSource vertexConsumerProvider, int i) {
		this.shadowRadius = 0.25F * (float) slimeEntity.getSize();
		super.render(slimeEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	protected void scale(EndSlimeEntity slimeEntity, PoseStack matrixStack, float partialTickTime) {
		matrixStack.scale(0.999F, 0.999F, 0.999F);
		matrixStack.translate(0.0D, 0.0010000000474974513D, 0.0D);
		float h = (float) slimeEntity.getSize();
		float i = Mth.lerp(partialTickTime, slimeEntity.oSquish, slimeEntity.squish)
				/ (h * 0.5F + 1.0F);
		float j = 1.0F / (i + 1.0F);
		matrixStack.scale(j * h, 1.0F / j * h, j * h);
	}

	private final class OverlayFeatureRenderer<T extends EndSlimeEntity>
			extends RenderLayer<T, EndSlimeEntityModel<T>> {
		private final EndSlimeEntityModel<T> modelOrdinal = new EndSlimeEntityModel<T>(true);
		private final EndSlimeEntityModel<T> modelLake = new EndSlimeEntityModel<T>(true);

		public OverlayFeatureRenderer(RenderLayerParent<T, EndSlimeEntityModel<T>> featureRendererContext) {
			super(featureRendererContext);
		}

		@Override
		public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int packedLightIn,
				T livingEntity, float f, float g, float h, float j, float k, float l) {
			if (!livingEntity.isInvisible()) {
				if (livingEntity.isLake()) {
					VertexConsumer vertexConsumer = vertexConsumerProvider
							.getBuffer(RenderType.entityCutout(this.getTextureLocation(livingEntity)));
					this.getParentModel().renderFlower(matrixStack, vertexConsumer, packedLightIn,
							LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F));
				} else if (livingEntity.isAmber() || livingEntity.isChorus()) {
					VertexConsumer vertexConsumer = vertexConsumerProvider
							.getBuffer(RenderType.entityCutout(this.getTextureLocation(livingEntity)));
					this.getParentModel().renderCrop(matrixStack, vertexConsumer, packedLightIn,
							LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F));
				}

				EndSlimeEntityModel<T> model = livingEntity.getSlimeType() == 1 ? modelLake : modelOrdinal;
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(livingEntity, f, g, h);
				model.setupAnim(livingEntity, f, g, j, k, l);
				VertexConsumer vertexConsumer = vertexConsumerProvider
						.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(livingEntity)));
				model.renderToBuffer(matrixStack, vertexConsumer, packedLightIn,
						LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	static {
		TEXTURE[0] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime.png");
		TEXTURE[1] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_mossy.png");
		TEXTURE[2] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_lake.png");
		TEXTURE[3] = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_amber.png");
		GLOW[0] = RenderType.eyes(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_glow.png"));
		GLOW[1] = GLOW[0];
		GLOW[2] = RenderType.eyes(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_lake_glow.png"));
		GLOW[3] = RenderType.eyes(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/end_slime/end_slime_amber_glow.png"));
	}
}