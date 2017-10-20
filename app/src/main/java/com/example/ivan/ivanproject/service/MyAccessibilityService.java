package com.example.ivan.ivanproject.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int eventType = accessibilityEvent.getEventType();
        printEventType(eventType);
        for (CharSequence txt : accessibilityEvent.getText()) {
            Log.e("MyAccessibilityService", "onAccessibilityEvent:text=" + txt);
        }
        getHomepageView();

//        switch (eventType) {
//            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
//                break;
//            default:
//                break;
//        }
    }

    @SuppressLint("NewApi")
    private void getHomepageView() {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo != null) {
            List<AccessibilityNodeInfo> accessibilityNodeInfos = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ak3");
            getListView(accessibilityNodeInfo);

        }
    }

    public int findKeyWorkPosition(List<AccessibilityNodeInfo> accessibilityNodeInfos){
        for(int i = 0;i<accessibilityNodeInfos.size();i++){
            if ("ivan".equals(accessibilityNodeInfos.get(i).getText().toString().trim())) {
                return i;
            }
        }
        return -1;
    }

    @SuppressLint("NewApi")
    public ListView getListView(AccessibilityNodeInfo accessibilityNodeInfo){
        List<AccessibilityNodeInfo> accessibilityNodeInfos = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bt0");
        for(AccessibilityNodeInfo info:accessibilityNodeInfos){
            if("android.widget.ListView".equals(info.getClassName())){


//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }

            }
        }
        return null;
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this, "服务终结", Toast.LENGTH_SHORT).show();
        Log.e("MyAccessibilityService", "onInterrupt: 服务终结 ");

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MyAccessibilityService", "onUnbind: 断开服务");
        Toast.makeText(this, "断开服务", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    protected void onServiceConnected() {
        Log.e("MyAccessibilityService", "onServiceConnected: 启动服务");
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

