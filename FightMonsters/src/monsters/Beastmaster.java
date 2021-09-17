package monsters;

public class Beastmaster extends Monster{
    String name;
    public Beastmaster(){
        super(40,35,15,45, "Beastmaster");
        name="Beastmaster";
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
