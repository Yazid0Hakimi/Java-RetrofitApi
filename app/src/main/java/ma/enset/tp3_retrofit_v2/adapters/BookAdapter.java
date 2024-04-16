package ma.enset.tp3_retrofit_v2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import ma.enset.tp3_retrofit_v2.R;
import ma.enset.tp3_retrofit_v2.models.Book;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_book_item, parent, false);
        }

        Book book = getItem(position);
        ImageView imageViewBook = convertView.findViewById(R.id.imageViewBook);
        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewAuthors = convertView.findViewById(R.id.textViewAuthors);
        TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);
        textViewTitle.setText(book.getVolumeInfo().getTitle());
        textViewAuthors.setText(book.getVolumeInfo().getAuthors().toString());
//        textViewDescription.setText(book.getVolumeInfo().getDescription().substring(0, 20)+"...");
        Picasso.get().load(book.getVolumeInfo().getImageLinks().getThumbnail().replace("http://", "https://")).into(imageViewBook);

        return convertView;
    }
}
