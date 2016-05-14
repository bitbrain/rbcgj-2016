package tv.rocketbeans.rbcgj.story;

import java.util.ArrayList;
import java.util.List;

import tv.rocketbeans.rbcgj.i18n.Bundle;

import static tv.rocketbeans.rbcgj.i18n.Messages.*;

public class StoryTeller {

    private List<String> texts;

    public StoryTeller() {
        texts = new ArrayList<String>();
        initTexts();
    }

    public String getNextStoryPoint() {
        return texts.remove(0);
    }

    public boolean hasNextStoryPoint() {
        return !texts.isEmpty();
    }

    private void initTexts() {
        texts.add(Bundle.general.get(STORY_1));
        texts.add(Bundle.general.get(STORY_2));
        texts.add(Bundle.general.get(STORY_3));
        texts.add(Bundle.general.get(STORY_4));
        texts.add(Bundle.general.get(STORY_5));
        texts.add(Bundle.general.get(STORY_6));
        texts.add(Bundle.general.get(STORY_7));
    }
}
