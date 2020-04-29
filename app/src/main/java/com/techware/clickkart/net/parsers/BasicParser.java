package com.techware.clickkart.net.parsers;


import com.techware.clickkart.app.App;
import com.techware.clickkart.model.BasicBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BasicParser {

    public BasicBean parseBasicResponse(String wsResponseString) {

        BasicBean basicBean = new BasicBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            basicBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                basicBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                basicBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        basicBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        basicBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        basicBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        basicBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    basicBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("notfound"))
                    basicBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    basicBean.setErrorMsg("Password Is Incorrect");
            }
            try {
                if (jsonObj.has("message")) {
                    basicBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {

                        if (dataObj.has("is_available")) {
                            basicBean.setPhoneAvailable(dataObj.optBoolean("is_available"));
                        }
                        if (dataObj.has("phone")) {
                            basicBean.setPhone(dataObj.optString("phone"));
                        }
                        if (dataObj.has("type")) {
                            basicBean.setPhotoType(dataObj.optInt("type"));
                        }
                        if (dataObj.has("photo_url")) {
                            basicBean.setPhotoUrl(App.getImagePath(App.removePublicFromPath(dataObj.optString("photo_url"))));
                        }
                        if (dataObj.has("url")) {
                            basicBean.setUrl(App.getImagePath(App.removePublicFromPath(dataObj.optString("url"))));
                        }
                        if (dataObj.has("file_url")) {
                            basicBean.setUrl(App.getImagePath(App.removePublicFromPath(dataObj.optString("file_url"))));
                        }
                        if (dataObj.has("edit")) {
                            JSONObject userObj = dataObj.optJSONObject("edit");

                            if (userObj != null) {

                             /*   if (userObj.has("driver_license")) {
                                    basicBean.setDriverLicense(userObj.optString("driver_license"));
                                }*/
                            }
                        }
                        if (dataObj.has("photos")) {
                           /* JSONArray photoArray = dataObj.optJSONArray("photos");
                            ArrayList<PhotoBean> list = new ArrayList<>();

                            for (int i = 0; i < photoArray.length(); i++) {
                                JSONObject photoObj = photoArray.optJSONObject(i);
                                PhotoBean photoBean = new PhotoBean();

                                if (photoObj.has("id")) {
                                    photoBean.setId(photoObj.optString("id"));
                                }
                                if (photoObj.has("url")) {
                                    photoBean.setUrl(photoObj.optString("url"));
                                }
                                list.add(photoBean);
                            }
                            basicBean.setPhotos(list);*/

                        }

                        if (dataObj.has("videos")) {
                            JSONArray videoArray = dataObj.optJSONArray("videos");
                           /* ArrayList<VideoBean> list = new ArrayList<>();

                            for (int i = 0; i < videoArray.length(); i++) {
                                JSONObject videoObj = videoArray.optJSONObject(i);
                                VideoBean videoBean = new VideoBean();

                                if (videoObj.has("id")) {
                                    videoBean.setId(videoObj.optString("id"));
                                }
                                if (videoObj.has("url")) {
                                    videoBean.setUrl(videoObj.optString("url"));
                                }
                                list.add(videoBean);
                            }
                            basicBean.setVideos(list);*/

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return basicBean;
    }
}
