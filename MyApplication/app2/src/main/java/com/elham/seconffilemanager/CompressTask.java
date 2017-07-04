package com.elham.seconffilemanager;

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
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Elham on 6/10/2017.
 */

public class CompressTask extends AsyncTask<Void, String, Void> {

    String sourcePath, toLocation;
    ProgressDialog progress;
    CompleteAction caMain;
    private static final int BUFFER = 2048;
    int filecountertotal;
    int filecounter = 0;
    String inputName;
    ArrayList<String> sourceFiles;
    String toLoc2;
    List<String> fileList;
    private static final String SOURCE_FOLDER = "C:\\testzip";


    public CompressTask(ProgressDialog progress, ArrayList<String> sourceFiles, String inputName) {
        //this.sourcePath = ssss;
        // this.toLocation = s;
        this.progress = progress;
        this.sourceFiles = sourceFiles;
        this.inputName = inputName;
    }

    @Override
    protected void onPostExecute(Void unused) {
        progress.dismiss();


    }

    @Override
    protected void onProgressUpdate(String... value) {

        Log.d("ANDRO_ASYNC", value[0]);

        progress.setProgress(Integer.parseInt(value[0]));
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    public CompressTask() {
        super();
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (int i = 0; i < 1; i++) {
            File sf = new File(sourceFiles.get(i));
            sourcePath = sf.getPath();

            String filePath = sourcePath.substring(0, sourcePath.lastIndexOf(File.separator));
            toLoc2 = filePath + "/" + inputName;
        }

        for (int i = 0; i < sourceFiles.size(); i++)
            filecountertotal +=  counter(new File(sourceFiles.get(i)));

        for (int i = 0; i < 1; i++) {
           if (new File(sourceFiles.get(i)).isDirectory()) {
                try {

                    zipFolder(sourceFiles, toLoc2);
               } catch (Exception e) {
                    e.printStackTrace();
                }
            } else


                zipFiles(sourceFiles);


        }






        //final boolean b = zipFileAtPath( sourcePath, sourcePath + ".zip");
        // createZipFile(sourcePath);
        if (caMain != null)
            caMain.onCompleteListener();
        return null;
    }

    public static int counter(File sourcefile) {
        File[] files = sourcefile.listFiles();
        int count = 0;
        if(files ==null)
            return 1;
        for (File f : files)
            if (f.isDirectory())
                count += counter(f);
            else
                count++;

        return count;
    }


    public void zipFolder(ArrayList<String> src2Folder, String destZipFile)
            throws Exception {
        String srcFolder;
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);
        for ( int i= 0; i< src2Folder.size() ; i++)
        {
            File  sf = new File(  src2Folder.get(i));
            srcFolder= sf.getPath();
            addFolderToZip("", srcFolder, zip);

        }
        zip.flush();
        zip.close();



    }

