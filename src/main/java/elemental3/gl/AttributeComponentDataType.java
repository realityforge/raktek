package elemental3.gl;

import java.lang.annotation.Documented;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import org.intellij.lang.annotations.MagicConstant;

@Generated("org.realityforge.webtack")
@Documented
@MagicConstant(
    intValues = {
        WebGL2RenderingContext.BYTE,
        WebGL2RenderingContext.UNSIGNED_BYTE,
        WebGL2RenderingContext.SHORT,
        WebGL2RenderingContext.UNSIGNED_SHORT,
        WebGL2RenderingContext.FLOAT,
        WebGL2RenderingContext.HALF_FLOAT
    }
)
public @interface AttributeComponentDataType {
  final class Util {
    private Util() {
    }

    @AttributeComponentDataType
    public static int requireValid(final int value) {
      assertValid( value );
      return value;
    }

    public static void assertValid(final int value) {
      assert isValid( value ) : "@AttributeComponentDataType annotated value must be one of [WebGL2RenderingContext.BYTE, WebGL2RenderingContext.UNSIGNED_BYTE, WebGL2RenderingContext.SHORT, WebGL2RenderingContext.UNSIGNED_SHORT, WebGL2RenderingContext.FLOAT, WebGL2RenderingContext.HALF_FLOAT] but is " + value;
    }

    public static boolean isValid(final int value) {
      return WebGL2RenderingContext.BYTE == value || WebGL2RenderingContext.UNSIGNED_BYTE == value || WebGL2RenderingContext.SHORT == value || WebGL2RenderingContext.UNSIGNED_SHORT == value || WebGL2RenderingContext.FLOAT == value || WebGL2RenderingContext.HALF_FLOAT == value;
    }

    @Nonnull
    public static String describe(final int value) {
      return WebGL2RenderingContext.BYTE == value ? "BYTE" : WebGL2RenderingContext.UNSIGNED_BYTE == value ? "UNSIGNED_BYTE" : WebGL2RenderingContext.SHORT == value ? "SHORT" : WebGL2RenderingContext.UNSIGNED_SHORT == value ? "UNSIGNED_SHORT" : WebGL2RenderingContext.FLOAT == value ? "FLOAT" : WebGL2RenderingContext.HALF_FLOAT == value ? "HALF_FLOAT" : "Unknown value " + value;
    }
  }
}
