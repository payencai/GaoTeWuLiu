package com.gaote.wuliu.ui.net;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.client.mine.UserInfoActivity;
import com.gaote.wuliu.ui.net.mvp.model.NetInfo;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = MyPath.Net.Shop)
public class NetShopActivity extends AppCompatActivity {
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_shop);
        ButterKnife.bind(this);
        initView();
    }
    private void getInfo(){
        NetUtils.getInstance().get(MyApp.token, Api.BASE_URL + Api.Net.URL_GET_INFO, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<NetInfo> result= GsonUtil.fromJsonObject(response,NetInfo.class);
                if(result.getResultCode()==0){
                    initData(result.getData());
                }else{
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void initData(NetInfo netInfo){
        if(!TextUtils.isEmpty(netInfo.getPicUrl()))
        Glide.with(this).load(netInfo.getPicUrl()).into(ivHead);
        tvAccount.setText(netInfo.getAccount());
        tvName.setText(netInfo.getNetworkName());
        tvAddress.setText(netInfo.getAdress());

    }
    private void initView() {
        getInfo();
    }

    @OnClick({R.id.tv_wallet,R.id.iv_back,R.id.rl_pic,R.id.rl_address,R.id.rl_name})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.tv_wallet:
                ARouter.getInstance().build(MyPath.Net.Wallet).navigation();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_pic:
                setHead();
                break;
            case R.id.rl_address:
                break;
            case R.id.rl_name:
                ARouter.getInstance().build(MyPath.Net.SetName).navigation();
                break;

        }
    }
    private void setHead(){
        new AlertView.Builder().setContext(this)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("选择操作")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive("拍照", "从相册中选择")
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                         //ToastUtils.showShort(position+"");
                        if(position==0){//拍照
                            PictureSelector.create(NetShopActivity.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                        }else if(position==1){ //选择图片
                             selectPic();
                        }
                    }
                })
                .build()
                .show();
    }
    private void selectPic(){

        PictureSelector.create(NetShopActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
                .setOutputCameraPath("/customPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(false)// 是否压缩 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code


    }
}
