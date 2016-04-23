package tv.rocketbeans.rbcgj.util;

import java.util.Comparator;

import tv.rocketbeans.rbcgj.core.GameObject;

public class ZIndexComparator implements Comparator<GameObject> {

    @Override
    public int compare(GameObject o1, GameObject o2) {
        if (o1.getZIndex() > o2.getZIndex()) {
            return 1;
        } else if (o1.getZIndex() < o2.getZIndex()) {
            return -1;
        } else {
            return 0;
        }
    }
}