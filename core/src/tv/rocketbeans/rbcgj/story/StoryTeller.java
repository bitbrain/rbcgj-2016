package tv.rocketbeans.rbcgj.story;

import java.util.ArrayList;
import java.util.List;

import tv.rocketbeans.rbcgj.assets.Assets;
import tv.rocketbeans.rbcgj.audio.SoundPoolLooper;

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
        texts.add("Das Leben war gut fuer Eddy die Erdnuss und seine vier Freunde Rosina die Rosine, Bernhardt die Mandoline spielende Mandel, Lord William Chashew II. und Jeremy die teilweise paralysierte Paranuss.");
        texts.add("In ihrer eigenen Studentenfutter-Packungs-WG, ganz in der Naehe des Stadtkerns, lachten, liebten und feierten sie ohne Unterlass.");
        texts.add("Bis eines Tages ein fauler Student die Packung der fuenf Freunde mitnahm und - faul wie er nun mal war - mitten in einem dunklen Wald wegwarf.");
        texts.add("Die Nuss-Gang hoerte seltsame Geraeusche von draussen, als ploetzlich gigantische Krallen in die Tuete langten und Eddies Freunde verschleppten.");
        texts.add("Eddy konnte nur mitansehen, wie ein riesiges, grauenhaftes Eichhoernchen die Genussnapten in seinen Bau trug.");
        texts.add("Er musste etwas tun! Die kleine Erdnuss kruemelte all ihren Mut zusammen und schlich in der Nacht dem Eichhoernchen hinterher.");
        texts.add("Doch wer weiss, ob es da nicht schon zu spaet war...");
    }
}
