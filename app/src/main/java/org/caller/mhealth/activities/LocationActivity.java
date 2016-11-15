package org.caller.mhealth.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;

import org.caller.mhealth.R;

import java.util.List;

public class LocationActivity extends AppCompatActivity implements BDLocationListener, OnGetPoiSearchResultListener, BaiduMap.OnMarkerClickListener {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private PoiSearch mPoiSearch;
    private LocationClient mLocationClient;
    private Marker mSelfMarker;
    private int load_Index = 0;
    private LatLng mPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_location);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        initLocationClient();
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mBaiduMap.setOnMarkerClickListener(this);
    }

    private void initLocationClient() {
        mLocationClient = new LocationClient(this);
        LocationClientOption options = new LocationClientOption();
        options.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        options.setScanSpan(5000);
        options.setCoorType("bd09ll");
        options.setOpenGps(true);
        options.setIsNeedAddress(true);
        mLocationClient.setLocOption(options);
        mLocationClient.registerLocationListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        mLocationClient.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        mLocationClient.stopIndoorMode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mLocationClient.unRegisterLocationListener(this);
        mPoiSearch.destroy();
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        List<PoiInfo> allPoi = result.getAllPoi();
        if (allPoi != null) {
            int index = 1;
            for (PoiInfo poiInfo : allPoi
                    ) {
                LatLng location = poiInfo.location;
                MarkerOptions options = new MarkerOptions();
                options.position(location);
                BitmapDescriptor descriptor = BitmapDescriptorFactory.fromAsset("Icon_mark" + index + ".png");
                options.icon(descriptor);
                index++;
                options.title(poiInfo.name);
                Bundle bundle = new Bundle();
                bundle.putParcelable("poiInfo", poiInfo);
                options.extraInfo(bundle);
                mBaiduMap.addOverlay(options);
                if (index == 5) {
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(location);
                    mBaiduMap.setMapStatus(update);
                }
            }
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult result) {

    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        int locType = location.getLocType();
        switch (locType) {
            case BDLocation.TypeGpsLocation:
            case BDLocation.TypeCacheLocation:
            case BDLocation.TypeNetWorkLocation:
            case BDLocation.TypeOffLineLocation:
                double latitude = location.getLatitude();
                double longtitude = location.getLongitude();
                mPoint = new LatLng(latitude, longtitude);

                MarkerOptions options = new MarkerOptions();
                Bundle bundle = new Bundle();
                bundle.putParcelable("poiInfo", null);
                options.extraInfo(bundle);
                options.position(mPoint);
                BitmapDescriptor descriptor = BitmapDescriptorFactory.fromAsset("Icon_start.png");
                options.icon(descriptor);
                mSelfMarker = ((Marker) mBaiduMap.addOverlay(options));

                break;
            default:
                String s = location.getLocationDescribe();
                Log.d("BaiduLoc", "locType" + s);
                break;
        }
        search();
    }

    public void search() {
        mBaiduMap.clear();
        PoiNearbySearchOption option1 = new PoiNearbySearchOption();
        option1.location(mPoint).keyword("运动场").pageCapacity(10).pageNum(load_Index).sortType(PoiSortType.distance_from_near_to_far).radius(50000);
        mPoiSearch.searchNearby(option1);
    }


    public void btnPrevious(View view) {
        if (load_Index >= 1) {
            load_Index--;
            search();
        }
    }

    public void btnNext(View view) {
        load_Index++;
        search();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final Bundle info = marker.getExtraInfo();
        PoiInfo poiInfo = null;
        if (info != null) {
            poiInfo = (PoiInfo) info.get("poiInfo");
        }
        if (poiInfo != null) {
            final String address = poiInfo.address;
            Snackbar.make(mMapView, address, Snackbar.LENGTH_LONG).
                    setAction("点击进入社区", new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(LocationActivity.this,CommentActivity.class);
                            intent.putExtra("address",address);
                            startActivity(intent);
                        }
                    }).
                    show();
        }
        return true;
    }
}

