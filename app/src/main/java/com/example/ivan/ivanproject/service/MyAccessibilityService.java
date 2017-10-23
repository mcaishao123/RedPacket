package com.example.ivan.ivanproject.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {
    private final static String HOME_PAGE_ITEM_ID = "com.tencent.mm:id/ajz";
    private final static String TAKE_RED_PACKET_TEX = "领取红包";
    private final static String TAKE_RED_PACKET_BTN_ID = "com.tencent.mm:id/brt";
    private final static String NEW_MESSAGE_IMG_ID = "com.tencent.mm:id/il";
    private final static String MESSAGE_TEXT_VIEW_ID = "com.tencent.mm:id/ak3";
    private final static String TAKE_FINISH_PAGE_BACK_VIEW_ID = "com.tencent.mm:id/hg";
    private final static String CHAT_PAGE_BACK_VIEW_ID = "android:id/text1";

    private final static String KEY_WORK = "ivan";


    private final static String TAKE_FINISH_PAGE = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI";
    private final static String TAKE_RED_PAGE_DIALOG = "com.tencent.mm.plugin.luckymoney.ui.En_fba4b94f";


    private final static int CLICK_NEW_MESSAGE = 1, CLICK_TAKE_RED_PACKET = 2, CLICK_OPEN_RED_PACKET = 3, CLICK_CHAT_BACK = 4, CLICK_TAKE_FINISH_BACK = 5;

    private boolean hadClickNewMessage = false;
    private boolean hadClickRedPacket = false;
    private boolean hadClickTakeFinishBack = false;
    private boolean hadClickChatBack = false;
    private boolean hadOpenRedPacket = false;

//    private Handler booleanHandler = new Handler(new Handler.Callback() {
//        @SuppressLint("InlinedApi")
//        @Override
//        public boolean handleMessage(Message message) {
//            hadClickNewMessage = false;
//            hadClickRedPacket = false;
//            hadClickTakeFinishBack = false;
//            hadClickChatBack = false;
//            hadOpenRedPacket = false;
//            return true;
//        }
//    });

    private Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint("InlinedApi")
        @Override
        public boolean handleMessage(Message message) {
            ((AccessibilityNodeInfo) message.obj).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            booleanHandler.removeMessages(0);
//            booleanHandler.sendEmptyMessageDelayed(0, 10000);
            return true;
        }
    });


    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
