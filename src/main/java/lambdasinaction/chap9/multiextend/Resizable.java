package lambdasinaction.chap9.multiextend;

/**
 * 包名：lambdasinaction.chap9.multiextend
 * 文件名：lambdasinaction.chap9.multiextend
 * 创建者：SUXH
 * 创建日：2020/3/18
 * CopyRight 2015 ShenZhen Fadada Technology Co.Ltd All Rights Reserved
 */
public interface Resizable {

    int getWidth();

    int getHeight();

    void setWidth(int width);

    void setHeight(int height);

    void setAbsoluteSize(int width,int height);

    default  void setRelativeSize(int wFactor,int hFactor) {
        setAbsoluteSize(getWidth() /wFactor,getHeight()/hFactor);
    }
}
