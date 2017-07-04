package com.elham.seconffilemanager;
import java.io.BufferedOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.sql.Date;
import java.util.Collections;
import java.text.DateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.content.Intent;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import static android.content.ContentValues.TAG;

public class MainActivity extends ListActivity    {
    CopyTask copyTask;
    DeleteTask deleteTask;
    CutTask cutTask;
    CompressTask compressTask;
    UnCompressTask uncompressTask;

    private File root;

    private ArrayList<File> fileList = new ArrayList<File>();
    private ListView listView;
    private FileArrayAdapter adapter;
    String currentPath ;
    protected File Directory;
    protected ArrayList<File> Files;
    protected boolean ShowHiddenFiles = false;
    protected String[] acceptedFileExtensions;
    private static final int BUFFER = 		2048;
    private File root2;
    public String dirFrom, dirTo;
    public int status;
    public  int status2;
    public String[] pathsssss;
    public int prevoiusAction;
    ActionMode actionMode = null;
    public Boolean statuscolr ;
    Button btncopy, btnpaste, btncut, btndelete, btncancel, btnshare, btncompress, btnrename, btnuncompress, btnpro ;
    ImageButton copyimangebtn, cutimagebtn, deleteimagebtn, moreimagebtn, pasteimagebtn, cancelimagebtn;
    TextView copytxt, cuttxt, deletetxt, moretxt, pastetxt, canceltxt;
    File srcDir, targetLocation;
    CheckBox checkBox;
    Boolean statusLongclick ;
    LinearLayout rlayout1, rlayout2, rlayout3 , rlayout4;
    public ArrayList<String> sourceFiles;
    public ArrayList<File> SourceFilesaddr;
    public ProgressBar progressBar;
    public ProgressDialog progress;
    public int WhichBtnPressed;
    public  String pathForcut;
    String ssss;
    String inputName;
    MainActivity mainActivity;
    TextView txtname, txtsize , txtdate , txtpath;
    Button btnok;
    public int al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sourceFiles= new ArrayList<String>();
        mainActivity = this;
        SourceFilesaddr = new ArrayList<File>();
        listView =(ListView) getListView();
        LayoutInflater inflater = getLayoutInflater();
        rlayout1 = (LinearLayout) findViewById(R.id.firstpannel);
        rlayout2 = (LinearLayout) findViewById(R.id.secondpannel);


        checkBox = (CheckBox) findViewById(R.id.check);
        statusLongclick = false;
        rlayout1.setVisibility(View.GONE);
        rlayout2.setVisibility(View.GONE);
        listView.setLongClickable(true);
        registerForContextMenu(getListView());


        copyimangebtn = (ImageButton) findViewById(R.id.copyimagebtn);
        copyimangebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               copyOnclick();

            }
        });
        copytxt = (TextView) findViewById(R.id.copytxt);
        copytxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               copyOnclick();

            }
        });


        cutimagebtn = (ImageButton) findViewById(R.id.cutimagebtn);
        cutimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutOnclick();
            }
        });
        cuttxt = (TextView)  findViewById(R.id.cuttxt);
        cuttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutOnclick();
            }
        });


        deleteimagebtn = (ImageButton)  findViewById(R.id.deleteimangebtn);
        deleteimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOnclick();
            }
        });
        deletetxt= (TextView) findViewById(R.id.deletetxt);
        deletetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOnclick();
            }
        });


        moreimagebtn= (ImageButton)  findViewById(R.id.moreimagebtn);
        moreimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreOnclick();
            }

        });
        moretxt= (TextView) findViewById(R.id.moretxt) ;
        moretxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreOnclick();
            }
        });



        pasteimagebtn= (ImageButton) findViewById(R.id.pasteimagebtn) ;
        pasteimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasteOnclick();

            }
        });
        pastetxt = (TextView) findViewById(R.id.pastetxt);
        pastetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasteOnclick();
            }
        });



        cancelimagebtn= (ImageButton)  findViewById(R.id.cancleimagebtn);
        cancelimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             cancelOnclick();
            }
        });
        canceltxt= (TextView) findViewById(R.id.canceltxt) ;
        canceltxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOnclick();
            }
        });

