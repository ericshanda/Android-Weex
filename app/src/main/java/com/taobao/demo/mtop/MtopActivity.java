package com.taobao.demo.mtop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.luyujie.innovationcourse.R;
import com.taobao.demo.EmasInit;

import mtopsdk.mtop.common.MtopCallback.MtopFinishListener;
import mtopsdk.mtop.common.MtopFinishEvent;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.domain.MethodEnum;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.intf.Mtop;
import mtopsdk.mtop.intf.MtopBuilder;
import mtopsdk.mtop.intf.MtopContentType;
import mtopsdk.mtop.intf.MtopProtocolVersion;

/**
 * @author jianbo
 * @Desctription
 * @Date 2018/4/24
 * @Email kaneki.cjb@alibaba-inc.com
 */
public class MtopActivity extends Activity {

    private static final String[] ENTRIES = { "String", "Int", "Boolean", "Double"};   //定义数组

    private static HashMap<String, Object> dataMap = new HashMap();
    private static final String paramterType[] = {"String", "Int", "Boolean", "Double"};

    static {
        dataMap.put("testBool", true);
        dataMap.put("testInteger", 2);
        dataMap.put("testBoolean", false);
        dataMap.put("testDoub", 1.1);
        dataMap.put("testStr", "test str");
        dataMap.put("testInt", 1);
        dataMap.put("testDouble", 1.2);
    }

    /*** 请求参数配置 ***/
    private final String api = "mtop.bizmock.test.passbody.http";
    private final String v = "1.0";
    private String data = "{\"testBool\":true,\"testInteger\":2,\"testBoolean\":false,\"testDoub\":1.1,"
        + "\"testStr\":\"test str\",\"testInt\":1,\"testDouble\":1.2}";
    private HashMap<String, String> queryMap = new HashMap<>();


    /*** 请求日志 ***/
    private StringBuilder consoleLogBuilder;

    private Button configBtn;
    private Button sendBtn;
    private TextView consoleTv;

    /*** 配置弹窗 ***/
    private AlertDialog inputDialog;
    private View dialogContentView;
    private LinearLayout paramsContainerLayout;

    private Button paramsAddBtn;
    private Button paramsRemoveBtn;
    private Button cancelBtn;
    private Button confirmBtn;

    private EditText apiNameEt;
    private EditText apiVersionEt;
    private EditText domainEt;
    private EditText dataEt;

    private RadioButton postRadioBtn;

    private RadioButton urlEncodeRadioBtn;
    private RadioButton jsonRadioBtn;

    private MtopBuilder mtopBuilder;

