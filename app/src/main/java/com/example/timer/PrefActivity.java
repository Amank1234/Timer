//package com.example.timer;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.preference.PreferenceActivity;
//import android.preference.PreferenceFragment;
//
//public class PrefActivity extends PreferenceActivity {
//    public static void start(Context context) {
//        context.startActivity(new Intent(context, PrefActivity.class));
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        addPreferencesFromResource(R.xml.preferences);
//        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefActivity()).commit();
//    }
//     public static class PrefActivity extends PreferenceFragment
//    {
//        @Override
//        public void onCreate(final Bundle savedInstanceState)
//        {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.preferences);
//        }
//    }
//}