//      listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
//        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                MenuInflater menuInflater = mode.getMenuInflater();
//                menuInflater.inflate(R.menu.menu,menu);
//
//                return true;
//
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
//                //adapter.toggleSelection(li );
//
//                switch (item.getItemId()) {
//                    case R.id.copy: {
//                        pathsssss = new String[checkedItems.size()];
//                        prevoiusAction = item.getItemId();
//                        for (int i = 0; i < checkedItems.size(); i++) {
//
//                            if (checkedItems.valueAt(i) == true) {
//                                int position = checkedItems.keyAt(i);
//                                Item item4 = adapter.getItem(position);
//                                pathsssss[i] = item4.getPath();
//                            }
//                        }
//                        getfile(new File(currentPath));
//                        adapter.notifyDataSetChanged();
//                        mode.finish();
//                       // actionMode = mode;
//                        return true;
//
//                    }
//                    case R.id.delete: {
//                        for (int i = 0; i < checkedItems.size(); i++) {
//
//                            if (checkedItems.valueAt(i) == true) {
//                                int position = checkedItems.keyAt(i);
//                                Item item4 = adapter.getItem(position);
//                                status2 = deleteTarget(item4.getPath());
//                            }
//                        }
//                        mode.finish();
//                        return true;
//
//                    }
//
//                    case R.id.cut: {
//                        pathsssss = new String[checkedItems.size()];
//                        prevoiusAction = item.getItemId();
//                        for (int i = 0; i < checkedItems.size(); i++) {
//
//                            if (checkedItems.valueAt(i) == true) {
//                                int position = checkedItems.keyAt(i);
//                                Item item4 = adapter.getItem(position);
//                                pathsssss[i] = item4.getPath();
//                            }
//                        }
//                        mode.finish();
//                        return  true;
//                    }
//                    case R.id.paste: {
//                         if (prevoiusAction == R.id.copy)
//                         {
//                             String dirTo = currentPath;
//                             copyToDirectory(pathsssss, dirTo );
//                             getfile(new File(currentPath));
//                             adapter.notifyDataSetChanged();
//                         }
//                        if (prevoiusAction == R.id.cut)
//                        {
//
//                        String dirTo2 = currentPath;
//                        copyToDirectory(pathsssss, dirTo2);
//                        deleteTarget(currentPath);
//                            getfile(new File(currentPath));
//                            adapter.notifyDataSetChanged();
//
//                        }
//                        mode.finish();
//                        return  true;
//
//
//                    }// end for paste
//
//
//
//                 //   mode.finish();
//                  //  return true;
//                    default:
//                        return false;
//                }
//
//
//
//
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//
//
//            }
//
//            @Override
//            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
//
//                adapter.toggleSelection(position);
//
//                mode.setTitle(""+listView.getCheckedItemCount()+" items selected");
//
//            }
//        });


//
//
//
//        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
//
//            @Override
//            public void onItemCheckedStateChanged(ActionMode mode,
//                                                  int position, long id, boolean checked) {
//                // Capture total checked items
//                final int checkedCount = listView.getCheckedItemCount();
//
//                // Set the CAB title according to total checked items
//
//                mode.setTitle(checkedCount + " Selected");
//                // Calls toggleSelection method from ListViewAdapter Class
//                adapter.toggleSelection(position);
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.delete:
//                        // Calls getSelectedIds method from ListViewAdapter Class
//                        SparseBooleanArray selected = adapter
//                                .getSelectedIds();
//                        // Captures all selected ids with a loop
//                        for (int i = (selected.size() - 1); i >= 0; i--) {
//                            if (selected.valueAt(i)) {
//                                Item selecteditem = adapter
//                                        .getItem(selected.keyAt(i));
//                                // Remove selected items following the ids
//                                adapter.remove(selecteditem);
//                            }
//                        }
//                        // Close CAB
//                        mode.finish();
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                mode.getMenuInflater().inflate(R.menu.menu, menu);
//                return true;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//                // TODO Auto-generated method stub
//                adapter.removeSelection();
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                // TODO Auto-generated method stub
//                return false;
//            }
//        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item o = adapter.getItem(position);
                sourceFiles.add(o.getPath().toString());
                root2= new File(o.getPath());
                rlayout1.setVisibility(LinearLayout.VISIBLE);
                rlayout2.setVisibility(LinearLayout.GONE);
                getListView().setItemChecked(position, true);
                FileArrayAdapter.showCheckBox = true;
                statusLongclick = true;
