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

package de.hda.photostreamexamples.main;

import android.os.Bundle;

import de.hda.photostreamexamples.R;
import de.hda.photostreamexamples.examples.comment.CommentActivity;
import de.hda.photostreamexamples.examples.notification.NotificationActivity;
import de.hda.photostreamexamples.examples.photo.PhotoActivity;
import de.hda.photostreamexamples.examples.plugin.AlertDialogPluginActivity;
import de.hda.photostreamexamples.examples.plugin.ContextualActionBarPluginActivity;
import de.hda.photostreamexamples.examples.search.SearchActivity;
import de.hda.photostreamexamples.examples.upload.PhotoUploadActivity;

/**
 * Nur ein Auswahlmenü für die Anzeige der einzelnen Beispielactivities.
 */
public class ExamplesActivity extends MenuActivity {

    private static final String MENU_PHOTOS = "Photos anzeigen";
    private static final String MENU_COMMENTS = "Kommentare anzeigen";
    private static final String MENU_UPLOAD_PHOTO = "Neues Photo hochladen";
    private static final String MENU_SEARCH = "Suche nach Photos anhand Beschreibung";
    private static final String MENU_BROADCAST_RECEIVER = "Broadcast Receiver Beispiel";
    private static final String MENU_CAB_EXTENSION = "Beispiel für Contextual ActionBar Plugin";
    private static final String MENU_DIALOG_EXTENSION = "Beispiel für AlertDialog Plugin";

    private static final MenuItem[] menu = new MenuItem[]{
            new MenuItem(MENU_PHOTOS, PhotoActivity.class),
            new MenuItem(MENU_CAB_EXTENSION, ContextualActionBarPluginActivity.class),
            new MenuItem(MENU_DIALOG_EXTENSION, AlertDialogPluginActivity.class),
            new MenuItem(MENU_COMMENTS, CommentActivity.class),
            new MenuItem(MENU_UPLOAD_PHOTO, PhotoUploadActivity.class),
            new MenuItem(MENU_SEARCH, SearchActivity.class),
            new MenuItem(MENU_BROADCAST_RECEIVER, NotificationActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initialize(menu);
    }

}
