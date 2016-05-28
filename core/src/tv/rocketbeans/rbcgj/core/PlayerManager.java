package tv.rocketbeans.rbcgj.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerManager {

    public class Collectible {

        private int currentAmount = 0;

        private int maxAmount = -1;

        private int type;

        private boolean justAchieved = false;

        public Collectible(int type) {
            this.type = type;
        }

        private void setMaxAmount(int maxAmount) {
            this.maxAmount = maxAmount;
        }

        private boolean addCurrent() {
            if (isNotAchieved()) {
                currentAmount++;
                if (!isNotAchieved()) {
                    justAchieved = true;
                }
                return true;
            }
            justAchieved = false;
            return false;
        }

        public boolean isJustAchieved() {
            return justAchieved;
        }

        public boolean isNotAchieved() {
            return maxAmount < 0 || currentAmount < maxAmount;
        }

        public int getCurrentAmount() {
            return currentAmount;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        public int getType() {
            return type;
        }
    }

    private Map<Integer, Collectible> collectibles;

    public interface PlayerListener {
        void onAddCollectible(Collectible collectible);
    }

    private Set<PlayerListener> listeners = new HashSet<PlayerListener>();

    public PlayerManager() {
        collectibles = new HashMap<Integer, Collectible>();
    }

    public void addListener(PlayerListener l) {
        listeners.add(l);
        for (Collectible c : collectibles.values()) {
            l.onAddCollectible(c);
        }
    }

    public void setMaxAmount(int type, int amount) {
        ensureCollectible(type);
        collectibles.get(type).setMaxAmount(amount);
        for (PlayerListener l : listeners) {
            l.onAddCollectible(getCollectible(type));
        }
    }

    public void addCollectible(int type) {
        ensureCollectible(type);
        if (collectibles.get(type).addCurrent()) {
            for (PlayerListener l : listeners) {
                l.onAddCollectible(getCollectible(type));
            }
        }
    }

    public Collectible getCollectible(int type) {
        return collectibles.get(type);
    }

    private void ensureCollectible(int type) {
        if (!collectibles.containsKey(type)) {
            collectibles.put(type, new Collectible(type));
        }
    }
}
