import java.util.Scanner;

public class NumGuesser {
    public static void main(String[] args){
        int num= (int) (Math.random() * 500 +1);  // 1-500

        Scanner guess=new Scanner(System.in);
        System.out.println("Guess a number");
        int answer;
        int count=0;

        while(guess.hasNextInt()){
            answer=guess.nextInt();
            count++;
            if(answer>num) System.out.println(answer+" is bigger than the number");
            else if(answer<num) System.out.println(answer+" is smaller than the number");
            else System.out.println("Bingo! You counted: "+count+" times");
            if(answer==num) break;
        }
        System.out.println("Game finished");
    }
}
