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

    public interface AttachmentClickListener {
        public void onAttachmentClicked(AttachmentPresentation attachmentPresentation);
    }

    public interface AttachmentLongClickListener {
        public void onAttachmentLongClicked(AttachmentPresentation attachmentPresentation);
    }

    @ViewType(layout = R.layout.list_attachment_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.image, name = "image", type = ImageView.class),
            })
    public final int attachmentItem = 0;

    ArrayList<AttachmentPresentation> attachmentPresentationList;
    private AttachmentClickListener attachmentClickListener;
    private AttachmentLongClickListener attachmentLongClickListener;

    public AttachmentListAdapter(Context context, ArrayList<AttachmentPresentation> attachmentPresentationList,
                                 AttachmentClickListener attachmentClickListener,
                                 AttachmentLongClickListener attachmentLongClickListener) {
        super(context);
        this.attachmentPresentationList = attachmentPresentationList;
        this.attachmentClickListener = attachmentClickListener;
        this.attachmentLongClickListener = attachmentLongClickListener;
    }

    public ArrayList<AttachmentPresentation> getAttachmentPresentationList() {
        return attachmentPresentationList;
    }

    public void setAttachmentPresentationList(ArrayList<AttachmentPresentation> attachmentPresentationList) {
        this.attachmentPresentationList = attachmentPresentationList;
    }

    @Override
    public int getItemCount() {
        return attachmentPresentationList == null ? 0 : attachmentPresentationList.size();
    }

    @Override
    public void initViewHolder(AttachmentListAdapterHolders.AttachmentItemViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(AttachmentListAdapterHolders.AttachmentItemViewHolder vh, int position) {
        final AttachmentPresentation attachmentPresentation = attachmentPresentationList.get(position);
        vh.image.setImageBitmap(attachmentPresentation.getBitmap());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachmentClickListener.onAttachmentClicked(attachmentPresentation);
            }
        });
        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                attachmentLongClickListener.onAttachmentLongClicked(attachmentPresentation);
                return true;
            }
        });
    }
}
