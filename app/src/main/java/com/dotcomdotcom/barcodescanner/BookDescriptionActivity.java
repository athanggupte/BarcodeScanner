package com.dotcomdotcom.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BookDescriptionActivity extends AppCompatActivity {

    BookFinder bookFinder;

    TextView titleTV, urlTV, authorsTV, publishersTV, pubDateTV;
    ImageView coverIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);

        bookFinder = new BookFinder(this);

        titleTV = (TextView) findViewById(R.id.title_tv);
        urlTV = (TextView) findViewById(R.id.url_tv);
        authorsTV = (TextView) findViewById(R.id.authors_tv);
        publishersTV = (TextView) findViewById(R.id.publishers_tv);
        pubDateTV = (TextView) findViewById(R.id.pub_date_tv);
        coverIV = (ImageView) findViewById(R.id.cover_img);

        Log.d("ISBN", getIntent().getExtras().getString("isbn"));

        bookFinder.findISBN(getIntent().getExtras().getString("isbn"),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Log.d("JsonReceived", response.toString());

                            if (response.length() > 0) {
                                Book book = new Book(response.getJSONObject("ISBN:" + getIntent().getExtras().getString("isbn")));

                                Log.d("BookReceived", book.toString());

                                titleTV.setText(book.getTitle());
                                urlTV.setText(book.getUrl());
                                authorsTV.setText(book.getAuthorsString());
                                publishersTV.setText(book.getPublishersString());
                                pubDateTV.setText(book.getPublishDate());
                                new ImageLoader(coverIV).execute(book.getImageUrl());
                            } else {
                                Toast.makeText(getApplicationContext(),"Could not find the book", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private static class ImageLoader extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;

        public ImageLoader(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap imgBmp = null;

            try {
                URL url = new URL(urls[Book.IMG_LARGE]);

                imgBmp = BitmapFactory.decodeStream(url.openStream());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return imgBmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
