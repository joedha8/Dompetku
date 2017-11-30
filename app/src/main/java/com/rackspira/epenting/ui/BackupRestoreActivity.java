package com.rackspira.epenting.ui;


import android.app.Activity;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DbHelper;

import org.apache.poi.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BackupRestoreActivity extends BaseGoogleApiActivity {

    private static final String MIME_TYPE = "application/x-sqlite-3";
    private static String DBPath = "//data//" + "com.rackspira.epenting"
            + "//databases//" + DbHelper.DATABASE_NAME;
    private Button buttonBackup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_restore);

        buttonBackup = (Button) findViewById(R.id.button_backup);
        buttonBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private static final String TAG = "CreateFileActivity";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDriveClientReady() {
        System.out.println("ok already");
        try {
            createFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createFile() throws FileNotFoundException {
        final Path path = Paths.get(DBPath);
        // [START create_file]
        final Task<DriveFolder> rootFolderTask = getDriveResourceClient().getRootFolder();
        final Task<DriveContents> createContentsTask = getDriveResourceClient().createContents();
        Tasks.whenAll(rootFolderTask, createContentsTask)
                .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
                    @Override
                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
                        DriveFolder parent = rootFolderTask.getResult();
                        DriveContents contents = createContentsTask.getResult();
                        OutputStream outputStream = contents.getOutputStream();
                        Files.copy(path, outputStream);
                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                .setTitle(DbHelper.DATABASE_NAME)
                                .setMimeType(MIME_TYPE)
                                .setStarred(true)
                                .build();

                        return getDriveResourceClient().createFile(parent, changeSet, contents);
                    }
                })
                .addOnSuccessListener(this,
                        new OnSuccessListener<DriveFile>() {
                            @Override
                            public void onSuccess(DriveFile driveFile) {
                                showMessage(getString(R.string.file_created,
                                        driveFile.getDriveId().encodeToString()));
                                finish();
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to create file", e);
                        showMessage(getString(R.string.file_create_error));
                        finish();
                    }
                });
        // [END create_file]
    }


}
