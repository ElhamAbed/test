package com.elham.seconffilemanager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Elham on 5/27/2017.
 */

public class DeleteTask extends AsyncTask<ArrayList<String>,String, Void> {

    ProgressDialog progressDialog;
    ArrayList<String> srcFile;
    File srcDir;
    CompleteAction completeAction;



    public void setOnCompleteListener(CompleteAction ca)
    {
        completeAction= ca;
    }



    public void deleteTarget(File target) {

        publishProgress(target.getAbsolutePath().toString());
        if (isCancelled())
            return;
        if(target.exists() && target.isFile() && target.canWrite()) {
            target.delete();

            return ;
        }

        else if(target.exists() && target.isDirectory() && target.canRead()) {
            String[] file_list = target.list();

            if(file_list != null && file_list.length == 0) {
                target.delete();

                return ;

            } else if(file_list != null && file_list.length > 0) {

                for(int i = 0; i < file_list.length; i++) {
                    File temp_f = new File(target.getAbsolutePath() + "/" + file_list[i]);

                    if(temp_f.isDirectory())
                        deleteTarget(temp_f);
                    else if(temp_f.isFile())
                        temp_f.delete();
                }
            }
            if(target.exists())
                if(target.delete())
                {
                    return ;}
        }
        return ;
    }
//    void deleteRecursive(File fileOrDirectory) {
//        if (isCancelled())
//            return;
//            if (fileOrDirectory.isDirectory())
//            for (File child : fileOrDirectory.listFiles()) {
//                publishProgress(fileOrDirectory.getAbsolutePath().toString());
//                deleteRecursive(child);
//
//            }
//
//        fileOrDirectory.delete();
//
//    }



    public DeleteTask(ProgressDialog progress, ArrayList<String> Src) {
        this.progressDialog = progress;
        this.srcFile= Src;


    }

    @Override
    protected Void doInBackground(ArrayList<String>... params) {
        for ( int i = 0 ; i < srcFile.size(); i++) {
            srcDir = new File(srcFile.get(i));
            deleteTarget(srcDir);
        }
        if(completeAction!=null)
            completeAction.onCompleteListener();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteTask.this.cancel(true);
                dialog.dismiss();
            }
        });

        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void unused) {

        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        progressDialog.setMessage(values[0]);
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}

