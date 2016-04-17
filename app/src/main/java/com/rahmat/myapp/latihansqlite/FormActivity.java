package com.rahmat.myapp.latihansqlite;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class FormActivity extends ListActivity {

    private CommentsDataSource datasource;
    EditText name, comment;
    Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        name = (EditText) findViewById(R.id.edit_name);
        comment = (EditText) findViewById(R.id.edit_comment);
        datasource = new CommentsDataSource(this);
        datasource.open();
        List<Comment> values = datasource.getAllComments();
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enter:
                //code goes here
                addNewCommentByUser();
                addName();
                break;
            case R.id.delete_one:
                delete();
                break;

            case R.id.delete_all:
                deleteAll();
                break;

            case R.id.move:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

        }

    }

    public void addNewCommentByUser() {
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment usercomment = null;
        String comments = comment.getText().toString();
        usercomment = datasource.createComment(comments);
        adapter.add(usercomment);
        adapter.notifyDataSetChanged();
    }

    public void addName() {
        TextView yourname = (TextView) findViewById(R.id.your_name);
        String intro = "Your Commenting as ";
        yourname.setText(intro + name.getText().toString());
    }

    public void delete() {
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        if (getListAdapter().getCount() > 0) {
            int last = getListAdapter().getCount() -1;
            comment = (Comment) getListAdapter().getItem(last);
            datasource.deleteComment(comment);
            adapter.remove(comment);
        }
        adapter.notifyDataSetChanged();
    }

    public void deleteAll() {
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        if (getListAdapter().getCount() > 0) {
            int last = getListAdapter().getCount() - 1;
            for (int i = last; i >= 0; i--) {
                comment = (Comment) getListAdapter().getItem(i);
                datasource.deleteComment(comment);
                adapter.remove(comment);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
