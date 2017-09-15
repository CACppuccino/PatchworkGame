package comp1140.ass2;

import org.junit.Test;

import java.util.Random;

import static comp1140.ass2.TestUtility.BASE_ITERATIONS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Task 10
 * <p>
 * Generate a valid move that follows from: the given placement, a piece to
 * play, and the piece the opponent will play next.
 */
public class GeneratePatchPlacementTest {

    @Test(timeout = 10000)
    public void testMove() {

        /* first ensure that the game correctly identifies broken placements */
        Random r = new Random();
        for (int j = 0; j < BASE_ITERATIONS; j++) {
            String test = TestUtility.badlyFormedTilePlacement(r);
            assertFalse("Placement string '" + test + "' contains a badly formed tile, but passed", PatchworkGame.isPlacementWellFormed(test));
        }

        /* now run a series of tests */
        for (int j = 0; j < BASE_ITERATIONS / 10; j++) {

            String patchCircle = generateRandomPatchCircleString();
            String game = "";

            String move;
            for (int i = 0; i < 20; i++) {
                move = PatchworkAI.generatePatchPlacement(patchCircle, game);
                checkMove(patchCircle, game, move);
                game += move;
                System.out.println("game " + game);
                move = PatchworkAI.generatePatchPlacement(patchCircle, game);
                System.out.println("move " + move);
                checkMove(patchCircle, game, move);
                game += move;
            }
        }
    }

    static String generateRandomPatchCircleString() {
        String patchCircleString = "";
        Random rand = new Random();
        int numNormalPatches = 33; // don't include special tile
        for (int i = 0; i < numNormalPatches; i++) {
            int patchIndex = rand.nextInt(numNormalPatches);
            char patchId;
            if (patchIndex >= 26) patchId = (char)('a' + (patchIndex - 26));
            else patchId = (char)('A' + patchIndex);
            while (patchCircleString.indexOf(patchId) > -1) {
                patchIndex = (patchIndex + 1) % numNormalPatches;
                if (patchIndex >= 26) patchId = (char) ('a' + (patchIndex - 26));
                else patchId = (char) ('A' + patchIndex);
            }
            patchCircleString += patchId;
        }
        return patchCircleString;
    }

    void checkMove(String patchCircle, String start, String move) {
        assertFalse("Null move returned", move == null);
        //assertTrue("Move '"+move+"' does not correctly use piece '"+piece, move.charAt(2) == piece);
        assertTrue("Invalid move '" + move + "', given starting point '" + start, PatchworkGame.isPlacementValid(patchCircle, start + move));
    }
}
