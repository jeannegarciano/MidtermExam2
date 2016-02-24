package midtermexam2.garciano.com.midtermexam2;

import android.app.ListActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private TextView booktitle;
    public static String url = "http://joseniandroid.herokuapp.com/api/books";

    public static final int SUCCESS_CODE = 200;

    public static final String PARAM_QUERY = "q";
    public static final String PARAM_MODE = "mode";
    public static final String PARAM_UNITS = "units";
    public static final String PARAM_API_KEY = "appId";
    private static final String OWM_CODE = "cod";

    private static final String TAG_BOOKS = "books";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_GENRE = "genre";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_ISREAD = "isread";
    private static final String OWM_MAIN = "main";

    public static Books getBooks(Uri uri, @NonNull String requestMethod) {
        String json = HttpUtils.getResponse(uri, requestMethod);
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        final String mid;
        final String mtitle;
        final String mgenre;
        final String mauthor;
        final boolean misread;


        try {
            JSONArray jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonRootObject.optJSONArray("Books");


            int statusCode = jsonArray.getJSONArray(OWM_CODE);
            if (statusCode == SUCCESS_CODE) {
            for(int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                mid = jsonArray.getJSONArray(TAG_ID);
                mtitle = jsonArray.getJSONArray(TAG_TITLE);
                mgenre = jsonArray.getJSONArray(TAG_GENRE);
                mauthor = jsonArray.getJSONArray(TAG_AUTHOR);
                misread = jsonArray.getJSONArray(TAG_ISREAD);


                Books book = new Books()
                        .setId(mid)
                        .setTitle(mtitle)
                        .setGenre(mgenre)
                        .setAuthor(mauthor)
                        .setIsread(misread);
             }
                return book;
            } else {
                return null;
            }
        } catch (
                JSONException e
                )

        {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView lv = getListView();

        booktitle = (TextView)findViewById(R.id.name);


    }


    public class FetchWeatherTask extends AsyncTask<String, Void, Books> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Books doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }



            Uri builtUri = Uri.parse(MainActivity.url).buildUpon()
                    .appendQueryParameter(MainActivity.PARAM_API_KEY,
                            BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                    .build();

            return MainActivity.getBooks(builtUri, "GET");
        }

        @Override
        protected void onPostExecute(Books b) {
            if (b == null) {

                return;

            }


            booktitle.setText(String.format(b.getTitle()));


        }

    }
}