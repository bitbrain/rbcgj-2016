package tv.rocketbeans.rbcgj.story;

import java.util.ArrayList;
import java.util.List;

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
        texts.add("Eddy die Erdnuss ist ein geselliger, freundlicher Kompane, der mit seinen Freunden Rosina die Rosine, Bernhardt die Mandel, Lord William Cashew II und Jeremy die teils paralysierten Paranuss ein wundervolles Leben in einer Studentenfutter-Packung lebt.");
        texts.add("Eines Tages gab es ein Krachen und Rumpeln und das Schicksal der 5 Freunde wurde auf eine harte Probe gestellt. Die Packung wurde von ihrem Besitzer, einem faulen und unachtsamen Studenten, fallengelassen - und das auch noch mitten im Wald!");
        texts.add("Leider direkt neben einem grossen Baum, welcher von einem Eichhoernchen bewohnt war.");
        texts.add("Dieses griff sofort die Freunde an und Eddy musste mitansehen, wie seine Freunde von dem Ungeheuer in dessen Bau verschleppt wurden.");
        texts.add("Eddy schlich sich in den tiefen Bau des grausamen Eichhorns, um sich auf die Suche zu machen.");
        texts.add("Nun liegt das Schicksal der Freunde in Deiner Hand!");
    }
}
