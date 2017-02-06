package com.yaxi.myview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by yaxi on 2017/1/3.
 */

public class ExpandableListActivity extends AppCompatActivity{
    private ExpandableListView expandableListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);
        expandableListView = (ExpandableListView) findViewById(R.id.expandable_lv);
        initExpandableListView();


    }

    private void initExpandableListView() {
        expandableListView.setAdapter(new ExpandableAdapter(this));
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

    class ExpandableAdapter extends BaseExpandableListAdapter{
        private String[] groupText;
        private String[][] childText;
        private Context context;
        private float scale;
        public ExpandableAdapter(Context context) {
            this.context = context;
            scale = context.getResources().getDisplayMetrics().density;
            groupText = new String[9];
            for (int i = 0; i < groupText.length; i++) {
                groupText[i] = "This is number "+i +"item";
            }
            childText = new String[9][5];
            for (int i = 0; i < groupText.length; i++) {
                for (int j = 0; j < childText[i].length; j++) {
                    childText[i][j] = "This is group"+i+"child"+j;
                }
            }
        }

        @Override
        public int getGroupCount() {
            return groupText.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childText[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupText[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childText[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_expandable_group,null);
            TextView tvGroupItem = (TextView) view.findViewById(R.id.tv_group);
            tvGroupItem.setText(groupText[groupPosition]);
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_expandable_group,null);
            TextView tvChildItem = (TextView) view.findViewById(R.id.tv_group);
            tvChildItem.setTextColor(Color.RED);
            tvChildItem.setTextSize(15);
            tvChildItem.setText(childText[groupPosition][childPosition]);
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
