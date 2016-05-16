package io.blackbricks.todomanager.events;

/**
 * Created by yegorkryndach on 16/05/16.
 */
public class AttachmentRemovedEvent {
    public final Integer attachmentId;

    public AttachmentRemovedEvent(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }
}
