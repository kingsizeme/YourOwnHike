package team10.cs442.project.com.Animal_Libs.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContentAnimal {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        String[] tmp = {"good"};

        addItem(new DummyItem("1", "Oak", "ha", "Item 1", tmp));
        addItem(new DummyItem("2", "Palm","ha","Item 2",tmp));
        addItem(new DummyItem("3", "Red Wood","ha","Item 3",tmp));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String Name;
        public String Brief;
        public String content;
        public String[] occur_trails;

        public DummyItem(String id, String names, String briefs,String content, String[] occur_trails) {
            this.id = id;
            this.Name = names;
            this.Brief = briefs;
            this.content = content;
            this.occur_trails = occur_trails;
        }

        @Override
        public String toString() {
            return content;
        }

        public String mytoString() {return Name;}

        public String mytoBrieft() {return Brief;}
    }
}
