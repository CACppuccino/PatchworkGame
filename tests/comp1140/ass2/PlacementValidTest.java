package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static comp1140.ass2.TestUtility.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Task 6
 * <p>
 * Determine whether a placement is valid.  To be valid, the placement must be well-formed
 * and each tile placement must follow the game's placement rules.
 */
public class PlacementValidTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(5000);

    @Test
    public void testEmpty() {
        assertFalse("Null placement string is not OK, but passed", PatchworkGame.isPlacementValid(PATCH_CIRCLES[0], null));
        assertFalse("Empty placement string is not OK, but passed", PatchworkGame.isPlacementValid(PATCH_CIRCLES[0], ""));
        assertFalse("Null patch circle string is not OK, but passed", PatchworkGame.isPlacementValid(null, PLACEMENTS[0]));
        assertFalse("Empty patch circle string is not OK, but passed", PatchworkGame.isPlacementValid("", PLACEMENTS[0]));
    }

    @Test
    public void testGood() {
        Random r = new Random();
        for (int i = 0; i < PLACEMENTS.length; i++) {
            String c = PATCH_CIRCLES[i];
            String p = PLACEMENTS[i];
            assertTrue("Placement '" + p + "' is valid, but failed ", PatchworkGame.isPlacementValid(c, p));
            for (int j = 0; j < BASE_ITERATIONS / 4; j++) {
                if (p.charAt(p.length() - 1) == '.') p = p.substring(0, p.length() - 1);
                else p = p.substring(0, p.length() - 4);
                assertTrue("Placement '" + p + "' is valid, but failed ", PatchworkGame.isPlacementValid(c, p));
            }
        }
    }

    @Test
    public void testBad() {
        for (int i = 0; i < INVALID_PLACEMENTS.length; i++) {
            String c = PATCH_CIRCLES[i];
            String p = INVALID_PLACEMENTS[i];
            assertFalse("Placement '" + p + "' is invalid, but passed ", PatchworkGame.isPlacementValid(c, p));
        }
    }

}