//        Log.e("MyAccessibilityService", "onAccessibilityEvent: "+accessibilityEvent.getClassName());
        int eventType = accessibilityEvent.getEventType();
        printEventType(eventType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                operationHomePage(accessibilityEvent.getSource());
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                typeWindowStateChanged(accessibilityEvent);
                break;
        }
    }

    @SuppressLint("NewApi")
    private void operationHomePage(AccessibilityNodeInfo accessibilityNodeInfo) {
        List<AccessibilityNodeInfo> items = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(HOME_PAGE_ITEM_ID);
        if (!items.isEmpty()) {
            clickListViewItem(items);
        } else {
            items = accessibilityNodeInfo.findAccessibilityNodeInfosByText(TAKE_RED_PACKET_TEX);
            if (!items.isEmpty()) {
                clickRedPacketMessage(items);
            } else {
                chatBack(accessibilityNodeInfo);
            }
        }
    }

    @SuppressLint("InlinedApi")
    public void clickKeyWorkView(List<AccessibilityNodeInfo> accessibilityNodeInfos) {
        for (AccessibilityNodeInfo accessibilityNodeInfo : accessibilityNodeInfos) {
            if (KEY_WORK.equals(accessibilityNodeInfo.getText().toString().trim())) {
                if (!hadClickNewMessage) {
                    hadClickNewMessage = true;
                    sendMessage(CLICK_NEW_MESSAGE, accessibilityNodeInfo.getParent(), 0);
                    break;
                }
            }
        }
    }



    private void clickRedPacketMessage(List<AccessibilityNodeInfo> items) {
        for (AccessibilityNodeInfo accessibilityNodeInfo : items) {
            if (!hadClickRedPacket) {
                hadClickRedPacket = true;
                sendMessage(CLICK_TAKE_RED_PACKET, accessibilityNodeInfo.getParent(), 0);
                break;
            }
        }
    }

    @SuppressLint("NewApi")
    private void chatBack(AccessibilityNodeInfo accessibilityNodeInfo) {
        List<AccessibilityNodeInfo> items = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(CHAT_PAGE_BACK_VIEW_ID);
        if (!items.isEmpty() && !hadClickChatBack) {
            hadClickChatBack = true;
            sendMessage(CLICK_CHAT_BACK, items.get(0).getParent(), 2000);
        }
    }

    private void typeWindowStateChanged(AccessibilityEvent accessibilityEvent) {
        String className = accessibilityEvent.getClassName().toString();
        switch (className) {
            case TAKE_RED_PAGE_DIALOG:
                openRedPacket(accessibilityEvent.getSource());
                break;
            case TAKE_FINISH_PAGE:
                takeFinishBack(accessibilityEvent.getSource());
                break;
            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    private void openRedPacket(AccessibilityNodeInfo accessibilityNodeInfo) {
        List<AccessibilityNodeInfo> items = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(TAKE_RED_PACKET_BTN_ID);
        if (!items.isEmpty() && !hadOpenRedPacket) {
            hadOpenRedPacket = true;
            sendMessage(CLICK_OPEN_RED_PACKET, items.get(0), 0);
        }
    }

    @SuppressLint("NewApi")
    private void takeFinishBack(AccessibilityNodeInfo accessibilityNodeInfo) {
        List<AccessibilityNodeInfo> items = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(TAKE_FINISH_PAGE_BACK_VIEW_ID);
        Log.e("MyAccessibilityService", "takeFinishBack: items.size="+items.size());
        if (!items.isEmpty() && !hadClickTakeFinishBack) {
            hadClickTakeFinishBack = true;
            sendMessage(CLICK_TAKE_FINISH_BACK, items.get(0).getParent(), 0);
        }
    }

    private void sendMessage(int what, AccessibilityNodeInfo accessibilityNodeInfo, long delayMillis) {
        Message message = new Message();
        message.what = what;
        message.obj = accessibilityNodeInfo;
        handler.sendMessageDelayed(message, delayMillis);
        Log.e("MyAccessibilityService", "sendMessage: ");
        
    }

    @SuppressLint("NewApi")
    private void clickListViewItem(List<AccessibilityNodeInfo> items) {
        for (AccessibilityNodeInfo info : items) {
            if (isNewMessage(info)) {
                clickKeyWorkView(info.findAccessibilityNodeInfosByViewId(MESSAGE_TEXT_VIEW_ID));
            }
        }
    }

    @SuppressLint("NewApi")
    private boolean isNewMessage(AccessibilityNodeInfo accessibilityNodeInfo) {
        List<AccessibilityNodeInfo> accessibilityNodeInfos = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(NEW_MESSAGE_IMG_ID);
        return !accessibilityNodeInfos.isEmpty();

    }


    @Override
    public void onInterrupt() {
//        Log.e("MyAccessibilityService", "onInterrupt: 服务终结 ");
//        Toast.makeText(this, "服务终结", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "断开服务", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    protected void onServiceConnected() {
        Toast.makeText(this, "启动服务", Toast.LENGTH_SHORT).show();
        super.onServiceConnected();

    }

    private void printEventType(int eventType) {
        switch (eventType) {
            case AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED");
                //MyAccessibilityService", "onAccessibilityEvent: Change type for TYPE_WINDOW_CONTENT_CHANGED event: The type of change is not defined."
                break;
            case AccessibilityEvent.MAX_TEXT_LENGTH:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.MAX_TEXT_LENGTH:");
                //MyAccessibilityService", "onAccessibilityEvent:Maximum length of the text fields."
                break;
            case AccessibilityEvent.TYPES_ALL_MASK:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPES_ALL_MASK:");
                //MyAccessibilityService", "onAccessibilityEvent:Mask for AccessibilityEvent all types."
                //MyAccessibilityService", "onAccessibilityEvent:Invalid selection/focus position."
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_ANNOUNCEMENT:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of an application making an announcement."
                break;
            case AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of the assistant currently reading the users screen context."
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_GESTURE_DETECTION_END:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of ending gesture detection."
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_GESTURE_DETECTION_START:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of beginning gesture detection."
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event showing a Notification."
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:");
                //MyAccessibilityService", "onAccessibilityEvent: Represents the event of ending a touch exploration gesture."
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of starting a touch exploration gesture."
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of the user ending to touch the screen."
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:");
                //MyAccessibilityService", "onAccessibilityEvent: Represents the event of the user starting to touch the screen."
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of gaining accessibility focus."
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of clearing accessibility focus."
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_CLICKED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of clicking on a View like Button, CompoundButton, etc."
                //MyAccessibilityService", "onAccessibilityEvent: Change type for TYPE_WINDOW_CONTENT_CHANGED event: A node in the subtree rooted at the source node was added or removed."
                break;
            case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of a context click on a View."
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_FOCUSED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of setting input focus of a View."
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of a hover enter over a View."
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of a hover exit over a View."
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of long clicking on a View like Button, CompoundButton, etc."
                //MyAccessibilityService", "onAccessibilityEvent: Change type for TYPE_WINDOW_CONTENT_CHANGED event: The node's text changed."
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_SCROLLED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of scrolling a view."
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_SELECTED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of selecting an item usually in the context of an AdapterView."
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of changing the text of an EditText."
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of changing the selection in an EditText."
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of traversing the text of a view at a given movement granularity."
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_WINDOWS_CHANGED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event change in the windows shown on the screen."
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of changing the content of a window and more specifically the sub-tree rooted at the event's source."
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.e("MyAccessibilityService", "printEventType: AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:");
                //MyAccessibilityService", "onAccessibilityEvent:Represents the event of opening a PopupWindow, Menu, Dialog, etc."
                break;
        }
    }

}

