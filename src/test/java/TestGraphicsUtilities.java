import static org.assertj.core.api.Assertions.assertThat;

import engines.graphics.GraphicalObject;
import engines.graphics.GraphicsUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * A template class for testing with assertJ.
 */
class TestGraphicsUtilities {

  @Test
  void testAddToAssetsPath() {
    GraphicsUtilities g = new GraphicsUtilities();
    g.assetsPaths = new HashMap<>();
    HashMap<String, String> assets = new HashMap<>();
    assets.put("testImages1","test/test/test/test/test.test");
    g.addToAssetsPath("testImages1", "test/test/test/test/test.test", g.assetsPaths);
    assets.put("testImages2","test/test/test/test/test.test");
    g.addToAssetsPath("testImages2", "test/test/test/test/test.test", g.assetsPaths);
    assets.put("testImages3","test/test/test/test/test.test");
    g.addToAssetsPath("testImages3", "test/test/test/test/test.test", g.assetsPaths);

    Assertions.assertEquals(g.assetsPaths, assets);
  }

  @Test
  void testCreateGraphicalObject() throws IOException {
    GraphicsUtilities g = new GraphicsUtilities();
    GraphicalObject gObjt = g.createGraphicalObject("ubuntu","src/main/resources/sample/image.png");
    Assertions.assertEquals (gObjt.getName(),"ubuntu");
  }


}
