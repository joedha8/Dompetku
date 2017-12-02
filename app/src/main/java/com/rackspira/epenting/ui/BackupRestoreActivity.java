package com.rackspira.epenting.ui;


import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.rackspira.epenting.R;
import com.rackspira.epenting.broadcast.AlarmReceiver;
import com.rackspira.epenting.database.DbHelper;
import com.rackspira.epenting.util.DBDrive;
import com.rackspira.epenting.util.SharedPreferencesStorage;

import org.apache.poi.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackupRestoreActivity extends BaseGoogleApiActivity {
    public static int ID = 213112116;
    private static final String MIME_TYPE = "application/x-sqlite-3";
    private static String DBPath = "/data/data/" + "com.rackspira.epenting"
            + "/databases/" + DbHelper.DATABASE_NAME;
    private static String title = "Backup_epenting.sqlite";
    private static int MODE_BACKUP = 100;
    private static int MODE_RESTORE = 200;
    private Button buttonBackup;
    private Button buttonRestore;
    private AppCompatCheckBox checkBoxAutoBackup;
    private ProgressBar mProgressBar;
    private Dialog dialog;
    private ExecutorService mExecutorService;
    private Context context;

    public BackupRestoreActivity(Context context) {
        this.context = context;
    }

    public BackupRestoreActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_restore);
        dialogProgress();

        final SharedPreferencesStorage storage = new SharedPreferencesStorage(this);
        final AlarmReceiver alarmReceiver = new AlarmReceiver();

        buttonBackup = (Button) findViewById(R.id.button_backup);
        buttonBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(MODE_BACKUP);
            }
        });

        buttonRestore = (Button) findViewById(R.id.button_restore);
        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setProgress(0);
                signIn(MODE_RESTORE);
            }
        });

        checkBoxAutoBackup = (AppCompatCheckBox) findViewById(R.id.checkbox_autobackup);
        checkBoxAutoBackup.setChecked(storage.getAutoBackup());
        checkBoxAutoBackup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                storage.setAutoBackup(isChecked);
                if (isChecked) {
                    signIn(MODE_BACKUP);
                    Calendar c = Calendar.getInstance();
                    alarmReceiver.setRepeatAlarmAutoBackup(BackupRestoreActivity.this, c, ID, AlarmReceiver.milMonth);
                } else {
                    alarmReceiver.cancelAlarm(BackupRestoreActivity.this, ID);
                }
            }
        });

        mExecutorService = Executors.newSingleThreadExecutor();
    }

    public void autoBackup(){
        signIn(MODE_BACKUP, context);
    }

    public void dialogProgress() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCancelable(false);
        mProgressBar = dialog.findViewById(R.id.progressBar);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setMax(100);
    }

    private static final String TAG = "CreateFileActivity";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDriveClientReady(int mode) {
        System.out.println("ok already");
        listFiles(mode);
        if (mode == MODE_RESTORE) {
            dialog.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createFile() throws IOException {
        final File file = new File(DBPath);
        final byte[] dbBytes = DBDrive.getInstance(this).getBytes(file);
        // [START create_file]
        final Task<DriveFolder> rootFolderTask = getDriveResourceClient().getRootFolder();
        final Task<DriveContents> createContentsTask = getDriveResourceClient().createContents();
        Tasks.whenAll(rootFolderTask, createContentsTask)
                .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
                    @Override
                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
                        DriveFolder parent = rootFolderTask.getResult();
                        DriveContents contents = createContentsTask.getResult();
                        try (OutputStream outputStream = contents.getOutputStream()) {
                            outputStream.write(dbBytes);
                        }

                        Log.d(TAG, "uploading");

                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                .setTitle(title)
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
                                showMessage("Backup berhasil");
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to create file", e);
                        showMessage("Backup tidak berhasil");
                    }
                });
        // [END create_file]
    }

    private void rewriteContents(DriveFile driveFile) throws IOException {
        // [START open_for_write]
        final File file = new File(DBPath);
        final byte[] dbBytes = DBDrive.getInstance(this).getBytes(file);
        Task<DriveContents> openTask =
                getDriveResourceClient().openFile(driveFile, DriveFile.MODE_WRITE_ONLY);
        // [END open_for_write]
        // [START rewrite_contents]
        openTask.continueWithTask(new Continuation<DriveContents, Task<Void>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                DriveContents driveContents = task.getResult();
                try (OutputStream out = driveContents.getOutputStream()) {
                    out.write(dbBytes);
                }
                // [START commit_content]
                Task<Void> commitTask =
                        getDriveResourceClient().commitContents(driveContents, null);
                // [END commit_content]
                return commitTask;
            }
        })
                .addOnSuccessListener(this,
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                showMessage("Backup berhasil");
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Backup tidak berhasil", e);
                        showMessage("Backup tidak berhasil");
                    }
                });
        // [END rewrite_contents]
    }

    /**
     * Retrieves results for the next page. For the first run,
     * it retrieves results for the first page.
     */
    private void listFiles(final int mode) {
        // [START query_title]
        Query query = new Query.Builder()
                .addFilter(Filters.eq(SearchableField.TITLE, title))
                .build();
        // [END query_title]
        Task<MetadataBuffer> queryTask =
                getDriveResourceClient()
                        .query(query)
                        .addOnSuccessListener(this,
                                new OnSuccessListener<MetadataBuffer>() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onSuccess(MetadataBuffer metadataBuffer) {
//                                        System.out.println("Ok ------- metadata " + metadataBuffer.getCount());

                                        if (metadataBuffer.getCount() == 0) {
                                            if (mode == MODE_BACKUP) {
                                                try {
                                                    createFile();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                showMessage("Belum ada data yang di backup.");
                                            }
                                        } else {
                                            if (mode == MODE_BACKUP) {
                                                try {
                                                    rewriteContents(metadataBuffer.get(0).getDriveId().asDriveFile());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                retrieveContents(metadataBuffer.get(0).getDriveId().asDriveFile());
                                            }

                                        }
                                    }
                                })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                                Log.e(TAG, "Error retrieving files", e);
//                                showMessage(getString(R.string.query_failed));
                            }
                        });
    }

    private void retrieveContents(DriveFile file) {
        // [START read_with_progress_listener]
        OpenFileCallback openCallback = new OpenFileCallback() {
            @Override
            public void onProgress(long bytesDownloaded, long bytesExpected) {
                // Update progress dialog with the latest progress.
                final int progress = (int) (bytesDownloaded * 100 / bytesExpected);
                System.out.println("Ok - " + progress);
                BackupRestoreActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setProgress(progress);
                    }
                });

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onContents(@NonNull DriveContents driveContents) {
                // onProgress may not be called for files that are already
                // available on the device. Mark the progress as complete
                // when contents available to ensure status is updated.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setProgress(100);
                    }
                });
                // Read contents
                // [START_EXCLUDE]
                try {
                    byte[] bytes = IOUtils.toByteArray(driveContents.getInputStream());
                    File fileDb = new File(DBPath);
                    OutputStream outputStream = new FileOutputStream(fileDb, false);
                    outputStream.write(bytes);
                    getDriveResourceClient().discardContents(driveContents);
                    dialog.dismiss();
                } catch (IOException e) {
                    onError(e);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onError(@NonNull Exception e) {
                // Handle error
                // [START_EXCLUDE]
                Log.e(TAG, "Unable to read contents", e);
//                showMessage(getString(R.string.read_failed));
                // [END_EXCLUDE]
            }
        };

        getDriveResourceClient().openFile(file, DriveFile.MODE_READ_ONLY, openCallback);
        // [END read_with_progress_listener]
    }

}
