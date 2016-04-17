package com.rahmat.myapp.latihansqlite;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {

    private CommentsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new CommentsDataSource(this);
        datasource.open();
        List<Comment> values = datasource.getAllComments();
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
// of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        switch (view.getId()) {
            case R.id.add:
                String[] comments = new String[] { "Keren", "Bagus Sekali", "Tidak Suka" };
                int nextInt = new Random().nextInt(3);
// save the new comment to the database
                comment = datasource.createComment(comments[nextInt]);
                adapter.add(comment);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    int last = getListAdapter().getCount() -1;
                    comment = (Comment) getListAdapter().getItem(last);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
            case R.id.deleteall:
                if(getListAdapter().getCount() > 0){
                    int last = getListAdapter().getCount()- 1;
                    for (int i = last; i >= 0; i--){
                        comment = (Comment) getListAdapter().getItem(i);
                        datasource.deleteComment(comment);
                        adapter.remove(comment);
                    }
                }break;
            }
            adapter.notifyDataSetChanged();
        }
        @Override
        protected void onResume() {
            datasource.open();
            super.onResume();
        }
        @Override
        protected void onPause() {
            datasource.close();
            super.onPause();
        }
}