    private List<View> paramsViewList = new ArrayList<>();
    private Mtop mtopInstance = Mtop.instance(Mtop.Id.INNER, MtopActivity.this, "");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtop_demo);

        initDefaultRequest();
        initView();
        initDialogView();
        setListener();
        setDialogListener();
    }

    private void initDefaultRequest() {
        MtopRequest mtopRequest = new MtopRequest();
        mtopRequest.setApiName(api);
        mtopRequest.setVersion(v);
        mtopRequest.setData(data);

        mtopBuilder = mtopInstance.build(mtopRequest, "600000@taobao_android_7.7.3");
        mtopBuilder.setCustomDomain(EmasInit.getInstance().mMTOPDoman);
        mtopBuilder.addListener(new OnFinishListener());
        mtopBuilder.setRequestProrocolVersion(MtopProtocolVersion.V6);
        mtopBuilder.setContentType(MtopContentType.URL_ENCODE);
        mtopBuilder.reqMethod(MethodEnum.GET);

        mtopBuilder.addHttpQueryParameter("testBool", "true");
        mtopBuilder.addHttpQueryParameter("testInteger", "2");
        mtopBuilder.addHttpQueryParameter("testBoolean", "false");
        mtopBuilder.addHttpQueryParameter("testDoub", "1.1");
        mtopBuilder.addHttpQueryParameter("testStr", "test str");
        mtopBuilder.addHttpQueryParameter("testInt", "1");
        mtopBuilder.addHttpQueryParameter("testDouble", "1.2");
        mtopBuilder.addHttpQueryParameter("test", "xxxx");
    }

    private void initView() {
        consoleTv = findViewById(R.id.activity_mtop_tv_console);
        configBtn = findViewById(R.id.activity_mtop_btn_config);
        sendBtn = findViewById(R.id.activity_mtop_btn_send);
    }

    private void initDialogView() {
        dialogContentView = LayoutInflater.from(this).inflate(R.layout.dialog_mtop_config, null);
        paramsContainerLayout = dialogContentView.findViewById(R.id.dialog_mtop_config_ll_param_container);
        paramsAddBtn = dialogContentView.findViewById(R.id.dialog_mtop_config_btn_add_param);
        paramsRemoveBtn = dialogContentView.findViewById(R.id.dialog_mtop_config_remove_param);
        cancelBtn = dialogContentView.findViewById(R.id.dialog_mtop_config_btn_cancel);
        confirmBtn = dialogContentView.findViewById(R.id.dialog_mtop_config_btn_confirm);
        apiNameEt = dialogContentView.findViewById(R.id.dialog_mtop_config_et_api_name);
        apiVersionEt = dialogContentView.findViewById(R.id.dialog_mtop_config_et_api_version);
        domainEt = dialogContentView.findViewById(R.id.dialog_mtop_config_et_domain);
        postRadioBtn = dialogContentView.findViewById(R.id.dialog_mtop_config_rb_post);
        urlEncodeRadioBtn = dialogContentView.findViewById(R.id.dialog_mtop_config_rb_url_encode);
        jsonRadioBtn = dialogContentView.findViewById(R.id.dialog_mtop_config_rb_json);
        dataEt = dialogContentView.findViewById(R.id.dialog_mtop_config_data);

        jsonRadioBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataEt.setVisibility(View.VISIBLE);
                } else {
                    dataEt.setVisibility(View.GONE);
                }
            }
        });

        apiNameEt.setText(api);
        apiVersionEt.setText(v);
        domainEt.setText(EmasInit.getInstance().mMTOPDoman);
        queryMap.clear();

        for(Entry<String, Object> entry: dataMap.entrySet()){//for循环迭代
            View view = LayoutInflater.from(MtopActivity.this).inflate(R.layout.view_mtop_param, null);
            Spinner valueTypeSpinner = view.findViewById(R.id.view_mtop_param_sp_value_type);
            EditText valueEt = view.findViewById(R.id.view_mtop_param_et_value);
            EditText keyEt = view.findViewById(R.id.view_mtop_param_et_key);
            RadioButton trueRadioBtn = view.findViewById(R.id.view_mtop_param_rb_true);
            RadioButton falseRadioBtn = view.findViewById(R.id.view_mtop_param_rb_false);
            RadioGroup radioGroup = view.findViewById(R.id.view_mtop_param_rg);

            valueTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ENTRIES));


            keyEt.setText(entry.getKey());
            Object object = entry.getValue();

            if (object instanceof String) {
                valueTypeSpinner.setSelection(0);
                valueEt.setInputType(InputType.TYPE_CLASS_TEXT);
                radioGroup.setVisibility(View.GONE);
                valueEt.setVisibility(View.VISIBLE);
                valueEt.setText(object.toString());
            } else if (object instanceof Integer) {
                valueTypeSpinner.setSelection(1);
                valueEt.setInputType(InputType.TYPE_CLASS_NUMBER);
                radioGroup.setVisibility(View.GONE);
                valueEt.setVisibility(View.VISIBLE);
                valueEt.setText(object.toString());
            } else if (object instanceof Boolean){
                valueTypeSpinner.setSelection(2);
                radioGroup.setVisibility(View.VISIBLE);
                valueEt.setVisibility(View.GONE);

                if ((Boolean) object) {
                    trueRadioBtn.setChecked(true);
                } else {
                    falseRadioBtn.setChecked(true);
                }
            } else if (object instanceof Float || object instanceof Double) {
                valueTypeSpinner.setSelection(3);
                valueEt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                radioGroup.setVisibility(View.GONE);
                valueEt.setVisibility(View.VISIBLE);
                valueEt.setText(object.toString());
            }

            paramsViewList.add(view);
            paramsContainerLayout.addView(view);
        }
    }

    private void setListener() {
        configBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputDialog == null) {
                    AlertDialog.Builder builder =
                        new AlertDialog.Builder(MtopActivity.this);
                    builder.setTitle("Mtop请求自定义").setView(dialogContentView);
                    inputDialog = builder.show();
                } else {
                    inputDialog.show();
                }
            }
        });

        sendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                consoleTv.setText("");
                mtopBuilder.asyncRequest();
            }
        });
    }

    private void setDialogListener() {
        paramsAddBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(MtopActivity.this).inflate(R.layout.view_mtop_param, null);
                Spinner spinner = view.findViewById(R.id.view_mtop_param_sp_value_type);
                final EditText editTextValue = view.findViewById(R.id.view_mtop_param_et_value);
                final RadioGroup radioGroup = view.findViewById(R.id.view_mtop_param_rg);
                spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                editTextValue.setInputType(InputType.TYPE_CLASS_TEXT);
                                radioGroup.setVisibility(View.GONE);
                                editTextValue.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                editTextValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                                radioGroup.setVisibility(View.GONE);
                                editTextValue.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                radioGroup.setVisibility(View.VISIBLE);
                                editTextValue.setVisibility(View.GONE);
                                break;
                            case 3:
                                editTextValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                radioGroup.setVisibility(View.GONE);
                                editTextValue.setVisibility(View.VISIBLE);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                paramsViewList.add(view);
                paramsContainerLayout.addView(view);
            }
        });

        paramsRemoveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paramsViewList.size() != 0) {
                    View view = paramsViewList.remove(paramsViewList.size() - 1);
                    paramsContainerLayout.removeView(view);
                }
            }
        });

        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.cancel();
            }
        });

        confirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MtopRequest mtopRequest = new MtopRequest();
                mtopRequest.setApiName(apiNameEt.getText().toString());
                mtopRequest.setVersion(apiVersionEt.getText().toString());

                if (urlEncodeRadioBtn.isChecked()) {
                    JSONObject jsonObject = new JSONObject();
                    for (View view : paramsViewList) {
                        Spinner valueTypeSpinner = view.findViewById(R.id.view_mtop_param_sp_value_type);
                        EditText valueEt = view.findViewById(R.id.view_mtop_param_et_value);
                        EditText keyEt = view.findViewById(R.id.view_mtop_param_et_key);
                        RadioButton trueRadioBtn = view.findViewById(R.id.view_mtop_param_rb_true);

                        if (!TextUtils.isEmpty(keyEt.getText().toString().trim())) {
                            int index = valueTypeSpinner.getSelectedItemPosition();
                            switch (index) {
                                case 0:
                                    if (!TextUtils.isEmpty(valueEt.getText().toString().trim())) {
                                        jsonObject.put(keyEt.getText().toString().trim(),
                                            valueEt.getText().toString().trim());
                                    }
                                    break;
                                case 1:
                                    if (!TextUtils.isEmpty(valueEt.getText().toString().trim())) {
                                        jsonObject.put(keyEt.getText().toString().trim(),
                                            Integer.parseInt(valueEt.getText().toString().trim()));
                                    }
                                    break;
                                case 2:
                                    jsonObject.put(keyEt.getText().toString().trim(), trueRadioBtn.isChecked());
                                    break;
                                case 3:
                                    if (!TextUtils.isEmpty(valueEt.getText().toString().trim())) {
                                        jsonObject.put(keyEt.getText().toString().trim(),
                                            Float.parseFloat(valueEt.getText().toString().trim()));
                                    }
                                    break;
                            }
                        }
                    }
                    mtopRequest.setData(jsonObject.toJSONString());
                    data = jsonObject.toJSONString();
                    mtopBuilder = mtopInstance.build(mtopRequest, "600000@taobao_android_7.7.3");
                } else {
                    mtopRequest.setData(dataEt.getText().toString());
                    mtopBuilder = mtopInstance.build(mtopRequest, "600000@taobao_android_7.7.3");
                    data = dataEt.getText().toString();
                    mtopBuilder.setContentType(MtopContentType.JSON);
                    for (View view : paramsViewList) {
                        Spinner valueTypeSpinner = view.findViewById(R.id.view_mtop_param_sp_value_type);
                        EditText valueEt = view.findViewById(R.id.view_mtop_param_et_value);
                        EditText keyEt = view.findViewById(R.id.view_mtop_param_et_key);
                        RadioButton trueRadioBtn = view.findViewById(R.id.view_mtop_param_rb_true);

                        if (!TextUtils.isEmpty(keyEt.getText().toString().trim())) {
                            int index = valueTypeSpinner.getSelectedItemPosition();
                            switch (index) {
                                case 0:
                                    if (!TextUtils.isEmpty(valueEt.getText().toString().trim())) {
                                        queryMap.put(keyEt.getText().toString().trim(),valueEt.getText().toString().trim());
                                        mtopBuilder.addHttpQueryParameter(keyEt.getText().toString().trim(),valueEt.getText().toString().trim());
                                    }
                                    break;
                                case 1:
                                    if (!TextUtils.isEmpty(valueEt.getText().toString().trim())) {
                                        queryMap.put(keyEt.getText().toString().trim(),valueEt.getText().toString().trim());
                                        mtopBuilder.addHttpQueryParameter(keyEt.getText().toString().trim(),valueEt.getText().toString().trim());
                                    }
                                    break;
                                case 2:
                                    queryMap.put(keyEt.getText().toString().trim(), String.valueOf(trueRadioBtn.isChecked()));
                                    mtopBuilder.addHttpQueryParameter(keyEt.getText().toString().trim(), String.valueOf(trueRadioBtn.isChecked()));
                                    break;
                                case 3:
                                    if (!TextUtils.isEmpty(valueEt.getText().toString().trim())) {
                                        queryMap.put(keyEt.getText().toString().trim(),String.valueOf(Float.parseFloat(valueEt.getText().toString().trim())));
                                        mtopBuilder.addHttpQueryParameter(keyEt.getText().toString().trim(),String.valueOf(Float.parseFloat(valueEt.getText().toString().trim())));
                                    }
                                    break;
                            }
                        }
                    }
                }

                mtopBuilder.reqMethod(postRadioBtn.isChecked() ? MethodEnum.POST : MethodEnum.GET);
                mtopBuilder.setCustomDomain(domainEt.getText().toString());
                mtopBuilder.addListener(new OnFinishListener());
                mtopBuilder.setRequestProrocolVersion(MtopProtocolVersion.V6);

                inputDialog.cancel();
            }
        });
    }

    private class OnFinishListener implements MtopFinishListener {

        public void onFinished(MtopFinishEvent mtopFinishEvent, Object o) {
            if (mtopFinishEvent.mtopResponse != null) {
                consoleLogBuilder = new StringBuilder();
                consoleLogBuilder.append("\t\tRequest: \n")
                    .append("\t\t\t\tdomain: ")
                    .append(TextUtils.isEmpty(domainEt.getText().toString().trim()) ? mtopInstance.getMtopConfig().mtopDomain.getDomain(
                        EnvModeEnum.ONLINE) + "\n" : domainEt.getText().toString().trim() + "\n")
                    .append("\t\t\t\tapiName: ")
                    .append(api + "\n")
                    .append("\t\t\t\tversion: ")
                    .append(v + "\n")
                    .append("\t\t\t\tmethod: ")
                    .append(mtopBuilder.getMtopContext$6e9e401a().d.method.getMethod() + "\n")
                    .append("\t\t\t\tdata: \n")
                    .append("\t\t\t\t" + JSONFormatUtil.formatJson(data) + "\n")
                    .append("\t\t\t\tqueryString: \n")
                    .append("\t\t\t\t" + (queryMap.size() == 0 ? "" : queryMap.toString()) + "\n")
                    .append("==================================\n");
                consoleLogBuilder.append("\t\tResponse: \n")
                    .append("\t\t\t\tretCode: ")
                    .append(mtopFinishEvent.mtopResponse.getRetCode() + "\n")
                    .append("\t\t\t\tretMsg: ")
                    .append(mtopFinishEvent.mtopResponse.getRetMsg() + "\n")
                    .append("\t\t\t\tret: ")
                    .append(Arrays.toString(mtopFinishEvent.mtopResponse.getRet()) + "\n")
                    .append("\t\t\t\tmappingCode: ")
                    .append(mtopFinishEvent.mtopResponse.getMappingCode() + "\n")
                    .append("\t\t\t\tresponseCode: ")
                    .append(mtopFinishEvent.mtopResponse.getResponseCode() + "\n")
                    .append("\t\t\t\theaderFields: \n")
                    .append(mtopFinishEvent.mtopResponse.getHeaderFields() == null ? "\t\t\t\tnull\n" : "\t\t\t\t" + JSONFormatUtil.formatJson(mtopFinishEvent.mtopResponse.getHeaderFields().toString()) + "\n")
                    .append("\t\t\t\tdata: \n")
                    .append(mtopFinishEvent.getMtopResponse().getDataJsonObject() == null ? "\t\t\t\t"+  mtopFinishEvent.getMtopResponse().getDataBody() +"\n" : "\t\t\t\t" + JSONFormatUtil.formatJson(mtopFinishEvent.mtopResponse.getDataJsonObject().toString()) + "\n")
                    .append("==================================");
                consoleTv.post(new Runnable() {
                    @Override
                    public void run() {
                        consoleTv.setText(consoleLogBuilder.toString());
                    }
                });
            }
        }
    }

}
