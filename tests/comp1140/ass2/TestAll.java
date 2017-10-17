package comp1140.ass2;

import org.junit.Test;

public class TestAll {
    public static void main(String[] args) {

    }
    @Test
    public void testAll(){
        PlacementValidTest t1 = new PlacementValidTest();
        t1.testBad();
        t1.testEmpty();
        t1.testGood();
        ScoreFromPlacementTest t2 = new ScoreFromPlacementTest();
        t2.testScore();
        GeneratePatchPlacementTest t3 = new GeneratePatchPlacementTest();
        t3.testMove();
    }
}
