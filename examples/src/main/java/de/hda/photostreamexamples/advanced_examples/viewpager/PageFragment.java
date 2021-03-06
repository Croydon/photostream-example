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

package de.hda.photostreamexamples.advanced_examples.viewpager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

import hochschuledarmstadt.photostream_tools.BitmapUtils;
import hochschuledarmstadt.photostream_tools.IPhotoStreamClient;
import hochschuledarmstadt.photostream_tools.PhotoStreamActivity;
import hochschuledarmstadt.photostream_tools.PhotoStreamFragment;
import hochschuledarmstadt.photostream_tools.adapter.DividerItemDecoration;
import hochschuledarmstadt.photostream_tools.callback.OnCommentsReceivedListener;
import de.hda.photostreamexamples.R;
import de.hda.photostreamexamples.Utils;
import de.hda.photostreamexamples.examples.comment.CommentAdapter;
import hochschuledarmstadt.photostream_tools.model.Comment;
import hochschuledarmstadt.photostream_tools.model.HttpError;
import hochschuledarmstadt.photostream_tools.model.Photo;

public class PageFragment extends PhotoStreamFragment implements OnCommentsReceivedListener {

    private static final String KEY_COMMENTS = "KEY_COMMENTS";

    private static final String TAG = PageFragment.class.getName();

    private ImageView imageView;
    private RecyclerView recyclerView;

    private CommentAdapter adapter;
    private Photo photo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String key = ViewPagerFragmentActivity.PhotoFragmentPagerAdapter.KEY_PHOTO;
        photo = getArguments().getParcelable(key);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_item, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new CommentAdapter();

        if (savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle(KEY_COMMENTS);
            adapter.restoreInstanceState(bundle);
        }

        recyclerView.setAdapter(adapter);

        imageView = (ImageView) getView().findViewById(R.id.imageView);

        loadBitmapAsync(photo.getImageFile(), new PhotoStreamActivity.OnBitmapLoadedListener() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onLoadBitmapError(IOException e) {
                Log.e(TAG, "Fehler beim Dekodieren", e);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_COMMENTS, adapter.saveInstanceState());
    }

    @Override
    protected void onPhotoStreamServiceConnected(IPhotoStreamClient service, Bundle savedInstanceState) {
        service.addOnCommentsReceivedListener(this);
        if (savedInstanceState == null)
            service.loadComments(photo.getId());
    }

    @Override
    protected void onPhotoStreamServiceDisconnected(IPhotoStreamClient service) {
        service.removeOnCommentsReceivedListener(this);
    }

    @Override
    public void onCommentsReceived(int photoId, List<Comment> comments) {
        if (photoId == photo.getId())
            adapter.set(comments);
    }

    @Override
    public void onDestroyView() {
        BitmapUtils.recycleBitmapFromImageView(imageView);
        super.onDestroyView();
    }

    @Override
    public void onReceiveCommentsFailed(int photoId, HttpError httpError) {
        Utils.showErrorInAlertDialog(getActivity(), "Kommentare konnten nicht geladen werden", httpError);
    }

}
