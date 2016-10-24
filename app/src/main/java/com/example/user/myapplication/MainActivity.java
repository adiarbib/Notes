package com.example.user.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Note> notesArrayList;
    private NotesService noteService;
    private GridView gridView;
    private ArrayAdapter<Note> notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteService = new InternalStorageImplement(this);

        gridView = (GridView)findViewById(R.id.grid);

        /*ImageButton plus=(ImageButton)findViewById(R.id.plus_btn);
        ImageButton minus=(ImageButton)findViewById(R.id.minus_btn);*/

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

        /*plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesAdapter.add(noteService.createNewNote());
            }
        });*/

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

    class NewNoteTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            noteService.createNewNote();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {



        }
    }
}
