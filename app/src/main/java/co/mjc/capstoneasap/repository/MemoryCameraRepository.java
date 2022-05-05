package co.mjc.capstoneasap.repository;

import android.media.Image;

import co.mjc.capstoneasap.dto.AllData;

@Deprecated
public class MemoryCameraRepository implements CameraRepository{

    @Override
    public void saveImage(AllData allData) {

    }

    @Override
    public Image getByImage(Image lecturePic) {
        return null;
    }

    @Override
    public void deleteImage(Image lecturePic) {

    }
}
