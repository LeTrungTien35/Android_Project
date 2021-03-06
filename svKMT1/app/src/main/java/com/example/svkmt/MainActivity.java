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
        //d??ng json ????? kh???i t???o listSV (listALL)

        listSV.add(new SV(1,"sv1", "Nguy???n V??n A", "54KMT","0987654321"));
        listSV.add(new SV(2, "sv2","Nguy???n V??n B", "54KMT","0988776655"));
        listSV.add(new SV(3, "sv3","Nguy???n V??n C", "54KMT","0912334455"));
        listSV.add(new SV(4, "sv4","Nguy???n V??n C1", "55KMT","55"));
        listSV.add(new SV(5, "sv5","Nguy???n V??n C2", "55KMT","6666"));
        listSV.add(new SV(6, "sv6","Nguy???n V??n C3", "55KMT","7777"));
        listSV.add(new SV(7, "sv7","Nguy???n V??n C4", "56KMT","888"));
        listSV.add(new SV(8, "sv8","Nguy???n V??n C5", "56KMT","99"));
        listSV.add(new SV(9, "sv9","Nguy???n V??n C6", "57KMT","0011"));
        listSV.add(new SV(10, "sv10","Nguy???n V??n C7", "57KMT","113"));
        listSV.add(new SV(11, "sv11","Nguy???n V??n C8", "53KMT","114"));
        listSV.add(new SV(11, "sv11","Nguy???n V??n C9", "54KMT","1142435678"));

        svListViewAdapter = new SVListViewAdapter(listSV); // ALL

        listViewSV=findViewById(R.id.listSV);
        //listViewSV.setAdapter(svListViewAdapter);

        listViewSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SV sv = (SV) svListViewAdapter.getItem(position);
                //L??m g?? ???? khi ch???n s???n ph???m (v?? d??? t???o m???t Activity hi???n th??? chi ti???t, bi??n t???p ..)
                //Toast.makeText(MainActivity.this, sv.ten+"\nS??T: "+sv.sdt, Toast.LENGTH_LONG).show();
                //make a call to sv number

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+sv.sdt));
                startActivity(intent);
            }
        });



        //==================combo l???p---------
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

        //--------------l???c theo text c???a ?? t??m ki???m-----
        //SV sv = (SV) svListViewAdapter.getItem(position);
        //khi ?? editText#tim: typing input keydown th?? s??? l???c tr??n nh???ng item sv ??ang hi???n th???: masv, hoten, sdt ch???a chu???i nh???p v??o
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
        //duy???t m???i sv ??ang show
        String lopSelected=spnChonLop.getSelectedItem().toString();
        ArrayList<SV> listSV1lop=(ArrayList<SV>)listSV.clone();

        if(!lopSelected.equals("ALL")){
            //view ds sv ch??? hi???n th??? sv c???a l???p ??ang ch???n
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

    //Model ph???n t??? d??? li???u hi???n
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

        //D??? li???u li??n k???t b???i Adapter l?? m???t m???ng c??c s???n ph???m
        final ArrayList<SV> listSV;

        SVListViewAdapter(ArrayList<SV> listSV) {
            this.listSV = listSV;
        }

        @Override
        public int getCount() {
            //Tr??? v??? t???ng s??? ph???n t???, n?? ???????c g???i b???i ListView
            return listSV.size();
        }

        @Override
        public Object getItem(int position) {
            //Tr??? v??? d??? li???u ??? v??? tr?? position c???a Adapter, t????ng ???ng l?? ph???n t???
            //c?? ch??? s??? position trong listProduct
            return listSV.get(position);
        }

        @Override
        public long getItemId(int position) {
            //Tr??? v??? m???t ID c???a ph???n
            return listSV.get(position).ID;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //convertView l?? View c???a ph???n t??? ListView, n???u convertView != null ngh??a l??
            //View n??y ???????c s??? d???ng l???i, ch??? vi???c c???p nh???t n???i dung m???i
            //N???u null c???n t???o m???i

            View viewSV;
            if (convertView == null) {
                viewSV = View.inflate(parent.getContext(), R.layout.sv_view, null);
            } else viewSV = convertView;

            //Bind s??? li???u ph???n t??? v??o View
            SV sv = (SV) getItem(position);
            ((TextView) viewSV.findViewById(R.id.masv)).setText(String.format("MaSV: %s", sv.masv));
            ((TextView) viewSV.findViewById(R.id.ten)).setText(String.format("%s", sv.ten));
            ((TextView) viewSV.findViewById(R.id.lop)).setText(String.format("L???p %s", sv.lop));


            return viewSV;
        }
    }
}