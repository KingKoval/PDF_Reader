package wyspa.example.com.pdfreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PDFOpener extends AppCompatActivity {

    PDFView pdfViewer;
    int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_opener);

        pdfViewer = (PDFView) findViewById(R.id.pdfViewer);
        position = getIntent().getIntExtra("position", -1);
        
        pdfView();
    }

    private void pdfView() {
        pdfViewer.fromFile(MainActivity.list_files.get(position))
                .enableSwipe(true)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
}