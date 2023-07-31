package com.hungnv28.quanlysanpham.utils;

import android.app.Activity;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CloudinaryUtils {
    private static final HashMap<String, String> config = new HashMap<>();

    public static void init(Activity activity) {
        config.put("cloud_name", "hungnguyen2809");
        config.put("api_key", "876575444377316");
        config.put("api_secret", "kLfV9lEgG_WmTiyfVZ1WHp2apYA");
        MediaManager.init(activity, config);
    }

    public static void upload(Activity activity, String filePath, UploadCallback callback) {
        if (config.isEmpty()) {
            Toast.makeText(activity, "Vui lòng khởi tạo Cloudinary", Toast.LENGTH_SHORT).show();
        } else if (filePath == null || filePath.isEmpty()) {
            Toast.makeText(activity, "Tệp tin không hợp lệ", Toast.LENGTH_SHORT).show();
        } else {
            MediaManager.get().upload(filePath).callback(callback).dispatch(activity);
        }
    }

    public interface UploadCallbackCloudinary {
        void onStart(String requestId);

        void onError(String requestId, String error);

        void onSuccess(String requestId, String url);
    }

    public static void upload(Activity activity, String filePath, CloudinaryUtils.UploadCallbackCloudinary callback) {
        if (config.isEmpty()) {
            Toast.makeText(activity, "Vui lòng khởi tạo Cloudinary", Toast.LENGTH_SHORT).show();
        } else if (filePath == null || filePath.isEmpty()) {
            Toast.makeText(activity, "Tệp tin không hợp lệ", Toast.LENGTH_SHORT).show();
        } else {
            MediaManager.get().upload(filePath).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    callback.onStart(requestId);
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    callback.onSuccess(requestId, Objects.requireNonNull(resultData.get("url")).toString());
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    callback.onError(requestId, error.getDescription());
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                }
            }).dispatch(activity);
        }
    }
}
