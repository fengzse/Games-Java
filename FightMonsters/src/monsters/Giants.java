package monsters;

public class Giants extends Monster {
    String name;

    public Giants() {                   // 自己的构造器参数列表为空，但是只要super匹配基类构造器即可
        super(30, 35, 40, 35, "Giant");
        name = "Giant";
    }

    @Override
    public boolean isDead() {
        if (getM_hp() == 0) {
            System.out.println("You killed a " + name);
            return true;
        } else {
            return false;
        }
    }
}