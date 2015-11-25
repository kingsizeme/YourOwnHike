package team10.cs442.project.com.SharedLibs.dummy;

import android.graphics.Bitmap;
import android.util.Log;

import com.parse.ParseFile;

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
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();


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
        public String content;
        public ParseFile simage;

        public DummyItem(String id, String names, String content, ParseFile simage) {
            this.id = id;
            this.Name = names;
            this.content = content;
            this.simage = simage;
        }

        @Override
        public String toString() {
            return content;
        }

        public String mytoString(){
            Log.v("mytostring", Name);return Name;}
    }
}
