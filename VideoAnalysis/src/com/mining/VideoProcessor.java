package com.mining;

import com.googlecode.javacv.CanvasFrame;
import static com.googlecode.javacv.cpp.opencv_core.*;
//import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.CvVideoWriter;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

public class VideoProcessor {

	private static long delay = 0;
	private final CvSize frameSize;
	private final CvCapture capture;
	private double actualFrameRate;

	public VideoProcessor(CvCapture capture) {
		this.capture = capture;
		// get size of from the capture device
		double w = cvGetCaptureProperty(capture, CV_CAP_PROP_FRAME_WIDTH);
		double h = cvGetCaptureProperty(capture, CV_CAP_PROP_FRAME_HEIGHT);

		frameSize = new CvSize((int) w, (int) h);

	}

	public void run() throws Exception {
		CvVideoWriter writer = createWriter();

		CanvasFrame inputCanvas = createCanvas("input");
		CanvasFrame outputCanvas = createCanvas("output");

		IplImage frame = null;

		while ((frame = readNextFrame()) != null) {

			// Display input frame, if canvas was created
			inputCanvas.showImage(frame);

			// calling the process function or method
			IplImage output = process(frame);

			// write output sequence
			writeNextFrame(writer, output);

			// Display output frame, if canvas was created
			outputCanvas.showImage(output);

			// introduce a delay
			if (delay > 0)
				Thread.sleep(delay);
		}

		// Release writer (if created) to make sure that data is flushed to the
		// output file, and file is closed.
		cvReleaseVideoWriter(writer);
	}

	private IplImage process(IplImage frame) {
		IplImage dest = IplImage.create(cvGetSize(frame), frame.depth(), 1);
		// Convert to gray
		cvCvtColor(frame, dest, CV_BGR2GRAY);
		// Compute Canny edges
		cvCanny(dest, dest, 100, 200, 3);
		// Invert the image
		cvThreshold(dest, dest, 128, 255, CV_THRESH_BINARY_INV);

		return dest;
	}

	private CvVideoWriter createWriter() {
		actualFrameRate = cvGetCaptureProperty(capture, CV_CAP_PROP_FPS);
		int actualCodec = (int) cvGetCaptureProperty(capture, CV_CAP_PROP_FOURCC);

		return cvCreateVideoWriter("test.avi"/*FIXME*/, // filename
				actualCodec, // codec to be used
				actualFrameRate, // frame rate of the video !
				frameSize, // frame size
				1 // is colored
		);

	}

	private IplImage readNextFrame() {
		return cvGrabFrame(capture) != 0 ? cvRetrieveFrame(capture) : null;
	}

	private void writeNextFrame(CvVideoWriter writer, IplImage frame)
			throws Exception {
		if (writer != null && cvWriteFrame(writer, frame) != 0) {
			throw new Exception("Video writing failed.");
		}
	}

	private CanvasFrame createCanvas(String title) {
		if (title != null && !title.isEmpty()) {
			return new CanvasFrame(title, 1);
		}

		return null;
	}

	public double getFrameRate() {
		return actualFrameRate;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
}
