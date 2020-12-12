package com.bjtu.campus_information_platform.fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bjtu.campus_information_platform.R;
import com.bjtu.campus_information_platform.fragment.view.MyCardView;
import com.bjtu.campus_information_platform.model.Hole;
import com.bjtu.campus_information_platform.util.network.HttpRequest;
import com.bjtu.campus_information_platform.util.network.MyJsonCallback;
import com.bjtu.campus_information_platform.util.network.OkHttpException;
import com.bjtu.campus_information_platform.util.network.RequestParams;
import com.bjtu.campus_information_platform.util.network.ResponseCallback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import stream.customalert.CustomAlertDialogue;

public class HoleFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    private ImageButton img_btn;
    private LinearLayout hole_linear_layout;

    Handler mHandler;

    private BGARefreshLayout mRefreshLayout;
    BGAStickinessRefreshViewHolder refreshViewHolder;

    public View view;

    private Long index_oldest = Long.valueOf(-1);    //最晚编号
    private Long index_newest =  Long.valueOf(-1);   //最新编号
    private boolean isHoleInit;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_hole, container, false);
        hole_linear_layout = (LinearLayout) view.findViewById(R.id.hole_linear_layout);
        img_btn = (ImageButton) view.findViewById(R.id.addHole);

        isHoleInit=true;
        index_oldest = Long.valueOf(-1);    //最晚编号
        index_newest =  Long.valueOf(-1);   //最新编号

        ArrayList<String> boxHint = new ArrayList<>();
        boxHint.add("");

        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(getActivity())
                        .setStyle(CustomAlertDialogue.Style.INPUT)
                        .setTitle("To: 树洞")
                        .setPositiveText("发送")
                        .setPositiveColor(R.color.positive)
                        .setPositiveTypeface(Typeface.DEFAULT_BOLD)
                        .setOnInputClicked(new CustomAlertDialogue.OnInputClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog, ArrayList<String> inputList) {
                                String content = inputList.get(0);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                                String time=format.format(new Date());

                                RequestParams params = new RequestParams();
                                try {
                                    params.put("id",-1);
                                    params.put("content",content);
                                    params.put("time",time);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                HttpRequest.postHoleAddHoleApi(params, new ResponseCallback() {
                                    @Override
                                    public void onSuccess(Object responseObj) {
                                        beginRefreshing();
                                    }

                                    @Override
                                    public void onFailure(OkHttpException failuer) {
                                        Log.e("hole refresh", "failed");
                                        Toast.makeText(getActivity(),"这回真没了",Toast.LENGTH_LONG).show();
                                        mRefreshLayout.endRefreshing();
                                    }

                                });

                                dialog.dismiss();
                            }
                        })
                        .setNegativeText("取消")
                        .setNegativeColor(R.color.negative)
                        .setOnNegativeClicked(new CustomAlertDialogue.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setBoxInputHint(boxHint)
                        .setDecorView(getActivity().getWindow().getDecorView())
                        .build();
                alert.show();
            }
        });

        initRefreshLayout();

        beginRefreshing();

        return view;
    }


    /**初始化刷新加载控件*/
    private void initRefreshLayout(){
        mRefreshLayout = findView(view, R.id.rl_hole_refresh);
        mRefreshLayout.setDelegate(this);
        refreshViewHolder = new BGAStickinessRefreshViewHolder(this.getActivity(), true);
        refreshViewHolder.setRotateImage(R.drawable.bga_refresh);
        refreshViewHolder.setStickinessColor(R.color.gery_inactive);
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.colorPrimary);

        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        // 在这里加载最新数据

        beginRefreshing();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以

        beginLoadingMore();

        return true;
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        //mRefreshLayout.beginRefreshing();

        HttpRequest.getHoleRefreshApi(index_newest.toString(),null, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

                List<Hole> list = (List<Hole>) responseObj;


                if (list.size()==0){
                    Toast.makeText(getActivity(),"这回真没了",Toast.LENGTH_LONG).show();
                }else {
                    list.forEach(hole -> {
                        MyCardView myCardView = new MyCardView(getActivity(),null);
                        myCardView.setReply_content(hole.getContent());
                        myCardView.setReply_time("发表于：" + hole.getTime());
                        hole_linear_layout.addView(myCardView,0);
                    });
                    index_newest = list.get(list.size()-1).getId();

                    if (isHoleInit){
                        index_oldest = list.get(0).getId();
                        isHoleInit=false;
                    }
                }
                
                mRefreshLayout.endRefreshing();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.e("hole refresh", "failed");
                Toast.makeText(getActivity(),"这回真没了",Toast.LENGTH_LONG).show();
                mRefreshLayout.endRefreshing();
            }

        });
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        //mRefreshLayout.beginLoadingMore();

        HttpRequest.getHoleLoadMoreApi(index_oldest.toString(),null, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {

                List<Hole> list = (List<Hole>) responseObj;

                if (list.size()==0){
                    Toast.makeText(getActivity(),"这回真没了",Toast.LENGTH_LONG).show();
                }else {

                    for(int i=list.size()-1;i>=0;i--){
                        MyCardView myCardView = new MyCardView(getActivity(),null);
                        myCardView.setReply_content(list.get(i).getContent());
                        myCardView.setReply_time("发表于：" + list.get(i).getTime());
                        hole_linear_layout.addView(myCardView);
                    }

                    index_oldest = list.get(0).getId();
                }

                mRefreshLayout.endLoadingMore();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.e("hole load more", "failed");
                Toast.makeText(getActivity(),"这回真没了",Toast.LENGTH_LONG).show();

                mRefreshLayout.endLoadingMore();
            }

        });
    }

    public <VT> VT findView(View rootView,int resId){
        VT t = (VT) rootView.findViewById(resId);
        return t;
    }
}
