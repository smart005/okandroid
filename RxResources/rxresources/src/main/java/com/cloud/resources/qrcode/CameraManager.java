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

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * This object wraps the Camera service object and expects to be the only one
 * talking to it. The implementation encapsulates the steps needed to take
 * preview-sized images, which are used for both preview and decoding.
 * 
 */
public final class CameraManager {

	private final int MIN_FRAME_SIZE = 320;
	private final int MAX_FRAME_SIZE = 480;

	public final int SDK_INT; // Later we can use Build.VERSION.SDK_INT

	private final Context context;
	private final CameraConfigurationManager configManager;
	private Camera camera;
	private Rect framingRect;
	private Rect framingRectInPreview;
	private boolean initialized;
	private boolean previewing;
	private final boolean useOneShotPreviewCallback;

	/**
	 * Preview frames are delivered here, which we pass on to the registered
	 * handler. Make sure to clear the handler so it will only receive one
	 * message.
	 */
	private final PreviewCallback previewCallback;
	/**
	 * Autofocus callbacks arrive here, and are dispatched to the Handler which
	 * requested them.
	 */
	private final AutoFocusCallback autoFocusCallback;

	public CameraManager(Context context) {
		this.context = context;
		int sdkInt;
		try {
			sdkInt = Integer.parseInt(Build.VERSION.SDK);
		} catch (NumberFormatException nfe) {
			// Just to be safe
			sdkInt = 10000;
		}
		SDK_INT = sdkInt;
		this.configManager = new CameraConfigurationManager(context, this);
		// Camera.setOneShotPreviewCallback() has a race condition in Cupcake,
		// so we use the older
		// Camera.setPreviewCallback() on 1.5 and earlier. For Donut and later,
		// we need to use
		// the more efficient one shot callback, as the older one can swamp the
		// system and cause it
		// to run out of memory. We can't use SDK_INT because it was introduced
		// in the Donut SDK.
		// useOneShotPreviewCallback = Integer.parseInt(Build.VERSION.SDK) >
		// Build.VERSION_CODES.CUPCAKE;
		useOneShotPreviewCallback = Integer.parseInt(Build.VERSION.SDK) > 3; // 3
																				// =
																				// Cupcake
		previewCallback = new PreviewCallback(configManager,
				useOneShotPreviewCallback);
		autoFocusCallback = new AutoFocusCallback();
	}

	/**
	 * Opens the camera driver and initializes the hardware parameters.
	 * 
	 * @param holder
	 *            The surface object which the camera will draw preview frames
	 *            into.
	 * @throws IOException
	 *             Indicates the camera driver failed to open.
	 */
	public void openDriver(SurfaceHolder holder) throws IOException {
		if (camera == null) {
			camera = Camera.open();
			if (camera == null) {
				throw new IOException();
			}
			camera.setPreviewDisplay(holder);

			if (!initialized) {
				initialized = true;
				configManager.initFromCameraParameters(camera);
			}
			configManager.setDesiredCameraParameters(camera);
			FlashlightManager.enableFlashlight();
		}
	}

	/**
	 * Closes the camera driver if still in use.
	 */
	public void closeDriver() {
		if (camera != null) {
			FlashlightManager.disableFlashlight();
			camera.release();
			camera = null;
		}
	}

	public void enableFlashlight() {
		Parameters parameters = camera.getParameters();
		parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(parameters);
		camera.autoFocus(new Camera.AutoFocusCallback() {
			public void onAutoFocus(boolean success, Camera camera) {
			}
		});
		// startPreview();
	}

	public void disableFlashlight() {
		Parameters p = camera.getParameters();
		p.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(p);
		// stopPreview();
	}

	/**
	 * Asks the camera hardware to begin drawing preview frames to the screen.
	 */
	public void startPreview() {
		if (camera != null && !previewing) {
			camera.startPreview();
			previewing = true;
		}
	}

	/**
	 * Tells the camera to stop drawing preview frames.
	 */
	public void stopPreview() {
		if (camera != null && previewing) {
			if (!useOneShotPreviewCallback) {
				camera.setPreviewCallback(null);
			}
			camera.stopPreview();
			previewCallback.setHandler(null, 0);
			autoFocusCallback.setHandler(null, 0);
			previewing = false;
		}
	}

	/**
	 * A single preview frame will be returned to the handler supplied. The data
	 * will arrive as byte[] in the message.obj field, with width and height
	 * encoded as message.arg1 and message.arg2, respectively.
	 * 
	 * @param handler
	 *            The handler to send the message to.
	 * @param message
	 *            The what field of the message to be sent.
	 */
	public void requestPreviewFrame(Handler handler, int message) {
		if (camera != null && previewing) {
			previewCallback.setHandler(handler, message);
			if (useOneShotPreviewCallback) {
				camera.setOneShotPreviewCallback(previewCallback);
			} else {
				camera.setPreviewCallback(previewCallback);
			}
		}
	}

