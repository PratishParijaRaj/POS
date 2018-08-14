//package com.example.pratishparija.pos.database;
//
//import android.arch.persistence.db.SupportSQLiteOpenHelper;
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.DatabaseConfiguration;
//import android.arch.persistence.room.InvalidationTracker;
//import android.arch.persistence.room.Room;
//import android.arch.persistence.room.RoomDatabase;
//import android.content.Context;
//
//@Database(entities = {ProductTable.class}, version = 1)
//
//public abstract class MyDatabase extends RoomDatabase {
//    public abstract ProdutDao produtDao();
//
//    private static MyDatabase myDatabase;
//
//    public static MyDatabase getDatabase(final Context context) {
//        if (myDatabase == null) {
//            synchronized (MyDatabase.class) {
//                if (myDatabase == null) {
//                    myDatabase = Room.databaseBuilder(context.getApplicationContext(),
//                            MyDatabase.class, "pos_database")
//                            .build();
//
//                }
//            }
//        }
//        return myDatabase;
//    }
//}
