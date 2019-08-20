package cn.org.htz.audio.utils;
import cn.org.htz.audio.AudioFileReader;
import cn.org.htz.audio.formats.ape.APEFileReader;
import cn.org.htz.audio.formats.flac.FLACFileReader;
import cn.org.htz.audio.formats.mp3.MP3FileReader;
import cn.org.htz.audio.formats.ogg.OGGFileReader;
import cn.org.htz.audio.formats.wav.WAVFileReader;
import cn.org.htz.audio.formats.wv.WVFileReader;

import java.util.ArrayList;
import java.util.List;

public class AudioUtil {
    private static ArrayList<AudioFileReader> readers = new ArrayList<AudioFileReader>();


    static {
        readers.add(new MP3FileReader());
        readers.add(new APEFileReader());
        readers.add(new FLACFileReader());
        readers.add(new WAVFileReader());
        readers.add(new OGGFileReader());
        readers.add(new WVFileReader());
    }

    public static AudioFileReader getAudioFileReaderByFilePath(String filePath) {
        String ext = getFileExt(filePath);
        for (AudioFileReader reader : readers) {
            if (reader.isFileSupported(ext)) {
                return reader;
            }
        }
        return null;
    }

    public static AudioFileReader getAudioFileReaderByFileExt(String fileExt) {
        fileExt = fileExt.toLowerCase();
        for (AudioFileReader reader : readers) {
            if (reader.isFileSupported(fileExt)) {
                return reader;
            }
        }
        return null;
    }


    private static String getFileExt(String filePath) {
        int pos = filePath.lastIndexOf(".");
        if (pos == -1) {
            return "";
        }
        return filePath.substring(pos + 1).toLowerCase();
    }

    /**
     * 获取支持的音频文件格式
     *
     * @return
     */
    public static List<String> getSupportAudioExts() {
        List<String> audioExts = new ArrayList<String>();
        for (AudioFileReader audioFileReader : readers) {
            audioExts.add(audioFileReader.getSupportFileExt());
        }
        return audioExts;
    }
}
