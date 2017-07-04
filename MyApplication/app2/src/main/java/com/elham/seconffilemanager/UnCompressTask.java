package com.elham.seconffilemanager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Elham on 6/10/2017.
 */

public class UnCompressTask extends AsyncTask<Void, String, Void> {


    String sourcePath, Location;
    ProgressDialog progress;
    CompleteAction caMain;
    private static final int BUFFER = 2048;
    int filecounter = 0;
    int filecountertotal;
    private int per = 0;


    public UnCompressTask(ProgressDialog progress, String ssss) {
        this.sourcePath = ssss;
        // this.toLocation = s;
        this.progress = progress;
        //this.Location = ssss - ".zip";
    }


    @Override
    protected void onPostExecute(Void unused) {
        progress.dismiss();


    }

    @Override
    protected void onProgressUpdate(String... value) {

        Log.d("ANDRO_ASYNC", value[0]);
        progress.setProgress(per);
      //  progress.setProgress(Integer.parseInt(value[0]));
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    public UnCompressTask() {
        super();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //unzip(sourcePath, Location);

        try {
            unzip(new File(sourcePath), new File("/sdcard"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (caMain != null)
            caMain.onCompleteListener();
        return null;
    }


    public void unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                per++;
                publishProgress(per);
                try {
                    
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }

    private void publishProgress(int per) {
    }


    @Override
    protected void onPreExecute() {
        progress.setMessage("Uncompressing...");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // progress.setIndeterminate(false);
        progress.setProgress(0);
        progress.setMax(100);
        progress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UnCompressTask.this.cancel(true);
                dialog.dismiss();
            }
        });

        progress.show();

        super.onPreExecute();
    }


    public void setOnCompleteListener(CompleteAction ca) {
        caMain = ca;
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}


//    /**
//     *
//     * @param zipName
//     * @param toDir
//     * @param fromDir
//     */
//    public void extractZipFilesFromDir(String zipName, String toDir, String fromDir) {
//        if(!(toDir.charAt(toDir.length() - 1) == '/'))
//            toDir += "/";
//        if(!(fromDir.charAt(fromDir.length() - 1) == '/'))
//            fromDir += "/";
//
//        String org_path = fromDir + zipName;
//
//        extractZipFiles(org_path, toDir);
//    }
//
//    /**
//     *
//     * @param zip_file
//     * @param directory
//     */
//    public void extractZipFiles(String zip_file, String directory) {
//        byte[] data = new byte[BUFFER];
//        String name, path, zipDir;
//        ZipEntry entry;
//        ZipInputStream zipstream;
//
//        if(!(directory.charAt(directory.length() - 1) == '/'))
//            directory += "/";
//
//        if(zip_file.contains("/")) {
//            path = zip_file;
//            name = path.substring(path.lastIndexOf("/") + 1,
//                    path.length() - 4);
//            zipDir = directory + name + "/";
//
//        } else {
//            path = directory + zip_file;
//            name = path.substring(path.lastIndexOf("/") + 1,
//                    path.length() - 4);
//            zipDir = directory + name + "/";
//        }
//
//        new File(zipDir).mkdir();
//
//        try {
//            zipstream = new ZipInputStream(new FileInputStream(path));
//
//            while((entry = zipstream.getNextEntry()) != null) {
//                String buildDir = zipDir;
//                String[] dirs = entry.getName().split("/");
//
//                if(dirs != null && dirs.length > 0) {
//                    for(int i = 0; i < dirs.length - 1; i++) {
//                        buildDir += dirs[i] + "/";
//                        new File(buildDir).mkdir();
//                    }
//                }
//
//                int read = 0;
//                FileOutputStream out = new FileOutputStream(
//                        zipDir + entry.getName());
//                while((read = zipstream.read(data, 0, BUFFER)) != -1)
//                    out.write(data, 0, read);
//
//                zipstream.closeEntry();
//                out.close();
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//




