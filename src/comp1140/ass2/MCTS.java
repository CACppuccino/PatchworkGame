//package comp1140.ass2;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//
//public class MCTS {
//
//    static Random r = new Random();
//
//    MCTS[] children;
//    static String circle;
//    static String placement = ".EAAA.dADA...JAFA...DBCB...OBED.ZCFBhAAA.....fCHAhAIA.MDBBhBDA.HFGB.aDDH......hCAA......hCBA";
//    static char tile = 'F';
//    double visits, value;
//    State player = PatchworkGame.p2;
//
//
//    public void selectAction() {
//        List<MCTS> visited = new ArrayList<MCTS>();
//        MCTS cur = this;
//        visited.add(this);
//        while (cur.children != null) {
//            cur = cur.select();
//        }
//    }
//
//    public static MCTS select() {
//        int n = r.nextInt(648);
//        String p = "" + tile + (char)('A' + n / 72) + (char)('A' + n / 8 % 9) + (char)('A' + n % 8);
//        while (!PatchworkGame.isPlacementValid(circle,placement+p)){
//            n=r.nextInt(648);
//            p = "" + tile + (char)('A' + n / 72) + (char)('A' + n / 8 % 9) + (char)('A' + n % 8);
//        }
//        return null;
//
//    }
//}
