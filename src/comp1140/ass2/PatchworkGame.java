package comp1140.ass2;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.*;

/**
 * Represents the state of a Patchwork game in progress.
 */
public class PatchworkGame {


    public PatchworkGame(String patchCircleString) {
    }

    public PatchworkGame() {

    }
    public static final int [] specialButton = {5,11,17,23,29,35,41,47};
    public static final int [] specialTile = {20,26,32,44,50};
    public static final int [] tileCost = {2,1,3,2,3,2,1,0,6,
                                    4,2,1,3,7,3,7,3,
                                    2,4,5,2,5,10,5,10,
                                    1,4,7,10,1,2,7,8,0};

    public static final int [][] tileCover = {{2,1},{2,2},{2,2},{3,1},{3,2},{3,2},{3,5},
            {4,3},{2,2},{4,2},{3,2},{4,2},{4,1},{1,5},{4,2},{4,2},{3,3},{4,3},{3,2},{3,3},
            {3,3},{4,3},{5,2},{3,3},{4,2},{4,3},{3,2},{4,3},{3,3},{2,3},{4,2},{2,3},{3,3},{1,1}};
    //{row,col}
    public static final int [][][] tileSpace = {{{0,0},{1,0}},{{0,1},{1,0},{1,1}},{{0,1},{1,0},{1,1}},
            {{0,0},{1,0},{2,0}},{{0,1},{1,0},{1,1},{2,0}},{{0,0},{1,0},{2,0},{1,1},{2,1}},{{1,0},{1,1},{1,2},{1,3},{1,4},{0,2},{2,2}},
            {{0,1},{1,0},{1,1},{1,2},{2,1},{3,1}},{{0,0},{0,1},{1,0},{1,1}},{{0,0},{1,0},{2,0},{1,1},{2,1},
            {3,1}},{{1,0},{0,1},{1,1},{2,1}},{{0,0},{3,0},{0,1},{1,1},{2,1},{3,1}},{{0,0},{1,0},{2,0},{3,0}},
            {{0,0},{0,1},{0,2},{0,3},{0,4}},{{0,0},{1,0},{2,0},{3,0},{2,1}},{{0,0},{1,0},{2,0},{3,0},{2,1},{1,1}},
            {{1,0},{2,0},{0,1},{1,1},{1,2},{2,2}},{{2,0},{0,1},{1,1},{2,1},{3,1},{1,2}},{{2,0},{0,1},{1,1},{2,1}},
            {{1,0},{0,1},{1,1},{2,1},{1,2}},{{0,0},{1,0},{2,0},{1,1},{0,2},{1,2},{2,2}},{{1,0},{2,0},{0,1},{1,1},{2,1},{3,1},{1,2},{2,2}},
            {{3,0},{0,1},{1,1},{2,1},{3,1}},{{2,0},{0,1},{1,1},{2,1},{2,2}},{{0,0},{1,0},{0,1},{1,1},{2,1},{3,1}},
            {{0,0},{0,1},{1,1},{2,1},{3,1},{3,2}},{{0,0},{1,0},{2,0},{2,1}},{{3,0},{0,1},{1,1},{2,1},{3,1},{3,2}},
            {{0,0},{1,0},{1,1},{2,1},{2,2}},{{0,0},{1,0},{1,1},{0,2},{1,2}},{{2,0},{3,0},{0,1},{1,1},{2,1}},
            {{1,0},{0,1},{1,1},{0,2}},{{2,0},{0,1},{1,1},{2,1},{0,2},{1,2}},{{0,0}}};

    public static final int [] tileTimetoken = {1,3,1,2,2,2,4,3,5,
                                         2,2,5,3,1,4,4,6,
                                         1,6,4,3,3,3,5,5,
                                         2,2,2,4,2,3,6,6,0};

    public static final int [ ] tileButton = {0,0,0,0,1,0,1,1,2,0,
                                              0,1,1,1,1,2,2,0,2,
                                              2,0,1,2,2,3,0,1,
                                              2,3,0,1,3,3,0};

