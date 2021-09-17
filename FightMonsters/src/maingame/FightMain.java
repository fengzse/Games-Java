package maingame;
import monsters.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

interface IPlayer{
    void showPlayer();
    int playerAttack(Monster ms);
    int getDamaged(Monster ms);
    int defendRecover();
    boolean isDead();
    int giveExp(Monster ms);
    void showMenu();
    boolean winTheGame();

}

class Player implements IPlayer{
    String name;
    Player(String n){
        name=n;
    }
    private int hp_level=100;  // 设定级别起始生命值
    private int hp=hp_level;   // 动态生命值，在游戏过程中减少。初始赋值等于级别起始生命值
    private int exp_toUpgrade=100;   // 升级所需的exp
    private int exp=0;            // 动态exp，随游戏增加
    private int exp_saved=0;
    private int power=35;  // 起始攻击和防御值，实际攻击值和防御值可以乘加权数
    private int defend_level=40;  // 每一级别的防御值设定,初始值为level_1的防御值
    private int defend=defend_level;
    private int level=1;

    int getHp(){return hp;}
    int getExp(){return exp;}
    int getPower(){return power;}
    int getDefend(){return defend;}
    int getLevel(){return level;}
    int getExp_toUpgrade(){return exp_toUpgrade;}

    void setHp(int h){hp=h;}
    void setExp(int e){exp=e;}
    void setPower(int p){power=p;}
    void setDefend(int d){defend=d;}
    void setLevel(int l){level=l;}
    void setHp_level(int sl){hp_level=sl;}
    void setExp_toUpgrade(int stu){exp_toUpgrade=stu;}
    void setDefend_level(int sdl){defend_level=sdl;}

    // 玩家资料，数据随游戏进行随时调整并打印输出
    public void showPlayer(){
        System.out.println("*************");
        System.out.println("* Name: " + name);
        System.out.println("* Level: "+getLevel());
        System.out.println("* HP: "+getHp()+"/"+hp_level);
        System.out.println("* Exp: "+getExp()+"/"+exp_toUpgrade);
        System.out.println("* Strength: "+getPower());
        System.out.println("* Defense: "+getDefend());
        System.out.println("*************");
    }

    @Override
    public int playerAttack(Monster ms){
        if(RandomHelper.getBigChance()){       // 如果得到攻击机会
            if(getPower()>ms.getM_defend()){  // 检验玩家攻击值与怪物防御值
                int temp_attack, hp_left;
                temp_attack=RandomHelper.getInt(1,getPower()-ms.getM_defend());
                // 随机产生多少实际攻击值,在1到攻击减怪兽防御值的区间内产生
                hp_left=ms.getM_hp()-temp_attack; // 怪物的剩余生命值=怪物当前生命值-产生的实际攻击值
                if(hp_left<=0){hp_left=0;}
                ms.setM_hp(hp_left);  // 重新设定怪物的生命值
                System.out.println("You hit the monster,dealing "+ temp_attack + " damages.");
                return ms.getM_hp();   // 返回怪物新的生命值
            }
            else if(getPower()==ms.getM_defend()){
                ms.setM_defend(ms.getM_defend()-RandomHelper.getInt(3,8));
                System.out.println("You undermined the monster's defense.");
                return ms.getM_defend();
            }
            else {
                int m_defend_reduce, m_defend_left;
                // 如果怪物防御力大于玩家攻击力，每次攻击削减防御力，直到防御力小于玩家攻击力
                m_defend_reduce=RandomHelper.getInt(1,ms.getM_defend()-getPower());
                m_defend_left=ms.getM_defend()-m_defend_reduce;
                ms.setM_defend(m_defend_left);   // 设定怪物新的防御值
                System.out.println("Your attack failed!");  // 攻击力小于对方防御力
                return ms.getM_defend();   // 返回被削减后的防御值
            }
        }else{
            System.out.println("You missed the monster");  // 没打到
            return ms.getM_hp();
        }
    }

    @Override
    public int getDamaged(Monster ms){
        if(RandomHelper.getBigChance()){
            if(ms.getM_power()>getDefend()){
                int temp_attacked, php_left;
                temp_attacked=RandomHelper.getInt(1,ms.getM_power()-getDefend());
                php_left=getHp()-temp_attacked;
                if(php_left<=0){php_left=0;}
                setHp(php_left);
                System.out.println("The monster hit you, dealing "+ temp_attacked+ " damages.");
                return getHp();
            }
            else if(ms.getM_power()==getDefend()){
                setDefend(getDefend()-RandomHelper.getInt(3,8));
                System.out.println("The monster undermined your defense!");
                return getDefend();
            }
            else {
                int defend_reduce,defend_left;
                defend_reduce=RandomHelper.getInt(1,getDefend()-ms.getM_power());
                defend_left=getDefend()-defend_reduce;
                setDefend(defend_left);
                System.out.println("Monster's attack failed!");
                System.out.println("player defend is now: "+getDefend());
                return getDefend();   // 返回被削减后的防御值，但是打败当前怪物后，需要恢复原始防御值
            }
        }else {
            System.out.println("Monster missed you!");
            return getHp();
        }
    }


