package ch.bailu.aat.views.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import org.osmdroid.views.MapView;

import ch.bailu.aat.coordinates.BoundingBox;
import ch.bailu.aat.preferences.SolidBoundingBox;
import ch.bailu.aat.views.AbsLabelTextView;


public class SolidBoundingBoxView extends AbsLabelTextView implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final SolidBoundingBox sbounding;


    public SolidBoundingBoxView(SolidBoundingBox bounding, final MapView map) {
        super(bounding.getContext(), bounding.getLabel());

        sbounding = bounding;
        setText(bounding.getValueAsString());


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sbounding.setValue(new BoundingBox(map.getBoundingBox()));
            }
        });
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        sbounding.register(this);

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (sbounding.hasKey(key)) {
            setText(sbounding.getValueAsString());
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        sbounding.unregister(this);
    }
}
