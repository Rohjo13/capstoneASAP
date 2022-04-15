package co.mjc.capstoneasap.repository;

import java.util.Date;

import co.mjc.capstoneasap.dto.Camera;

public interface CameraRepository {
    // 이미지 저장
    void saveImage(Camera camera);
}
