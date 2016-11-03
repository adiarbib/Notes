package com.example.user.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditNote extends AppCompatActivity {
    Note thisNote;
    private NotesService noteService;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        noteService=new InternalStorageImplement(this);
        Intent editIntent=getIntent();
        thisNote=(Note)editIntent.getSerializableExtra("edit");
        editText=(EditText)findViewById(R.id.editText);
        editText.setText(thisNote.getContent().toString());
    }

    @Override
    public void onBackPressed() {
        thisNote.setContent(editText.getText().toString());
        thisNote.setTitle(editText.getText().toString().split("\n")[0]);
        noteService.updateNote(thisNote);
        super.onBackPressed();
    }
}
