package ch.bailu.aat.preferences;

import android.content.Context;

import ch.bailu.aat.R;

public class SolidWeight extends SolidInteger {

    final private static String KEY="weight";
    
    
    public SolidWeight(Context c) {
        super(Storage.global(c), KEY);
        
    }
    
    @Override
    public String getLabel() {
        return getContext().getString(R.string.p_weight_title);
    }
}
