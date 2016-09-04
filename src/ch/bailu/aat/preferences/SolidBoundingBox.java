package ch.bailu.aat.preferences;

import android.content.Context;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import ch.bailu.aat.coordinates.BoundingBox;
import ch.bailu.aat.services.directory.GpxDbConstants;

public class SolidBoundingBox implements SolidTypeInterface {
    private final SolidInteger N, W, S, E;

    
    public SolidBoundingBox(final Storage storage, final String key) {
        N = new SolidInteger(storage, key + "_N");
        E = new SolidInteger(storage, key + "_E");
        S = new SolidInteger(storage, key + "_S");
        W = new SolidInteger(storage, key + "_W");
    }
    
    public BoundingBox getValue() {
        return new BoundingBox(
                N.getValue(),
                E.getValue(),
                S.getValue(),
                W.getValue());
    }
    
    public void setValue(BoundingBox b) {
        N.setValue(b.getLatNorthE6());
        E.setValue(b.getLonEastE6());
        S.setValue(b.getLatSouthE6());
        W.setValue(b.getLonWestE6());
    }
    
    public boolean hasKey(String k) {
        return N.hasKey(k) || E.hasKey(k) || S.hasKey(k) || W.hasKey(k);
    }

    @Override
    public Context getContext() {
        return N.getContext();
    }

    @Override
    public String getKey() {
        return N.getKey().substring(0,N.getKey().length()-3);
    }

    @Override
    public Storage getStorage() {
        return N.getStorage();
    }

    @Override
    public String getLabel() {
        return SolidType.NULL_LABEL;
    }

    @Override
    public void register(OnSharedPreferenceChangeListener listener) {
        N.register(listener);
    }

    @Override
    public void unregister(OnSharedPreferenceChangeListener listener) {
        N.unregister(listener);
    }
    

    public String createSelectionStringOverlaps() {
       
        final int n = N.getValue(), e = E.getValue(), s = S.getValue(), w = W.getValue();
        
        return
         "(("
        +          GpxDbConstants.KEY_NORTH_BOUNDING  + " < " + n
        +" AND " + GpxDbConstants.KEY_NORTH_BOUNDING  + " > " + s
        
        +") OR ("
        
        +          GpxDbConstants.KEY_SOUTH_BOUNDING  + " < " + n
        +" AND " + GpxDbConstants.KEY_SOUTH_BOUNDING  + " > " + s
        +"))"

        
        + " AND "

        
        +"((" 
        +" AND " + GpxDbConstants.KEY_EAST_BOUNDING   + " > " + w
        +" AND " + GpxDbConstants.KEY_EAST_BOUNDING   + " < " + e
        
        +") OR (" 
        
        +" AND " + GpxDbConstants.KEY_WEST_BOUNDING   + " > " + w
        +" AND " + GpxDbConstants.KEY_WEST_BOUNDING   + " < " + e
        +"))";

    }

    
    public String createSelectionStringInside() {
        final int n = N.getValue(), e = E.getValue(), s = S.getValue(), w = W.getValue();
        
        return    GpxDbConstants.KEY_NORTH_BOUNDING  + " < " + n +
        " AND " + GpxDbConstants.KEY_SOUTH_BOUNDING  + " > " + s +
        " AND " + GpxDbConstants.KEY_EAST_BOUNDING   + " < " + e +
        " AND " + GpxDbConstants.KEY_WEST_BOUNDING   + " > " + w; 
    }
}
