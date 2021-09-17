/*
1. JDK-12
2. Two maps for each player,one to show player's battlefield with ships,the other to mark points representing attack
3. Players decide how many battleships to play, max 4 for each player
4. Players decide
   1). the positions for ships by inputting the first y and x axis of each ship.
   2). how many lattices that each ship should occupy
   3). direction of ships. horizontal or vertical.
   "1)" must be inputted, "2)" and "3)" will be implemented automatically after inputting one integer and one string
5. Attack by inputting y and x axis to position the lattice
6. Destroy a ship means, if one ship E.g has 3 lattices means all these 3 lattices must be attacked
7. When all the ships of one player are destroyed by opponent, game over
 */

import java.util.Scanner;

class ShipBoard {
    String[][] ch;           // Map for current player's battle view
    String playerName;
    String[][] ch_map;      //  Battle map for attacker

    ShipBoard(int i, int j, String name) {
        ch = new String[i][j];
        ch_map=new String[i][j];
        playerName=name;
    }

    String[][] initBoard() {
        for (int i = 0; i < ch.length; i++) {
            for (int j = 0; j < ch[i].length; j++) {
                if (i == 0) ch[i][j] = ("  " + j);
                else ch[i][j] = "[ ]";
                if (j == 0) ch[i][j] = String.valueOf(i);
                ch_map[i][j]=ch[i][j];              // deep copy
            }
        }
        return ch;
    }

    String[][] setShips(int y, int x, int n, String d) {
        for (int i = 0; i < ch.length; i++) {
            for (int j = 0; j < ch[i].length; j++) {
                if (i == y && j == x) {
                    ch[i][j] = "[S]";
                    if (d.equals("h")) {
                        try {
                            for (int k = 1; k < n; k++) ch[i][j + k] = "[S]";
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println(e);
                        }
                    } else if (d.equals("v")) {
                        try {
                            for (int k = 1; k < n; k++) ch[i + k][j] = "[S]";
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println(e);
                        }
                    }
                }
            }
        }
        return ch;
    }

    void showBattle(){
        for (String[] strings : ch) {             // 二维数组的 for each 方法
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }
    void attackMap(){
        for (String[] strings : ch_map) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
    }


    String[][] Canon(int y, int x){                   // Battle map for attacker
        for(int i=0;i<ch_map.length;i++) {
            for (int j = 0; j < ch_map[i].length; j++) {
                if (i == y && j == x) {
                    ch_map[i][j]="[O]";
                }
                System.out.print(ch_map[i][j]);
            }
            System.out.println();
        }
        return ch_map;
    }

    // 同样由1号调用2号的被攻击图，修改2号的图，给1号击中点也改变标记
    boolean Attacked(int y, int x, ShipBoard board){  // current player's battle map
        for(int i=0;i<ch_map.length;i++) {
            for (int j = 0; j < ch_map[i].length; j++) {
                if(i== y && j== x){
                    if(board.ch[i][j].equals("[S]")){   // 调用2号的被攻击图
                        board.ch[i][j]="[¤]";
                        ch_map[i][j]="[B]";             // 在攻击图同步攻击方和被攻击方击中点

                        System.out.println("Boom!!! Attack again!"+"\n"+"----------------------"+"\n");
                        return true;
                    }
                    else board.ch[i][j]="[X]";

                    System.out.println("Sploosh!"+"\n"+"----------------------"+"\n");
                }
            }
        }
        return false;
    }

    boolean gameFinish(){              // if all the "S" in one player's ch(battlefield) be replaced, lose the game
        for (String[] strings : ch) {
            for (String string : strings) {
                if (string.equals("[S]")) return true;
            }
        }
        return false;
    }
}


class Players{
    String playerName;
    ShipBoard plsb;
    Players(String name){
        playerName=name;
        plsb=new ShipBoard(10,10,playerName);
    }

