package com.example.vinsfran.galeriaconpicasso.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vinsfran.galeriaconpicasso.R;
import com.example.vinsfran.galeriaconpicasso.adapters.AnimalAdapter;
import com.example.vinsfran.galeriaconpicasso.adapters.ImagesAdapter;
import com.example.vinsfran.galeriaconpicasso.adapters.PartyAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<String> images;
    private String[] animals;
    private int[] parties;

    private AnimalAdapter animalAdapter;
    private PartyAdapter partyAdapter;
    private ImagesAdapter imagesAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private final int PERMISSION_READ_EXTERNAL_MEMORY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animals = getAnimalsLinks();
        parties = getPartyPics();
        images = getImagesPath();

        animalAdapter = new AnimalAdapter(this, animals, R.layout.image_layout);
        partyAdapter = new PartyAdapter(this, parties, R.layout.image_layout);
        imagesAdapter = new ImagesAdapter(this, images, R.layout.image_layout);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(animalAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.links_adapter:
                recyclerView.setAdapter(animalAdapter);
                return true;
            case R.id.resources_adapter:
                recyclerView.setAdapter(partyAdapter);
                return true;
            case R.id.memory_adapter:
                checkForPermission();
                images.clear();
                images.addAll(getImagesPath());
                recyclerView.setAdapter(imagesAdapter);
                imagesAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_EXTERNAL_MEMORY);
        }
    }

    private boolean hasPermission(String permissionToCheck) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, permissionToCheck);

        return (permissionCheck == PackageManager.PERMISSION_GRANTED);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_MEMORY:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    images.clear();
                    images.addAll(getImagesPath());
                    imagesAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    public String[] getAnimalsLinks() {
        String[] links = {
                "https://static.pexels.com/photos/86462/red-kite-bird-of-prey-milan-raptor-86462.jpeg",
                "https://static.pexels.com/photos/67508/pexels-photo-67508.jpeg",
                "https://static.pexels.com/photos/62640/pexels-photo-62640.jpeg"
        };
        return links;
    }

    private int[] getPartyPics() {
        int[] values = {
                R.drawable.uno,
                R.drawable.dos,
                R.drawable.tres
        };
        return values;
    }

    public List<String> getImagesPath() {
        List<String> listOfAllImages = new ArrayList<>();

        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};

            Cursor cursor = getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, MediaStore.Images.Media._ID);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        listOfAllImages.add(path);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
        }
        return listOfAllImages;
    }

    @Override
    protected void onResume() {
        super.onResume();
        images.clear();
        images.addAll(getImagesPath());
        imagesAdapter.notifyDataSetChanged();
    }
}
