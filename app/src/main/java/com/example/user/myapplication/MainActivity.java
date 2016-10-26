package com.example.user.myapplication;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.parse.Parse;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Note> notesArrayList;
    private NotesService noteService;
    private GridView gridView;
    private ArrayAdapter<Note> notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Your Back4app application ID")
                .clientKey("You can found it on b4a core setting")
                .server("https://parseapi.back4app.com/").build()
        );

        noteService = new InternalStorageImplement(this);

        gridView = (GridView)findViewById(R.id.grid);

        ArrayList<Note> notesArrayList = new ArrayList<>();

        notesAdapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1,  notesArrayList);

        gridView.setAdapter(notesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = notesAdapter.getItem(position);

                //TODO: do stuff
            }
        });


        new LoadAllNotes().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadAllNotes extends AsyncTask<Void, Void, ArrayList<Note>>
    {
        @Override
        protected ArrayList<Note> doInBackground(Void... params)
        {
            ArrayList<String> fileNames = noteService.getNoteNames();
            ArrayList<Note> allNotes = new ArrayList<>();

            for (String name : fileNames)
            {
                Note note = noteService.loadNote(name);
                allNotes.add(note);
            }

            return allNotes;
        }

        @Override
        protected void onPostExecute(ArrayList<Note> allNotes)
        {
            notesAdapter.clear();

            for (Note note : allNotes)
                notesAdapter.add(note);

            notesAdapter.notifyDataSetChanged();
        }
    }

    class NewNoteTask extends AsyncTask<Void, Void, Note>
    {
        @Override
        protected Note doInBackground(Void... params)
        {
            Note note =noteService.createNewNote();
            return note;
        }

        @Override
        protected void onPostExecute(Note note)
        {
            Intent intent=new Intent(MainActivity.this,EditNote.class);
            intent.putExtra("edit",note);

        }
    }
}
