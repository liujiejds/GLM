package com.example.admin.glm;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Filter;
import android.widget.SimpleAdapter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 17-11-20.
 */

public class MySimpleAdapter extends SimpleAdapter implements Filterable {
    private  GlmFilter filter;
    private ArrayList<Map<String,Object>> list;
    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        list=(ArrayList<Map<String,Object>>) data;
    }
    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter = new GlmFilter(list);
        }
        return filter;
    }
    private class GlmFilter extends Filter{
       private ArrayList<Map<String,Object>>  original;
       public GlmFilter(ArrayList<Map<String,Object>> list){
           this.original=list;
       }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
             FilterResults filterResults=new FilterResults();
             if (TextUtils.isEmpty(charSequence)){
                 filterResults.values=original;
                 filterResults.count=original.size();
             }else {
                 ArrayList<Map<String,Object>> mlist=new ArrayList<>();
                 for (Map<String,Object> map:list){
                     if (map.get("address").toString().toUpperCase().indexOf(charSequence.toString().toUpperCase())!=-1){
                         Log.i("MySimpleAdapter",map.get("address").toString()+"=="+charSequence);
                         mlist.add(map);
                     }
                 }
                 filterResults.values=mlist;
                 filterResults.count=mlist.size();
             }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                     list.clear();
                     list.addAll((Collection< ? extends Map<String,Object>>) filterResults.values);
                     if (list.size()>0){
                         notifyDataSetChanged();
                     }else {
                         notifyDataSetInvalidated();
                     }
        }
    }
}
