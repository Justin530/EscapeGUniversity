package PhysicsEffect;

public class Circle {
    private int x;
    private int y;
    private int radius;

    public Circle(){
        this.x = 0;
        this.y = 0;
        this.radius = 0;
    }

    public Circle(int x, int y, int radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean isCollide(Circle circle){
        int distance = (int)Math.sqrt(Math.pow(this.x - circle.x, 2) + Math.pow(this.y - circle.y, 2));
        return distance <= this.radius + circle.radius;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getRadius(){ return this.radius; }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }
}
