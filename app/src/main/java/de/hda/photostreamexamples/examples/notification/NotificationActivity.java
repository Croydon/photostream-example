/*
 * The MIT License
 *
 * Copyright (c) 2016 Andreas Schattney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.hda.photostreamexamples.examples.notification;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import hochschuledarmstadt.photostream_tools.IPhotoStreamClient;
import hochschuledarmstadt.photostream_tools.PhotoStreamActivity;
import de.hda.photostreamexamples.R;
import hochschuledarmstadt.photostream_tools.model.Photo;

/**
 * Activity dient nur zur Demonstration der Intentverarbeitung durch den Broadcast Receiver.
 * Dieser Intent wird eigentlich von der Photostream Bibliothek erzeugt und gesendet.
 */
public class NotificationActivity extends PhotoStreamActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout erzeugen
        setContentView(R.layout.activity_notification);
        // Button referenzieren
        Button button = (Button) findViewById(R.id.intent_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Photo photo = loadFakePhoto();
                    // Wenn auf den Button geklickt wurde
                    Intent intent = new Intent(IPhotoStreamClient.INTENT_ACTION_NEW_PHOTO_AVAILABLE);
                    // dann einen Broadcast an das System senden.
                    intent.putExtra(IPhotoStreamClient.INTENT_KEY_PHOTO, photo);
                    sendBroadcast(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Photo loadFakePhoto() throws IOException, NoSuchFieldException, IllegalAccessException {
        InputStream is = getAssets().open("architecture.png");
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        File file = new File(getExternalCacheDir(), "1.png");
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, new FileOutputStream(file));
        Photo photo = new Gson().fromJson("{\"photo_id\":1,\"comment\":\"Photostream Architektur\",\"deleteable\":false,\"comment_count\":0,\"favorite\":0}", Photo.class);
        Field f = Photo.class.getDeclaredField("imageFilePath");
        f.setAccessible(true);
        f.set(photo, file.getAbsolutePath());
        return photo;
    }

    @Override
    protected void onPhotoStreamServiceConnected(IPhotoStreamClient photoStreamClient, Bundle savedInstanceState) {
        // Wird in diesem Beispiel nicht verwendet
    }

    @Override
    protected void onPhotoStreamServiceDisconnected(IPhotoStreamClient photoStreamClient) {
        // Wird in diesem Beispiel nicht verwendet
    }
}