// baraye inke ba long click roye yek item check box ma auto matic entekhab shavad az in 3 khat estefade mishavad
                FileArrayAdapter.ViewHolder cb = (FileArrayAdapter.ViewHolder) view.getTag();
                cb.checkbox.setChecked(true);
                cb.checkbox.callOnClick();



                  adapter.notifyDataSetChanged();
                //vaghrti true bargashte dade mishavad dige setonitemclicklistneber kar nemikonad
                  return true;
            }
        });
        //getting SDcard root path
        root = Environment.getExternalStorageDirectory();
        getfile(root);
        currentPath  = root.getPath();
    }

    private void cancelOnclick() {
        sourceFiles.clear();
        rlayout2.setVisibility(LinearLayout.GONE);
    }

    private void moreOnclick() {
        PopupMenu popup = new PopupMenu(MainActivity.this, moreimagebtn);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().equals("compress"))
                {
                     compressOnclick();
                }


                if (item.getTitle().equals("uncompress"))
                {
                        uncompressOnclcik();
                }


                if (item.getTitle().equals("rename"))
                {
                         renameOnclick();
                }

                if (item.getTitle().equals("propertise"))
                {
                    propertiseOnclick();
                }
                //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popup.show();
    }

    private void propertiseOnclick() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.propertise);
        dialog.setTitle("propertise");

        // set the custom dialog components - text, image and button
