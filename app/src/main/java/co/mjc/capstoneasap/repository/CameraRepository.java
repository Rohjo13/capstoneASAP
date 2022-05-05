package co.mjc.capstoneasap.repository;

import android.media.Image;

import co.mjc.capstoneasap.dto.AllData;

@Deprecated
public interface CameraRepository {
    // 사진 저장
    void saveImage(AllData allData);
    // 사진 불러오기
    Image getByImage(Image lecturePic);
    // 사진 삭제
    void deleteImage(Image lecturePic);
}
