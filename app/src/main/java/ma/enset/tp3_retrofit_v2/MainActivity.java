package ma.enset.tp3_retrofit_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import ma.enset.tp3_retrofit_v2.activities.BookDetailsActivity;
import ma.enset.tp3_retrofit_v2.adapters.BookAdapter;
import ma.enset.tp3_retrofit_v2.api.GoogleBooksService;
import ma.enset.tp3_retrofit_v2.models.Book;
import ma.enset.tp3_retrofit_v2.models.GoogleBooksResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Book> books;
    ListView listViewBooks;
    Button searchBtn;
    EditText editTextQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        books = new ArrayList<>();
        BookAdapter bookAdapter = new BookAdapter(this, R.layout.list_book_item, books);
        listViewBooks = findViewById(R.id.listViewBooks);
        searchBtn = findViewById(R.id.searchBtn);
        editTextQuery = findViewById(R.id.editTextQuery);
        listViewBooks.setAdapter(bookAdapter);
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GoogleBooksService booksService = retrofit.create(GoogleBooksService.class);
        searchBtn.setOnClickListener(v -> {
            String query = editTextQuery.getText().toString();
            Call<GoogleBooksResponse> call = booksService.searchBooks(query);
            call.enqueue(new Callback<GoogleBooksResponse>() {
                @Override
                public void onResponse(Call<GoogleBooksResponse> call, Response<GoogleBooksResponse> response) {
                    GoogleBooksResponse booksResponse = response.body();
                    books.clear();
                    books.addAll(booksResponse.getItems());
                    bookAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<GoogleBooksResponse> call, Throwable throwable) {
//                throwable.printStackTrace();
                    Log.i("info", "no");
                    Toast.makeText(MainActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT);
                }
            });
        });

        listViewBooks.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), BookDetailsActivity.class);
            intent.putExtra("book", books.get(i));
            startActivity(intent);
        });
    }
}