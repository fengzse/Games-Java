import java.util.Scanner;

class Guesser{
    private int low;
    private int high;
    private String answer;
    private int temp;
    private int number;
    Guesser(int l,int h,int num){
        low=l;
        high=h;
        number=num;
    }
    void setter_low(int a) {low=a;}
    void setter_high(int b) {high=b;}
    int getter_low() {return low;}
    int getter_high() {return high;}

    int getNumber(){
        return (int)(getter_low()+((Math.random()*(getter_high()+1-getter_low())))); // number between low and high
    }

    int getPlayerNum(){return number;}

    void rules(){
        try{
            if(low>high){
                throw new IllegalArgumentException_2(low,high);
            }
        }catch (IllegalArgumentException_2 e){
            System.out.println(e);
            System.exit(-1);
        }
    }

    void setAnswer(String a){answer=a;}

    boolean doGame(int number){
        temp=number;

        if(answer.equalsIgnoreCase("t")){
            setter_high(temp);
            return true;
        }
        else if(answer.equalsIgnoreCase("f")){
           setter_low(temp);
           return false;
        }
        /*
        if(getter_high()==getter_low() || temp==number){
            System.out.println("The number you guess is: "+temp);
            return true;
           }
         */
        return false;
    }
}

class IllegalArgumentException_2 extends Exception{
    int i,j;
    IllegalArgumentException_2(int m, int n){
        i=m;
        j=n;
    }

    @Override
    public String toString() {
        return "The first parameter must be lower than the second parameter";
    }
}

public class NumGuess {
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        Scanner asw=new Scanner(System.in);
        String answer;
        int larg;
        int harg;
        int trueNumber;
        int count=1;
        int temp;
        System.out.println("Please enter the range of the game, min and max numbers.");
        larg = scan.nextInt();
        harg = scan.nextInt();
        trueNumber=scan.nextInt();
        Guesser guess=new Guesser(larg,harg,trueNumber);
        guess.rules();
        System.out.println("Game starts, answer by \"t\" or \"f\".");
       do{
           temp=guess.getNumber();
           System.out.println("Is the number less than or equal to: "+ temp +" ?");
           answer=asw.nextLine();
           guess.setAnswer(answer);
           if(guess.doGame(temp)){
               guess.setter_low(temp);
           }else if(!guess.doGame(temp)){
               guess.setter_high(temp);
           }

           count+=1;
           if(guess.getter_low()==guess.getter_high() || temp==trueNumber) break;
       } while(guess.getter_high()!=guess.getter_low() && temp!=trueNumber);
        System.out.println("The true number is: "+guess.getPlayerNum());
        System.out.println("You guessed "+count+" times");
    }
}
