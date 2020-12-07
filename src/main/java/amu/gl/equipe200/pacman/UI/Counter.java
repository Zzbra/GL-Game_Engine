package amu.gl.equipe200.pacman.UI;

import java.util.ArrayList;

public class Counter {
    private Digit unite;
    private Digit dizaine;
    private Digit centaine;
    private double x, y, w, h;

    public Counter (){
        unite = new Digit(0);
        dizaine = new Digit(0);
        centaine = new Digit(0);
    }

    public void setValue(int value){
        if (value > 999) value = 999;
        centaine.setValue(value/100);
        dizaine.setValue((value%100)/10);
        unite.setValue(value%10);
    }

    public void setX(double x) {
        this.x = x;
        unite.setX(x + 2 * w);
        dizaine.setX(x + w);
        centaine.setX(x);
    }

    public void setY(double y) {
        this.y = y;
        unite.setY(y);
        dizaine.setY(y);
        centaine.setY(y);
    }

    public void setWidth(double w) {
        this.w = w;
        for(Digit d : getDigits()){
            d.setW(w);
        }
    }

    public void setHeight(double h) {
        this.h = h;
        for(Digit d : getDigits()){
            d.setH(h);
        }
    }


    public ArrayList<Digit> getDigits(){
        ArrayList<Digit> digits = new ArrayList<>();
        digits.add(unite);
        digits.add(dizaine);
        digits.add(centaine);
        return digits;
    }

    public void setUnite(int value){
        unite.setValue(value);
    }

    public void setDizaine(int value){
        dizaine.setValue(value);
    }

    public void setCentaine(int value){
        centaine.setValue(value);
    }
}
