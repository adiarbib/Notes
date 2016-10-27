package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditNote extends AppCompatActivity {
    Note thisNote;
    private NotesService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        noteService=new InternalStorageImplement(this);
        Intent editIntent=getIntent();
        thisNote=(Note)editIntent.getSerializableExtra("edit");
        EditText editText=(EditText)findViewById(R.id.editText);
        editText.setText(thisNote.content.toString());




    }

    @Override
    public void onBackPressed() {
        noteService.updateNote(thisNote);
        super.onBackPressed();
    }
}
