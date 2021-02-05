package com.xindihuang.accountingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

//@author: Xindi Huang 16518350 zy18350@nottingham.edu.cn
public class ListViewAdapter extends BaseAdapter {

    private LinkedList<RecordBean> records = new LinkedList<>();

    private LayoutInflater mInflater;   //used for initialization
    private Context mContext;

    public ListViewAdapter(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(LinkedList<RecordBean> records){
        this.records = records;
        notifyDataSetChanged();
    }

    @Override
    //return the number of items in the listView
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.cell_list_view,null);

            RecordBean recordBean = (RecordBean) getItem(position);
            holder = new ViewHolder(convertView, recordBean);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}

//view used for saving resources by only initialize the records which is able to display
class ViewHolder{
    TextView remarkTV;
    TextView amountTV;
    TextView timeTV;
    ImageView categoryIcon;

    public ViewHolder(View itemView, RecordBean record){
        remarkTV = itemView.findViewById(R.id.textView_remark);
        amountTV = itemView.findViewById(R.id.textView_amount);
        timeTV = itemView.findViewById(R.id.textView_time);
        categoryIcon = itemView.findViewById(R.id.imageView_category);

        remarkTV.setText(record.getRemark());

        if (record.getType() == 1){
            amountTV.setText("- "+record.getAmount());
        } else {
            amountTV.setText("+ "+record.getAmount());
        }

        timeTV.setText(DateUtil.getFormattedTime(record.getTimeStamp()));
        categoryIcon.setImageResource(GlobalUtil.getInstance().getResourceIcon(record.getCategory()));
    }

}