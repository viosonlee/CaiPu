package lee.vioson.caipu.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Author:李烽
 * Date:2016-05-21
 * FIXME
 * Todo 存储记录的地方
 */

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "lee.vioson.caipu.provider.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}