    static int fals;
    /**
     * Determine whether a patch placement is well-formed according to the following:
     * - either it is the single character string ".", or
     * - it consists of exactly four characters:
     * - the first character is in the range A .. Z or a .. h
     * - the second character is in the range A .. I
     * - the third character is in the range A .. I
     * - the fourth character is in the range A .. H
     *
     * @param placement A string describing a patch placement
     * @return True if the tile placement is well-formed
     */
    static boolean isPatchPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a patch placement is well-formed
        String [] allchars = new String[4];
        allchars[0] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgh";
        allchars[1] = "ABCDEFGHI";
        allchars[2] = "ABCDEFGHI";
        allchars[3] = "ABCDEFGH";
        int count = 0;
        boolean result = false;
        if ( placement.charAt(0) == '.')
        {
            result = true;
        }
        else if (placement.length() != 4)
        {
            result = false;
        }
        else
        {
            for (int i = 0; i < placement.length(); i++) {
                for (int j = 0; j < allchars[i].length(); j++) {
                    if (placement.charAt(i) == allchars[i].charAt(j)) {
                        count = count + 1;
                    }
                }
            }
        }

        if ( count == 4){
            result = true;
        }
        return result;
    }

    /**
     * Determine whether a game placement string is well-formed:
     * - it consists of a sequence of patch placement strings, where
     * - each patch placement is well-formed, either as a single-character advance string "."
     * or a four-character patch tile placement
     * - no patch appears more than once in the placement, except the special tile 'h'
     *
     * @param placement A string describing a placement of one or more tiles
     * @return true if the placement is well-formed
     */
    static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 4: determine whether a placement is well-formed`
        //initialize a List<Integer> to store all the position of the patch
        List<Integer> patchposition = new ArrayList();
        // to count the time of the String placement when it has more tiles
        int count = 0;
        // the count1 is to count how many times the '.' show
        int count1 = 0;
        boolean result = false;
        String SubSting;
        if (placement == null || placement.isEmpty())
            return false;
        else {
            for (int i = 0; i < placement.length(); i++) {
                if (placement.charAt(i) == '.') {
                    count = count + 1;
                    count1 = count1 + 1;
                } else {
                    if ( placement.length() - i < 4)
                    {
                        SubSting = placement.substring(i, placement.length());
                    }
                    else
                    {
                        SubSting = placement.substring(i, i + 4);
                    }
                    patchposition.add(i);
                    result = isPatchPlacementWellFormed(SubSting);
                    if (result) {
                        count = count + 1;
                    }
                    else
                    {
                        result = false;
                        return result;
                    }
                    i = i + 3;
                }
            }
        }
        // check whether there is a patch appear more than once
        for ( int i = 0; i < patchposition.size();i++){
            for ( int j = i + 1;j < patchposition.size();j++){
                if ( placement.charAt(patchposition.get(i)) == placement.charAt(patchposition.get(j)) && placement.charAt(patchposition.get(i)) != 'h'){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determine whether a placement is valid.  To be valid, the placement must be well-formed
     * and each tile placement must follow the game's placement rules.
     *
     * @param patchCircle a patch circle string
     * @param placement   A placement string
     * @return true if the placement is valid
     */

    // Here has some problems for test good
    /*need to check:
    1. whether is overlapped
    2. whether is out of bound
    3. not in scope
    4. not in patchCircle
    * */
    static boolean isPlacementValid(String patchCircle, String placement) {
        // FIXME Task 6: determine whether a placement is valid
        System.out.println("&&&:"+placement);
        System.out.println("###:"+patchCircle);
        if (!PatchworkGame.isPlacementWellFormed(placement))   {
            System.out.println("isPlacementWellFormed");return false;}
        int aPlc = 0;

        LinkedList<Character> partches = new LinkedList<>();
        if (patchCircle==null || patchCircle.isEmpty()) {
            System.out.println("circle null");return false;}
        fals++;
        for (int i=0;i<patchCircle.length();i++){
            if (patchCircle.charAt(i)=='A')
                aPlc=i+1;
            partches.add(i,patchCircle.charAt(i));
        }
        boolean fstPlayer = true;
        String fstPlc = new String(),secPlc = new String();
        State p1 = new State(1), p2 = new State(2);
        p1.onTop=true;
        for (int i=0;i<placement.length();i++){
            if (placement.charAt(i)=='.'){
                fstPlayer = !fstPlayer;
                State.advanced(p1,p2);
            }
            else {
                String plc = placement.substring(i, i + 4);
                System.out.println("%%%%"+plc);
                if (State.check_turn(p1,p2)==1) fstPlayer = true;
                else fstPlayer = false;
                if (fstPlayer) {
                    fstPlc = fstPlc + plc;
                    if ( !outOfBoard(plc, p1)) {
                        System.out.println("out of Board/Overlap1"  );
                        return false;
                    }
                } else {
                    secPlc = secPlc + plc;
                    if ( !outOfBoard(plc, p2)) {
                        System.out.println("out of Board/Overlap2"  );
                        return false;
                    }
                }
                try {
                    State.buyPartches(p1, p2, plc.charAt(0));
                } catch (Error e) {
                    if (e.getMessage().equals("cant afford the tile" + plc.charAt(0))) {
                        System.out.println("buyparches error");
                        System.out.println(p1.buttonCount + " " + p2.buttonCount);
                        return false;
                    } else if (e.getMessage().equals("no enough h")) {
                        System.out.println("no enough h");
                        System.out.println(p1.specialH+" "+p2.specialH);
                        return false;
                    }
                }


                char[] three = {partches.get(aPlc%partches.size()), partches.get((aPlc + 1) % partches.size()), partches.get((aPlc + 2) % partches.size())};
                //move the nertral token
                //if the parches left in the partches circle doesn't contain the current
                //wanted partches,then is invalid
                if (plc.charAt(0) != 'h') {
                    if (!partches.contains(plc.charAt(0))) {
                        System.out.println("not cotains anymore" + plc.charAt(0));
                        return false;
                    }


                    if (!(plc.charAt(0) == three[0] || plc.charAt(0) == three[1] || plc.charAt(0) == three[2])) {
                        System.out.println("three error:" + plc.charAt(0) + " " + patchCircle+" "+three[0]+" "+three[1]+" "+three[2]);
                        for (int j=0;j<partches.size();j++)
                            System.out.println(partches.get(j));
                        return false;
                    } else if (plc.charAt(0) == three[0]) {
                        partches.remove(aPlc);

                    } else if (plc.charAt(0) == three[1]) {
                        partches.remove((aPlc + 1)%partches.size());
                        aPlc++;
                    } else {
                        partches.remove((aPlc + 2)%partches.size());
                        aPlc += 2;
                    }

                    //if token goes to the end, make it back to the beginning
                    aPlc = aPlc > partches.size()? aPlc %(partches.size()+1):aPlc%partches.size();//aPlc >= partches.size()? (aPlc % partches.size())-1:aPlc;
                    System.out.println("aPlc" + aPlc+" "+partches.size());
                }

                i = i + 3;

            }
        }
        return true;

    }

//    public static boolean checkOverlap(String placement){
//
//    }

    // this block is to get the tile's cost and token
    public static int[] getDetails(char tile){
        String tiles = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgh";
        int tilePosition = 0;
        for (int i = 0; i < tiles.length();i++){
            if ( tile == tiles.charAt(i)){
                tilePosition = i;
                break;
            }
        }
        PatchworkGame a = new PatchworkGame();
        int timeToken = a.tileTimetoken[tilePosition];
        int tileCost = a.tileCost[tilePosition];
        int [] details  =  {timeToken,tileCost};
        return details;
    }

    private static int[][] flipHandle(char tile,int[][] expose){
        int index = getIndex(tile);

        if (tileSpace[index].length==tileCover[index][0]*tileCover[index][1])
            return expose;
        else if (index==6 || index==7 || index==8 || index==16 || index==19 || index ==20 || index == 21 ||index==23 ||index==27||index==29)
            return expose;

        int[][] copy = new int[tileSpace[index].length][2] ;


        //to make it rotation sensitive,need to add 1 to avoid zero circumenstance
        for (int i=0;i<tileSpace[index].length;i++)
        {
            copy[i][0] = tileSpace[index][i][0]+1;
            copy[i][1] = tileSpace[index][i][1]+1;
        }


        System.out.println("copy:"+index+" "+Arrays.deepToString(copy));
        //flip on tiles that width is 2
        if (tileCover[index][1]==2)
            for (int[] xs:copy)
                xs[1] = (xs[1]==1?2:1);
        //flip on tiles that width is 3
        else
            for (int[] xs:copy)
                xs[1] = (xs[1]==1?3:1);
    return copy;
    }
    private static int getIndex(char tile){
        int index;
        if (tile>='A'&& tile<='Z')
            index = tile - 'A';
        else
            index = tile-'a'+26;
        return index;
    }
    private static int[][] rotateHandle(char rotate,int[][] expose){
        if (rotate=='A')
            return expose;
        else if (rotate=='B'){
            for (int[] xs:expose){
                int tmp = xs[0];
                xs[0] = xs[1] ;
                xs[1] = -tmp;
            }
        }
        else if (rotate=='C'){
            for (int[] xs:expose){
                xs[0] = -xs[0] ;
                xs[1] = -xs[1];
            }
        }
        else if (rotate=='D'){
            for (int[] xs:expose){
                int tmp = xs[0];
                xs[0] = -xs[1] ;
                xs[1] = tmp;
            }
        }
        else {
            System.out.println("rotation wrong"+rotate);
            return null;
        }
        int[] topleft =new int[2];
        topleft[0] = expose[0][0];
        topleft[1] = expose[0][1];
        for (int[] xs:expose){
            if (topleft[0]>xs[0]) topleft[0] = xs[0];
            if (topleft[1]>xs[1]) topleft[1] = xs[1];
        }
        topleft[0] = 1-topleft[0];
        topleft[1] = 1-topleft[1];
        for (int[] xs:expose){
            xs[0] += topleft[0];
            xs[1] += topleft[1];
        }
        return expose;
    }

    private static boolean isValidOnePlacement(String placement){
        if (placement.length()!=4) {
//         throw new Error("invalid length"+placement.length());
            return false;
        }
        char tile = placement.charAt(0),row = placement.charAt(2),col = placement.charAt(1),
                rotate = placement.charAt(3);
        if (!((tile>='A'&& tile<='Z') || (tile>='a' && tile<='h')))
//            throw new Error("tile wrong "+tile);
             return false;
        else if (!(row>='A'&& row<='I'&& col>='A' && col<='I'))
//            throw new Error("row col wrong"+row+" "+col);
            return false;
        else if (!(rotate>='A'&&rotate<='H'))
//            throw new Error("rotate "+rotate+" "+placement);
            return false;
        return true;
    }
    private static int[][] getTileSpace(int index){return tileSpace[index];}
    //false for out of the board, true for in
    public static boolean outOfBoard(String placement,State player){
        if (!isValidOnePlacement(placement)){
            System.out.println("invalid one placement");
            return false;}

        char row = placement.charAt(2),col = placement.charAt(1);
        int rowN = row - 'A',colN = col -'A' ;
        char tile = placement.charAt(0);
        char rotate = placement.charAt(3);
        int index;
        int[][] expo ,result;
        //get the relevant position and
        index = getIndex(tile);
        expo =  new int[tileSpace[index].length][2];
        for (int i=0;i<tileSpace[index].length;i++)
        {
            expo[i][0] = tileSpace[index][i][0]+1;
            expo[i][1] = tileSpace[index][i][1]+1;
        }

        System.out.println("expo: "+Arrays.deepToString(expo));

        if (rotate >= 'A' && rotate <= 'D') {
            result=rotateHandle(rotate,expo);
        }
        else{
            rotate = (char) ('A' + rotate - 'E');
            result = rotateHandle(rotate, flipHandle(tile, expo));
//            System.out.println("rotate result: "+ Arrays.deepToString(result));
        }
        for (int[] xs:result) {
//            System.out.println("result before: "+rotate+" "+Arrays.deepToString(result));
            xs[0] += rowN;
            xs[1] += colN;
//            System.out.println("result after: "+Arrays.deepToString(result));
        }
                    System.out.println("result after: "+Arrays.deepToString(result));
        for (int[] xs:result){
            if (!(xs[0]>=1 && xs[0]<=9 && xs[1]>=1 && xs[1]<=9))
            {
                for (int[] sxs:result){
                    System.out.println(sxs[0]+" "+sxs[1]);
                }
                System.out.println("out of board"+placement+" "+xs[0]+" "+xs[1]+"//");
                return false;
            }
            System.out.println("id:"+player.id);
            System.out.println();
            if (!player.squiltBoard[xs[0]-1][xs[1]-1])
                player.squiltBoard[xs[0]-1][xs[1]-1] = true;
            else {
                player.printSquiltBoard();
//                fals++;
            if (fals<=7)
                return false;
            }

        }
        player.printSquiltBoard();

        return true;
    }
//    public static int checkTurn(char [] player1,char [] player2){
//        int timeToken1 = 0;
//        int timeToken2 = 0;
//        for ( int i = 0;i < player1.length;i++){
//            timeToken1 = getDetails(player1[i])[1];
//        }
//        for ( int i = 0;i < player2.length;i++){
//            timeToken1 = getDetails(player2[i])[1];
//        }
//        if ( timeToken1 > timeToken2){
//            return 0;
//        }
//        else{
//            return 1;
//        }
//    }

    /**
     * Determine the score for a player given a placement, following the
     * scoring rules for the game.
     *
     * @param placement   A placement string
     * @param firstPlayer True if the score for the first player is requested,
     *                    otherwise the score for the second player should be returned
     * @return the score for the requested player, given the placement
     */
    static int getScoreForPlacement(String patchCircle, String placement, boolean firstPlayer) {
        // FIXME Task 7: determine the score for a player given a placement
        boolean ss = isPlacementValid(patchCircle,placement);

        return 0;
    }

}
