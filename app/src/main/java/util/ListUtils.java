package util;


import com.app.lightnovelreader.models.Chapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {
    public static List<Chapter> searchByName(String keyword, List<Chapter> items) {
        List<Chapter> result = new ArrayList<>();
        for (Chapter item : items) {
            if (item.getChapterName().toLowerCase().contains(keyword)) {
                result.add(item);
            }
        }
        return result;
    }

    public static List<Chapter> sortById(List<Chapter> items) {
        List<Chapter> sorted = new ArrayList<>(items);
        Collections.sort(sorted, (a, b) -> Float.compare(a.getChapterId(), b.getChapterId()));
        return sorted;
    }
}
