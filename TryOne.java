// Write a Java program that receives a value and prints a pyramid with so many levels.
// Each level should indicate how many numbers it consists of.

import java.util.Scanner;

class Pyramid{

    void ConPyr(int n){
        int s=n;
        System.out.println("Outputs:");
        for(int i=1;i<n+1;i++){             // 这个for是为了向内层输入i的值，并控制打印层数
            for(int x=s;x>0;x--)           // 这一层是为了控制每行的横向空格，第一行打印最多空格，此后每行递减。每行打印多少空格由s
                System.out.print(" ");      // 决定，s在每一行输出完成后递减，再赋值x控制下一行少打一个空格
            for(int j=0; j<i; j++){       //j的for迭代只是为了控制横向打印几次，次数由i的值控制，i为3时，j为0-2，循环3次打印i
                System.out.print(i+" ");
            }
            System.out.println();s--;
        }
    }
}

// Write a Java program that receives an odd number and prints a diamond of that size (so many lines).
class Diamond{
    void ConDia(int n){
        int s=n;
        if(n%2!=0){
            // 因为有s--，且循环次数也是由s控制，最开始设定循环9次，但是每一次s都减去1. 循环可以执行次数一直在改变[1-10)为9次,[2-9)为7次,
            // [3-8)为5次,[4-7)为3次,[5-6)为1次,并且这时候刚好已经执行了5次循环。对应n的值为第一次循环为9，递减为8，7，6，5。当第5次循环
            // 结束执行n--后，n的值变为4，回到顶部循环体n+1为5，循环次数变为[6-5)为0次，循环检测为false，循环终止
            // 和python不同，java的for迭代中同时在循环控制条件和循环体内的变量(如这里的s)改值会相互影响
            // 而python的for range(n)中的n即便在循环体内改值(如n+=1)，只会影响循环体内n的值和全局或局部变量n的值，但range的参数n只取原值
            for(int i=1;i<s+1;i++){
                for(int j=s;j>0;j--){
                    System.out.print(" ");
                }
                for(int k=0;k<(i*2-1);k++){
                    System.out.print("*");
                }
                System.out.println();s--;   // 最后一次执行完s==4
            }

            // 1. 关键点在于空格起始位置必须以n和s的关系为基点，因为出最长一行后，下一行必须以上一行为基准增加一个空格。同时必须实现空格在每行
            // 增加一个。也就是扩大j的取值范围。以n==9为例，前面的代码执行完毕后s==4。此时输出的最后一行空格为5，那么接下去要输出的空格必须
            // 为6，并且7，8，9递增。首先想到n-s，实现以上一行空格数为基点，由于空格的for循环实在外层for循环中的，外层i随循环递增1，因此将
            // i带入空格循环。循环体内n和s的值都在递减，n-s的值始终不变，n-s+i就实现了随i递增，扩大了j的迭代范围，实现了空格每行递增
            // 2. *号输出需要逐行递减，同时也必须以上半段最后一行输出数量为基础，每行为单数，因此使用2*s-1逐步缩小k的迭代范围。s--即缩小了k
            // 的迭代范围，同时也控制了k的for循环次数，当s==1时，k取[0,1)进行最后一次迭代。for循环每次在顶部进行循环条件检查，当s==0时，
            // 变成k=0，k<-1，不满足循环条件，k的for循环跳出
            // 3. n--的目的：(1)同上，实际控制顶层循环的次数，初始设定为n次，但是随着n--，每次for顶部进行循环检查的时候都会更新可循环次数，
            // 实际上循环次数会随着n递减而减少。(2)控制k循环中空格的迭代范围，n-s保持恒定值，再+i就可以逐步扩大迭代次数范围
            for(int i=0;i<n;i++){
                for(int j=0;j<=n-s+i;j++){
                    System.out.print(" ");
                }
                for(int k=0;k<2*s-1;k++){
                    System.out.print("*");
                }
                System.out.println();s--;n--;
            }
        }else System.out.println("Error:Please enter odds");
    }
}

public class TryOne {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Scanner scan_2=new Scanner(System.in);
        int value;
        int value_2;
        Pyramid pyr=new Pyramid();
        System.out.println("Enter a number,q to quit: ");
        while(scan.hasNextInt()){
            value=scan.nextInt();
            pyr.ConPyr(value);
        }
        System.out.println("Pyramid ends");
        System.out.println();

        Diamond dia=new Diamond();
        System.out.println("Enter a number,q to quit: ");
        while (scan_2.hasNextInt()){
            value_2=scan_2.nextInt();
            dia.ConDia(value_2);
        }
    }

}


