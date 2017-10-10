package comp1140.ass2;

import org.junit.Test;

import static comp1140.ass2.TestUtility.PATCH_CIRCLES;
import static comp1140.ass2.TestUtility.PLACEMENTS;
import static comp1140.ass2.TestUtility.SCORES;
import static comp1140.ass2.TestUtility.SEVEN_BY_SEVEN;
import static org.junit.Assert.assertTrue;

/**
 * Task 7
 * Determine the score for a player given a patch circle and placement, following the
 * scoring rules for the game.
 */
public class ScoreFromPlacementTest {

    @Test(timeout=10000)
    public void testScore() {
        int score = -1;
        int ref = -1;
        for (int i = 0; i < PLACEMENTS.length; i++) {
            score = PatchworkGame.getScoreForPlacement(PATCH_CIRCLES[i], PLACEMENTS[i], true);
            ref = SCORES[i*2];
            if (SEVEN_BY_SEVEN[i*2]) {
                assertTrue("Incorrect score for '" + PLACEMENTS[i] + "', expected " + ref + "or " + (ref+7) + ",  but got " + score, score == ref || score == ref + 7);
            } else {
                assertTrue("Incorrect score for '" + PLACEMENTS[i] + "', expected " + ref + ", but got " + score, score == ref);
            }
            score = PatchworkGame.getScoreForPlacement(PATCH_CIRCLES[i], PLACEMENTS[i], false);
            ref = SCORES[1+(i*2)];
            if (SEVEN_BY_SEVEN[1+(i*2)]) {
                assertTrue("Incorrect score for '" + PLACEMENTS[i] + "', expected " + ref + "or " + (ref+7) + ", but got " + score, score == ref || score == ref + 7);
            } else {
                assertTrue("Incorrect score for '" + PLACEMENTS[i] + "', expected " + ref + ", but got " + score, score == ref);
            }
        }
    }
}
