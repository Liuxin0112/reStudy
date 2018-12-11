package com.whtlkj.duku.utils;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Pcm2Wav {
    public static void main(String[] args) throws Exception {
        parse("C:\\Users\\HP\\Desktop\\sample\\msc1539863629009.pcm", "C:\\Users\\HP\\Desktop\\sample\\msc1539859720313.wav");
    }

    public static void parse(String source, String target) throws Exception {
        float sampleRate = 16000;       // 采样率(Hz)
        int sampleSizeInBits = 16;      // 比特率
        int channels = 1;               // 声道
        boolean signed = true;          // 是否签名
        boolean bigEndian = false;      // 单个样本的数据是否存在
        AudioFormat af = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        File sourceFile = new File(source);
        FileOutputStream out = new FileOutputStream(new File(target));
        AudioInputStream audioInputStream = new AudioInputStream(new FileInputStream(sourceFile), af, sourceFile.length());
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
        audioInputStream.close();
        out.flush();
        out.close();
    }

    public static FileOutputStream parseOutputStream(String source, String target) throws Exception {

        float sampleRate = 16000;       // 采样率(Hz)
        int sampleSizeInBits = 16;      // 比特率
        int channels = 1;               // 声道
        boolean signed = true;          // 是否签名
        boolean bigEndian = false;      // 单个样本的数据是否存在
        AudioFormat af = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        File sourceFile = new File(source);
        FileOutputStream out = new FileOutputStream(new File(target));
        AudioInputStream audioInputStream = new AudioInputStream(new FileInputStream(sourceFile), af, sourceFile.length());
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
        audioInputStream.close();
        out.flush();
        out.close();
        return out;
    }
}
