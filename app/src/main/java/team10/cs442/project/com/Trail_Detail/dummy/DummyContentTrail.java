package team10.cs442.project.com.Trail_Detail.dummy;

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
public class DummyContentTrail {

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
        addItem(new DummyItem("1", "Balanced Rock","A difficult, steep, climbing trail with stone steps on the south face of the East Bluff. Spectacular views of Devil’s Lake with the Balanced Rock formation off to the south of the trail. (.4 mile, approximate hiking time 1 hour)"));
        addItem(new DummyItem("2", "CCC Trail","A difficult, steep, climbing trail with stone steps on the south face of the East Bluff with many scenic views. (.3 mile, approximate hiking time 1 hour)"));
        addItem(new DummyItem("3", "Devil’s Doorway","This is an easy, level asphalt trail along the edge of the top of the East Bluff to views of Devil’s Lake with drop-offs along the way. There is a notable side trail with stone steps to the Devil’s Doorway rock formation. (.5 mile, approximate hiking time .25 hour) Access to this trail is from East Bluff, East Bluff Woods, CCC, Potholes and/or Balanced Rock trails."));
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
        public String Trails_Name;
        public String content;

        public DummyItem(String id, String name, String content) {
            this.id = id;
            this.Trails_Name = name;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }

        public String mytoString(){return Trails_Name;}
    }
}
