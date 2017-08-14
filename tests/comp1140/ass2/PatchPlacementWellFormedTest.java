package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static comp1140.ass2.TestUtility.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Task 3
 * <p>
 * Determine whether a patch placement is well-formed according to the following:
 * - it consists of exactly four characters
 * - the first character is in the range A .. Z or a .. h
 * - the second character is in the range A .. I
 * - the third character is in the range A .. I
 * - the fourth character is in the range A .. H
 */
public class PatchPlacementWellFormedTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testSimple() {
        int i = 0;
        while (i < PLACEMENTS[0].length()) {
            String placement;
            if (PLACEMENTS[0].substring(i, i + 1).equals(".")) {
                placement = PLACEMENTS[0].substring(i, i + 1);
                i += 1;
            } else {
                placement = PLACEMENTS[0].substring(i, i + 4);
                i += 4;
            }
            assertTrue("Simple tile placement string '" + placement + "', should be OK, but was not", PatchworkGame.isPatchPlacementWellFormed(placement));
        }
    }

    @Test
    public void testCase() {
        int i = 0;
        while (i < PLACEMENTS[0].length()) {
            String placement;
            if (PLACEMENTS[0].substring(i, i + 1).equals(".")) {
                placement = PLACEMENTS[0].substring(i, i + 1);
                i += 1;
            } else {
                placement = PLACEMENTS[0].substring(i, i + 4);
                i += 4;
                String test = placement.toLowerCase();
                assertFalse("Simple tile placement string '" + test + "', is lower case, but passed", PatchworkGame.isPatchPlacementWellFormed(test));
            }
        }
    }

    @Test
    public void testGood() {
        Random r = new Random();
        for (int i = 0; i < BASE_ITERATIONS; i++) {
            char a = (char) ('A' + r.nextInt(TILES));
            if (a > 'Z') a += 'a' - 'Z';
            char b = (char) ('A' + r.nextInt(COLS));
            char c = (char) ('A' + r.nextInt(ROWS));
            char d = (char) ('A' + r.nextInt(ORIENTATIONS));
            String test = "" + a + b + c + d;
            assertTrue("Well-formed piece placement string '" + test + "' failed", PatchworkGame.isPatchPlacementWellFormed(test));
        }
    }

    @Test
    public void testBad() {
        Random r = new Random();
        for (int i = 0; i < BASE_ITERATIONS; i++) {
            String test = TestUtility.badlyFormedTilePlacement(r);
            assertFalse("Badly-formed piece placement string '" + test + "' passed", PatchworkGame.isPatchPlacementWellFormed(test));
        }
    }
}