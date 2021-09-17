package monsters;

public class Goblin extends Monster{
    String name;
    public Goblin(){
        super(15,20,25,15, "Goblin");
        name="Goblin";
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
