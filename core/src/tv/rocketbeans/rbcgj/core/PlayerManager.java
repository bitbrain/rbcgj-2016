package tv.rocketbeans.rbcgj.core;

public class PlayerManager {

    private int crumbs = 0;

    public PlayerManager() {

    }

    public void addCrumb() {
        crumbs++;
    }

    public int getCrumbCount() {
        return crumbs;
    }
}
