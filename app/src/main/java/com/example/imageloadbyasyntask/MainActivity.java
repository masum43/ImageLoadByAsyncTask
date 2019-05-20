package com.example.imageloadbyasyntask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageViewId);

        String pic = "https://www.opencollege.info/wp-content/uploads/2016/02/relaxation-skills.jpg";
        new imageLoadingTask().execute(pic); // ei method ta call hole doInBackground method ta o call hbe
                                    //execute er arguement e r o string pathano jabe. karon eti string type array


    }

    //inner class creating to use AsynTask Class
    public class imageLoadingTask extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) { // this method is worked as a background thread. ei method ti background thread e run kore
                                                             //here ... is a argument of string which is called var arguement , work as an array.

            String pic = strings[0];

            try {
                URL url = new URL(pic);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                //InputStreamReader inputStreamReader = new InputStreamReader(inputStream); eti inputstream k convert kore human readable e niye asto. image bole korar dorkar nai.. json niye kaj korle korbo
                httpURLConnection.connect();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() { //to initialize ui like to set anything on imageview. ei method ti main thread e run kore


            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) { //work paralally with doInBackground method but run on main thread.
            super.onProgressUpdate(values);               //video download korar tm e j loading dekhay ta er maddhome kora jay
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) { //Work as a Handler. so run on main thread.

            imageView.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }
}
