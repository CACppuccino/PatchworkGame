package comp1140.ass2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class State {
    final static int GRIDS = 53;
    List<Character> tiles = new ArrayList<Character>();
//    calculating the squares left in two quilt boards.
    int squareleft = 49;

//    calculating the time left in the time board of two players
    int timecount = 0;

//    calculating the button of each player holds
    int buttonCount = 5;

    // how many buttons should a player be rewarded each special event of button event
    int specialButton = 0;
//    showing two players' states on the time board,
// 1 indicates moving first, 0 indicates moving later,
// 2 indicates the player has reached the final point.
    int tbState = 0;

//    indicating the place of the neutral token, if ntState is i,
// then the patch i,i+1,i+2 should be displayed in the candidate area.
    int ntState = 0;

    //showing whether the player is on the top
    boolean onTop = false;

    final static char[] PATCHES = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W',
            'X','Y','Z','a','b','c','d','e','f','g'};

//    this list should be changed when being initialised randomly by the initialization()/
// once the player buys an patch/ choose undo after placed a patch
    ArrayList<Character> currentPatch = new ArrayList<Character>();


    //    this function is used for recognizing the end of the game.
// Both of the players' time consumption is full,
// i.e. players' time token have reached the end square of the time board.
    public boolean check_fullstate(){
        return this.timecount==GRIDS;}

    //    this function is used for checking the who should be the person doing the next turn.
//    Two situation should be considered: if they are not in the same square of the time board,
// the one fall behind should take the turn. If they are in the same square, the player who
// last moved to that space takes the turn.
    public static int check_turn(State p1,State p2){
        if (p1.timecount>p2.timecount)
            return 2;
        else if (p1.timecount<p2.timecount)
            return 1;
        else {
            if (p1.onTop && p2.onTop)   throw new Error("both player token on the top");
            else if (p1.onTop)  return 1;
            else    return 2;
        }
    }

    public static void advanced(State p1,State p2){
        int t =check_turn(p1,p2);
        int diff =0;
        if (t==1) {
            //be infront of the p2
            diff = p2.timecount+1<=GRIDS?(p2.timecount-p1.timecount)+1:GRIDS-p2.timecount;
            p1.buttonCount += diff;
            p1.timecount = p2.timecount + 1 > GRIDS ? GRIDS : p2.timecount + 1;

        }
        else {
            diff = p1.timecount+1<=GRIDS?(p1.timecount-p2.timecount)+1:GRIDS-p1.timecount;
            p2.buttonCount += diff;
            p2.timecount = p1.timecount + 1 > GRIDS ? GRIDS : p1.timecount + 1;
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
        System.out.println("player"+t+"buying "+p+" hosts"+(t==1?p1.buttonCount:p2.buttonCount)+"and cost him"+buttonDec);
        if (t==1) {
            p1.buttonCount = p1.buttonCount - buttonDec;
            if (p1.buttonCount<0)
                //suppose to notice the Viewer
                throw new Error("cant afford the tile"+p);
            p1.specialButton += specialButton;
            specialEvent(p1,p1.timecount,timeInc);
            p1.timecount = p1.timecount+timeInc>GRIDS ? GRIDS : p1.timecount+timeInc;
            System.out.println("now get special Button "+p1.specialButton+" now get button"+p1.buttonCount);
            //considering the timetoken overlap situation
            if (p1.timecount==p2.timecount)
            {
                p1.onTop = true;
                p2.onTop = false;
            }
        }
        else {
            p2.buttonCount = p2.buttonCount - buttonDec;
            if (p2.buttonCount<0)
                //suppose to notice the Viewer
                throw new Error("cant afford the tile"+p);
            p2.specialButton += specialButton;
            specialEvent(p2,p2.timecount,timeInc);
            p2.timecount = p2.timecount+timeInc>GRIDS ? GRIDS : p2.timecount+timeInc;
            System.out.println("now get special Button "+p2.specialButton+" now get button"+p2.buttonCount);
            if (p1.timecount==p2.timecount)
            {
                p2.onTop = true;
                p1.onTop = false;
            }
        }

    }
    public static void specialEvent(State player,int start,int steps){
        for (int sb:PatchworkGame.specialButton){
            if (sb>=start+1 && sb<=start+steps)
                player.buttonCount += player.specialButton;
        }
        for (int st:PatchworkGame.specialTile){
            if (st>=start+1 && st<=start+steps)
                player.tiles.add('h');
        }
    }
    //    the functions is called to get two players' score,
// first int is for player 1, second for player 2
    public int getScore(){return buttonCount-squareleft*2;}
}