//        TextView text = (TextView) dialog.findViewById(R.id.text);
//        text.setText("Android custom dialog example!");
        Item obj = null;

        for ( int i =0; i < adapter.getSelectedIds().size() ; i++) {
            al=adapter.mSelectedItemsIds.keyAt(i);
            obj = adapter.getItem(al);}


        txtname =(TextView) dialog.findViewById(R.id.txtname2);
        txtsize = (TextView) dialog.findViewById(R.id.txtsize2);
        txtpath = (TextView) dialog.findViewById(R.id.txtpath2);
        txtdate = (TextView) dialog.findViewById(R.id.txtdate2);

        txtsize.setText(obj.getData().toString());
      //  editTextname.setText();
        txtname.setText(obj.getName().toString());
        txtpath.setText(obj.getPath().toString());
        txtdate.setText(obj.getDate().toString());






      //  ImageView image = (ImageView) dialog.findViewById(R.id.image);
      //  image.setImageResource(R.drawable.cancelicon);

        Button btnok = (Button) dialog.findViewById(R.id.btnok);
       //  if button is clicked, close the custom dialog
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                FileArrayAdapter.showCheckBox = false;
                FileArrayAdapter fa= (FileArrayAdapter) getListView().getAdapter();
                fa.removeSelection();
                rlayout1.setVisibility(LinearLayout.GONE);
                rlayout2.setVisibility(LinearLayout.GONE);
            }
        });

        dialog.show();
    }

    private void renameOnclick() {


        for ( int i =0; i < adapter.getSelectedIds().size() ; i++) {
            al=adapter.mSelectedItemsIds.keyAt(i);
            Item o = adapter.getItem(al);
            ssss= o.getPath().toString();
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Rename");
        alert.setMessage("Type your new name, here");
        final EditText input = new EditText(MainActivity.this);
        input.setText(ssss.substring(ssss.lastIndexOf("/")+1));
        alert.setView(input);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                // Grab the EditText's input
                String inputName = input.getText().toString();

                // File dir2 = Environment.getExternalStorageDirectory();
                File file = new File(currentPath,ssss.substring(ssss.lastIndexOf("/")+1));
                File file2 = new File(currentPath, inputName);
                boolean success = file.renameTo(file2);
                // Put it into memory (don't forget to commit!)


                // Welcome the new user
                Toast.makeText(getApplicationContext(), "the name changed to " + inputName , Toast.LENGTH_LONG).show();
                FileArrayAdapter.showCheckBox = false;
                FileArrayAdapter fa= (FileArrayAdapter) getListView().getAdapter();
                fa.removeSelection();
                rlayout1.setVisibility(LinearLayout.GONE);
                getfile(new File(currentPath));
                adapter.notifyDataSetChanged();
            }

        });

        // Make a "Cancel" button
        // that simply dismisses the alert
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {}
        });
        FileArrayAdapter.showCheckBox = false;
        FileArrayAdapter fa= (FileArrayAdapter) getListView().getAdapter();
        fa.removeSelection();
        rlayout1.setVisibility(LinearLayout.GONE);
        getfile(new File(currentPath));
        adapter.notifyDataSetChanged();
        alert.show();
    }

    private void uncompressOnclcik() {
        sourceFiles.clear();
        String ssss = null;
        for (int i = 0; i < adapter.getSelectedIds().size(); i++) {
            al = adapter.mSelectedItemsIds.keyAt(i);
            Item o = adapter.getItem(al);
            ssss = o.getPath().toString();

        }
        ProgressDialog progress = new ProgressDialog(MainActivity.this);
        uncompressTask = new UnCompressTask(progress, ssss);
        uncompressTask.setOnCompleteListener(new CompleteAction() {
            @Override
            public void onCompleteListener() {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getfile(new File(currentPath));
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });
        uncompressTask.execute();

        FileArrayAdapter.showCheckBox = false;
        FileArrayAdapter fa = (FileArrayAdapter) getListView().getAdapter();
        fa.removeSelection();
        rlayout1.setVisibility(LinearLayout.GONE);
        rlayout2.setVisibility(LinearLayout.GONE);

    }

    private  class  c1 implements CompleteAction
    {

        @Override
        public void onCompleteListener() {

        }
    }
    private void pasteOnclick() {


        if (WhichBtnPressed == R.id.btncopy) {
            ProgressDialog progress = new ProgressDialog(MainActivity.this);
            copyTask = new CopyTask(progress, sourceFiles, new File(currentPath));
            copyTask.setOnCompleteListener(new CompleteAction() {
                @Override
                public void onCompleteListener() {



                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getfile(new File(currentPath));
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
            });
            copyTask.execute();
        }

        if (WhichBtnPressed == R.id.btncut) {
            ProgressDialog progress = new ProgressDialog(MainActivity.this);
            cutTask = new CutTask(progress, sourceFiles, new File(currentPath), pathForcut);
            cutTask.setOnCompleteListener(new CompleteAction() {
                @Override
                public void onCompleteListener() {
                    //MainActivity mainActivity = null;
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getfile(new File(currentPath));
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
            });
            cutTask.execute();
            sourceFiles.clear();
            FileArrayAdapter.showCheckBox = false;
            FileArrayAdapter fa = (FileArrayAdapter) getListView().getAdapter();
            fa.removeSelection();
            rlayout1.setVisibility(LinearLayout.GONE);
            rlayout2.setVisibility(LinearLayout.GONE);


        }
        sourceFiles.clear();
        FileArrayAdapter.showCheckBox = false;
        FileArrayAdapter fa = (FileArrayAdapter) getListView().getAdapter();
        fa.removeSelection();
        rlayout1.setVisibility(LinearLayout.GONE);
        rlayout2.setVisibility(LinearLayout.GONE);
    }

    public void deleteOnclick() {
        sourceFiles.clear();
        for ( int i =0; i < adapter.getSelectedIds().size() ; i++) {
            al=adapter.mSelectedItemsIds.keyAt(i);
            Item o = adapter.getItem(al);
            sourceFiles.add(o.getPath().toString());

        }



        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ProgressDialog progress = new ProgressDialog(MainActivity.this);
                        deleteTask= new DeleteTask(progress, sourceFiles);
                        deleteTask.setOnCompleteListener(new CompleteAction() {
                            @Override
                            public void onCompleteListener() {
                                //MainActivity mainActivity = null;
                                mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getfile(new File(currentPath));
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        });
                        deleteTask.execute();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        FileArrayAdapter.showCheckBox = false;
                        FileArrayAdapter fa= (FileArrayAdapter) getListView().getAdapter();
                        fa.removeSelection();
                        rlayout1.setVisibility(LinearLayout.GONE);
                        getfile(new File(currentPath));
                        adapter.notifyDataSetChanged();

                    }
                });
        FileArrayAdapter.showCheckBox = false;
        FileArrayAdapter fa= (FileArrayAdapter) getListView().getAdapter();
        fa.removeSelection();
        rlayout1.setVisibility(LinearLayout.GONE);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void cutOnclick() {
        sourceFiles.clear();
        WhichBtnPressed=R.id.btncut;
        pathForcut= currentPath;
        for ( int i =0; i < adapter.getSelectedIds().size() ; i++) {
            al=adapter.mSelectedItemsIds.keyAt(i);
            Item o = adapter.getItem(al);
            sourceFiles.add(o.getPath().toString());

        }

        FileArrayAdapter.showCheckBox = false;
        FileArrayAdapter fa= (FileArrayAdapter) getListView().getAdapter();
        fa.removeSelection();
        rlayout1.setVisibility(LinearLayout.GONE);
        rlayout2.setVisibility(LinearLayout.VISIBLE);
    }

    private void compressOnclick()
    {
        sourceFiles.clear();
        String ssss = null;
        for (int i = 0; i < adapter.getSelectedIds().size(); i++) {
            al=adapter.mSelectedItemsIds.keyAt(i);
            Item o = adapter.getItem(al);
            sourceFiles.add(o.getPath().toString());
            ssss = o.getPath().toString();

        }
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Compress");
        alert.setMessage("Type your new name, here");
        final EditText input = new EditText(MainActivity.this);
        input.setText(ssss.substring(ssss.lastIndexOf("/")+1)+".zip");
        alert.setView(input);
        final String finalSsss = ssss;
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                // Grab the EditText's input
                inputName = input.getText().toString();
                ProgressDialog progress = new ProgressDialog(MainActivity.this);
                compressTask = new  CompressTask(progress, sourceFiles, inputName);
                compressTask.setOnCompleteListener(new CompleteAction() {
                    @Override
                    public void onCompleteListener() {
                        mainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getfile(new File(currentPath));
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }
                });
                compressTask.execute();

                FileArrayAdapter.showCheckBox = false;
                FileArrayAdapter fa = (FileArrayAdapter) getListView().getAdapter();
                fa.removeSelection();
                rlayout1.setVisibility(LinearLayout.GONE);
                rlayout2.setVisibility(LinearLayout.GONE);
            }

        });
        alert.show();
    }


    public void copyOnclick()
    {
        WhichBtnPressed=R.id.btncopy;
        sourceFiles.clear();
        for ( int i =0; i < adapter.getSelectedIds().size() ; i++) {
            al=adapter.mSelectedItemsIds.keyAt(i);
            Item o = adapter.getItem(al);
            sourceFiles.add(o.getPath().toString());
        }
        FileArrayAdapter.showCheckBox = false;
        FileArrayAdapter fa= (FileArrayAdapter) getListView().getAdapter();
        fa.removeSelection();
        rlayout1.setVisibility(LinearLayout.GONE);
        rlayout2.setVisibility(LinearLayout.VISIBLE);
    }


    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Item o = adapter.getItem(position);
        if(o.getImage().equalsIgnoreCase("directory_icon")||o.getImage().equalsIgnoreCase("directory_up")){

            if (o.getImage().equalsIgnoreCase("directory_up") && currentPath.equals("/storage/emulated/0")) {


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                currentPath= "/storage/emulated/0";
                                getfile(new File(currentPath));

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }


            root = new File(o.getPath());
            currentPath = root.getPath();

            getfile(root);
        }
        else
        {
            onFileClick(o.getPath());
        }
    }
    public int copyToDirectory( String old, String newDir) {
         {

            File old_file = new File(old);
            File temp_dir = new File(newDir);
            byte[] data = new byte[BUFFER];
            int read = 0;

            if (old_file.isFile() && temp_dir.isDirectory() && temp_dir.canWrite()) {
                String file_name = old.substring(old.lastIndexOf("/"), old.length());
                File cp_file = new File(newDir + file_name);

                try {
                    BufferedOutputStream o_stream = null;
                    try {
                        o_stream = new BufferedOutputStream(
                                new FileOutputStream(cp_file));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    BufferedInputStream i_stream = new BufferedInputStream(
                            new FileInputStream(old_file));

                    while ((read = i_stream.read(data, 0, BUFFER)) != -1)
                        o_stream.write(data, 0, read);

                    o_stream.flush();
                    i_stream.close();
                    o_stream.close();


                } catch (FileNotFoundException e) {
                    Log.e("FileNotFoundException", e.getMessage());
                    return -1;

                } catch (IOException e) {
                    Log.e("IOException", e.getMessage());
                    return -1;
                }

            } else if (old_file.isDirectory() && temp_dir.isDirectory() && temp_dir.canWrite()) {
                String files[] = old_file.list();
                String dir = newDir + old.substring(old.lastIndexOf("/"), old.length());
                int len = files.length;

                if (!new File(dir).mkdir())
                    return -1;

                for (int i = 0; i < len; i++)
                    copyToDirectory(old , dir);

            } else if (!temp_dir.canWrite())
                return -1;
        }
            return 0;

    }


//   public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.copy:
//
//
//                return true;
//            case R.id.cut:
//
//
//                return true;
//            case R.id.paste:
//
//
//            case R.id.delete:
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


@Override
    public void onBackPressed(){
    if (statusLongclick == true) {
        FileArrayAdapter.showCheckBox = false;
        rlayout1.setVisibility(LinearLayout.GONE);
       listView.clearChoices();
        sourceFiles.clear();
      //  getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);


        // 3 khat paiin baraye in ast ke range vaghti back zade shod range mavared select shode az bein berevad
        getListView().setAdapter(getListAdapter());
        FileArrayAdapter fa= (FileArrayAdapter) getListView().getAdapter();
        fa.removeSelection();
        statusLongclick = false;
      // listView.requestLayout();
    }
    else {
        FileArrayAdapter.showCheckBox = false;
        rlayout1.setVisibility(LinearLayout.GONE);
        sourceFiles.clear();
        currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));

        getfile(new File(currentPath));
    }
    if ( currentPath.equals ("/storage/emulated")) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        FileArrayAdapter.showCheckBox = false;
                        rlayout1.setVisibility(LinearLayout.GONE);
                        currentPath= "/storage/emulated/0";
                        FileArrayAdapter.showCheckBox = false;
                        rlayout1.setVisibility(LinearLayout.GONE);
                        getfile(new File(currentPath));

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    adapter.notifyDataSetChanged();
    }
    private void onFileClick(String url) {

        File file = new File(url);//File path
        if (file.exists()) //Checking for the file is exist or not
        {

            Uri path = Uri.fromFile(file);
            Intent objIntent = new Intent(Intent.ACTION_VIEW);

            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {

                objIntent.setDataAndType(path, "application/msword");



            } else if (url.toString().contains(".pdf")) {
                // PDF file
                objIntent.setDataAndType(path, "application/pdf");



            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                objIntent.setDataAndType( path, "application/vnd.ms-powerpoint");




            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                objIntent.setDataAndType(path, "application/vnd.ms-excel");



            } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
               objIntent.setDataAndType(path, "application/zip");




            } else if (url.toString().contains(".rtf")) {
                // RTF file
                objIntent.setDataAndType(path, "application/rtf");



            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                objIntent.setDataAndType(path, "audio/x-wav");




            } else if (url.toString().contains(".gif")) {
                // GIF file
                objIntent.setDataAndType(path, "image/gif");



            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                objIntent.setDataAndType(path, "image/jpeg");



            } else if (url.toString().contains(".txt")) {
                // Text file
               // objIntent.setDataAndType(path, "text/plain");
                StringBuilder text = new StringBuilder();

                try {
                    BufferedReader br = new BufferedReader(new FileReader(url.toString()));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                }
                catch (IOException e) {
                    //You'll need to add proper error handling here
                }

                Intent myIntent = new Intent(MainActivity.this, EditorActivity.class);
                myIntent.putExtra("message", text.toString());
                myIntent.putExtra("path", url.toString());
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                return;



            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                objIntent.setDataAndType(path, "video/*");



            } else {
                objIntent.setDataAndType(path, "*/*");
            }

            objIntent.setFlags(Intent. FLAG_ACTIVITY_NO_HISTORY);
            startActivity(objIntent);
        } else {

            Toast.makeText(this, "The file not exists! ", Toast.LENGTH_SHORT).show();

        }
    }
    public void getfile(File f) {
        File[]dirs = f.listFiles();
        this.setTitle("Current Dir: "+f.getName());
        List<Item> dir = new ArrayList<Item>();
        List<Item>fls = new ArrayList<Item>();
        try{
            for(File ff: dirs)
            {
                Date lastModDate = new Date(ff.lastModified());
                DateFormat formater = DateFormat.getDateTimeInstance();
                String date_modify = formater.format(lastModDate);
                if(ff.isDirectory()){


                    File[] fbuf = ff.listFiles();
                    int buf = 0;
                    if(fbuf != null){
                        buf = fbuf.length;
                    }
                    else buf = 0;
                    String num_item = String.valueOf(buf);
                    if(buf == 0) num_item = num_item + " item";
                    else num_item = num_item + " items";

                    //String formated = lastModDate.toString();
                    dir.add(new Item(ff.getName(),num_item,date_modify,ff.getAbsolutePath(),"directory_icon"));
                }
                else if (ff.getName().toLowerCase().endsWith(".txt"))
                {                    fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"txt"));
                }
                else if (ff.getName().toLowerCase().endsWith(".pdf"))
                {                    fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"pdf"));
                }
                else if (ff.getName().toLowerCase().endsWith(".jpg"))
                {                    fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"cam"));
                }
                else if (ff.getName().toLowerCase().endsWith(".rc"))
                {                    fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"system"));
                }

                else
                {                    fls.add(new Item(ff.getName(),ff.length() + " Byte", date_modify, ff.getAbsolutePath(),"file_icon"));
                }


            }
        }catch(Exception e)
        {

        }
        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if(!f.getName().equalsIgnoreCase("sdcard"))
            dir.add(0,new Item("..","Parent Directory","",f.getParent(),"directory_up"));
        adapter = new FileArrayAdapter(this,R.layout.file_view,dir);

        rlayout1.setVisibility(LinearLayout.GONE);
        FileArrayAdapter.showCheckBox = false;
        this.setListAdapter(adapter);
    }

