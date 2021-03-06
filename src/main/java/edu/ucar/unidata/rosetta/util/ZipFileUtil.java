package edu.ucar.unidata.rosetta.util;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**

 */
public class ZipFileUtil {

    private String name;

    public ZipFileUtil(String zipFileName) {
        this.setName(zipFileName.replace("file://", ""));
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void addAllToZip(List<String> filesToAdd) {
        addAllToZip(filesToAdd.toArray(new String[0]));
    }

    public void addAllToZip(String[] filesToAdd) {
        FileInputStream inStream;
        byte[] buffer = new byte[1024];
        int bytesRead;

        // Create input and output streams
        try {

            ZipOutputStream outStream = new ZipOutputStream(
                    new FileOutputStream(this.getName()));

            for (String inputFile : filesToAdd) {
                inStream = new FileInputStream(inputFile);
                String filenameInZip = FilenameUtils.getName(inputFile);

                // Add a zip entry to the output stream
                outStream.putNextEntry(new ZipEntry(filenameInZip));

                while ((bytesRead = inStream.read(buffer)) > 0) {
                    outStream.write(buffer, 0, bytesRead);
                }

                // Close zip entry and file streams
                outStream.closeEntry();
                inStream.close();
            }
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFileFromZip(String fileName) {
        String data = "";
        String line;
        try (ZipFile zf = new ZipFile(this.getName())) {
            // ZipEntry fileInZip = zf.getEntry(topLevelZipDir + "/" +
            // fileName);
            ZipEntry fileInZip = zf.getEntry(fileName);
            if (fileInZip != null) {
                InputStream inStream = zf.getInputStream(fileInZip);
                BufferedReader buffReader = new BufferedReader(
                        new InputStreamReader(inStream));
                while ((line = buffReader.readLine()) != null) {
                    data = data + line;
                }
                buffReader.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }
}
