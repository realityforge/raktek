package elemental3.gl;

import java.lang.annotation.Documented;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import org.intellij.lang.annotations.MagicConstant;

@Generated("org.realityforge.webtack")
@Documented
@MagicConstant(
    stringValues = {
        EXT_color_buffer_half_float.NAME,
        EXT_color_buffer_float.NAME,
        EXT_float_blend.NAME,
        EXT_texture_compression_bptc.NAME,
        EXT_texture_compression_rgtc.NAME,
        EXT_texture_filter_anisotropic.NAME,
        EXT_texture_norm16.NAME,
        KHR_parallel_shader_compile.NAME,
        OES_texture_float_linear.NAME,
        OES_texture_half_float_linear.NAME,
        WEBGL_compressed_texture_astc.NAME,
        WEBGL_compressed_texture_s3tc.NAME,
        WEBGL_compressed_texture_s3tc_srgb.NAME,
        WEBGL_debug_renderer_info.NAME,
        WEBGL_debug_shaders.NAME,
        WEBGL_depth_texture.NAME,
        WEBGL_lose_context.NAME,
        WEBGL_multi_draw.NAME
    }
)
public @interface WebGLExtensionName {
  final class Validator {
    private Validator() {
    }

    @WebGLExtensionName
    @Nonnull
    public static String cast(@Nonnull final String value) {
      assertValid( value );
      return value;
    }

    public static void assertValid(@Nonnull final String value) {
      assert isValid( value ) : "@WebGLExtensionName annotated value must be one of [EXT_color_buffer_half_float.NAME, EXT_color_buffer_float.NAME, EXT_float_blend.NAME, EXT_texture_compression_bptc.NAME, EXT_texture_compression_rgtc.NAME, EXT_texture_filter_anisotropic.NAME, EXT_texture_norm16.NAME, KHR_parallel_shader_compile.NAME, OES_texture_float_linear.NAME, OES_texture_half_float_linear.NAME, WEBGL_compressed_texture_astc.NAME, WEBGL_compressed_texture_s3tc.NAME, WEBGL_compressed_texture_s3tc_srgb.NAME, WEBGL_debug_renderer_info.NAME, WEBGL_debug_shaders.NAME, WEBGL_depth_texture.NAME, WEBGL_lose_context.NAME, WEBGL_multi_draw.NAME] but is " + value;
    }

    public static boolean isValid(@Nonnull final String value) {
      return EXT_color_buffer_half_float.NAME.equals( value ) || EXT_color_buffer_float.NAME.equals( value ) || EXT_float_blend.NAME.equals( value ) || EXT_texture_compression_bptc.NAME.equals( value ) || EXT_texture_compression_rgtc.NAME.equals( value ) || EXT_texture_filter_anisotropic.NAME.equals( value ) || EXT_texture_norm16.NAME.equals( value ) || KHR_parallel_shader_compile.NAME.equals( value ) || OES_texture_float_linear.NAME.equals( value ) || OES_texture_half_float_linear.NAME.equals( value ) || WEBGL_compressed_texture_astc.NAME.equals( value ) || WEBGL_compressed_texture_s3tc.NAME.equals( value ) || WEBGL_compressed_texture_s3tc_srgb.NAME.equals( value ) || WEBGL_debug_renderer_info.NAME.equals( value ) || WEBGL_debug_shaders.NAME.equals( value ) || WEBGL_depth_texture.NAME.equals( value ) || WEBGL_lose_context.NAME.equals( value ) || WEBGL_multi_draw.NAME.equals( value );
    }

    @Nonnull
    public static String describe(@Nonnull final String value) {
      return EXT_color_buffer_half_float.NAME.equals( value ) ? "NAME" : EXT_color_buffer_float.NAME.equals( value ) ? "NAME" : EXT_float_blend.NAME.equals( value ) ? "NAME" : EXT_texture_compression_bptc.NAME.equals( value ) ? "NAME" : EXT_texture_compression_rgtc.NAME.equals( value ) ? "NAME" : EXT_texture_filter_anisotropic.NAME.equals( value ) ? "NAME" : EXT_texture_norm16.NAME.equals( value ) ? "NAME" : KHR_parallel_shader_compile.NAME.equals( value ) ? "NAME" : OES_texture_float_linear.NAME.equals( value ) ? "NAME" : OES_texture_half_float_linear.NAME.equals( value ) ? "NAME" : WEBGL_compressed_texture_astc.NAME.equals( value ) ? "NAME" : WEBGL_compressed_texture_s3tc.NAME.equals( value ) ? "NAME" : WEBGL_compressed_texture_s3tc_srgb.NAME.equals( value ) ? "NAME" : WEBGL_debug_renderer_info.NAME.equals( value ) ? "NAME" : WEBGL_debug_shaders.NAME.equals( value ) ? "NAME" : WEBGL_depth_texture.NAME.equals( value ) ? "NAME" : WEBGL_lose_context.NAME.equals( value ) ? "NAME" : WEBGL_multi_draw.NAME.equals( value ) ? "NAME" : "Unknown value " + value;
    }
  }
}