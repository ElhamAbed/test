package com.elham.seconffilemanager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elham on 5/28/2017.
 */

public class CutTask extends AsyncTask<Object, Object, Void> {
    ProgressDialog progress;
    File targetPathmain ;
    List<String> slistMain;
    CompleteAction caMain;
    String pathforcut;

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progress.dismiss();
    }

    public CutTask(ProgressDialog progress , ArrayList<String> slist , File targetpath, String pathforcut) {
        this.progress = progress;
        this.slistMain  = (List<String>)slist.clone();
        targetPathmain = targetpath;
        this.pathforcut = pathforcut;
    }

    @Override
    protected Void doInBackground(Object... params) {
        try {
            for (int i = 0; i < slistMain.size(); i++) {
                File srcDir = new File(slistMain.get(i));
                if (srcDir.isDirectory()) {
                    copyDirectory(srcDir, targetPathmain);
                    deleteTarget(srcDir);

                }

                //raise complete event
                if (caMain != null)
                    caMain.onCompleteListener();

            }
          //  return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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


    public void copyDirectory(File sourceLocation , File targetLocation)
            throws IOException {


        if (sourceLocation.isDirectory()) {
            if (isCancelled())
                return;
            targetLocation = new File(targetLocation ,sourceLocation.getName() );
            if (!targetLocation.exists() && !targetLocation.mkdirs()) {
                throw new IOException("Cannot create dir " + targetLocation.getAbsolutePath());
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {
            publishProgress(sourceLocation.getAbsolutePath().toString());
            if (isCancelled())
                return;
            // make sure the directory we plan to store the recording in exists
            File directory = targetLocation.getParentFile();
            if (directory != null && !directory.exists() && !directory.mkdirs()) {
                throw new IOException("Cannot create dir " + directory.getAbsolutePath());
            }

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

        }


    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setMessage("Loading...");
        progress.setCancelable(true);
        progress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CutTask.this.cancel(true);
                dialog.dismiss();
            }
        });

        progress.show();
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


    public void setOnCompleteListener(CompleteAction ca)
    {
        caMain = ca;
    }


}
