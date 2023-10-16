package base;

public abstract class Icon {
    char base;

    public Icon(char base){
        this.base = encircle(base);
    }

    abstract char encircle(char c);

    public String toString(){
        return ""+base;
    }
}
