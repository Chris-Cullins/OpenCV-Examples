package com.example.example_image_blurring;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.example.example_image_blurring.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	private Button button;
	
	private Bitmap myBitmap;
	private Mat image;
	private Mat imageDest;
	private ImageView imageView1;
	private int blurNumOne;
	private int blurNumTwo;
	
	private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
		   switch (status) {
		       case LoaderCallbackInterface.SUCCESS:
		       {
		      Log.i("TAG", "OpenCV loaded successfully");
		      // Create and set View
		      setContentView(R.layout.activity_main);
		      Context context = getBaseContext();
		      readImage(context);
		       } break;
		       default:
		       {
		      super.onManagerConnected(status);
		       } break;
		   }
		    }
		};

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_2, this, mOpenCVCallBack))
	    {
	      Log.e("TAG", "Cannot connect to OpenCV Manager");
	    }
		
		
		
	}
	
	
	public void readImage(Context context) {
		
		blurNumOne = 5;
		blurNumTwo = 5;
		
		InputStream open;
		
		
		AssetManager manager = getAssets(); 
		try {
			open = manager.open("islandview.jpg");
			myBitmap = BitmapFactory.decodeStream(open);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		
		imageView1.setImageBitmap(myBitmap);
		
		image = new Mat(); 
		
		Utils.bitmapToMat(myBitmap, image);
		
		imageDest = image.clone(); 
		
		button = (Button) findViewById(R.id.button1);
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				blurNumOne += 5;
				blurNumTwo += 5; 
				Imgproc.blur(image, imageDest, new Size(blurNumOne, blurNumTwo));
				Utils.matToBitmap(imageDest, myBitmap);
				Log.e("TAG", "Image has been blurred!");
				imageView1.setImageBitmap(myBitmap);
			}
			
		});
		
	}
}
