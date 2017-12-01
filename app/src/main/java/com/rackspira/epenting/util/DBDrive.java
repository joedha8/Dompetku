package com.rackspira.epenting.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.rackspira.epenting.database.DbHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kal on 18/11/17.
 */

public class DBDrive {

    public static DBDrive instance;
    private Context context;
    public static final String TAG = "drive";
    public static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    public static final int REQUEST_CODE_CREATOR = 2;
    /**
     * Request code for google sign-in
     */
    protected static final int REQUEST_CODE_SIGN_IN = 0;

    /**
     * Request code for the Drive picker
     */
    protected static final int REQUEST_CODE_OPEN_ITEM = 1;

    /**
     * Handles high-level drive functions like sync
     */
    private DriveClient mDriveClient;

    /**
     * Handle access to Drive resources/files.
     */
    private DriveResourceClient mDriveResourceClient;

    /**
     * Tracks completion of the drive picker
     */
    private TaskCompletionSource<DriveId> mOpenItemTaskSource;
    private GoogleSignInClient mGoogleSignInClient;
    private Bitmap mBitmapToSave;
    private static final String MIME_TYPE = "application/x-sqlite-3";
    private static String DBPath = "//data//" + "com.rackspira.dompetku"
            + "//databases//" + DbHelper.DATABASE_NAME;


    private void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "<package name>"
                        + "//databases//" + "<database name>";
                String backupDBPath = "<backup db filename>"; // From SD directory.
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(backupDB).getChannel();
                FileChannel dst = new FileOutputStream(currentDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(context, "Import Successful!",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

            Toast.makeText(context, "Import Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    private void exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.rackspira.dompetku"
                        + "//databases//" + DbHelper.DATABASE_NAME;
                String backupDBPath = "<destination>";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(context, "Backup Successful!",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

            Toast.makeText(context, "Backup Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    public static DBDrive getInstance(Context context) {
        if (instance == null)
            return new DBDrive(context);

        return instance;
    }

    public DBDrive(Context context) {
        this.context = context;
    }

    /**
     * Start sign in activity.
     */
    public void signIn() {
        Log.i(TAG, "Start sign in");
        mGoogleSignInClient = buildGoogleSignInClient();
        ((Activity) context).startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    /**
     * Build a Google SignIn client.
     */
    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(context, signInOptions);
    }

    /**
     * Create a new file and save it to Drive.
     */
    private void saveFileToDrive() {
        // Start by creating a new contents, and setting a callback.
        Log.i(TAG, "Creating new contents.");
        final File data = Environment.getDataDirectory();

        mDriveResourceClient
                .createContents()
                .continueWithTask(
                        new Continuation<DriveContents, Task<Void>>() {
                            @Override
                            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                                return createFileIntentSender(task.getResult(), new File(data, DBPath));
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Failed to create new contents.", e);
                            }
                        });
    }

    /**
     * Creates an {@link IntentSender} to start a dialog activity with configured {@link
     * CreateFileActivityOptions} for user to create a new photo in Drive.
     */
    private Task<Void> createFileIntentSender(DriveContents driveContents, File file) {
        Log.i(TAG, "New contents created.");
        // Get an output stream for the contents.
        OutputStream outputStream = driveContents.getOutputStream();
        // Write the bitmap data from it.
        ByteArrayOutputStream fileStream = new ByteArrayOutputStream();
        try {
            outputStream.write(fileStream.toByteArray());
        } catch (IOException e) {
            Log.w(TAG, "Unable to write file contents.", e);
        }

        // Create the initial metadata - MIME type and title.
        // Note that the user will be able to change the title later.
        MetadataChangeSet metadataChangeSet =
                new MetadataChangeSet.Builder()
                        .setMimeType(MIME_TYPE)
                        .setTitle(DbHelper.DATABASE_NAME)
                        .build();
        // Set up options to configure and display the create file activity.
        CreateFileActivityOptions createFileActivityOptions =
                new CreateFileActivityOptions.Builder()
                        .setInitialMetadata(metadataChangeSet)
                        .setInitialDriveContents(driveContents)
                        .build();

        return mDriveClient
                .newCreateFileActivityIntentSender(createFileActivityOptions)
                .continueWith(
                        new Continuation<IntentSender, Void>() {
                            @Override
                            public Void then(@NonNull Task<IntentSender> task) throws Exception {
                                ((Activity) context).startIntentSenderForResult(task.getResult(), REQUEST_CODE_CREATOR, null, 0, 0, 0);
                                return null;
                            }
                        });
    }

    public void result(int requestCode, int resultCode, Intent data) {
        Log.i(DBDrive.TAG, requestCode + " " + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                Log.i(DBDrive.TAG, "Sign in request code");
                // Called after user is signed in.
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(DBDrive.TAG, "Signed in successfully.");
                    // Use the last signed in account here since it already have a Drive scope.
                    mDriveClient = Drive.getDriveClient(context, GoogleSignIn.getLastSignedInAccount(context));
                    // Build a drive resource client.
                    mDriveResourceClient =
                            Drive.getDriveResourceClient(context, GoogleSignIn.getLastSignedInAccount(context));
                    // Start camera.
                    ((Activity) context).startActivityForResult(
                            new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAPTURE_IMAGE);
                } else {
                    Log.i(DBDrive.TAG, "Signed in failed");
                }
                break;
            case REQUEST_CODE_CAPTURE_IMAGE:
                Log.i(TAG, "capture image request code");
                // Called after a photo has been taken.
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "Image captured successfully.");
                    // Store the image data as a bitmap for writing later.
                    mBitmapToSave = (Bitmap) data.getExtras().get("data");
                    saveFileToDrive();
                }
                break;
            case REQUEST_CODE_CREATOR:
                Log.i(TAG, "creator request code");
                // Called after a file is saved to Drive.
                if (resultCode == RESULT_OK) {
                    Log.i(TAG, "Image successfully saved.");
                    mBitmapToSave = null;
                    // Just start the camera again for another photo.
                    ((Activity) context).startActivityForResult(
                            new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAPTURE_IMAGE);
                }
                break;
        }
    }

    public byte[] getBytes(File file) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();

        return bytesArray;
    }

}
