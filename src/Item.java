/**
 * An item is a rectangle, which will be inserted
 */
public class Item{
    private int width;
    private int height;
    private int x;
    private int y;
    private int area;
    private boolean rotation;
    private int id;

    public Item(int width, int height, int x, int y, boolean rotation, int id) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.id = id;
        this.area = width * height;
    }

    public void rotate(){
        int w = this.width;
        int h = this.height;
        this.width = h;
        this.height = w;
        if(this.rotation == true){
            this.rotation = false;
        }
        else{
            this.rotation = true;
        }
    }

    public boolean getRotation() {
        return rotation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getArea() {
        return area;
    }

    public boolean isRotation() {
        return rotation;
    }

    public int getId() {
        return id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
