package raktek.util;

import elemental3.core.Float32Array;
import java.util.Objects;
import javax.annotation.Nonnull;

public abstract class Float32ArrayUniform
  extends Uniform
{
  @Nonnull
  private Float32Array _values;

  protected Float32ArrayUniform( @Nonnull final UniformDescriptor uniform, @Nonnull final Float32Array values )
  {
    super( uniform );
    _values = Objects.requireNonNull( values );
  }

  public final void setValues( @Nonnull final Float32Array values )
  {
    _values = Objects.requireNonNull( values );
  }

  @Nonnull
  protected final Float32Array getValues()
  {
    return _values;
  }
}
