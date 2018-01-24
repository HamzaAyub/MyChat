package com.example.hp.mychat;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.hp.mychat.Adapters.ContactAdapter;
import com.example.hp.mychat.model.ListItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShowContacts extends AppCompatActivity {

    private RecyclerView recView;
    private ContactAdapter adapter;
    private ImageView imgShow;
    private Context ctx;

    private static final String TAG = ShowContacts.class.getSimpleName();

    private Uri uriContact;

    private String contactID;     // contacts unique ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contacts);

        uriContact= ContactsContract.Contacts.CONTENT_URI;

        recView = (RecyclerView)findViewById(R.id.recyclerview_contact);
        // LayoutManager

        recView.setLayoutManager(new LinearLayoutManager(this));
        // adapter = new DerpAdapter(DerpData.setListData(),this);
        adapter = new ContactAdapter(listContact(),this);
        recView.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }





    public List<ListItem> listContact(){
        List<ListItem> data1 = new ArrayList<>();
        String name = null;
        String phoneNumber1 = null;

        Cursor cursor =  getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID},
                null, null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");


        Cursor cursor1 =  getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");


/*        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);
*/






        if(cursor!=null && cursor.getCount()>0) {


            cursor.moveToFirst();
            cursor1.moveToFirst();


            do {

                contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                name = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));


                //////////////////////////////////////////////////////////////

                Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
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
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
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
