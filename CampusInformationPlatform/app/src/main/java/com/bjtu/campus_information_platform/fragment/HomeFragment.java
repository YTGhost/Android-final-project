package com.bjtu.campus_information_platform.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.adapter.ClassroomAdapter;
import com.bjtu.campus_information_platform.fragment.view.MyCardView;
import com.bjtu.campus_information_platform.model.Hole;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    private String building = "逸夫";
    private String classTime = "第一节";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView classroomList = (RecyclerView) view.findViewById(R.id.classroomList);


        NiceSpinner bulidingSpinner = (NiceSpinner) view.findViewById(R.id.building_spinner);
        List<String> buildingDataset = new LinkedList<>(Arrays.asList("逸夫", "思源", "思西", "思东", "九教", "八教", "机械楼", "电气楼", "东教一楼"));
        bulidingSpinner.attachDataSource(buildingDataset);

        bulidingSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                building = (String)parent.getItemAtPosition(position);
            }
        });

        NiceSpinner classSpinner = (NiceSpinner) view.findViewById(R.id.class_spinner);
        List<String> classDataset = new LinkedList<>(Arrays.asList("第一节", "第二节", "第三节", "第四节", "第五节", "第六节", "第七节"));
        classSpinner.attachDataSource(classDataset);

        classSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                classTime = (String)parent.getItemAtPosition(position);
            }
        });

        Button queryButton = (Button) view.findViewById(R.id.class_query_button);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.put("building",building);
                params.put("course", classTime);

                HttpRequest.postClassQueryApi(params, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        List<String> list = (List<String>) responseObj;

                        if (list.size()==0){
                            Toast.makeText(getActivity(),"这回真没了",Toast.LENGTH_LONG).show();
                        }else {

                            for (int i=0;i<list.size();i++){
                                list.set(i,building+" "+list.get(i)+" --空闲--");
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            classroomList.setLayoutManager(linearLayoutManager);
                            ClassroomAdapter adapter = new ClassroomAdapter(list);
                            classroomList.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        Log.e("hole refresh", "failed");
                        Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_LONG).show();
                    }

                });
            }
        });


        return view;
    }
}
