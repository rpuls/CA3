package jsonmappers;

import entity.Shop;

/**
 *
 * @author rasmus
 */
public class TinyShopMapper {

    private String name;
    private double x;
    private double y;
    private double angle;

    public TinyShopMapper(String name, double x, double y, double angle) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

}