    private void addFileToZip(String path, String srcFile,
                              ZipOutputStream zip) throws Exception {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            filecounter++;
            publishProgress("" + (int) ((filecounter * 100) / filecountertotal));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    private void addFolderToZip(String path, String srcFolder,
                                ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);
        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
               // addFileToZip(folder.getName(), toLoc2, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/"
                        + fileName, zip);
            }
        }
    }






    @Override
    protected void onPreExecute() {
        progress.setMessage("compressing...");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //   progress.setIndeterminate(false);
        progress.setProgress(0);
        progress.setMax(100);
        progress.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CompressTask.this.cancel(true);
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


    public boolean zipFileAtPath(ArrayList<String> sourcePath, String toLocation) throws IOException {
        final int BUFFER = 2048;
        BufferedInputStream origin = null;
        FileOutputStream dest = new FileOutputStream(toLocation);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
        for (int i = 0; i < sourcePath.size(); i++) {
            File sourceFile = new File(sourcePath.get(i));
            String source2File = sourceFile.getPath();
            try {

                if (sourceFile.isDirectory()) {
                    zipSubFolder(out, sourceFile, sourceFile.getParent().length());
                } else {
                    byte data[] = new byte[BUFFER];

                    FileInputStream fi = new FileInputStream(String.valueOf(sourcePath));
                    origin = new BufferedInputStream(fi, BUFFER);
                    ZipEntry entry = new ZipEntry(getLastPathComponent(sourceFile.getPath()));
                    out.putNextEntry(entry);

                    int count;

                    while ((count = origin.read(data, 0, BUFFER)) != -1) {

                        out.write(data, 0, count);
                    }



            }
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }}
            return true;
        }


/*
 *
 * Zips a subfolder
 *
 */






    private void zipSubFolder(ZipOutputStream out, File folder,
                              int basePathLength) throws IOException {

        final int BUFFER = 2048;

        File[] fileList = folder.listFiles();
        int s = fileList.length;
        BufferedInputStream origin = null;
        for (File file : fileList) {
            if (file.isDirectory()) {
                zipSubFolder(out, file, basePathLength);
            } else {
                byte data[] = new byte[BUFFER];
                String unmodifiedFilePath = file.getPath();

                String relativePath = unmodifiedFilePath
                        .substring(basePathLength);
                FileInputStream fi = new FileInputStream(unmodifiedFilePath);
                origin = new BufferedInputStream(fi, BUFFER);

                ZipEntry entry = new ZipEntry(relativePath);
                out.putNextEntry(entry);

                int count;
                filecounter++;
                publishProgress(""+(int)((filecounter*100)/filecountertotal));
                while ((count = origin.read(data, 0, BUFFER)) != -1) {

                    out.write(data, 0, count);
                }
                origin.close();
            }
        }
    }

    /*
     * gets the last path component
     *
     * Example: getLastPathComponent("downloads/example/fileToZip");
     * Result: "fileToZip"
     */
    public String getLastPathComponent(String filePath) {
        String[] segments = filePath.split("/");
        if (segments.length == 0)
            return "";
        String lastPathComponent = segments[segments.length - 1];
        return lastPathComponent;
    }



    /**
     * @param path
     */
    public void createZipFile(String path) {
        File dir = new File(path);
        String[] list = dir.list();
        String name = path.substring(path.lastIndexOf("/"), path.length());
        String _path;

        if (!dir.canRead() || !dir.canWrite())
            return;

        int len = list.length;

        if (path.charAt(path.length() - 1) != '/')
            _path = path + "/";
        else
            _path = path;

        try {
            ZipOutputStream zip_out = new ZipOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(_path + name + ".zip"), BUFFER));

            for (int i = 0; i < len; i++)
                zip_folder(new File(_path + list[i]), zip_out);

            zip_out.close();

        } catch (FileNotFoundException e) {
            Log.e("File not found", e.getMessage());

        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
    }


    private void zip_folder(File file, ZipOutputStream zout) throws IOException {
        byte[] data = new byte[BUFFER];
        int read;

        if (file.isFile()) {
            ZipEntry entry = new ZipEntry(file.getName());
            zout.putNextEntry(entry);
            BufferedInputStream instream = new BufferedInputStream(
                    new FileInputStream(file));

            while ((read = instream.read(data, 0, BUFFER)) != -1)
                zout.write(data, 0, read);

            zout.closeEntry();
            instream.close();

        } else if (file.isDirectory()) {
            String[] list = file.list();
            int len = list.length;

            for (int i = 0; i < len; i++)
                zip_folder(new File(file.getPath() + "/" + list[i]), zout);
        }
    }


    public void zipFiles(List<String> files){

        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
       // FileOutputStream dest = new FileOutputStream(toLocation);
        try {
            fos = new FileOutputStream(toLoc2);
            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
            for(String filePath:files){
                File input = new File(filePath);
                fis = new FileInputStream(input);
                ZipEntry ze = new ZipEntry(input.getName());
                System.out.println("Zipping the file: "+input.getName());
                zipOut.putNextEntry(ze);
                byte[] tmp = new byte[4*1024];
                int size = 0;
                while((size = fis.read(tmp)) != -1){
                    zipOut.write(tmp, 0, size);
                }
                zipOut.flush();
                fis.close();
            }
            zipOut.close();
            System.out.println("Done... Zipped the files...");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try{
                if(fos != null) fos.close();
            } catch(Exception ex){

            }
        }
    }

}






