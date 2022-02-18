package com.example.svkmt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<SV> listSV;
    SVListViewAdapter svListViewAdapter;
    ListView listViewSV;
    EditText txtTim;

    Spinner spnChonLop;

    Context myContext;
    private void writeToFile(String fn, String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fn, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(String fn,Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(fn);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myContext=this;

        String qx= readFromFile("q.txt",myContext).trim();
        //Toast.makeText(MainActivity.this, "q read file="+qx, Toast.LENGTH_LONG).show();

        //Khoi tao ListProduct
        listSV = new ArrayList<>();
        //data fix hardCode
        //data get from Cloud (dynamic): first time -> save local -> 2nd .. (check local: exists Load Local, else get from cloud)

        //get json from Local file data.txt -> if(empty) get json from Cloud -> save local
        //dùng json để khởi tạo listSV (listALL)

        listSV.add(new SV(1,"sv1", "Nguyễn Văn A", "54KMT","0987654321"));
        listSV.add(new SV(2, "sv2","Nguyễn Văn B", "54KMT","0988776655"));
        listSV.add(new SV(3, "sv3","Nguyễn Văn C", "54KMT","0912334455"));
        listSV.add(new SV(4, "sv4","Nguyễn Văn C1", "55KMT","55"));
        listSV.add(new SV(5, "sv5","Nguyễn Văn C2", "55KMT","6666"));
        listSV.add(new SV(6, "sv6","Nguyễn Văn C3", "55KMT","7777"));
        listSV.add(new SV(7, "sv7","Nguyễn Văn C4", "56KMT","888"));
        listSV.add(new SV(8, "sv8","Nguyễn Văn C5", "56KMT","99"));
        listSV.add(new SV(9, "sv9","Nguyễn Văn C6", "57KMT","0011"));
        listSV.add(new SV(10, "sv10","Nguyễn Văn C7", "57KMT","113"));
        listSV.add(new SV(11, "sv11","Nguyễn Văn C8", "53KMT","114"));
        listSV.add(new SV(11, "sv11","Nguyễn Văn C9", "54KMT","1142435678"));

        svListViewAdapter = new SVListViewAdapter(listSV); // ALL

        listViewSV=findViewById(R.id.listSV);
        //listViewSV.setAdapter(svListViewAdapter);

        listViewSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SV sv = (SV) svListViewAdapter.getItem(position);
                //Làm gì đó khi chọn sản phẩm (ví dụ tạo một Activity hiện thị chi tiết, biên tập ..)
                //Toast.makeText(MainActivity.this, sv.ten+"\nSĐT: "+sv.sdt, Toast.LENGTH_LONG).show();
                //make a call to sv number

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+sv.sdt));
                startActivity(intent);
            }
        });



        //==================combo lớp---------
        spnChonLop=findViewById(R.id.chon_lop);

        List<String> listLop = new ArrayList<>();
        listLop.add("ALL");
        listLop.add("54KMT");
        listLop.add("55KMT");
        listLop.add("56KMT");
        listLop.add("57KMT");

        ArrayAdapter<String> adapterLop = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listLop);
        adapterLop.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnChonLop.setAdapter(adapterLop);

        spnChonLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LocDuLieu();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //--------------lọc theo text của ô tìm kiếm-----
        //SV sv = (SV) svListViewAdapter.getItem(position);
        //khi ô editText#tim: typing input keydown thì sẽ lọc trên những item sv đang hiển thị: masv, hoten, sdt chứa chuỗi nhập vào
        txtTim=findViewById(R.id.tim);
        txtTim.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String q = s.toString();
                //test viec Save local ->: save q . app run: load q -> Toast:
                writeToFile("q.txt",q, myContext );
                //save text file
                //save sqlite

                LocDuLieu();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        txtTim.setText(qx);
        int position = txtTim.length();
        Editable etext = txtTim.getText();
        Selection.setSelection(etext, position);

        LocDuLieu();
    }

    void LocDuLieu(){
        String q = txtTim.getText().toString();
        //duyệt mọi sv đang show
        String lopSelected=spnChonLop.getSelectedItem().toString();
        ArrayList<SV> listSV1lop=(ArrayList<SV>)listSV.clone();

        if(!lopSelected.equals("ALL")){
            //view ds sv chỉ hiển thị sv của lớp đang chọn
            for (int i = 0; i < listSV1lop.size(); ) {
                if (!listSV1lop.get(i).lop.equals(lopSelected)) {
                    listSV1lop.remove(i);
                } else {
                    i++;
                }
            }
        }

        for (int i = 0; i < listSV1lop.size(); ) {
            if (!listSV1lop.get(i).isOKSearch(q)){
                listSV1lop.remove(i);
            } else {
                i++;
            }
        }

        svListViewAdapter=new SVListViewAdapter(listSV1lop);
        listViewSV.setAdapter(svListViewAdapter);

        //Toast.makeText(MainActivity.this, "q="+q+" -> listSV1lop="+listSV1lop.size(), Toast.LENGTH_LONG).show();
    }

    //Model phần tử dữ liệu hiện
    class SV {
        String masv, ten, lop, sdt;
        int ID;

        public SV(int ID, String masv, String ten,String lop,String sdt) {
            this.ID=ID;
            this.masv=masv;
            this.ten=ten;
            this.lop=lop;
            this.sdt=sdt;
        }

        public boolean isOKSearch(String q){
            return masv.contains(q) || ten.contains(q) || sdt.contains(q);
        }
    }

    class SVListViewAdapter extends BaseAdapter {

        //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
        final ArrayList<SV> listSV;

        SVListViewAdapter(ArrayList<SV> listSV) {
            this.listSV = listSV;
        }

        @Override
        public int getCount() {
            //Trả về tổng số phần tử, nó được gọi bởi ListView
            return listSV.size();
        }

        @Override
        public Object getItem(int position) {
            //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
            //có chỉ số position trong listProduct
            return listSV.get(position);
        }

        @Override
        public long getItemId(int position) {
            //Trả về một ID của phần
            return listSV.get(position).ID;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
            //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
            //Nếu null cần tạo mới

            View viewSV;
            if (convertView == null) {
                viewSV = View.inflate(parent.getContext(), R.layout.sv_view, null);
            } else viewSV = convertView;

            //Bind sữ liệu phần tử vào View
            SV sv = (SV) getItem(position);
            ((TextView) viewSV.findViewById(R.id.masv)).setText(String.format("MaSV: %s", sv.masv));
            ((TextView) viewSV.findViewById(R.id.ten)).setText(String.format("%s", sv.ten));
            ((TextView) viewSV.findViewById(R.id.lop)).setText(String.format("Lớp %s", sv.lop));


            return viewSV;
        }
    }
}