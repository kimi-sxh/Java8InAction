package lambdasinaction.chap9.multiextend;

/**
 * <h3>概要:</h3>
 * TODO(请在此处填写概要)
 * <br>
 * <h3>功能:</h3>
 * <ol>
 * <li>TODO(这里用一句话描述功能点)</li>
 * </ol>
 * <h3>履历:</h3>
 * <ol>
 * <li>2020/3/18[SUXH] 新建</li>
 * </ol>
 */
public class Monster implements Rotable,Moveable,Resizable {

    private int x;
    private int y;
    private int width;
    private int height;
    private int rotationAngle;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void setAbsoluteSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setRotationAngle(int angleInDegrees) {
        this.rotationAngle = angleInDegrees;
    }

    @Override
    public int getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", rotationAngle=" + rotationAngle +
                '}';
    }
}
