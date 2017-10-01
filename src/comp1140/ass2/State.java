package comp1140.ass2;

import java.util.ArrayList;


import java.util.*;

public class State {
    final static int GRIDS = 53;
    final int id;
    List<Character> tiles = new ArrayList<Character>();
    //    calculating the squares left in two quilt boards.
    int squareleft = 81;

    //    calculating the time left in the time board of two players
    int timecount = 0;
    // for counting each player's score

    //when the game finshed, the score should be scoreCount = scoreCount + buttonCount - squareleft*2
    int scoreCount = 0;
    //    calculating the button of each player holds
    int buttonCount = 5;

    // how many buttons should a player be rewarded each special event of button event
    int specialButton = 0;
    //    showing two players' states on the time board,
// 1 indicates moving first, 0 indicates moving later,
// 2 indicates the player has reached the final point.
//    int tbState = 0;

    //    indicating the place of the neutral token, if ntState is i,
// then the patch i,i+1,i+2 should be displayed in the candidate area.
//    int ntState = 0;

    //for the special tile event
    static boolean spt = false;
    //for special h to be paied out
    boolean specialH = false;

    //showing whether the player is on the top
    boolean onTop = false;

    final static char[] PATCHES = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
            'X','Y','Z','a','b','c','d','e','f','g'};

    State(int id){
        this.id = id;
        for (boolean[] sq: squiltBoard)
            for (boolean sqq: sq)
                sqq = false;
    }

    //    this list should be changed when being initialised randomly by the initialization()/
// once the player buys an patch/ choose undo after placed a patch
    ArrayList<Character> currentPatch = new ArrayList<Character>();
    boolean[][] squiltBoard = new boolean[9][9];

    //    this function is used for recognizing the end of the game.
// Both of the players' time consumption is full,
// i.e. players' time token have reached the end square of the time board.
    public boolean check_fullstate(){
        return this.timecount==GRIDS;}
//for copying the state instance
    public void copy(State target){
        squareleft = target.squareleft;
        timecount = target.timecount;
        scoreCount = target.scoreCount;
        buttonCount = target.buttonCount;
        specialButton = target.specialButton;
        specialH = target.specialH;
        onTop = target.onTop;
        for (int i = 0;i < 9; i++){
            for (int j = 0;j < 9;j++){
                this.squiltBoard[i][j] = target.squiltBoard[i][j];
            }
        }
    }
    //    this function is used for checking the who should be the person doing the next turn.
//    Two situation should be considered: if they are not in the same square of the time board,
// the one fall behind should take the turn. If they are in the same square, the player who
// last moved to that space takes the turn.
    public static int check_turn(State p1,State p2){
        if (p1.specialH)    return 1;
        else if (p2.specialH) return 2;
        if (p1.timecount>p2.timecount)
            return 2;
        else if (p1.timecount<p2.timecount)
            return 1;
        else {
            if (p1.onTop && p2.onTop)   throw new Error("both player token on the top");
            else if (p2.onTop)  return 2;
            else    return 1;
        }
    }

