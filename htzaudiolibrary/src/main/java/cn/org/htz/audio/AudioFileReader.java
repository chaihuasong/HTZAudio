package cn.org.htz.audio;


import cn.org.htz.audio.utils.AudioMathUtil;
import cn.org.htz.audio.utils.MediaUtil;

import org.jaudiotagger.audio.generic.GenericAudioHeader;

import java.io.File;

public abstract class AudioFileReader {
    public TrackInfo read(File file) {
        TrackInfo trackInfo = new TrackInfo();
        trackInfo.setFileSize(file.length());
        trackInfo.setFileSizeStr(MediaUtil.getFileSize(file.length()));
        String filePath = file.getPath();
        trackInfo.setFilePath(filePath);
        trackInfo.setFileExt(MediaUtil.getFileExt(filePath));
        return reload(trackInfo);
    }

    private TrackInfo reload(TrackInfo trackInfo) {
        TrackInfo res = readSingle(trackInfo);

        double totalMS = AudioMathUtil.samplesToMillis(
                trackInfo.getTotalSamples(), trackInfo.getSampleRate());
        long duration = Math.round(totalMS);

        trackInfo.setDuration(duration);

        String durationStr = MediaUtil.formatTime((int) duration);
        trackInfo.setDurationStr(durationStr);

        return res;
    }

    protected void copyHeaderFields(GenericAudioHeader header, TrackInfo trackInfo) {
        if ((header != null) && (trackInfo != null)) {
            trackInfo.setChannels(header.getChannelNumber());
            int frameSize = trackInfo.getChannels() *
                    2;
            trackInfo.setFrameSize(frameSize);
            trackInfo.setTotalSamples(header.getTotalSamples().longValue());
            trackInfo.setSampleRate(header.getSampleRateAsNumber());
            trackInfo.setStartPosition(0L);
            trackInfo.setCodec(header.getFormat());
            trackInfo.setBitrate((int) header.getBitRateAsNumber());
        }
    }

    protected abstract TrackInfo readSingle(TrackInfo paramTrackInfo);

    public abstract boolean isFileSupported(String ext);

    /**
     * 获取支持的文件后缀名
     *
     * @return
     */
    public abstract String getSupportFileExt();
}