//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                    ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        AdapterView.AdapterContextMenuInfo info;
//        try {
//            info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//        } catch (ClassCastException e) {
//            Log.e("id", "bad menuInfo", e);
//            return;
//        }
//
//
//        menu.setHeaderTitle("Select The Action");
//        menu.add(0, v.getId(), 0, "copy");
//        menu.add(0, v.getId(), 0, "paste");
//        menu.add(0, v.getId(), 0, "cut");
//        menu.add(0, v.getId(), 0, "delete");
//
//
//    }
//
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item)
//    {
//
//
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
//
//        //  info.position will give the index of selected item
//        int IndexSelected=info.position;
//        if(item.getTitle()=="copy")
//        {
//             String dirFrom= currentPath;
////
////            Snackbar bar = Snackbar.make(v, "Weclome to SwA", Snackbar.LENGTH_LONG)
////                    .setAction("Dismiss", new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            // Handle user action
////                        }
////                    });
////
////            bar.show();
//
//
//        }
//        else if(item.getTitle()=="paste")
//        {
//             String dirTo = currentPath;
//             int status=  copyToDirectory(dirFrom, dirTo);
//            if ( status == 0) return true; else return false;
//
//        }
//        else if(item.getTitle()=="cut")
//        {
//
//
//        }
//        else if(item.getTitle()=="delete")
//        {
//
//
//        }
//        else
//        {
//            return false;
//        }
//        return true;
//
//
//    }





//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info;
//        try {
//            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        } catch (ClassCastException e) {
//            Log.e(TAG, "bad menuInfo", e);
//            return false;
//        }
//
//        switch (item.getItemId()) {
//            case DO_SOMETHING:
//            /* Do sothing with the id */
//                Something something = getListAdapter().getItem(info.position);
//                return true;
//        }
//    }


    @Override
    public void onPause(){

        super.onPause();
        if(progress != null)
            progress.dismiss();
    }
    @Override
    protected void onStop() {

        super.onStop();

        if (progress != null) {
            progress.dismiss();
            progress = null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( progress!=null && progress.isShowing() ){
            progress.dismiss();
        }
    }







}



