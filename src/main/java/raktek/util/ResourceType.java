package raktek.util;

import java.util.Objects;
import javax.annotation.Nonnull;
import raktek.Raktek;

public final class ResourceType
{
  /**
   * The extension for the resource without the leading "." character.
   */
  @Nonnull
  private final String _extension;
  /**
   * Directory relative to the resource base where assets should be stored.
   */
  @Nonnull
  private final String _relativeDirectory;

  public ResourceType( @Nonnull final String extension, @Nonnull final String relativeDirectory )
  {
    _extension = Objects.requireNonNull( extension );
    _relativeDirectory = Objects.requireNonNull( relativeDirectory );
  }

  @Nonnull
  public String getExtension()
  {
    return _extension;
  }

  @Nonnull
  public String getRelativeDirectory()
  {
    return _relativeDirectory;
  }

  @Override
  public String toString()
  {
    return Raktek.shouldSupportDebugToString() ? toDebugString() : super.toString();
  }

  @Nonnull
  public String toDebugString()
  {
    return "ResourceType[" + getExtension() + ":" + getRelativeDirectory() + "]";
  }
}
