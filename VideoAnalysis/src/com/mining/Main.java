package com.mining;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class Main {
	public static void main(String[] args) {

		// Load image img1 as IplImage
		final IplImage image = cvLoadImage("img1.jpg");

		// create canvas frame named 'Demo'
		final CanvasFrame canvas = new CanvasFrame("Demo");

		// Show image in canvas frame
		canvas.showImage(image);

		// This will close canvas frame on exit
		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
	
//	public static void main(String[] args) {
//		// 0-default camera, 1 - next...so on 
//		  final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
//		  try { 
//		    grabber.start();
//		    IplImage img = grabber.grab();
//		    if (img != null) { 
//		      cvSaveImage("capture.jpg", img);
//		    }
//		  } catch (Exception e) { 
//		    e.printStackTrace(); 
//		  } 
//	}
}
