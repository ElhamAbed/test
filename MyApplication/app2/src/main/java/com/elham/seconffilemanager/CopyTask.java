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

public class CopyTask extends AsyncTask<Void, String, Void>  {
        ProgressDialog progress;
        File targetPathmain ;
        List<String> slistMain;
        CompleteAction caMain;


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        progress.setMessage(values[0]);
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


        public CopyTask(ProgressDialog progress , ArrayList<String> slist , File targetpath) {
            this.progress = progress;
            this.slistMain  = (List<String>)slist.clone();
            targetPathmain = targetpath;
        }

        public void setOnCompleteListener(CompleteAction ca)
        {
            caMain = ca;
        }

        public void onPreExecute() {

            progress.setMessage("Loading...");
           progress.setCancelable(true);

            progress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CopyTask.this.cancel(true);
                    dialog.dismiss();
                }
            });

           progress.show();
        }



    public Void doInBackground(Void... unused) {
            try {
                for ( int i = 0 ; i < slistMain.size(); i++) {
                    File srcDir = new File(slistMain.get(i));
                    if (srcDir.isDirectory()) {
                        copyDirectory(srcDir, targetPathmain);

                    }

                }
                //raise complete event
                if(caMain!=null)
                    caMain.onCompleteListener();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }



    public void onPostExecute(Void unused) {
        if(progress != null)

            progress.dismiss();

        }



    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected void onCancelled() {        super.onCancelled();    }


    }

