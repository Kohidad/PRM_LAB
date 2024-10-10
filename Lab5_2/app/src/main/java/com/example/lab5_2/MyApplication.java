package com.example.lab5_2;

import android.app.Application;

import java.util.ArrayList;

// We're establishing the list as a public variable.
// Once application is created, declare your application in manifest.
public class MyApplication extends Application {
    private static ArrayList<Fruit> fruitList = new ArrayList<>();
    int[] fruitImages = {R.drawable.apple,R.drawable.banana,R.drawable.c4};

    @Override
    public void onCreate() {
        super.onCreate();
        setUpFruitList();  // Call this here, as resources will be ready
    }

    public static ArrayList<Fruit> getFruitList() {
        return fruitList;
    }

    public static void setFruitList(ArrayList<Fruit> fruitList) {
        MyApplication.fruitList = fruitList;
    }

    public void setUpFruitList(){
        String[] fruitTitle = getResources().getStringArray(R.array.fruit_title);
        String[] fruitDescription = getResources().getStringArray(R.array.fruit_description);
        for (int i = 0; i < fruitTitle.length; i++) {
            fruitList.add(new Fruit(fruitTitle[i],
                    fruitDescription[i],
                    fruitImages[i]));
        }
    }
}
