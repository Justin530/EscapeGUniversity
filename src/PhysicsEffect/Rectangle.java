package PhysicsEffect;

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(){
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public Rectangle(int x, int y, int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isCollide(Rectangle rectangle){
        if(this.x + this.width < rectangle.x || this.x > rectangle.x + rectangle.width)
            return false;
        if(this.y + this.height < rectangle.y || this.y > rectangle.y + rectangle.height)
            return false;
        return true;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth(){ return this.width; }

    public int getHeight(){ return this.height; }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }
}
