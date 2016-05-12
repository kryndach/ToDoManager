package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Attachment;

/**
 * Created by yegorkryndach on 12/05/16.
 */
public class AttachmentInserterEvent {
    public final Attachment attachment;

    public AttachmentInserterEvent(Attachment attachment) {
        this.attachment = attachment;
    }
}