	/**
	 * Asks the camera hardware to perform an autofocus.
	 * 
	 * @param handler
	 *            The Handler to notify when the autofocus completes.
	 * @param message
	 *            The message to deliver.
	 */
	public void requestAutoFocus(Handler handler, int message) {
		if (camera != null && previewing) {
			autoFocusCallback.setHandler(handler, message);
			// Log.d(TAG, "Requesting auto-focus callback");
			camera.autoFocus(autoFocusCallback);
		}
	}

	/**
	 * Calculates the framing rect which the UI should draw to show the user
	 * where to place the barcode. This target helps with alignment as well as
	 * forces the user to hold the device far enough away to ensure the image
	 * will be in focus.
	 * 
	 * @return The rectangle to draw on screen in window coordinates.
	 */
	public Rect getFramingRect(int cornerWidth, int rectWidth, int rectHeight) {
		// Point screenResolution = configManager.getScreenResolution();
		if (framingRect == null) {
			if (camera == null) {
				return null;
			}
			int size = rectWidth * 4 / 5;
			if (size < MIN_FRAME_SIZE) {
				size = MIN_FRAME_SIZE;
			} else if (size > MAX_FRAME_SIZE) {
				size = MAX_FRAME_SIZE;
			}
			int leftOffset = (rectWidth - size - 2 * cornerWidth) / 2;
			int topOffset = (rectHeight - size - 2 * cornerWidth) / 2;
			int resize = 120;
			framingRect = new Rect(leftOffset, topOffset - resize, leftOffset
					+ size + 2 * cornerWidth, topOffset + size + 2
					* cornerWidth + resize);
		}
		return framingRect;
	}

	/**
	 * Like {@link #getFramingRect} but coordinates are in terms of the preview
	 * frame, not UI / screen.
	 */
	private Rect getFramingRectInPreview(int cornerWidth, int rectWidth,
			int rectHeight) {
		if (framingRectInPreview == null) {
			Rect rect = new Rect(getFramingRect(cornerWidth, rectWidth,
					rectHeight));
			Point cameraResolution = configManager.getCameraResolution();
			Point screenResolution = configManager.getScreenResolution();
			rect.left = rect.left * cameraResolution.y / screenResolution.x;
			rect.right = rect.right * cameraResolution.y / screenResolution.x;
			rect.top = rect.top * cameraResolution.x / screenResolution.y;
			rect.bottom = rect.bottom * cameraResolution.x / screenResolution.y;
			framingRectInPreview = rect;
		}
		return framingRectInPreview;
	}

	/**
	 * Converts the result points from still resolution coordinates to screen
	 * coordinates.
	 * 
	 * @param points
	 *            The points returned by the Reader subclass through
	 *            Result.getResultPoints().
	 * @return An array of Points scaled to the size of the framing rect and
	 *         offset appropriately so they can be drawn in screen coordinates.
	 */
	/*
	 * public Point[] convertResultPoints(ResultPoint[] points) { Rect frame =
	 * getFramingRectInPreview(); int count = points.length; Point[] output =
	 * new Point[count]; for (int x = 0; x < count; x++) { output[x] = new
	 * Point(); output[x].x = frame.left + (int) (points[x].getX() + 0.5f);
	 * output[x].y = frame.top + (int) (points[x].getY() + 0.5f); } return
	 * output; }
	 */

	/**
	 * A factory method to build the appropriate LuminanceSource object based on
	 * the format of the preview buffers, as described by Camera.Parameters.
	 * 
	 * @param data
	 *            A preview frame.
	 * @param width
	 *            The width of the image.
	 * @param height
	 *            The height of the image.
	 * @return A PlanarYUVLuminanceSource instance.
	 */
	public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data,
			int width, int height, int cornerWidth, int rectWidth,
			int rectHeight) {
		Rect rect = getFramingRectInPreview(cornerWidth, rectWidth, rectHeight);
		int previewFormat = configManager.getPreviewFormat();
		String previewFormatString = configManager.getPreviewFormatString();
		// PixelFormat.YCbCr_420_SP
		// This is the standard Android format which all devices are REQUIRED to
		// support.
		// In theory, it's the only one we should ever care about.
		// PixelFormat.YCbCr_422_SP
		// This format has never been seen in the wild, but is compatible as we
		// only care
		// about the Y channel, so allow it.
		// =========other==============
		// The Samsung Moment incorrectly uses this variant instead of the 'sp'
		// version.
		// Fortunately, it too has all the Y data up front, so we can read it.
		if (previewFormat == PixelFormat.YCbCr_420_SP
				|| previewFormat == PixelFormat.YCbCr_422_SP) {
			return new PlanarYUVLuminanceSource(data, width, height, rect.left,
					rect.top, rect.width(), rect.height());
		} else {
			if (TextUtils.equals(previewFormatString, "yuv420p")) {
				return new PlanarYUVLuminanceSource(data, width, height,
						rect.left, rect.top, rect.width(), rect.height());
			}
		}
		throw new IllegalArgumentException("Unsupported picture format: "
				+ previewFormat + '/' + previewFormatString);
	}

	public Context getContext() {
		return context;
	}

}
