package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static comp1140.ass2.TestUtility.BASE_ITERATIONS;
import static comp1140.ass2.TestUtility.PLACEMENTS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Task 4
 * <p>
 * Determine whether a game placement string is well-formed:
 * - it consists of a sequence of patch placement strings, where
 * - each patch placement is well-formed, either as a single-character advance string "."
 * or a four-character patch tile placement
 * - no patch appears more than once in the placement, except the special tile 'h'
 */
public class PlacementWellFormedTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testEmpty() {
        assertFalse("Null placement string is not OK, but passed", PatchworkGame.isPlacementWellFormed(null));
        assertFalse("Empty placement string is not OK, but passed", PatchworkGame.isPlacementWellFormed(""));
    }

    @Test
    public void testIncomplete() {
        String incomplete = PLACEMENTS[0].substring(0, PLACEMENTS[0].length() - 3);
        assertFalse("Placement string '" + incomplete + "'was incomplete, but passed", PatchworkGame.isPlacementWellFormed(incomplete));
    }

    @Test
    public void testBadlyFormedPlacement() {
        Random r = new Random();
        for (int j = 0; j < BASE_ITERATIONS; j++) {
            String test = "MMUA" + TestUtility.badlyFormedTilePlacement(r);
            assertFalse("Placement string '" + test + "' contains a badly formed tile, but passed", PatchworkGame.isPlacementWellFormed(test));
        }
    }

    @Test
    public void testGood() {
        Random r = new Random();

        for (int i = 0; i < PLACEMENTS.length; i++) {
            String p = PLACEMENTS[i];
            for (int j = 0; j < BASE_ITERATIONS / 4; j++) {
                if (p.charAt(p.length() - 1) == '.') p = p.substring(0, p.length() - 1);
                else p = p.substring(0, p.length() - 4);
                assertTrue("Placement '" + p + "' is valid, but failed ", PatchworkGame.isPlacementWellFormed(p));
            }
        }
    }

    @Test
    public void testReplication() {
        for (int i = 0; i < PLACEMENTS.length; i++) {
            String p = PLACEMENTS[i];
            int s = 0;
            while (p.charAt(s) == '.') s++;
            s += 4;
            while (p.charAt(s) == '.') s++;
            String d = p.substring(s, s+1);
            int n=s+4;
            while (p.charAt(n) == '.') n++;
            String test = p.substring(0, n) + d + p.substring(n+1);
            System.out.println(test);
            assertFalse("Placement string '" + test + "' contains has piece " + d + " more than twice, but passed", PatchworkGame.isPlacementWellFormed(test));
        }
    }
}
