package lee.vioson.caipu.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lee.vioson.caipu.R;
import lee.vioson.caipu.sqlite.SearchHistoryDBHelper;

/**
 * Author:李烽
 * Date:2016-05-19
 * FIXME
 * Todo
 */
public class SuggestAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflater;

    public SuggestAdapter(Context context, Cursor c) {
        super(context, c);
        init(context);
    }

    public SuggestAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        init(context);
    }

    public SuggestAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        init(context);
    }

    private void init(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mLayoutInflater.inflate(R.layout.search_history_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.key_word);
        textView.setText(cursor.getString(cursor.getColumnIndex(SearchHistoryDBHelper.KEY_WORD)));
    }
}
