package cn.org.htz.audio.formats.wav;


import android.util.Log;

import cn.org.htz.audio.AudioFileReader;
import cn.org.htz.audio.TrackInfo;
import cn.org.htz.audio.utils.AudioMathUtil;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.audio.wav.WavFileReader;

public class WAVFileReader
        extends AudioFileReader {
    protected TrackInfo readSingle(TrackInfo trackInfo) {
        try {
            WavFileReader reader = new WavFileReader();
            AudioFile af1 = reader.read(trackInfo.getFile());
            GenericAudioHeader audioHeader = (GenericAudioHeader) af1
                    .getAudioHeader();
            //
            long millis = (long) (audioHeader.getPreciseLength() * 1000);
            long totalSamples = AudioMathUtil.millisToSamples(millis, audioHeader.getSampleRateAsNumber());
            audioHeader.setTotalSamples(totalSamples);
            copyHeaderFields(audioHeader, trackInfo);
        } catch (Exception e) {
            Log.e("WAVFileReader", e.toString());
        }
        return trackInfo;
    }

    public boolean isFileSupported(String ext) {
        return ext.equalsIgnoreCase("wav");
    }

    @Override
    public String getSupportFileExt() {
        return "wav";
    }

}
