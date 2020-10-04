public class FreeRectangle {
    private int width;
    private int height;
    private int x;
    private int y;
    private int area;

    /**
     * @param width
     * @param height
     * @param x
     * @param y
     */
    public FreeRectangle(int width, int height, int x, int y){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.area = x * y;
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

    public int getArea(){
        return  this.area;
    }
}
