package com.japkaur.one;

/**
 * Created by jap on 20/1/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {
    public static final String TAG = BooksAdapter.class.getSimpleName();

    private List<Book> books;

    private Context context;


    public static class BooksViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout booksLayout;
        TextView bookTitle;
        TextView author;
        TextView quantity;

        public BooksViewHolder(View v) {
            super(v);
            booksLayout = (RelativeLayout) v.findViewById(R.id.bookslayout);
            bookTitle = (TextView) v.findViewById(R.id.BookName);
            author = (TextView) v.findViewById(R.id.author);
            quantity = (TextView) v.findViewById(R.id.quantity);
        }
    }

    public BooksAdapter(List<Book> books, Context context) {
        this.books = books;

        this.context = context;
    }

    @Override
    public BooksAdapter.BooksViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_books, parent, false);
        return new BooksViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BooksViewHolder holder, final int position) {
        //holder.bookTitle.setText(books.get(position).getTitle());
        //holder.author.setText(books.get(position).getAuthor());
        //holder.quantity.setText(books.get(position).getQuantity());
        Log.e(TAG ,"Error!" );
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}