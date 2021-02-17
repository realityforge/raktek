package elemental3;

import elemental3.lang.JsArray;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@Generated("org.realityforge.webtack")
@JsType(
    isNative = true,
    namespace = JsPackage.GLOBAL,
    name = "?"
)
public interface HeadersInit {
  @JsOverlay
  @Nonnull
  static HeadersInit of(@Nonnull final JsArray<JsArray<String>> value) {
    return Js.cast( value );
  }

  @JsOverlay
  @Nonnull
  static HeadersInit of(@Nonnull final JsArray<String>[] value) {
    return Js.cast( value );
  }

  @JsOverlay
  @Nonnull
  static HeadersInit of(@Nonnull final JsPropertyMap<String> value) {
    return Js.cast( value );
  }
}
