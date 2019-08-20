package cn.org.htz.audio.formats.flac;


import android.util.Log;

import cn.org.htz.audio.AudioFileReader;
import cn.org.htz.audio.TrackInfo;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.generic.GenericAudioHeader;

public class FLACFileReader
        extends AudioFileReader {
    public TrackInfo readSingle(TrackInfo trackInfo) {
        try {
            FlacFileReader reader = new FlacFileReader();
            AudioFile af1 = reader.read(trackInfo.getFile());

            GenericAudioHeader audioHeader = (GenericAudioHeader) af1
                    .getAudioHeader();
            copyHeaderFields(audioHeader, trackInfo);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("FLACFileReader", e.toString());
        }
        return trackInfo;
    }

    public boolean isFileSupported(String ext) {
        return ext.equalsIgnoreCase("flac");
    }

    @Override
    public String getSupportFileExt() {
        return "flac";
    }
}
