//package com.devgd.melonclone.global.media;
//
//import android.os.Handler;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//public class MediaController {
//    /**
//     *
//     controller.setAnchorView(
//     (FrameLayout) ((Activity) mContext).findViewById(R.id.videoSurfaceContainer),
//     (ViewGroup) ((Activity) mContext).findViewById(R.id.custom_controller));
//     controller.setPrevNextListeners(nextListener, prevListener);
//
//     * @param controller
//     * @param controllerHandler
//     */
//    public void setController(MelonMediaController controller, Handler controllerHandler) {
//        this.controller = controller;
//        this.controllerHandler = controllerHandler;
//
//        controllerInit();
//    }
//
//    public void controllerInit() {
//        controller.setPlayer(this);
//        controller.show();
//    }
//
//    public void removeController() {
//        this.controller = null;
//        this.controllerHandler = null;
//    }
//
//    public void controllerShow() {
//        if (controller != null) {
//            controller.show();
//        }
//    }
//
//    public void setSurfaceView(SurfaceView videoSurface) {
//        SurfaceHolder videoHolder = videoSurface.getHolder();
//        videoHolder.addCallback(this);
//    }
//}
