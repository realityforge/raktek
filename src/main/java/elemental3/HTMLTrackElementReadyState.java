package elemental3;

import java.lang.annotation.Documented;
import javax.annotation.Generated;
import org.intellij.lang.annotations.MagicConstant;

@Generated("org.realityforge.webtack")
@Documented
@MagicConstant(
    intValues = {
        HTMLTrackElement.NONE,
        HTMLTrackElement.LOADING,
        HTMLTrackElement.LOADED,
        HTMLTrackElement.ERROR
    }
)
public @interface HTMLTrackElementReadyState {
  final class Validator {
    private Validator() {
    }

    @HTMLTrackElementReadyState
    @SuppressWarnings("MagicConstant")
    public static int cast(final int value) {
      assertValid( value );
      return value;
    }

    public static void assertValid(final int value) {
      assert isValid( value ) : "@HTMLTrackElementReadyState annotated value must be one of [HTMLTrackElement.NONE, HTMLTrackElement.LOADING, HTMLTrackElement.LOADED, HTMLTrackElement.ERROR] but is " + value;
    }

    public static boolean isValid(final int value) {
      return HTMLTrackElement.NONE == value || HTMLTrackElement.LOADING == value || HTMLTrackElement.LOADED == value || HTMLTrackElement.ERROR == value;
    }
  }
}
