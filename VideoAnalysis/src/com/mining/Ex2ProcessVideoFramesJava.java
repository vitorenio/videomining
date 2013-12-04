package com.mining;

import static com.googlecode.javacv.cpp.opencv_highgui.cvCreateFileCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvReleaseCapture;

import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;

public class Ex2ProcessVideoFramesJava {
	
	private static final int MAX_CORNERS = 500;

	public static void main(String[] args) {
		// Open video file
	    CvCapture capture = cvCreateFileCapture("data/bike.avi");

	    // Create video processor instance
	    VideoProcessor processor = new VideoProcessor(capture);
	    // Play the video at the original frame rate
	    processor.setDelay(Math.round(1000d / processor.getFrameRate()));

	    // Start the processing loop
	    try {
			processor.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	    // Close the video file
	    cvReleaseCapture(capture);
    }

}
