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
public class Test {

    public static void main(String[] args) {
        Monster monster = new Monster();
        monster.setRotationAngle(20);
        monster.rotateBy(45);
        monster.setY(40);
        monster.moveVertically(10);
        System.out.println(monster);
    }
}
