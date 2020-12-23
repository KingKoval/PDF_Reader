package wyspa.example.com.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recView_pdf;
    public static ArrayList<File> list_files = new ArrayList<File>();
    ArrayAdapter<File> adapter;
    File folder;
    String[] items;
    private final int REQUEST_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission();
        } else {
            initView();
        }

    }

    private void permission() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
           Toast.makeText(this, "Please grant permission", Toast.LENGTH_SHORT).show();

           ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
           ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            Toast.makeText(this, "You have already granted permission", Toast.LENGTH_SHORT).show();
            initView();
        }

    }

    private void initView() {
        folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        list_files = getPdfFiles(folder);

        ArrayList<File> list_files2 = getPdfFiles(Environment.getExternalStorageDirectory());
        items = new String[list_files2.size()];

        for(int i = 0; i < items.length; i++){
            items[i] = list_files2.get(i).getName().replace(".pdf", "");
        }

        PDFAdapter pdfAdapter = new PDFAdapter(this, list_files, items);
        recView_pdf = (RecyclerView) findViewById(R.id.recView_pdf);
        recView_pdf.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recView_pdf.setAdapter(pdfAdapter);
    }

    private ArrayList<File> getPdfFiles(File folder) {
        ArrayList<File> list_files1 = new ArrayList<File>();
        File[] files = folder.listFiles();

        if(files != null){
            for(File itemFile : files){
                if(itemFile.isDirectory() && !itemFile.isHidden()){
                    list_files1.addAll(getPdfFiles(itemFile));
                } else {
                    if(itemFile.getName().endsWith(".pdf")){
                        list_files1.add(itemFile);
                    }
                }
            }
        }

        return list_files1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You already permission", Toast.LENGTH_SHORT).show();
                initView();
            }else{
                Toast.makeText(this, "Please press Allow to continue", Toast.LENGTH_SHORT).show();
            }
        }
    }
}