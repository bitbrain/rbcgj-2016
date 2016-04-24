package tv.rocketbeans.rbcgj.core;

import java.util.HashSet;
import java.util.Set;

public class PlayerManager {

    private int crumbs = 0;

    public interface PlayerListener {
        void onAddCrumb(int newAmount);
    }

    private Set<PlayerListener> listeners = new HashSet<PlayerListener>();

    public PlayerManager() {

    }

    public void addListener(PlayerListener l) {
        listeners.add(l);
    }

    public void addCrumb() {
        crumbs++;
        for (PlayerListener l : listeners) {
            l.onAddCrumb(crumbs);
        }
    }

    public int getCrumbCount() {
        return crumbs;
    }
}
