package PhysicsEffect;

public class CollisionDetector {
    public static boolean isCollide(Circle circle, Rectangle rectangle){
        int circleDistanceX = Math.abs(circle.getX() - rectangle.getX());
        int circleDistanceY = Math.abs(circle.getY() - rectangle.getY());

        if(circleDistanceX > (rectangle.getWidth() / 2 + circle.getRadius()))
            return false;
        if(circleDistanceY > (rectangle.getHeight() / 2 + circle.getRadius()))
            return false;

        if(circleDistanceX <= (rectangle.getWidth() / 2))
            return true;
        if(circleDistanceY <= (rectangle.getHeight() / 2))
            return true;

        int cornerDistance = (int)Math.pow(circleDistanceX - (double) rectangle.getWidth() / 2, 2) +
                (int)Math.pow(circleDistanceY - (double) rectangle.getHeight() / 2, 2);

        return (cornerDistance <= Math.pow(circle.getRadius(), 2));
    }

    public static boolean isCollide(Rectangle rectangle, Circle circle){
        return isCollide(circle, rectangle);
    }

    public static boolean isCollide(Circle circle1, Circle circle2){
        int distance = (int)Math.sqrt(Math.pow(circle1.getX() - circle2.getX(), 2) + Math.pow(circle1.getY() - circle2.getY(), 2));
        return distance <= circle1.getRadius() + circle2.getRadius();
    }

    public static boolean isCollide(Rectangle rectangle1, Rectangle rectangle2){
        if(rectangle1.getX() + rectangle1.getWidth() < rectangle2.getX() || rectangle1.getX() > rectangle2.getX() + rectangle2.getWidth())
            return false;
        return rectangle1.getY() + rectangle1.getHeight() >= rectangle2.getY() && rectangle1.getY() <= rectangle2.getY() + rectangle2.getHeight();
    }
}
