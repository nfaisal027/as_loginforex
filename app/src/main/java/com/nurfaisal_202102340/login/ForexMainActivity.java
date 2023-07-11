package com.nurfaisal_202102340.login;


import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class ForexMainActivity extends AppCompatActivity {

    private ProgressBar loadingProgressBar;
    private SwipeRefreshLayout swipeRefreshLayout1;
    private TextView bifTextView, bmdTextView, bndTextView, bobTextView, brlTextView, idrTextView,  btcTextView, btnTextView, bwpTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forex_main);

        swipeRefreshLayout1 = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout1);
        bifTextView = (TextView) findViewById(R.id.bifTextView);
        bmdTextView = (TextView) findViewById(R.id.bmdTextView);
        bndTextView = (TextView) findViewById(R.id.bndTextView);
        bobTextView = (TextView) findViewById(R.id.bobTextView);
        brlTextView = (TextView) findViewById(R.id.brlTextView);
        idrTextView = (TextView) findViewById(R.id.idrTextView);
        btcTextView = (TextView) findViewById(R.id.btcTextView);
        btnTextView = (TextView) findViewById(R.id.btnTextView);
        bwpTextView = (TextView) findViewById(R.id.bwpTextView);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        initSwipeRefreshLayout();
        initForex();
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout1.setOnRefreshListener(() -> {
            initForex();

            swipeRefreshLayout1.setRefreshing(false);
        });
    }

    public String formatNumber(double number, String format){
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);
    }

    private void initForex() {
        loadingProgressBar.setVisibility(TextView.VISIBLE);

        String url = "https://openexchangerates.org/api/latest.json?app_id=6db110092c224a9d8432b0d037f63d05";

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                ForexRootModel rootModel = gson.fromJson(new String(responseBody), ForexRootModel.class);
                ForexRatesModel ratesModel = rootModel.getRatesModel();

                double bif = ratesModel.getIDR() / ratesModel.getBIF();
                double bmd = ratesModel.getIDR() / ratesModel.getBMD();
                double bnd = ratesModel.getIDR() / ratesModel.getBDN();
                double bob = ratesModel.getIDR() / ratesModel.getBOB();
                double brl = ratesModel.getIDR() / ratesModel.getBRL();
                double btc = ratesModel.getIDR() / ratesModel.getBTC();
                double btn = ratesModel.getIDR() / ratesModel.getBTN();
                double bwp = ratesModel.getIDR() / ratesModel.getBWP();
                double idr = ratesModel.getIDR();

                bifTextView.setText(formatNumber(bif, "###,##0.##"));
                bmdTextView.setText(formatNumber(bmd, "###,##0.##"));
                bndTextView.setText(formatNumber(bnd, "###,##0.##"));
                bobTextView.setText(formatNumber(bob, "###,##0.##"));
                brlTextView.setText(formatNumber(brl, "###,##0.##"));
                idrTextView.setText(formatNumber(idr, "###,##0.##"));
                btcTextView.setText(formatNumber(btc, "###,##0.##"));
                btnTextView.setText(formatNumber(btn, "###,##0.##"));
                bwpTextView.setText(formatNumber(bwp, "###,##0.##"));

                loadingProgressBar.setVisibility(TextView.INVISIBLE);
            }

            @Override
            public
            void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                loadingProgressBar.setVisibility(TextView.INVISIBLE);
            }
        });
    }
}