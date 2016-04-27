package io.blackbricks.todomanager.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import java.util.ArrayList;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.model.Attachment;
import io.blackbricks.todomanager.task.model.AttachmentPresentation;

/**
 * Created by yegorkryndach on 26/04/16.
 */
public class AttachmentListAdapter extends SupportAnnotatedAdapter implements AttachmentListAdapterBinder {

    @ViewType(layout = R.layout.list_attachment_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.image, name = "image", type = ImageView.class),
            })
    public final int attachmentItem = 0;

    ArrayList<AttachmentPresentation> attachmentPresentationList;

    public AttachmentListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return attachmentPresentationList.size();
    }

    @Override
    public void initViewHolder(AttachmentListAdapterHolders.AttachmentItemViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(AttachmentListAdapterHolders.AttachmentItemViewHolder vh, int position) {
        final AttachmentPresentation attachmentPresentation = attachmentPresentationList.get(position);
        vh.image.setImageBitmap(attachmentPresentation.getBitmap());
    }
}
