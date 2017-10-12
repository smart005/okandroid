package com.cloud.basicfun.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.cloud.basicfun.beans.AddressItem;
import com.cloud.core.ObjectJudge;
import com.cloud.core.cache.RxCache;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.JsonUtils;
import com.cloud.resources.beans.City;
import com.cloud.resources.beans.County;
import com.cloud.resources.beans.Province;
import com.cloud.resources.picker.AddressPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取地址数据并显示地址选择器
 *
 * @author 李玉江[QQ:1032694760]
 * @since 2015/12/15
 */
public class AddressInitTask extends AsyncTask<AddressItem, Void, List<Province>> {

    private Activity activity;
    private AddressItem province = null;
    private AddressItem city = null;
    private AddressItem region = null;
    private boolean hideCounty = false;
    private List<AddressItem> addressItems = null;
    private List<Province> provinces = null;
    private boolean isOriginalData = true;

    public void setAddressList(List<AddressItem> addressItems) {
        this.addressItems = addressItems;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public void setOriginalData(boolean isOriginalData) {
        this.isOriginalData = isOriginalData;
    }

    protected void onAddressSelected(Province province, City city, County county) {

    }

    protected void onInitAddressComplected() {

    }

    /**
     * 初始化为不显示区县的模式
     *
     * @param activity
     * @param hideCounty is hide County
     */
    public AddressInitTask(Activity activity, boolean hideCounty) {
        this.activity = activity;
        this.hideCounty = hideCounty;
    }

    public AddressInitTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Province> doInBackground(AddressItem... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    province = params[0];
                    break;
                case 2:
                    province = params[0];
                    city = params[1];
                    break;
                case 3:
                    province = params[0];
                    city = params[1];
                    region = params[2];
                    break;
                default:
                    break;
            }
        }
        List<Province> data = new ArrayList<Province>();
        try {
            if (isOriginalData) {
                if (!ObjectJudge.isNullOrEmpty(addressItems)) {
                    for (AddressItem addressItem : addressItems) {
                        Province province = new Province();
                        province.setAreaId(addressItem.getId());
                        province.setAreaName(addressItem.getName());
                        if (!ObjectJudge.isNullOrEmpty(addressItem.getChildren())) {
                            for (AddressItem cityItem : addressItem.getChildren()) {
                                City city = new City();
                                city.setAreaId(cityItem.getId());
                                city.setAreaName(cityItem.getName());
                                if (!ObjectJudge.isNullOrEmpty(cityItem.getChildren())) {
                                    for (AddressItem regionItem : cityItem.getChildren()) {
                                        County county = new County();
                                        county.setAreaId(regionItem.getId());
                                        county.setAreaName(regionItem.getName());
                                        city.getCounties().add(county);
                                    }
                                }
                                province.getCities().add(city);
                            }
                        }
                        data.add(province);
                    }
                    RxCache.setCacheData("area_cache_data_ret", JsonUtils.toStr(data));
                }
            } else {
                data = provinces;
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return data;
    }

    @Override
    protected void onPostExecute(List<Province> result) {
        if (!ObjectJudge.isNullOrEmpty(result)) {
            AddressPicker picker = new AddressPicker(activity, result);
            picker.setHideCounty(hideCounty);
            if (hideCounty) {
                picker.setColumnWeight(1 / 3.0, 2 / 3.0);//将屏幕分为3份，省级和地级的比例为1:2
            } else {
                picker.setColumnWeight(2 / 8.0, 3 / 8.0, 3 / 8.0);//省级、地级和县级的比例为2:3:3
            }
            picker.setSelectedItem(province.getName(), city.getName(), region.getName());
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    onAddressSelected(province, city, county);
                }
            });
            picker.show();
        }
        onInitAddressComplected();
    }
}