    @Override
    public int defendRecover(){          // 打完恢复防御值
        setDefend(defend_level);
        return getDefend();
    }

    @Override
    public boolean isDead(){
        return getHp() == 0;
    }

    public void playerUpgrade(){
        setExp_toUpgrade(exp_toUpgrade+=30);  // 满级经验上升30
        setLevel(level+=1);   // 升一级
        setHp_level(hp_level+=30);  // 升级后经验上限增加
        setHp(hp_level);         // 升级后补满生命值
        setPower(power+=10);     // 升级后力量和防御值增加
        setDefend_level(defend_level+=10);
    }

    @Override
    public int giveExp(Monster ms){
        setExp(exp+=ms.getExp_giving());
        if(getExp()>=getExp_toUpgrade()){
            exp_saved=exp-exp_toUpgrade;
            playerUpgrade();
            setExp(exp_saved);   // 升级后，exp仅留多余部分
            return getExp();
        }
        return getExp();
    }
    @Override
    public void showMenu(){
        System.out.println("1.Go Adventure");
        System.out.println("2.Show details about your character.");
        System.out.println("3.Exit and end the game.");
    }
    @Override
    public boolean winTheGame(){
        if(getLevel()==10){
            System.out.println("The great hero "+name+" defeated all the monsters!");
            return true;
        }
        return false;
    }
}

class RandomHelper{

    public static boolean getBigChance(){
        double chance;
        Random r=new Random();
        chance= r.nextDouble()*100;
        return chance <= 80.0;
    }

    public static boolean get50Chance(){
        Random r_half=new Random();
        double halfChance= r_half.nextDouble();
        return halfChance <= 0.5;
    }

    public static int getInt(int lower, int upper){
        Random r_btw=new Random();
        int btw;
        btw=lower+(int)(r_btw.nextDouble()*(upper+1-lower));  // an int between lower and upper (inclusive)
        return btw;
    }
}



public class FightMain {
    public static void main(String[] args){
        String name;
        int choice;
        Scanner input=new Scanner(System.in);
        System.out.println("Welcome to the adventure,Hero");
        System.out.println("Please enter your name");
        name=input.nextLine();

        IPlayer player=new Player(name);
        Monster cruMon;
        ArrayList<Monster> cru=new ArrayList<>();
        player.showMenu();
        System.out.println("Please choose what to do next step");
        while (input.hasNextInt()){
            choice=input.nextInt();
            if (choice == 1) {
                if(RandomHelper.getBigChance()){
                    cru.add(new Giants());    // 每次随循环添加新的怪物实例，循环结束清除列表元素，每次都生成新实例
                    cru.add(new Goblin());
                    cru.add(new Beastmaster());
                    cruMon=cru.get(RandomHelper.getInt(0,cru.size()-1));  // 随机获取一个列表集合元素，怪物实例
                    System.out.println("You meet a "+cruMon.getName()+"\nFight!!");
                    do {
                        if(RandomHelper.get50Chance()){
                            player.playerAttack(cruMon);
                            player.getDamaged(cruMon);
                        }else{
                            player.getDamaged(cruMon);
                            player.playerAttack(cruMon);
                        }
                    }while (!player.isDead() && !cruMon.isDead() && !player.winTheGame());
                    if (player.isDead()) {
                        System.out.println("You are killed"+"\n Game Over");
                        // 玩家失败条件判定，怪物死亡判定要在monsters类中设定类似方法
                        System.exit(0);
                    }else{
                        player.defendRecover();
                        player.giveExp(cruMon);
                        if(player.winTheGame()){
                            System.out.println("Hero won!");
                            System.exit(0);
                        }
                    }
                    cru.clear();
                }else{
                    System.out.println("You see noting but swaying grass around you.");
                }
            }
            else if(choice==2){
                player.showPlayer();
            }
            else if(choice==3){
                System.out.println("Hero is scared, he runs home"+"\nGame Over");
                System.exit(0);
            }
            else{
                System.out.println("What do you want?!");
            }
            System.out.println("\nPlease choose what to do next step \n");
            player.showMenu();
        }
    }
}
