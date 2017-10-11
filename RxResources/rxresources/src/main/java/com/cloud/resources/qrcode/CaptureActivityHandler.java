/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.resources.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.cloud.resources.R;
import com.cloud.resources.RedirectUtils;

import java.util.Vector;

/**
 * This class handles all the messaging which comprises the state machine for
 * capture.
 */
public final class CaptureActivityHandler extends Handler {

    private Activity curractivity = null;
    private final DecodeThread decodeThread;
    private State state;
    private ViewfinderView mviewfinderView = null;
    private CaptureListener mclistener = null;

    private enum State {
        PREVIEW, SUCCESS, DONE
    }

    public CaptureActivityHandler(Activity activity,
                                  Vector<BarcodeFormat> decodeFormats, String characterSet,
                                  ViewfinderView viewfinderView, CaptureListener clistener,
                                  DecodeListener dlistener) {
        this.curractivity = activity;
        this.mviewfinderView = viewfinderView;
        this.mclistener = clistener;
        decodeThread = new DecodeThread(decodeFormats, characterSet,
                new ViewfinderResultPointCallback(viewfinderView), dlistener,
                viewfinderView.CORNER_WIDTH, viewfinderView.rectWidth,
                viewfinderView.rectHeight, viewfinderView.getCameraManager());
        decodeThread.start();
        state = State.SUCCESS;
        // Start ourselves capturing previews and decoding.
        viewfinderView.getCameraManager().startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what == R.id.auto_focus) {
            // Log.d(TAG, "Got auto-focus message");
            // When one auto focus pass finishes, start another. This is the
            // closest thing to
            // continuous AF. It does seem to hunt a bit, but I'm not sure what
            // else to do.
            if (state == State.PREVIEW) {
                mviewfinderView.getCameraManager().requestAutoFocus(this,
                        R.id.auto_focus);
            }
        } else if (message.what == R.id.restart_preview) {
            restartPreviewAndDecode();
        } else if (message.what == R.id.decode_succeeded) {
            state = State.SUCCESS;
            Bundle bundle = message.getData();
            /***********************************************************************/
            Bitmap barcode = bundle == null ? null : (Bitmap) bundle
                    .getParcelable(DecodeThread.BARCODE_BITMAP);
            if (mclistener != null) {
                mclistener.handleDecode((Result) message.obj, barcode);
            }
            /***********************************************************************/
        } else if (message.what == R.id.decode_failed) {
            // We're decoding as fast as possible, so when one decode fails,
            // start another.
            state = State.PREVIEW;
            mviewfinderView.getCameraManager().requestPreviewFrame(
                    decodeThread.getHandler(), R.id.decode);
        } else if (message.what == R.id.return_scan_result) {
            curractivity.setResult(Activity.RESULT_OK, (Intent) message.obj);
            RedirectUtils.finishActivity(curractivity);
        } else if (message.what == R.id.launch_product_query) {
            String url = (String) message.obj;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            curractivity.startActivity(intent);
        }
    }

    public void quitSynchronously() {
        state = State.DONE;
        mviewfinderView.getCameraManager().stopPreview();
        Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
        quit.sendToTarget();
        try {
            decodeThread.join();
        } catch (InterruptedException e) {
            // continue
        }
        // Be absolutely sure we don't send any queued up messages
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }

    private void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            mviewfinderView.getCameraManager().requestPreviewFrame(
                    decodeThread.getHandler(), R.id.decode);
            mviewfinderView.getCameraManager().requestAutoFocus(this,
                    R.id.auto_focus);
            mviewfinderView.drawViewfinder();
        }
    }
}
