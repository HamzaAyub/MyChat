package com.example.hp.mychat.fragments;


import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.mychat.Adapters.ContactAdapter;
import com.example.hp.mychat.MainActivity;
import com.example.hp.mychat.R;
import com.example.hp.mychat.model.ListItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contacts extends Fragment {


    private RecyclerView recView;
    private ContactAdapter adapter;
    private String contactID;     // contacts unique ID
    private Uri uriContact;


    public Contacts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View rootView= inflater.inflate(R.layout.fragment_contacts, container, false);

        uriContact= ContactsContract.Contacts.CONTENT_URI;
        recView = (RecyclerView)rootView.findViewById(R.id.recyclerview1);
        // LayoutManager

        recView.setLayoutManager(new LinearLayoutManager(getContext()));  //getApplicationContext()
        // adapter = new DerpAdapter(DerpData.setListData(),this);
        adapter = new ContactAdapter(listContact(),getContext());
        recView.setAdapter(adapter);

        return rootView;
    }


    public List<ListItem> listContact(){
        List<ListItem> data1 = new ArrayList<>();
        String name = null;
        String phoneNumber1 = null;


        Cursor cursor =  getContext().getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID},
                null, null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");


        Cursor cursor1 =  getContext().getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");

        if(cursor!=null && cursor.getCount()>0) {


            cursor.moveToFirst();
            cursor1.moveToFirst();


            do {

                contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                name = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));


                //////////////////////////////////////////////////////////////

                Cursor cursorPhone = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                        new String[]{contactID},
                        null);

                if (cursorPhone.moveToFirst()) {
                    phoneNumber1 = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                cursorPhone.close();


                ////////////////////////////////////////////////////




                Bitmap image1 = retrieveContactPhoto();

                ListItem item = new ListItem();


                item.setName(name);
                item.setNumber(phoneNumber1);
                item.setPhoto(image1);
                data1.add(item);

            } while (cursor.moveToNext() && cursor1.moveToNext());

            cursor.close();
            cursor1.close();


        }// end if
        else{

        }

        return data1;
    }



    /////////////////////////////////////////////////////////


    private Bitmap retrieveContactPhoto() {

        Bitmap photo = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContext().getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            }
            else{

                photo = BitmapFactory.decodeResource(this.getResources(), R.drawable.detail_cont);
            }

            assert inputStream != null;
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return photo;

    }




}
