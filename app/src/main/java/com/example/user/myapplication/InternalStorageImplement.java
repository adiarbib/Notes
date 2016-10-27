package com.example.user.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by User on 06/10/2016.
 */
public class InternalStorageImplement implements NotesService {
    private Context context;

    public InternalStorageImplement(Context context) {
        this.context = context;
    }

    @Override
    public Note createNewNote() {
        String fileName = generateRandomId();
        try {
            context.openFileOutput(fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
        }

        Note note = new Note("", "", fileName);

        return note;
    }

    @Override
    public void updateNote(Note note) {
        try {
            FileOutputStream outputStream = context.openFileOutput(note.getFileName(), Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(note.content);
            outputStream.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getNoteNames() {
        ArrayList<String> result = new ArrayList<>();
        String[] fileNames = context.fileList();

        for (String name : fileNames) {
            if (!name.startsWith("NOTE"))
                continue;

            result.add(name);
        }

        return result;
    }

    @Override
    public Note loadNote(String fileName) {
        String content = "";
        String title = "";

        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            String line = bufferedReader.readLine();
            title = line;
            Log.d("the title", Boolean.toString(title == null));

            while (line != null) {
                content += line + "\n";
                line = bufferedReader.readLine();
            }

            inputStream.close();
            streamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Note(title, content, fileName);
    }

    @Override
    public void delete(Note note) {
        context.deleteFile(note.getFileName());
    }

    private String generateRandomId() {
        Random rnd = new Random();
        char[] characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        String id = "NOTE";
        for (int i = 0; i < 20; i++) {
            id += characters[rnd.nextInt(characters.length)];
        }
        return id;
    }
}
