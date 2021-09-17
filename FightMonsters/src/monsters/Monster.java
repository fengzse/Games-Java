package monsters;

abstract public class Monster{
    private int m_power;
    private int m_defend;
    private int m_hp;
    private int exp_giving;
    private String name;

    Monster(int mp,int md, int mhp, int epg, String nm){
        m_power=mp;
        m_defend=md;
        m_hp=mhp;
        exp_giving=epg;
        name=nm;
    }

    public int getM_defend(){return m_defend;}
    public int getM_power(){return m_power;}
    public int getM_hp(){return m_hp;}
    public int getExp_giving(){return exp_giving;}
    public String getName(){return name;}

    public void setM_hp(int sm){        // 这两个方法要在交战中改值
        m_hp=sm;
    }
    public void setM_defend(int sd){
        m_defend=sd;
    }
    abstract public boolean isDead();
}


