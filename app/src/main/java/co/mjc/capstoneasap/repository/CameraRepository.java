package co.mjc.capstoneasap.repository;

import android.media.Image;

import java.util.Date;

import co.mjc.capstoneasap.dto.Camera;

public interface CameraRepository {
    // 사진 저장
    void saveImage(Camera camera);
    // 사진 불러오기
    Image getByImage(Image lecturePic);
    // 사진 삭제
    void deleteImage(Image lecturePic);
}
