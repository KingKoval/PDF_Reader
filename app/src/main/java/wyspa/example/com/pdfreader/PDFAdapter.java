package wyspa.example.com.pdfreader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<File> list_files;
    String[] items;

    public PDFAdapter(Context context, ArrayList<File> list_files, String[] items){
        this.context = context;
        this.list_files = list_files;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pdf_adapter, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView_pdfName.setText(items[position]);

        holder.pdf_item.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PDFOpener.class);

                        intent.putExtra("position", position);
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return list_files.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView_pdfName;
        ImageView imageView_pdfIcon;
        RelativeLayout pdf_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_pdfName = itemView.findViewById(R.id.textView_pdfName);
            imageView_pdfIcon = itemView.findViewById(R.id.imageView_pdfIcon);
            pdf_item = itemView.findViewById(R.id.pdf_item);
        }
    }
}
