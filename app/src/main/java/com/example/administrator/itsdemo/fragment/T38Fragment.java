package com.example.administrator.itsdemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 */
public class T38Fragment extends Fragment {

    private  ArrayList<RoadBean> roadBeanArrayList = new ArrayList<RoadBean>();
    private ListView listview_t38 = null;

    private T38Adapter t38Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_t38, container, false);
        listview_t38 = (ListView) view.findViewById(R.id.listview_t38);


        getRoadData();



        listview_t38.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                roadBeanArrayList.remove(position);
                t38Adapter.notifyDataSetChanged();


                return true;
            }
        });




        return view;
    }

    private void getRoadData() {


        if(roadBeanArrayList.size()!=0){

            roadBeanArrayList.clear();

        }


        new Thread(new Runnable() {
            @Override
            public void run() {


                for (int i = 1; i <= 12; i++) {


                    final int finalI = i;
                    new HttpAsyncUtils(Common.GET_ROAD_STATUS, "{\"RoadId\":" + finalI + "}") {


                        @Override
                        public void success(String data) {


                            try {
                                Log.i("wk", finalI + "==>json==>" + data);


                                RoadBean roadBean = new RoadBean();

                                roadBean.setRoadId(finalI);


                                JSONObject jsonObject = new JSONObject(data);

                                int status = jsonObject.getInt("Balance");

                                roadBean.setRoadStatus(status);

                                roadBeanArrayList.add(roadBean);

                                Log.i("wk","集合的长度==》"+roadBeanArrayList.size());

                                if(roadBeanArrayList.size() == 12){


                                    Log.i("wk","都获取到了");

                                    roadBeanArrayList.sort(new Comparator<RoadBean>() {

                                        //按照道路id进行升序排序
                                        @Override
                                        public int compare(RoadBean o1, RoadBean o2) {
                                            if(o1.getRoadId()<o2.getRoadId()){

                                                return -1;
                                            }else {

                                                return 1;
                                            }


                                        }
                                    });


                                    t38Adapter = new T38Adapter(roadBeanArrayList);

                                    listview_t38.setAdapter(t38Adapter);

                                    int roadId = roadBeanArrayList.get(0).getRoadId();


                                    Log.i("wk","排序之后的道路id:"+roadId);
                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


//                            roadBeanArrayList.add()


                        }

                        @Override
                        public void error() {

                        }
                    };

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();





    }

    class RoadBean {

        private int roadId;
        private int roadStatus;


        public int getRoadId() {
            return roadId;
        }

        public void setRoadId(int roadId) {
            this.roadId = roadId;
        }

        public int getRoadStatus() {
            return roadStatus;
        }

        public void setRoadStatus(int roadStatus) {
            this.roadStatus = roadStatus;
        }
    }



    class T38Adapter extends BaseAdapter{


        ArrayList<RoadBean> BeanList = null;

        public T38Adapter(ArrayList<RoadBean> BeanList) {
            this.BeanList = BeanList;
        }

        @Override
        public int getCount() {
            return BeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return BeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(getContext(),R.layout.item_t38,null);

            TextView tv_road_name = (TextView) view.findViewById(R.id.tv_road);
            TextView tv_road_status = (TextView)view.findViewById(R.id.tv_road_status);


            RoadBean roadBean = BeanList.get(position);

            tv_road_name.setText("第"+roadBean.getRoadId()+"号公路");

            if(roadBean.getRoadStatus() == 0){
                tv_road_status.setText("畅通");
                tv_road_status.setTextColor(Color.GREEN);
            }else {


                tv_road_status.setText("红色饱和");
                tv_road_status.setTextColor(Color.RED);

            }


            return view;
        }



    }

}
