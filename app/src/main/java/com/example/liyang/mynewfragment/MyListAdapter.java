package com.example.liyang.mynewfragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by liyang on 2017/4/12.
 */

public class MyListAdapter extends BaseAdapter {

    private Context context;
    public MyListAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public int getCount() {
        // How many items are in the data set represented by this Adapter.(在此适配器中所代表的数据集中的条目数)
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.(获取数据集中与指定索引对应的数据项)
        return null;
    }

    @Override
    public long getItemId(int position) {
        // Get the row id associated with the specified position in the list.(取在列表中与指定索引对应的行id)
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get a View that displays the data at the specified position in the data set.
        return null;
    }
}
