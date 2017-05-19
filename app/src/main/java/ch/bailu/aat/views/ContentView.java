package ch.bailu.aat.views;

import android.content.Context;
import android.widget.LinearLayout;

public class ContentView extends LinearLayout {

    public ContentView(Context context) {
        this(context, LinearLayout.VERTICAL);
    }

    
    public ContentView(Context context, int o) {
        super(context);
        setOrientation(o);
    }
}
