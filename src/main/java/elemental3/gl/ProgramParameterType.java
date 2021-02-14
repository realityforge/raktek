package elemental3.gl;

import java.lang.annotation.Documented;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import org.intellij.lang.annotations.MagicConstant;

@Generated("org.realityforge.webtack")
@Documented
@MagicConstant(
    intValues = {
        WebGL2RenderingContext.DELETE_STATUS,
        WebGL2RenderingContext.LINK_STATUS,
        WebGL2RenderingContext.VALIDATE_STATUS,
        WebGL2RenderingContext.ATTACHED_SHADERS,
        WebGL2RenderingContext.ACTIVE_ATTRIBUTES,
        WebGL2RenderingContext.ACTIVE_UNIFORMS,
        WebGL2RenderingContext.TRANSFORM_FEEDBACK_BUFFER_MODE,
        WebGL2RenderingContext.TRANSFORM_FEEDBACK_VARYINGS,
        WebGL2RenderingContext.ACTIVE_UNIFORM_BLOCKS
    }
)
public @interface ProgramParameterType {
  final class Validator {
    private Validator() {
    }

    @ProgramParameterType
    public static int cast(final int value) {
      assertValid( value );
      return value;
    }

    public static void assertValid(final int value) {
      assert isValid( value ) : "@ProgramParameterType annotated value must be one of [WebGL2RenderingContext.DELETE_STATUS, WebGL2RenderingContext.LINK_STATUS, WebGL2RenderingContext.VALIDATE_STATUS, WebGL2RenderingContext.ATTACHED_SHADERS, WebGL2RenderingContext.ACTIVE_ATTRIBUTES, WebGL2RenderingContext.ACTIVE_UNIFORMS, WebGL2RenderingContext.TRANSFORM_FEEDBACK_BUFFER_MODE, WebGL2RenderingContext.TRANSFORM_FEEDBACK_VARYINGS, WebGL2RenderingContext.ACTIVE_UNIFORM_BLOCKS] but is " + value;
    }

    public static boolean isValid(final int value) {
      return WebGL2RenderingContext.DELETE_STATUS == value || WebGL2RenderingContext.LINK_STATUS == value || WebGL2RenderingContext.VALIDATE_STATUS == value || WebGL2RenderingContext.ATTACHED_SHADERS == value || WebGL2RenderingContext.ACTIVE_ATTRIBUTES == value || WebGL2RenderingContext.ACTIVE_UNIFORMS == value || WebGL2RenderingContext.TRANSFORM_FEEDBACK_BUFFER_MODE == value || WebGL2RenderingContext.TRANSFORM_FEEDBACK_VARYINGS == value || WebGL2RenderingContext.ACTIVE_UNIFORM_BLOCKS == value;
    }

    @Nonnull
    public static String describe(final int value) {
      return WebGL2RenderingContext.DELETE_STATUS == value ? "DELETE_STATUS" : WebGL2RenderingContext.LINK_STATUS == value ? "LINK_STATUS" : WebGL2RenderingContext.VALIDATE_STATUS == value ? "VALIDATE_STATUS" : WebGL2RenderingContext.ATTACHED_SHADERS == value ? "ATTACHED_SHADERS" : WebGL2RenderingContext.ACTIVE_ATTRIBUTES == value ? "ACTIVE_ATTRIBUTES" : WebGL2RenderingContext.ACTIVE_UNIFORMS == value ? "ACTIVE_UNIFORMS" : WebGL2RenderingContext.TRANSFORM_FEEDBACK_BUFFER_MODE == value ? "TRANSFORM_FEEDBACK_BUFFER_MODE" : WebGL2RenderingContext.TRANSFORM_FEEDBACK_VARYINGS == value ? "TRANSFORM_FEEDBACK_VARYINGS" : WebGL2RenderingContext.ACTIVE_UNIFORM_BLOCKS == value ? "ACTIVE_UNIFORM_BLOCKS" : "Unknown value " + value;
    }
  }
}