    void gameSetUp(){
        plsb.initBoard();
        System.out.println("How many ships do you need?");
        int shipNum,y_axis_set,x_axis_set,ship_size;
        String dir;
        Scanner setShip= new Scanner(System.in);
        Scanner setHV=new Scanner(System.in);
        shipNum=setShip.nextInt();
        System.out.println("You have "+shipNum+" Battleships.");
        System.out.println("Place your battleships"+"\n"+
                "1.Enter the first y-axis of each ship." +"\n"+
                "2.Enter the first x-axis of each ship."+"\n"+
                "3.Enter the size (how long) for each ship."+"\n"+
                "4.If the ship will be placed horizontal, enter 'h'. To be vertical, enter 'v'. ");
        System.out.println("Player "+playerName+" is setting up");
        int count=0;
        for(int i=0;i<shipNum;i++){
            y_axis_set=setShip.nextInt();
            x_axis_set=setShip.nextInt();
            ship_size=setShip.nextInt();
            dir=setHV.nextLine();
            count++;
            plsb.setShips(y_axis_set,x_axis_set,ship_size,dir);
            System.out.println("Ship "+count+" is done");
        }
        System.out.println("Player "+playerName+"'s setup is done."+"\n"+
                "---------------------------------------");
    }
    void fire(int y,int x){
        plsb.Canon(y,x);
    }

    boolean turn(int y, int x,ShipBoard otherplayer){
        if(plsb.Attacked(y, x,otherplayer)){
            return true;
        }
        return false;
    }
}


public class BattleShips {
    public static void main(String[] args) {
        Players[] plGame = new Players[2];
        Scanner name = new Scanner(System.in);

        for (int i = 0; i < plGame.length; i++) {
            System.out.println("Enter players' names");
            plGame[i] = new Players(name.nextLine());
            plGame[i].gameSetUp();
        }
        Scanner axis = new Scanner(System.in);
        int y_ack, x_ack;
        boolean gameFinish = true;
        boolean turn=true;
        System.out.println("---------------------------------------"+"\n"+"Game starts, Good luck!");
        while (gameFinish) {
            do {
                System.out.println(plGame[0].playerName + "'s turn" + "\n");
                System.out.println(plGame[0].playerName + "'s battlefiled");
                plGame[0].plsb.showBattle();
                System.out.println();
                System.out.println(plGame[0].playerName + "'s attacking map");
                plGame[0].plsb.attackMap();


                System.out.println("Enter the axis-point which you want to attack!");
                y_ack = axis.nextInt();
                x_ack = axis.nextInt();

                System.out.println("New attacked point marked");
                plGame[0].fire(y_ack, x_ack);
                if (!plGame[0].turn(y_ack, x_ack, plGame[1].plsb)) {
                    System.out.println("--------------------------------");
                    System.out.println("Player " + plGame[1].playerName + "'s turn");
                    turn=false;
                }
                if (!plGame[1].plsb.gameFinish()) {
                    gameFinish = false;
                    System.out.println(plGame[0].playerName+" wins");
                }
            } while (turn && gameFinish);
            turn=true;
            if(!gameFinish) break;

            do {
                System.out.println(plGame[1].playerName + "'s turn" + "\n");
                System.out.println(plGame[1].playerName + "'s battlefiled");
                plGame[1].plsb.showBattle();
                System.out.println();
                System.out.println(plGame[1].playerName + "'s attacking map");
                plGame[1].plsb.attackMap();


                System.out.println("Enter the axis-point which you want to attack!");
                y_ack = axis.nextInt();
                x_ack = axis.nextInt();

                System.out.println("New attacked point marked");
                plGame[1].fire(y_ack, x_ack);
                if (!plGame[1].turn(y_ack, x_ack, plGame[0].plsb)) {
                    System.out.println("--------------------------------");
                    System.out.println("Player " + plGame[0].playerName + "'s turn");
                    turn=false;
                }
                if (!plGame[0].plsb.gameFinish()) {
                    gameFinish = false;
                    System.out.println(plGame[1].playerName+" wins");
                }
            } while (turn && gameFinish);
            turn=true;
        }
        System.out.println("Game Over!");
    }
}
