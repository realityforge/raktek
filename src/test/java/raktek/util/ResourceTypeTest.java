package raktek.util;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class ResourceTypeTest
{
  @Test
  public void basicOperation()
  {
    final ResourceType resourceType = new ResourceType( "vert", "shaders" );
    assertEquals( resourceType.getExtension(), "vert" );
    assertEquals( resourceType.getRelativeDirectory(), "shaders" );
    assertEquals( resourceType.toString(), "ResourceType[vert:shaders]" );
    assertEquals( resourceType.toDebugString(), "ResourceType[vert:shaders]" );
  }
}
