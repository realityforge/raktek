package raktek.util;

import akasha.Global;
import akasha.promise.Promise;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class ResourceDirectory
{
  @Nonnull
  private final String _baseURL;

  public ResourceDirectory( @Nonnull final String baseURL )
  {
    _baseURL = Objects.requireNonNull( baseURL );
  }

  @Nonnull
  public Promise<String> loadText( @Nonnull final ResourceType resourceType, @Nonnull final String name )
  {
    return
      Global
        .fetch( deriveURL( name, resourceType ) )
        .then( response -> {
          if ( !response.ok() )
          {
            throw new ResourceFetchErrorException( resourceType, name, response.status(), response.statusText() );
          }
          else
          {
            return response.text();
          }
        } );
  }

  @Nonnull
  private String deriveURL( @Nonnull final String name, @Nonnull final ResourceType resourceType )
  {
    return _baseURL + "/" + resourceType.getRelativeDirectory() + "/" + name + "." + resourceType.getExtension();
  }
}
