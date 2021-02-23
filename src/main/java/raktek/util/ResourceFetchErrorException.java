package raktek.util;

import java.util.Objects;
import javax.annotation.Nonnull;

public class ResourceFetchErrorException
  extends RuntimeException
{
  @Nonnull
  private final ResourceType _resourceType;
  @Nonnull
  private final String _name;
  /**
   * Error code when fetching the resource.
   * The code is dependent on the underlying resource directory implementation but is
   * typically the status code of the http request.
   */
  private final int _errorCode;
  /**
   * Error message when fetching the resource.
   * The message format is dependent on the underlying resource directory implementation but is
   * typically the status message of the http request.
   */
  @Nonnull
  private final String _errorMessage;

  public ResourceFetchErrorException( @Nonnull final ResourceType resourceType,
                                      @Nonnull final String name,
                                      final int errorCode,
                                      @Nonnull final String errorMessage )
  {
    _resourceType = Objects.requireNonNull( resourceType );
    _name = Objects.requireNonNull( name );
    _errorCode = errorCode;
    _errorMessage = Objects.requireNonNull( errorMessage );
  }

  @Nonnull
  public ResourceType getResourceType()
  {
    return _resourceType;
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  public int getErrorCode()
  {
    return _errorCode;
  }

  @Nonnull
  public String getErrorMessage()
  {
    return _errorMessage;
  }
}