//    void printSquiltBoard(){
//        for (boolean[] sq: squiltBoard){
//            for (boolean sqq: sq) {
//                if(!sqq) System.out.print("@");
//                else System.out.print("*");
//            }
//            System.out.println();
//        }
//    }
    public static void advanced(State p1,State p2){
        int t =check_turn(p1,p2);
        int diff ;

        if (t==1) {
            //be infront of the p2
            diff = p2.timecount+1<=GRIDS?(p2.timecount-p1.timecount)+1:GRIDS-p1.timecount;
            p1.buttonCount += diff;
            State.specialEvent(p1,p2,p1.timecount,diff);
            p1.timecount = p2.timecount + 1 > GRIDS ? GRIDS : p2.timecount + 1;
//            System.out.println("player "+t+"make advance "+diff+"steps and now in "+p1.timecount);
        }
        else {
            diff = p1.timecount+1<=GRIDS?(p1.timecount-p2.timecount)+1:GRIDS-p2.timecount;

            p2.buttonCount += diff;
            State.specialEvent(p2,p1,p2.timecount,diff);
            p2.timecount = p1.timecount + 1 > GRIDS ? GRIDS : p1.timecount + 1;
//            System.out.println("player "+t+"make advance "+diff+"steps and now in "+p2.timecount);
        }
    }

    public static void buyPartches(State p1,State p2,char p){
        int index;
        if (p>='A' && p<='Z') index = p-'A';
        else    index = p-'a'+26;
        //get the time decrement from the array
        final int timeInc = PatchworkGame.tileTimetoken[index];
        final int buttonDec = PatchworkGame.tileCost[index];
        final int specialButton= PatchworkGame.tileButton[index];

        int t = check_turn(p1,p2);
//        System.out.println("player "+t+" buying "+p+" hosts"+(t==1?p1.buttonCount:p2.buttonCount)+"and cost him"+buttonDec);
        if (t==1) {
            if (p=='h'){
                if (p1.tiles.size()>0)
                {
                    p1.tiles.remove(p1.tiles.size()-1);
                    p1.specialH = false;
                    p1.squareleft --;
                    throw new Error("spend a h");
                }
                else {
                    throw new Error("no enough h");
                }
            }
            p1.buttonCount = p1.buttonCount - buttonDec;
            if (p1.buttonCount<0){
                p1.buttonCount+=buttonDec;
                //suppose to notice the Viewer
                throw new Error("cant afford the tile"+p);}
            p1.specialButton += specialButton;
            specialEvent(p1,p2,p1.timecount,timeInc);
            p1.timecount = p1.timecount+timeInc>GRIDS ? GRIDS : p1.timecount+timeInc;
            p1.squareleft -= PatchworkGame.tileSpace[index].length;
//            System.out.println("now get special Button "+p1.specialButton+" now get button"+p1.buttonCount);
//            System.out.println("make forward "+timeInc+" now stepping "+p1.timecount);
//            System.out.println("now have "+p1.squareleft+" squares left");
            //considering the timetoken overlap situation
            if (p1.timecount==p2.timecount)
            {
                p1.onTop = true;
                p2.onTop = false;
            }

        }
        else {
            if (p=='h'){
                if (p2.tiles.size()>0){
                    p2.tiles.remove(p2.tiles.size()-1);
                    p2.specialH = false;
                    p2.squareleft--;
                    throw new Error("spend a h");
                }
                else {
                    throw new Error("no enough h");
                }
            }
            p2.buttonCount = p2.buttonCount - buttonDec;
            if (p2.buttonCount<0){
                p2.buttonCount+=buttonDec;
                //suppose to notice the Viewer
                throw new Error("cant afford the tile"+p);}
            p2.specialButton += specialButton;
            specialEvent(p2,p1,p2.timecount,timeInc);
            p2.timecount = p2.timecount+timeInc>GRIDS ? GRIDS : p2.timecount+timeInc;
            p2.squareleft -= PatchworkGame.tileSpace[index].length;
//            System.out.println("now get special Button "+p2.specialButton+" now get button"+p2.buttonCount);
//            System.out.println("make forward "+timeInc+" now stepping "+p2.timecount);
//            System.out.println("now have "+p2.squareleft+" squares left");

            if (p1.timecount==p2.timecount)
            {
                p2.onTop = true;
                p1.onTop = false;
            }
            if (isSeven(p1)) specialTile(p1);
            else if (isSeven(p2)) specialTile(p2);
        }

    }

    char [][] printPlayerBoard(){
        int counter1 = 0;
        char [][] playerBoardPrint = new char[9][9];

        for(boolean [] sq1 : squiltBoard){
            int counter2 = 0;
            for ( boolean sqq1:sq1){
                if(!sqq1) {
                    playerBoardPrint[counter1][counter2] = '@';
                    counter2++;
                }
                else {
                    playerBoardPrint[counter1][counter2] = '*';
                    counter2++;
                }
            }
            counter1++;
        }
        return playerBoardPrint;
    }

    public static boolean isSeven(State player) {
        // check whether the player's board is 7 * 7 full
        //special tile is already owned by one of the player
        if (spt) return false;
        //haven't filled 49 squares even
        if (player.squareleft>32) return false;

        // this block is to check player's board is full 7 * 7
//        player.printSquiltBoard();
        int positionMark = 100;
        int counterCol = 0;
        for ( int i = 0; i < player.printPlayerBoard().length;i++){
            int counterRow = 0;
            for (int j = (positionMark != 100)? positionMark:0; j < player.printPlayerBoard()[i].length;j++){
                if(player.printPlayerBoard()[i][j] == '*'){
                    if ( positionMark > j) positionMark = j;
                    counterRow++;
                }
            }
            counterCol++;
            if (counterRow < 7 && counterCol < 2) positionMark = 100;
            else if (counterRow < 7 && counterCol > 2) return false;
        }
        return true;
    }

    /*
    * The function is only called when the signal is True
    */
    static boolean affordPartch(int buttonCount, int cost){
        if (buttonCount - cost< 0) return false;
        return true;
    }


    public static void specialTile(State player){
        player.scoreCount +=7;
        //this event should only be touched once
        spt = true;
    }
    public static void specialEvent(State player,State oplyaer,int start,int steps){
        for (int sb:PatchworkGame.specialButton){
            if (sb>=start+1 && sb<=start+steps)
                player.buttonCount += player.specialButton;
        }
        for (int i=0;i<PatchworkGame.specialTile.length;i++)
        {
            if (PatchworkGame.specialTile[i]>=start+1 && PatchworkGame.specialTile[i]<=start+steps
                    && oplyaer.timecount<PatchworkGame.specialTile[i] ){
                player.tiles.add('h');
                player.specialH = true;
            }
        }
        if (player.tiles.size()>0)
            player.specialH = true;
    }
    //    the functions is called to get two players' score,
// first int is for player 1, second for player 2

    //this function should only be called at the final of the game
    public int getScore(){
        scoreCount = scoreCount + buttonCount - squareleft*2;
//        System.out.println("the player"+id+"have "+buttonCount+" buttons and"+ squareleft+" squares left");
        return scoreCount;}
}
