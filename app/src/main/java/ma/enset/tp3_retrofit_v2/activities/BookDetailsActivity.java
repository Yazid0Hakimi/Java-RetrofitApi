package ma.enset.tp3_retrofit_v2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import ma.enset.tp3_retrofit_v2.R;
import ma.enset.tp3_retrofit_v2.models.Book;

public class BookDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details_activity);
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra("book");
        TextView textViewTitle = findViewById(R.id.textViewTitle1);
        TextView textViewAuthors = findViewById(R.id.textViewAuthors1);
        TextView textViewDescription = findViewById(R.id.textViewDescription1);
        ImageView imageViewBook = findViewById(R.id.imageViewBook1);
        Button btnShare = findViewById(R.id.btn_share);

        textViewTitle.setText(book.getVolumeInfo().getTitle());
        textViewAuthors.setText(book.getVolumeInfo().getAuthors().toString());
        textViewDescription.setText(book.getVolumeInfo().getDescription());
        Picasso.get().load(book.getVolumeInfo().getImageLinks().getThumbnail().replace("http://", "https://")).into(imageViewBook);
        btnShare.setOnClickListener(view -> {
            String sharedMsg = "Titre :"+book.getVolumeInfo().getTitle()+"\n"
                    +"Authors :"+book.getVolumeInfo().getAuthors().toString()+"\n"
                    +"Description :"+book.getVolumeInfo().getDescription();
            Intent intent1 = new Intent(Intent.ACTION_SEND);
            intent1.setType("text/plain");
            intent1.putExtra(Intent.EXTRA_TEXT, sharedMsg);
            startActivity(Intent.createChooser(intent1, "Partager via"));
        });
    }
}
