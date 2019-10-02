package com.dotcomdotcom.barcodescanner;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BookFinder {

    Context m_Context;

    public BookFinder(Context context) {
        m_Context = context;
    }

    public void findISBN(String isbn, Response.Listener<JSONObject> listener) {

        RequestQueue requestQueue = Volley.newRequestQueue(m_Context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data", null, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(m_Context, "Something went wrong !!", Toast.LENGTH_LONG).show();
                        Log.e("JsonRequest", error.toString());
                        VolleyLog.e("JsonRequest", error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

}
