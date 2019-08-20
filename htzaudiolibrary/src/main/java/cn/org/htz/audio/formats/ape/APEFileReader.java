package cn.org.htz.audio.formats.ape;


import android.util.Log;

import cn.org.htz.audio.AudioFileReader;
import cn.org.htz.audio.TrackInfo;

import java.io.File;

import davaguine.jmac.info.APEFileInfo;
import davaguine.jmac.info.APEHeader;
import davaguine.jmac.tools.RandomAccessFile;

public class APEFileReader
        extends AudioFileReader {
    protected TrackInfo readSingle(TrackInfo trackInfo) {
        try {
            RandomAccessFile ras = new RandomAccessFile(new File(
                    trackInfo.getFilePath()), "r");
            APEHeader header = new APEHeader(ras);
            APEFileInfo fileInfo = new APEFileInfo();
            header.Analyze(fileInfo);
            parseInfo(trackInfo, fileInfo);

            ras.close();
            return trackInfo;
        } catch (Exception e) {
            Log.e("APEFileReader", e.toString());
        }
        return null;
    }

    private void parseInfo(TrackInfo trackInfo, APEFileInfo fileInfo) {
        trackInfo.setChannels(fileInfo.nChannels);
        int frameSize = trackInfo.getChannels() *
                2;
        trackInfo.setFrameSize(frameSize);
        trackInfo.setSampleRate(fileInfo.nSampleRate);
        trackInfo.setTotalSamples(fileInfo.nTotalBlocks);
        trackInfo.setStartPosition(0L);
        trackInfo.setCodec("Monkey's Audio");
        trackInfo.setBitrate(fileInfo.nAverageBitrate);
    }

    public boolean isFileSupported(String ext) {
        return ext.equalsIgnoreCase("ape");
    }

    @Override
    public String getSupportFileExt() {
        return "ape";
    }
}
