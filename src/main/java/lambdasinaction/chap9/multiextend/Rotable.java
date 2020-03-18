package lambdasinaction.chap9.multiextend;

/**
 * 包名：lambdasinaction.chap9.multiextend
 * 文件名：lambdasinaction.chap9.multiextend
 * 创建者：SUXH
 * 创建日：2020/3/18
 * CopyRight 2015 ShenZhen Fadada Technology Co.Ltd All Rights Reserved
 */
public interface Rotable {

    void  setRotationAngle(int angleInDegrees);

    int getRotationAngle();

    default  void rotateBy(int angleInDegrees) {
        setRotationAngle((getRotationAngle() + angleInDegrees)%360);
    }
}
