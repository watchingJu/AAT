package ch.bailu.aat.gpx.interfaces;

import ch.bailu.aat.coordinates.BoundingBoxE6;

public interface GpxDeltaInterface {
    float getDistance();
    float getSpeed();
    float getAcceleration();
    long  getTimeDelta();
    BoundingBoxE6 getBoundingBox();
}
