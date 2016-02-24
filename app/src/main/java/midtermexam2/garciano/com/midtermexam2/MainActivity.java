package midtermexam2.garciano.com.midtermexam2;

import android.app.ListActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {


    private static String url = "http://172.17.3.51:5856/api/books";

    /*
        private static final String TAG_CONTACTS = "contacts";
        private static final String TAG_ID = "id";
        private static final String TAG_NAME = "name";
        private static final String TAG_EMAIL = "email";
        private static final String TAG_ADDRESS = "address";
        private static final String TAG_GENDER = "gender";
        private static final String TAG_PHONE = "phone";
        private static final String TAG_PHONE_MOBILE = "mobile";
        private static final String TAG_PHONE_HOME = "home";
        private static final String TAG_PHONE_OFFICE = "office";
        */
    private static final String TAG_BOOKS = "books";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_GENRE = "genre";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_ISREAD = "isread";

    JSONArray books = null;

    ArrayList<HashMap<String, String>> bookList;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bookList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();


        new GetBooks().execute();

    }


    private class GetBooks extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

        }

        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    books = jsonObj.getJSONArray(TAG_BOOKS);

                    // looping through All Contacts
                    for (int i = 0; i < books.length(); i++) {
                        JSONObject b = books.getJSONObject(i);

                        String id = b.getString(TAG_ID);
                        String title = b.getString(TAG_TITLE);
                        String genre = b.getString(TAG_GENRE);
                        String author = b.getString(TAG_AUTHOR);
                        String isread = b.getString(TAG_ISREAD);


                        // tmp hashmap for single contact
                        HashMap<String, String> book = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        book.put(TAG_ID, id);
                        book.put(TAG_TITLE, title);
                        book.put(TAG_GENRE, genre);
                        book.put(TAG_AUTHOR, author);
                        book.put(TAG_ISREAD, isread);

                        // adding contact to contact list
                        bookList.add(book);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, bookList,
                    R.layout.listview_item, new String[] { TAG_TITLE}, new int[] { R.id.name});

            setListAdapter(adapter);
        }

    }


}
