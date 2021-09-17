// JDK-12
// Hi,Vincent. I didn't use all the methods required in the instruction, is that OK?
// The game works as the instruction describes, if my understanding of the instruction is correct.
// But I am not satisfied with this work product, I think it can be more compact.
// Would you give me any feedback on how to improve it? Thanks

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Die{
    int current_value=1;
    int sides;
    static int s_sides;

    Die(int sd){
        s_sides=sd;
    }
    void setter(int sd_setter){
        sides=sd_setter;
    }
    int getter(){return s_sides=sides;}
    static int generator(){
        Random rd=new Random();
        int rnum;
        rnum=rd.nextInt(s_sides+1);
        if(rnum>0)
        return rnum;
        else
            return 1;
    }

    int Roll(){
        current_value= generator();
        return current_value;
    }
}

class Player{
    String name;
    int pSides;
    int[] list;
    int score=0;

    Player(String n, int ps){
        name=n;
        pSides=ps;
    }

    ArrayList<Die> ld=new ArrayList<Die>();
    ArrayList<Die> Playlist(){
        Die current_dice = new Die(pSides);   // pSides set sides of the current dice
        for(int i=0; i<5;i++){
            ld.add(current_dice);
        }
        return ld;
    }

    void clearPlaylist(){
        ld.clear();
    }

    public int[] rollDie(){
        ArrayList<Die> current_ld=Playlist();
        list=new int[current_ld.size()];
        for(int i=0; i<current_ld.size();i++){
            list[i]=current_ld.get(i).Roll();
        }
        return list;
   }

   public Integer GetDieValue(){
        int sum=0;
        for(int die_value:list){
            sum+=die_value;
        }
        return sum;
   }

   public int increaseScore(){
       return score+=1;
   }

   // what is this method used for?
   public void addDie(int side){
        Die addie=new Die(side);
        ld.add(addie);
   }
}

public class SimpleDiceGame {
    public static void main(String[] args){
        Scanner scan_1=new Scanner(System.in);
        Scanner scan_2=new Scanner(System.in);
        Scanner scan_3=new Scanner(System.in);
        String[] names;

        System.out.print("Please enter the number of players: ");
        int num=scan_1.nextInt();      // number of players
        int[] playerScore=new int[num];
        System.out.print("Please enter the sides of dice: ");
        int sides=scan_1.nextInt();    // sides of dices

        System.out.println("Please enter the name of players");
        names=new String[num];
        for(int i=0;i<num;i++){
            names[i]=scan_2.nextLine();  // store players' name
        }

        ArrayList<Player> plys=new ArrayList<>();   // store player objects in plys
        for(int i=0; i<num;i++){
            plys.add(new Player(names[i],sides));
        }

        int playDieValueSum;
        System.out.println("\nEnter guess value to continue,enter q to quit the game."+"\n"+
                "Attention: you can only quit the game when each entire round is finished!\n");
        System.out.println("The first player guess a sum-value of your dice");
        while(scan_3.hasNextInt()){
            for(int i=0;i<plys.size();i++){
                int guess=scan_3.nextInt();
                plys.get(i).rollDie();
                playDieValueSum= plys.get(i).GetDieValue();
                System.out.println("Player "+names[i]+"'s sum of dice is: "+ playDieValueSum);
                if(guess==playDieValueSum){
                    playerScore[i]=plys.get(i).increaseScore();
                }
                System.out.println(names[i]+"'s score is: "+playerScore[i]);
                System.out.println();
                plys.get(i).clearPlaylist();
                System.out.println("Next player guess a sum-value of your dice"+"\n"+
                        "(If now turns to the first player, you can choose to enter 'q'" +
                        "to quit the game.)");
            }
        }
        // find the player who win the game : find has highest score by index
        int maxIndex=0;
        ArrayList<String> winners=new ArrayList<>();

        //find the first highest score and its index
        for(int i=0; i<playerScore.length-1;i++){
            if(playerScore[maxIndex]<playerScore[i+1]){
                maxIndex=i+1;
            }
        }
        // get players who have the highest score
        for(int j=0; j<playerScore.length;j++){
            if(playerScore[j]==playerScore[maxIndex]){
                winners.add(names[j]);
            }
        }
        System.out.print("The winners are: "+winners.toString());
    }
}
