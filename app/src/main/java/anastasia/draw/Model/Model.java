package anastasia.draw.Model;

/**
 * Created by Администратор on 09.12.2017.
 */

public class Model implements MyAbstractModel {
    private String type;
    private float x1 = 0, y1 = 0,x2 = 0,y2 = 0;
    private int color = 0xFF660000;
    public Model(String type, float x1, float y1, float x2, float y2, int color) {
        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    public Model(String type, float x1, float y1, int color) {
        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
    }

    public Model(String type) {
        this.type = type;
    }

    public Model(){}


    public String getType() {
        return type;
    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "type: "+type+", color:"+color+", x1: "+x1+", x2: "+x2+", y1: "+y1+", y2: "+y2;
    }
}
