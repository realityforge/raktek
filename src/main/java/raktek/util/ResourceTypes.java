package raktek.util;

import javax.annotation.Nonnull;

public final class ResourceTypes
{
  @Nonnull
  public static final ResourceType VERTEX_SHADER = new ResourceType( "vert", "shaders" );

  private ResourceTypes()
  {
  }
}
