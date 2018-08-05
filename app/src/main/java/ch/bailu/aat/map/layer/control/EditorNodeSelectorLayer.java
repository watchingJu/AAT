package ch.bailu.aat.map.layer.control;

import android.view.View;

import org.mapsforge.core.model.Point;

import ch.bailu.aat.dispatcher.EditorSourceInterface;
import ch.bailu.aat.gpx.GpxInformation;
import ch.bailu.aat.gpx.GpxPointNode;
import ch.bailu.aat.map.MapContext;
import ch.bailu.aat.util.ToDo;
import ch.bailu.util_java.foc.Foc;

public class EditorNodeSelectorLayer extends GeneralNodeViewLayer {

    private final EditorSourceInterface editorSource;
    private boolean showNode = false;


    public EditorNodeSelectorLayer(MapContext mc, EditorSourceInterface e) {
        super(mc);
        editorSource = e;

        setText();
    }

    private void setText() {
        if (editorSource.isEditing()) {
            if (!showNode) setNoNodeSelectedText(editorSource.getFile());
        } else {
            showNode = false;
            setLoadEditorText(editorSource.getFile());
            setGraph(GpxInformation.NULL, 0);
        }
    }


    @Override
    public GpxPointNode getSelectedNode() {
        return editorSource.getEditor().getSelected();
    }




    @Override
    public void setSelectedNode(int IID, GpxInformation info, GpxPointNode node, int index) {
        showNode = true;
        editorSource.getEditor().select(node);
        super.setSelectedNode(IID, info, node, index);
    }


    @Override
    public void onContentUpdated(int iid, GpxInformation info) {
        super.onContentUpdated(iid, info);
        setBackgroundColorFromIID(iid);

        setText();
    }


    private void setLoadEditorText(Foc file) {
        html.appendHeader(file.getName());
        html.append(ToDo.translate("Load into editor"));
        setHtmlText(html);

    }


    private void setNoNodeSelectedText(Foc file) {
        html.appendHeader(file.getName());
        setHtmlText(html);
    }


    @Override
    public void onClick(View v) {
        if (editorSource.isEditing())
            super.onClick(v);

        else
            editorSource.edit();

    }

    @Override
    public boolean onTap(Point tapXY) {
        return false;
    }


    @Override
    public void onAttached() {

    }

    @Override
    public void onDetached() {

    }
}
