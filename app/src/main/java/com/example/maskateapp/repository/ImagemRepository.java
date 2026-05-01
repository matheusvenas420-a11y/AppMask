package com.example.maskateapp.repository;

import android.graphics.Bitmap;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ImagemRepository {

    public String upload(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            Map config = new HashMap();
            config.put("cloud_name", "dr99lumlh");
            config.put("api_key", "176164324272929");
            config.put("api_secret", "gfJJQF7CmEQzRxYqnOdf38M8VN0");

            Cloudinary cloudinary = new Cloudinary(config);

            Map upload = cloudinary.uploader().upload(data, ObjectUtils.emptyMap());
            return upload.get("url").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
