package com.peppertap.caravan.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Message Handler class that supports buffering up of messages when the activityOrFragment is paused i.e. in the background.
 */
public abstract class PauseHandler extends Handler {

    /**
     * Message Queue Buffer
     */
    private final List<Message> messageQueueBuffer = Collections.synchronizedList(new ArrayList<Message>());

    /**
     * Flag indicating the pause state
     */
    private WeakReference<Object> activityOrFragment;

    /**
     * Resume the handler.
     */
    public final synchronized void resume(Object activity) {
        setActivity(activity);

        while (messageQueueBuffer.size() > 0) {
            final Message msg = messageQueueBuffer.get(0);
            messageQueueBuffer.remove(0);
            sendMessage(msg);
        }
    }

    final void setActivity(Object activity) {
        this.activityOrFragment = new WeakReference<Object>(activity);
    }

    /**
     * Pause the handler.
     */
    public final synchronized void pause() {
        activityOrFragment = null;
    }

    /**
     * Store the message if we have been paused, otherwise handle it now.
     *
     * @param msg   Message to handle.
     */
    @Override
    public final synchronized void handleMessage(Message msg) {
        if (activityOrFragment == null) {
            // Activity or fragment has been paused
            final Message msgCopy = new Message();
            msgCopy.copyFrom(msg);
            messageQueueBuffer.add(msgCopy);
        }else if(activityOrFragment.get() == null){
            // context has died, do nothing
        } else {
            processMessage(activityOrFragment.get(), msg);
        }
    }

    /**
     * Notification message to be processed. This will either be directly from
     * handleMessage or played back from a saved message when the activityOrFragment was
     * paused.
     *
     * @param activity  Activity/Fragment owning this Handler that isn't currently paused.
     * @param message   Message to be handled
     */
    protected abstract void processMessage(Object activity, Message message);

}
