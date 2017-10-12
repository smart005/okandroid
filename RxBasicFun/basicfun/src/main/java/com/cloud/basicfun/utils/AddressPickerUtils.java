package com.cloud.basicfun.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.cloud.basicfun.beans.AddressItem;
import com.cloud.basicfun.tasks.AddressInitTask;
import com.cloud.core.ObjectJudge;
import com.cloud.core.cache.RxCache;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.StorageUtils;
import com.cloud.resources.beans.City;
import com.cloud.resources.beans.County;
import com.cloud.resources.beans.Province;
import com.cloud.resources.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/11/6
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class AddressPickerUtils {

    private AddressInitTask addressInitTask = null;
    private boolean hideCounty = false;
    private LoadingDialog mloading = new LoadingDialog();
    private Activity activity = null;
    private AddressItem province = null;
    private AddressItem city = null;
    private AddressItem region = null;
    private List<AddressItem> dataList = null;
    private List<AddressItem> tempAddressItems = null;
    private List<Province> tempProvinces = null;
    private AddressItem tempOriginalDataProvince = null;
    private Province tempProvince = null;
    private boolean tempOriginalDataFlag = false;

    protected void onAddressPicked(AddressItem province, AddressItem city, AddressItem region) {

    }

    public void setHideCounty(boolean hideCounty) {
        this.hideCounty = hideCounty;
    }

    public void setDataList(List<AddressItem> dataList) {
        this.dataList = dataList;
    }

    public void show(Activity activity,
                     AddressItem province,
                     AddressItem city,
                     AddressItem region) {
        try {
            mloading.showDialog(activity, "正在初始化数据...", null);
            this.activity = activity;
            this.province = province;
            this.city = city;
            this.region = region;
            GlobalUtils.cancelTask(addressInitTask);
            addressInitTask = new AddressInitTask(activity, hideCounty) {
                @Override
                protected void onInitAddressComplected() {
                    mloading.dismiss();
                }

                @Override
                protected void onAddressSelected(Province province, City city, County county) {
                    onAddressPicked(new AddressItem(province.getAreaId(), province.getAreaName()),
                            new AddressItem(city.getAreaId(), city.getAreaName()),
                            new AddressItem(county.getAreaId(), county.getAreaName()));
                }
            };
            loadAddressData(new AddressCall<List<AddressItem>, List<Province>, Boolean>() {
                @Override
                public void call(List<AddressItem> addressItems, List<Province> provinces, Boolean isOriginalData) {
                    if (isOriginalData) {
                        addressInitTask.setAddressList(addressItems);
                        addressInitTask.setOriginalData(true);
                        addressInitTask.execute(AddressPickerUtils.this.province,
                                AddressPickerUtils.this.city,
                                AddressPickerUtils.this.region);
                    } else {
                        addressInitTask.setProvinces(provinces);
                        addressInitTask.setOriginalData(false);
                        addressInitTask.execute(AddressPickerUtils.this.province,
                                AddressPickerUtils.this.city,
                                AddressPickerUtils.this.region);
                    }
                }
            });
        } catch (Exception e) {
            Logger.L.error("show area panel error:", e);
        }
    }

    private interface AddressCall<T1, T2, T3> {
        public void call(T1 t1, T2 t2, T3 t3);
    }

    private void loadAddressData(AddressCall<List<AddressItem>, List<Province>, Boolean> addressCall) {
        if (ObjectJudge.isNullOrEmpty(dataList)) {
            String cacheArea = RxCache.getCacheData("area_cache_data_ret");
            if (tempProvinces == null && TextUtils.isEmpty(cacheArea)) {
                cacheArea = RxCache.getCacheData("area_cache_data");
                if (tempAddressItems == null && TextUtils.isEmpty(cacheArea)) {
                    String assetsFileContent = StorageUtils.readAssetsFileContent(activity, "allarea.rx");
                    if (!TextUtils.isEmpty(assetsFileContent)) {
                        List<AddressItem> addressItems = JsonUtils.parseArray(assetsFileContent, AddressItem.class);
                        if (!ObjectJudge.isNullOrEmpty(addressItems)) {
                            tempAddressItems = addressItems;
                            if (addressCall != null) {
                                addressCall.call(addressItems, null, true);
                            }
                        }
                        RxCache.setCacheData("area_cache_data", assetsFileContent);
                    }
                    mloading.dismiss();
                } else {
                    if (tempAddressItems == null) {
                        List<AddressItem> addressItems = JsonUtils.parseArray(cacheArea, AddressItem.class);
                        tempAddressItems = addressItems;
                        if (addressCall != null) {
                            addressCall.call(addressItems, null, true);
                        }
                    } else {
                        if (addressCall != null) {
                            addressCall.call(tempAddressItems, null, true);
                        }
                    }
                }
            } else {
                if (tempProvinces == null) {
                    List<Province> provinces = JsonUtils.parseArray(cacheArea, Province.class);
                    tempProvinces = provinces;
                    if (addressCall != null) {
                        addressCall.call(null, provinces, false);
                    }
                } else {
                    if (addressCall != null) {
                        addressCall.call(null, tempProvinces, false);
                    }
                }
            }
        } else {
            if (addressCall != null) {
                addressCall.call(dataList, null, true);
            }
            RxCache.setCacheData("area_cache_data", JsonUtils.toStr(dataList));
        }
    }

    public AddressItem getProvinceById(final String id) {
        if (tempOriginalDataProvince == null && tempProvince == null) {
            loadAddressData(new AddressCall<List<AddressItem>, List<Province>, Boolean>() {
                @Override
                public void call(List<AddressItem> addressItems, List<Province> provinces, Boolean isOriginalData) {
                    tempOriginalDataFlag = isOriginalData;
                    if (isOriginalData) {
                        if (ObjectJudge.isNullOrEmpty(addressItems)) {
                            return;
                        }
                        for (AddressItem addressItem : addressItems) {
                            if (TextUtils.equals(addressItem.getId(), id)) {
                                tempOriginalDataProvince = addressItem;
                                break;
                            }
                        }
                    } else {
                        if (ObjectJudge.isNullOrEmpty(provinces)) {
                            return;
                        }
                        for (Province provinceItem : provinces) {
                            if (TextUtils.equals(provinceItem.getAreaId(), id)) {
                                tempProvince = provinceItem;
                                tempOriginalDataProvince = new AddressItem(provinceItem.getAreaId(),
                                        provinceItem.getAreaName());
                                break;
                            }
                        }
                    }
                }
            });
        } else {
            if (!tempOriginalDataFlag && tempProvince != null) {
                tempOriginalDataProvince = new AddressItem(tempProvince.getAreaId(),
                        tempProvince.getAreaName());
            }
        }
        return tempOriginalDataProvince == null ?
                tempOriginalDataProvince = new AddressItem() :
                tempOriginalDataProvince;
    }

    public AddressItem getProvinceByName(final String name) {
        if (tempOriginalDataProvince == null && tempProvince == null) {
            loadAddressData(new AddressCall<List<AddressItem>, List<Province>, Boolean>() {
                @Override
                public void call(List<AddressItem> addressItems, List<Province> provinces, Boolean isOriginalData) {
                    tempOriginalDataFlag = isOriginalData;
                    if (isOriginalData) {
                        if (ObjectJudge.isNullOrEmpty(addressItems)) {
                            return;
                        }
                        for (AddressItem addressItem : addressItems) {
                            if (TextUtils.equals(addressItem.getId(), name)) {
                                tempOriginalDataProvince = addressItem;
                                break;
                            }
                        }
                    } else {
                        if (ObjectJudge.isNullOrEmpty(provinces)) {
                            return;
                        }
                        for (Province provinceItem : provinces) {
                            if (TextUtils.equals(provinceItem.getAreaId(), name)) {
                                tempProvince = provinceItem;
                                tempOriginalDataProvince = new AddressItem(provinceItem.getAreaId(),
                                        provinceItem.getAreaName());
                                break;
                            }
                        }
                    }
                }
            });
        } else {
            if (!tempOriginalDataFlag && tempProvince != null) {
                tempOriginalDataProvince = new AddressItem(tempProvince.getAreaId(),
                        tempProvince.getAreaName());
            }
        }
        return tempOriginalDataProvince == null ?
                tempOriginalDataProvince = new AddressItem() :
                tempOriginalDataProvince;
    }

    public AddressItem getCityById(String id) {
        AddressItem item = null;
        if (tempOriginalDataFlag) {
            if (tempOriginalDataProvince == null || ObjectJudge.isNullOrEmpty(tempOriginalDataProvince.getChildren())) {
                return new AddressItem();
            } else {
                for (AddressItem addressItem : tempOriginalDataProvince.getChildren()) {
                    if (TextUtils.equals(addressItem.getId(), id)) {
                        item = addressItem;
                        break;
                    }
                }
            }
        } else {
            if (tempProvince == null || ObjectJudge.isNullOrEmpty(tempProvince.getCities())) {
                return new AddressItem();
            } else {
                for (City city1 : tempProvince.getCities()) {
                    if (TextUtils.equals(city1.getAreaId(), id)) {
                        item = new AddressItem(city1.getAreaId(), city1.getAreaName());
                        break;
                    }
                }
            }
        }
        return item;
    }

    public AddressItem getCityByName(String name) {
        AddressItem item = null;
        if (tempOriginalDataFlag) {
            if (tempOriginalDataProvince == null || ObjectJudge.isNullOrEmpty(tempOriginalDataProvince.getChildren())) {
                return new AddressItem();
            } else {
                for (AddressItem addressItem : tempOriginalDataProvince.getChildren()) {
                    if (TextUtils.equals(addressItem.getName(), name)) {
                        item = addressItem;
                        break;
                    }
                }
            }
        } else {
            if (tempProvince == null || ObjectJudge.isNullOrEmpty(tempProvince.getCities())) {
                return new AddressItem();
            } else {
                for (City city1 : tempProvince.getCities()) {
                    if (TextUtils.equals(city1.getAreaName(), name)) {
                        item = new AddressItem(city1.getAreaId(), city1.getAreaName());
                        break;
                    }
                }
            }
        }
        return item;
    }
}
