package com.rackspira.epenting.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nononsenseapps.filepicker.AbstractFilePickerFragment;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;
import com.rackspira.epenting.ExcelReport.BuildReportWorksheet;
import com.rackspira.epenting.R;
import com.rackspira.epenting.database.DataMasuk;
import com.rackspira.epenting.database.DbHelper;
import com.victor.loading.rotate.RotateLoading;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportActivity extends AppCompatActivity {
    private Button buttonExport;
    private RotateLoading loading;
    private DbHelper dbHelper;
    private TextView textViewLokasi;

    static final int CODE_SD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        dbHelper = DbHelper.getInstance(this);
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        loading = (RotateLoading)findViewById(R.id.rotateloading);
        buttonExport = (Button) findViewById(R.id.button_export);
        textViewLokasi = (TextView) findViewById(R.id.textView2);
        buttonExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CODE_SD, FilePickerActivity.class);

                /*buttonExport.setEnabled(false);
                loading.start();
                new ExportTask().execute();*/
            }
        });
    }

    protected void startActivity(final int code, final Class<?> klass) {
        final Intent i = new Intent(this, klass);

        i.setAction(Intent.ACTION_GET_CONTENT);

        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);

        // What mode is selected
        final int mode=AbstractFilePickerFragment.MODE_DIR;

        i.putExtra(FilePickerActivity.EXTRA_MODE, mode);

        // This line is solely so that test classes can override intents given through UI
        i.putExtras(getIntent());

        startActivityForResult(i, code);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK &&
                (CODE_SD == requestCode)) {
            // Use the provided utility method to parse the result
            List<Uri> files = Utils.getSelectedFilesFromResult(intent);

            // Do something with your list of files here
            StringBuilder sb = new StringBuilder();
            for (Uri uri : files) {
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                sb.append(CODE_SD == requestCode ?
                        Utils.getFileForUri(uri).toString() :
                        uri.toString());
            }
            textViewLokasi.setText("location file in : "+sb.toString());
        }
    }

    public static boolean exportExcel(Context context, String fileName, Map<String, Object> data) {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.w("FileUtils", "Storage not available or read only");
            return false;
        }

        boolean success = false;
        BuildReportWorksheet reportWorksheet = new BuildReportWorksheet(data);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            reportWorksheet.getWorkbook().write(fileOutputStream);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != fileOutputStream)
                    fileOutputStream.close();
            } catch (Exception ex) {
            }
        }

        return success;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private class ExportTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            List<DataMasuk> pemasukkan = dbHelper.getPemasukkan();
            List<DataMasuk> pengeluaran = dbHelper.getPengeluaran();

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("pemasukkan", pemasukkan);
            data.put("pengeluaran", pengeluaran);
            exportExcel(ExportActivity.this, "report_uangku_"+new Date().getTime()+".xls", data);
            return "Export sukses!";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.stop();
            buttonExport.setEnabled(true);
            Toast.makeText(ExportActivity.this, s, Toast.LENGTH_LONG).show();
        }
    }
}
